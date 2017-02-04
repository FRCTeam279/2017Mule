package org.usfirst.frc.team279.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Ultrasonic;
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
	private Encoder encoderLeftFront = null;
	private Encoder encoderRightFront = null;
	private Encoder encoderLeftRear = null;
	private Encoder encoderRightRear = null;
	
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
	
	public Encoder getEncoderLeftFront(){
		return encoderLeftFront;
	}
	
	public Encoder getEncoderRightFront(){
		return encoderRightFront;
	}
	
	public Encoder getEncoderLeftRear(){
		return encoderLeftRear;
	}
	
	public Encoder getEncoderRightREar(){
		return encoderRightRear;
	}
		
	
	
	private int rangeGearLeftTrig = 0;
	private int rangeGearLeftEcho = 1;
	private int rangeGearRightTrig = 2;
	private int rangeGearRightEcho = 3;
	
	private Ultrasonic rangeLeftGear = null;
	public Ultrasonic getGearLeftUS(){
		return rangeLeftGear;
	}
	private Ultrasonic rangeRightGear = null;
	public Ultrasonic getGearRightUS(){
		return rangeRightGear;
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
		
		driveSpeedFactor = c.load(prefPrefix + "driveSpeedFactor", driveSpeedFactor);
		turnSpeedFactor = c.load(prefPrefix + "turnSpeedFactor", turnSpeedFactor);
		
		rangeGearLeftTrig = c.load(prefPrefix + "rangeGearLeftTrig", rangeGearLeftTrig);
		rangeGearLeftEcho = c.load(prefPrefix + "rangeGearLeftEcho", rangeGearLeftEcho);
		rangeGearRightTrig = c.load(prefPrefix + "rangeGearRightTrig", rangeGearRightTrig);
		rangeGearRightEcho = c.load(prefPrefix + "rangeGearRightEcho", rangeGearRightEcho);
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
		
		rangeLeftGear = new Ultrasonic(rangeGearLeftTrig, rangeGearLeftEcho, Ultrasonic.Unit.kInches);
		rangeRightGear = new Ultrasonic(rangeGearRightTrig, rangeGearRightEcho, Ultrasonic.Unit.kInches);
		rangeLeftGear.setAutomaticMode(true);
		rangeRightGear.setAutomaticMode(true);
		
		
		encoderLeftFront = new Encoder(5,6);
		encoderRightFront = new Encoder(7,8);
		encoderLeftRear = new Encoder(9,10);
		encoderRightRear = new Encoder(11,12);
	}
	
	
		
    public void initDefaultCommand() {
    	setDefaultCommand(new org.usfirst.frc.team279.robot.commands.MecDriveTeleopDefaultFPS());
    	System.out.println("MD: Set default command to MecDriveTeleopDefaultFPS");
    }
    
    
    public void stop(){
    	roboDrive.stopMotor();
    }
    
}

