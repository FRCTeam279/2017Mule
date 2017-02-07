package org.usfirst.frc.team279.robot.subsystems;

import org.usfirst.frc.team279.robot.Robot;
import org.usfirst.frc.team279.util.Config;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {
	
	/*
	 * Note:
	 * > Encoder on the motor before the gearRatio
	 * > Angle will now be two fixed angles which are TBD
	 * > 
	 */
	
	//The preferencesPrefix will be prepended to the preferences loaded from the Robot Preferences
	private String prefPrefix = "sh_";
	
	//RPM Calculation Constants
	private final double MAX_SHOOTER_MOTOR_RPM    = 18730.0;//RPM
	//private final double SHOOTER_GEAR_RATIO     = 2.0;    //Ratio
	
	//Calculation Constants
	private final double MAX_DISTANCE             = 0.0; //feet
	private final double MAX_ANGLE                = 0.0; //degrees
	private final double MAX_SPEED                = 0.0; //rpm
	private final double MIN_DISTANCE             = 0.0; //feet
	private final double MIN_ANGLE                = 0.0; //degrees
	private final double MIN_SPEED                = 0.0; //rpm
	
	//Feed Motor Default Speed
	private double feedSpeed = 0.0;
	
	//Shooter PID Values (leave at 0.0)
	private double p         = 0.0;
	private double i         = 0.0;
	private double d         = 0.0;
	private double dP        = 0.0;
	private double dI        = 0.0;
	private double dD        = 0.0;
	
	//SpeedController Ports
	private int shooterPort  = 1;
	private int screwPort    = 0;
	private int feedPort     = 0;
	
	//SpeedController Inverts
	private boolean invertShooter = false;
	private boolean invertScrew   = false;
	private boolean invertFeed    = false;
	
	//CANTalon Encoder Invert
	private boolean invertEncoder = true;
	
	//Shooter CANTalon with getter
	private CANTalon shooterMotor = null;
	public CANTalon getShooterController() {
		return shooterMotor;
	}
	
	//SpeedControllers
	private SpeedController screwMotor  = null;
	private SpeedController feedMotor   = null;
	
	
	//private double testSpeed = 0.0;
	
	
	
	//*** INIT *******************************************************
	
    public void init() {
    	
    	System.out.println("SH: Shooter Init Starting");
    	
    	loadPrefs();
		System.out.println("SH: Preferences loaded");
    	
    	shooterMotor = new CANTalon(shooterPort);
    	//screwMotor   = new Talon(screwPort); commented out because the talons are not on the robot
    	//feedMotor    = new Talon(feedPort);  commented out because the talons are not on the robot
		System.out.println("SH: Speed Controllers Setup");
    	
    	shooterConfig();
		System.out.println("SH: Shooter Config Loaded");
    }

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//System.out.println("SH: Set default command to ...");
    }
    
    
    /*
     * loadPrefs()
     * loads the values from the SmartDashboard
     */
    public void loadPrefs() {
		Config c = new Config();
		
		//testSpeed     = c.load(prefPrefix + "testSpeed", testSpeed);
		shooterPort   = c.load(prefPrefix + "shooterPort", shooterPort);
		screwPort     = c.load(prefPrefix + "screwPort", screwPort);
		feedPort      = c.load(prefPrefix + "feedPort", feedPort);
		invertShooter = c.load(prefPrefix + "invertShooter", invertShooter);
		invertScrew   = c.load(prefPrefix + "invertScrew", invertScrew);
		invertFeed    = c.load(prefPrefix + "invertFeed", invertFeed);
		invertEncoder = c.load(prefPrefix + "invertEncoder", invertEncoder);
		dP            = c.load(prefPrefix + "defaultP", dP);
		dI            = c.load(prefPrefix + "defaultI", dI);
		dD            = c.load(prefPrefix + "defaultD", dD);
		feedSpeed     = c.load(prefPrefix + "feedSpeed", feedSpeed);
	}
    
    
    /*
     * shooterConfig()
     * sets up the TalonSRX for the shooter
     */
    private void shooterConfig() {
    	//Setup Feedback
    	shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
    	shooterMotor.reverseSensor(invertEncoder);
    	
    	//Setup the Voltage Max and Nominal
    	shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
    	shooterMotor.configPeakOutputVoltage(+12.0f, -12.0f);
    	
    	//Setup PID
    	resetDefaultPIDValues();
    	resetPID();
    	
    	//Other Setup
    	shooterMotor.enableBrakeMode(false);
    	shooterMotor.reverseOutput(invertShooter);
 
    }
    
//    public void test(double num) {
//    	shooterMotor.changeControlMode(TalonControlMode.Voltage);
//    	shooterMotor.set(num);
//    }
    
    
    
    
    //*** SHOOTER MOTOR **********************************************
    
    /*
     * setDefaultPIDValues(double p, double i, double d)
     * Sets a new default PID Value
     */
    public void setDefaultPIDValues(double p, double i, double d) {
    	this.p = p;
    	this.i = i;
    	this.d = d;
    }
    
    
    /*
     * resetDefaultPIDValues()
     * Resets PID back to the values received from prefs
     */
    public void resetDefaultPIDValues() {
    	p = dP;
    	i = dI;
    	d = dD;
    }
    
    
    /*
     * resetPID()
     * sets PID to the default values
     */
    private void resetPID() {
    	shooterMotor.setPID(p, i, d);
    }
    
    
    /*
     * shootRPM(double rpm)
     * sets the motor speed according to the inputed rpm
     * uses default PID settings
     */
    public void shootRPM(double rpm) {
    	resetPID();
    	
    	//prevent errors
    	if(rpm > MAX_SHOOTER_MOTOR_RPM) { rpm = MAX_SHOOTER_MOTOR_RPM; }
    	if(rpm < -MAX_SHOOTER_MOTOR_RPM){ rpm = -MAX_SHOOTER_MOTOR_RPM; }
    	
    	//set speed
    	shooterMotor.changeControlMode(TalonControlMode.Speed);
    	shooterMotor.set(rpm);
    }
    
    
    /*
     * shootPWM(double pwm)
     * input a number between -1 and 1
     * sets the motor speed according to the inputed spd
     * uses default PID settings
     */
    public void shootPWM(double pwm) {
    	resetPID();
    	
    	//prevent errors
    	if(pwm>1) { pwm = 1; }
    	if(pwm<-1){ pwm = -1;}
    	
    	//set speed
    	shooterMotor.changeControlMode(TalonControlMode.Voltage);
    	shooterMotor.set(pwm*12); //12 = Max Voltage 
    }
    
    
    /*
     * shootPID(double rpm, double p, double i, double d)
     * sets the motor speed according to the inputed rpm
     * sets a custom pid for the shot
     */
    public void shootPID(double rpm, double p, double i, double d) {
    	shooterMotor.setPID(p, i, d);
    	//calculate a -1 to 1 number from given rpm
    	
    	//prevent errors
    	if(rpm>MAX_SHOOTER_MOTOR_RPM) { rpm = MAX_SHOOTER_MOTOR_RPM; }
    	if(rpm<-MAX_SHOOTER_MOTOR_RPM){ rpm = -MAX_SHOOTER_MOTOR_RPM;}
    	
    	//set speed
    	shooterMotor.changeControlMode(TalonControlMode.Speed);
    	shooterMotor.set(rpm);
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
    	if(invertScrew) { spd *= -1; }
    	
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
    
    /*
     * feed()
     * run the feed motor forward at the rate received from prefs
     */
    public void feed() {
    	feedMotor.set(feedSpeed);
    }
    
    
    /*
     * reverseFeed()
     * run the feed motor in reverse at the rate received from prefs
     */
    public void reverseFeed() {
    	feedMotor.set(-feedSpeed);
    }
    
    
    /*
     * stopFeed()
     * stops the feed motor
     */
    public void stopFeed() {
    	feedMotor.stopMotor();
    }
    
    
    
    
    //*** E-STOP *****************************************************
    
    /*
     * stopAll()
     * stops the motor running the shooter
     * stops the motor running the screw
     * stops the motor running the feed
     */
    public void stopAll() {
    	stopShooter();
    	stopScrewing();
    	stopFeed();
    }
    
    
    
    
    //*** CALCULATIONS ***********************************************
    
    /*
     * speedCalc()
     * gets the distance from the camera and compares it to pre-recorded
     * information(Max and Min shot data) to get the speed needed.
     * 
     * gets a percent:    % = d / (dMax - dMin)
     * returns a speed: spd = (spdMax - spdMin) * %
     */
    public double speedCalc() {
    	double percent = getDistance() / (MAX_DISTANCE - MIN_DISTANCE);
    	return (MAX_SPEED - MIN_SPEED) * percent;
    }
    
    
    /*
     * angleCalc()
     * gets the distance from the camera and compares it to pre-recorded
     * information(Max and Min shot data) to return the angle needed.
     * 
     * gets a percent:    % = d / (dMax - dMin)
     * returns a speed: ang = (angMax - angMin) * %
     */
    public double angleCalc() {
    	double percent = getDistance() / (MAX_DISTANCE - MIN_DISTANCE);
    	return (MAX_ANGLE - MIN_ANGLE) * percent;
    }
    

    
    
    //*** VISION GETTER'S ********************************************
    
    /*
     * updateDistance()
     * updates the Distance from the camera to the base of the tower 
     * based on the information received from the Rasp PI through the
     * Network Tables
     */
	public double getDistance() {
    	return Robot.boilerTable.getNumber("distance", 0.0);
    }
    
    
    /*
     * updateAngle()
     * updates the Rotation Angle of the robot based on the information
     * received from the Rasp PI through the Network Tables
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

