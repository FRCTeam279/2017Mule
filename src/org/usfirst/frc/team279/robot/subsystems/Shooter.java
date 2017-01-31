package org.usfirst.frc.team279.robot.subsystems;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	
	private final double MAX_SHOOTER_MOTOR_RPM  = 2500.0; //RPM
	private final double SHOOTER_GEAR_RATIO     = 2.0;    //Ratio
	private final int    INVERT_SHOOTER_MOTOR   = 1;      //Make this -1 to change the direction
	private final int    INVERT_SCREW_MOTOR     = 1;      //Make this -1 to change the direction
	
	private final double MAX_DISTANCE           = 0.0; //feet
	private final double MAX_ANGLE              = 0.0; //degrees
	private final double MAX_SPEED              = 0.0; //rpm
	
	private final double MIN_DISTANCE           = 0.0; //feet
	private final double MIN_ANGLE              = 0.0; //degrees
	private final double MIN_SPEED              = 0.0; //rpm
	
	private final double FEED_SPEED             = 0.5;
	
	private SpeedController shooterMotor;
	private SpeedController screwMotor;
	private SpeedController feedMotor;
	
	//*** INIT *******************************************************
    public void init() {
    	shooterMotor = new TalonSRX(0);
    	screwMotor   = new Talon(0);
    	feedMotor    = new Talon(0);
    }

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    //*** SHOOTER MOTOR **********************************************
    /*
     * spinShooterToRPM(double rpm)
     * sets the motor speed according to the inputed rpm
     */
    public void spinShooterToRPM(double rpm) {
    	//calculate a -1 to 1 number from given rpm
    	double spd = rpm / (MAX_SHOOTER_MOTOR_RPM * SHOOTER_GEAR_RATIO);
    	spd *= INVERT_SHOOTER_MOTOR;
    	
    	//prevent errors
    	if(spd>1) { spd = 1; }
    	if(spd<-1){ spd = -1;}
    	
    	//set speed
    	shooterMotor.set(spd);
    }
    
    
    /*
     * spinShooter(double spd)
     * input a number between -1 and 1
     * sets the motor speed according to the inputed spd
     */
    public void spinShooter(double spd) {
    	spd *= INVERT_SHOOTER_MOTOR;
    	
    	//prevent errors
    	if(spd>1) { spd = 1; }
    	if(spd<-1){ spd = -1;}
    	
    	//set speed
    	shooterMotor.set(spd);
    }
    
    
    /*
     * stopShooter()
     * stops the motor running the shooter
     */
    public void stopShooter() {
    	shooterMotor.stopMotor();
    }
    
    
    //*** SCREW MOTOR ************************************************
    /*
     * screw(double spd)
     * input a number between -1 and 1
     * sets the motor speed according to the inputed spd
     */
    public void screw(double spd) {
    	spd *= INVERT_SCREW_MOTOR;
    	
    	//prevent errors
    	if(spd>1) { spd = 1; }
    	if(spd<-1){ spd = -1;}
    	
    	//set speed
    	screwMotor.set(spd);
    }
    
    
    /*
     * stopScrewing()
     * stops the motor running the screw
     */
    public void stopScrewing() {
    	screwMotor.stopMotor();
    }
    
    
    //*** FEED MOTOR *************************************************
    public void feed() {
    	feedMotor.set(FEED_SPEED);
    }
    
    public void revearseFeed() {
    	feedMotor.set(-FEED_SPEED);
    }
    
    public void stopFeed() {
    	feedMotor.stopMotor();
    }
    
    
    //*** E-STOP *****************************************************
    /*
     * stopAll()
     * stops the motor running the shooter
     * stops the motor running the screw
     */
    public void stopAll() {
    	stopShooter();
    	stopScrewing();
    	stopFeed();
    }
    
    
    //*** CALCULATIONS ***********************************************
    public double calcForSpeed() {
    	double percent = getDistance() / (MAX_DISTANCE - MIN_DISTANCE);
    	return (MAX_SPEED - MIN_SPEED) * percent;
    }
    
    
    public double calcForAngle() {
    	double percent = getDistance() / (MAX_DISTANCE - MIN_DISTANCE);
    	return (MAX_ANGLE - MIN_ANGLE) * percent;
    }
    
    
    //*** VISION GETTER'S ********************************************
    
    /*
     * updateDistance()
     * updates the Distance from the camera to the base of the tower based on the information received from the Rasp PI
     */
	public double getDistance() {
    	return Robot.boilerTable.getNumber("distance", 0.0);
    }
    
    
    /*
     * updateAngle()
     * updates the Rotation Angle of the robot based on the information received from the Rasp PI
     */
    public double getAngle() {
    	return Robot.boilerTable.getNumber("angle", 0.0);
    }
    
    
    /*
     * eyesOnTarget()
     * returns true if the camera can see the target
     */
    public boolean getEyes() {
    	return Robot.boilerTable.getBoolean("eyes", false);
    }
}

