
package org.usfirst.frc.team279.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team279.robot.commands.*;
import org.usfirst.frc.team279.robot.subsystems.*;
import org.usfirst.frc.team279.util.Config;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	private String prefPrefix = "robot_";
	
	private static AHRS ahrs = null;
	public static AHRS getAhrs(){
		if(ahrs == null) {
			try {
		          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
		          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
		          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
		          Robot.ahrs = new AHRS(SPI.Port.kMXP); 
		      } catch (RuntimeException ex ) {
		          DriverStation.reportError("Robot: Error instantiating navX-MXP:  " + ex.getMessage(), true);
		      }
		}
		return ahrs;
	}
	
	
	private double ahrsGyroAdjustment = 0.0;
	public double getAhrsGyroAdjustment(){
		return ahrsGyroAdjustment;
	}
	
	//--------------------------------------------------------------------------
	
	public static final MecanumDrive mecanumDrive = new MecanumDrive();
	public static final Shooter shooter = new Shooter();
	public static final Ultrasonics ultrasonics = new Ultrasonics();
	public static final GearGizmo geargizmo = new GearGizmo();
	public static final Harvelator harvelator = new Harvelator();
	
	public static OI oi;
	
	public static NetworkTable boilerTable;
	public static NetworkTable gearTable; 
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	
	
	public void loadPrefs(){
		Config c = new Config();
		ahrsGyroAdjustment = c.load(prefPrefix + "ahrsGyroAdjustment", ahrsGyroAdjustment);
	}
	

	public void robotInit() {

		//Subsystem Init's -- Start
		try {
			Robot.mecanumDrive.init();
		} catch(RuntimeException e) {
			DriverStation.reportError("Robot: Error instantiating MecanumDrive:  " + e.getMessage(), true);
		}
		
		try {
			Robot.shooter.init();
		} catch(RuntimeException e) {
			DriverStation.reportError("Robot: Error instantiating Shooter:  " + e.getMessage(), true);
		}
		
		try {
			Robot.ultrasonics.init();
		} catch(RuntimeException e) {
			DriverStation.reportError("Robot: Error instantiating Ultrasonics:  " + e.getMessage(), true);
		}
		
		try {
			Robot.geargizmo.init();
		} catch(RuntimeException e) {
			DriverStation.reportError("Robot: Error instantiating GearGizmo:  " + e.getMessage(), true);
		}
		
		try {
			Robot.harvelator.init();
		} catch(RuntimeException e) {
			DriverStation.reportError("Robot: Error instantiating Harvelator:  " + e.getMessage(), true);
		}
		//Subsystem Init's -- End
	
	
		try {
			//Setup Tables for Vision
			NetworkTable.initialize();
			boilerTable = NetworkTable.getTable("Boiler");
			gearTable   = NetworkTable.getTable("Gear");
		} catch(Exception e) {
			DriverStation.reportError("Robot: Error instantiating NetworkTables:  " + e.getMessage(), true);
		}
		
		oi = new OI();
		oi.init();
	
	
		Robot.getAhrs().setAngleAdjustment(ahrsGyroAdjustment);
		
		chooser.addDefault("Default Auto", new DefaultAuto());
		chooser.addObject("Rotate Angle Degrees", new RotateAngleDegrees(45.0, 0.3));
		chooser.addObject("AutoDriveForward", new AutoDriveFoward(-4000, 0.5));
		SmartDashboard.putData("Auto mode", chooser);
		
		
		SmartDashboard.putData("Start Ultrasonics",new StartUltrasonicsThread());
		SmartDashboard.putData("Stop Ultrasonics",new StopUltrasonicsThread());
		SmartDashboard.putData("Start Harvelator",new RunHarvelatorFWD());
		SmartDashboard.putData("Reverse Harvelator",new RunHarvelatorRWD());
		SmartDashboard.putData("Stop Harvelator",new StopHarvelator());
		
		SmartDashboard.putData("Open Door", new OpenGearDoor());
		SmartDashboard.putData("Close Door", new CloseGearDoor());
		
		SmartDashboard.putData("Save Config",new SaveConfig());
	}

	
	//--------------------------------------------------------------------------
	@Override
	public void robotPeriodic() {	


	}
	

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	
	
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	
	
	
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	
}
