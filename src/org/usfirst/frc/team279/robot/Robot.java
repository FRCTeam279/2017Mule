
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
import org.usfirst.frc.team279.robot.commands.AutoDriveFoward;
import org.usfirst.frc.team279.robot.subsystems.*;
import org.usfirst.frc.team279.util.Config;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team279.robot.commands.RotateAngleDegrees;

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
	public static OI oi;
	
	public static NetworkTable boilerTable;
	public static NetworkTable gearTable; 
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	
	
	public void loadPrefs(){
		Config c = new Config();
		ahrsGyroAdjustment = c.load(prefPrefix + "ahrsGyroAdjustment", ahrsGyroAdjustment);
		
	}
	
	
	//--------------------------------------------------------------------------
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		Robot.mecanumDrive.init();
		
		//Setup Tables for Vision
		NetworkTable.initialize();
		boilerTable = NetworkTable.getTable("Boiler");
		gearTable   = NetworkTable.getTable("Gear");
		
		oi = new OI();
		oi.init();

		//TODO make config option
		Robot.getAhrs().setAngleAdjustment(ahrsGyroAdjustment);
		
		chooser.addObject("AutoDriveForward", new AutoDriveFoward());
		chooser.addDefault("Default Auto", new DefaultAuto());
		chooser.addObject("Rotate Angle Degrees", new RotateAngleDegrees(45.0, 0.3));
		// add other choices here...
		SmartDashboard.putData("Auto mode", chooser);
		
		SmartDashboard.putData("Get Gear Distances",new DisplayGearRanges());		
		SmartDashboard.putData("Save Config",new SaveConfig());
	}

	
	//--------------------------------------------------------------------------
	@Override
	public void robotPeriodic() {
		//Periodic code for all robot modes should go here. 
		//This function is called each time a new packet is received from the driver station. 
		//Packets are received approximately every 20ms. Fixed loop timing is not guaranteed due to network timing variability and the function may not be called at all if the Driver Station is disconnected. For most use cases the variable timing will not be an issue. If your code does require guaranteed fixed periodic timing, consider using Notifier or PIDController instead.
	}
	
	
	//--------------------------------------------------------------------------
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	
	//--------------------------------------------------------------------------
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	
	
	
	//--------------------------------------------------------------------------
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	
	
	//--------------------------------------------------------------------------
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	
}
