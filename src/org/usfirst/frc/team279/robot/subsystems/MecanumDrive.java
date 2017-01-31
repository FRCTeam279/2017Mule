package org.usfirst.frc.team279.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.usfirst.frc.team279.util.Config;

import edu.wpi.first.wpilibj.*;



/**
 *
 */
public class MecanumDrive extends Subsystem {


	private RobotDrive roboDrive = null;
	public RobotDrive getRoboDrive() {
		return roboDrive;
	}
	
	//The preferencesPrefix will be prepended to the preferences loaded from the Robot Preferences
	private String prefPrefix = "md_";

	private int leftFrontSpeedCtrlPort = 0;
	private int rightFrontSpeedCtrlPort = 2;
	private int leftRearSpeedCtrlPort = 1;
	private int rightRearSpeedCtrlPort = 3;
	
	private boolean invertLeftFront = true;
	private boolean invertRightFront = false;
	private boolean invertLeftRear = true;
	private boolean invertRightRear = false;
	

	private SpeedController leftFrontSpeedCtrl = null;
	private SpeedController rightFrontSpeedCtrl = null;
	private SpeedController leftRearSpeedCtrl = null;
	private SpeedController rightRearSpeedCtrl = null;
	
	
	private double driveSpeedFactor = 1.0;  //variable to reduce max speed
	public double getDriveSpeedFactor() {
		return driveSpeedFactor;
	}
	
	private double turnSpeedFactor = 1.0; //variable to reduce max turn rate
	public double getTurnSpeedFactor(){
		return turnSpeedFactor;
	}	
		
	
	public void loadPrefs(){
		Config c = new Config();
		
		leftFrontSpeedCtrlPort = c.load(prefPrefix + "leftFrontSpeedCtrlPort", leftFrontSpeedCtrlPort);
		leftRearSpeedCtrlPort = c.load(prefPrefix + "leftRearSpeedCtrlPort", leftRearSpeedCtrlPort);
		rightFrontSpeedCtrlPort = c.load(prefPrefix + "rightFrontSpeedCtrlPort", rightFrontSpeedCtrlPort);
		rightRearSpeedCtrlPort = c.load(prefPrefix + "rightRearSpeedCtrlPort", rightRearSpeedCtrlPort);
		
		invertLeftFront = c.load(prefPrefix + "invertLeftFront", invertLeftFront);
		invertRightFront = c.load(prefPrefix + "invertRightFront", invertRightFront);
		invertLeftRear = c.load(prefPrefix + "invertLeftRear", invertLeftRear);
		invertRightRear = c.load(prefPrefix + "invertRightRear", invertRightRear);
		
		driveSpeedFactor = c.load(prefPrefix + "driveSpeedFactor;", driveSpeedFactor);
		turnSpeedFactor = c.load(prefPrefix + "turnSpeedFactor;", turnSpeedFactor);
	}

	
	public void init(){
		
		System.out.println("MD: MechenumDrive Init Starting");
		
		loadPrefs();
		System.out.println("MD: Preferences loaded");
		
    	leftFrontSpeedCtrl = new VictorSP(leftFrontSpeedCtrlPort);
		rightFrontSpeedCtrl = new VictorSP(rightFrontSpeedCtrlPort);
		leftRearSpeedCtrl = new VictorSP(leftRearSpeedCtrlPort);
		rightRearSpeedCtrl = new VictorSP(rightRearSpeedCtrlPort);
		
		//RobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor)
		roboDrive = new RobotDrive(leftFrontSpeedCtrl, leftRearSpeedCtrl, rightFrontSpeedCtrl, rightRearSpeedCtrl);
		roboDrive.setSafetyEnabled(false);
		//roboDrive.setExpiration(0.5);

		roboDrive.setInvertedMotor(MotorType.kFrontLeft, invertLeftFront);
		roboDrive.setInvertedMotor(MotorType.kFrontRight, invertRightFront);
		roboDrive.setInvertedMotor(MotorType.kRearLeft, invertLeftRear);
		roboDrive.setInvertedMotor(MotorType.kRearRight, invertRightRear);
		System.out.println("MD: MechenumDrive Init Complete");
	}
	
	
		
    public void initDefaultCommand() {
    	setDefaultCommand(new org.usfirst.frc.team279.robot.commands.MecDriveTeleopDefaultFPS());
    	System.out.println("MD: Set default command to MecDriveTeleopDefaultFPS");
    }
    
    
    public void stop(){
    	roboDrive.stopMotor();
    }
    
}

