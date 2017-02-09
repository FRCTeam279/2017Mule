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
	 * > Encoder is between the motor and the gearbox (measures direct motor speeds)
	 * > Angle will now be two fixed angles which are TBD
	 * > 
	 */
	
	//The preferencesPrefix will be prepended to the preferences loaded from the Robot Preferences
	private String prefPrefix = "sh_";
	
	//Shooter motor max speed
	private double  maxShooterMotorRPM = 18730.0;//RPM
	
	//Angles - in degrees
	private double  degOne    = 0.0;
	private double  degTwo    = 0.0;
	private boolean whichDeg = false; //One = false; Two = true;
	
	//MAX and MIN values at degOne and degTwo
	private double  degOneDistanceMax = 0.0;
	private double  degOneDistanceMin = 0.0;
	private double  degOneSpeedMax    = 0.0;
	private double  degOneSpeedMin    = 0.0;
	private double  degTwoDistanceMax = 0.0;
	private double  degTwoDistanceMin = 0.0;
	private double  degTwoSpeedMax    = 0.0;
	private double  degTwoSpeedMin    = 0.0;
	
	//Feed Motor Default Speed
	private double  feedSpeed     = 0.0;
	
	//Shooter PID Values
	private double  p             = 0.0;
	private double  i             = 0.0;
	private double  d             = 0.0;
	private double  f             = 0.0;
	private double  dP            = 0.0;
	private double  dI            = 0.0;
	private double  dD            = 0.0;
	private double  dF            = 0.0;
	
	//Shooter CANTalon with getter
	private CANTalon shooterMotor = null;
	public CANTalon getShooterController() {
		return shooterMotor;
	}
	
	//Feed SpeedController
	private SpeedController feedMotor   = null;
	
	//SpeedController Ports
	private int     shooterPort   = 1;
	private int     feedPort      = 0;
	
	//SpeedController Inverts
	private boolean invertShooter = false;
	private boolean invertFeed    = false;
	
	//CANTalon Encoder Invert
	private boolean invertEncoder = true;
	
	
	
	//*** INIT *******************************************************
	
    public void init() {
    	
    	System.out.println("SH: Shooter Init Starting");
    	
    	loadPrefs();
		System.out.println("SH: Preferences loaded");
    	
    	shooterMotor = new CANTalon(shooterPort);
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
		
		shooterPort   = c.load(prefPrefix + "shooterPort", shooterPort);
		feedPort      = c.load(prefPrefix + "feedPort", feedPort);
		invertShooter = c.load(prefPrefix + "invertShooter", invertShooter);
		invertFeed    = c.load(prefPrefix + "invertFeed", invertFeed);
		invertEncoder = c.load(prefPrefix + "invertEncoder", invertEncoder);
		
		dP            = c.load(prefPrefix + "defaultP", dP);
		dI            = c.load(prefPrefix + "defaultI", dI);
		dD            = c.load(prefPrefix + "defaultD", dD);
		dF            = c.load(prefPrefix + "defaultF", dF);
		
		degOne        = c.load(prefPrefix + "degreeOne", degOne);
		degTwo        = c.load(prefPrefix + "degreeTwo", degTwo);
		whichDeg      = c.load(prefPrefix + "whichDegree", whichDeg);
		
		maxShooterMotorRPM = c.load(prefPrefix + "maxShooterMotorRPM", maxShooterMotorRPM);
		feedSpeed     = c.load(prefPrefix + "feedSpeed", feedSpeed);

		degOneDistanceMax = c.load(prefPrefix + "degOneDistanceMax", degOneDistanceMax);
		degOneDistanceMin = c.load(prefPrefix + "degOneDistanceMin", degOneDistanceMin);
		degOneSpeedMax    = c.load(prefPrefix + "degOneSpeedMax", degOneSpeedMax);
		degOneSpeedMin    = c.load(prefPrefix + "degOneSpeedMin", degOneSpeedMin);
		degTwoDistanceMax = c.load(prefPrefix + "degTwoDistanceMax", degTwoDistanceMax);
		degTwoDistanceMin = c.load(prefPrefix + "degTwoDistanceMin", degTwoDistanceMin);
		degTwoSpeedMax    = c.load(prefPrefix + "degTwoSpeedMax", degTwoSpeedMax);
		degTwoSpeedMin    = c.load(prefPrefix + "degTwoSpeedMin", degTwoSpeedMin);
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
    
    
    
    //*** SHOOTER MOTOR **********************************************
    
    /*
     * setDefaultPIDValues(double p, double i, double d)
     * Sets a new default PID Value
     */
    public void setDefaultPIDValues(double p, double i, double d, double f) {
    	this.p = p;
    	this.i = i;
    	this.d = d;
    	this.f = f;
    }
    
    
    /*
     * resetDefaultPIDValues()
     * Resets pid default values to the values received from prefs
     */
    public void resetDefaultPIDValues() {
    	p = dP;
    	i = dI;
    	d = dD;
    	f = dF;
    }
    
    
    /*
     * resetPID()
     * sets PID to the current pid default values
     */
    private void resetPID() {
    	shooterMotor.setP(p);
    	shooterMotor.setI(i);
    	shooterMotor.setD(d);
    	shooterMotor.setF(f);
    }
    
    
    /*
     * shootRPM(double rpm)
     * sets the motor speed according to the inputed rpm
     * uses default PID settings
     */
    public void shootRPM(double rpm) {
    	resetPID();
    	
    	//prevent errors
    	if(rpm > maxShooterMotorRPM) { rpm = maxShooterMotorRPM; }
    	if(rpm < -maxShooterMotorRPM){ rpm = -maxShooterMotorRPM; }
    	
    	//set speed
    	shooterMotor.changeControlMode(TalonControlMode.Speed);
    	shooterMotor.set(rpm);
    }
    
    
    /*
     * shootPWM(double pwm)
     * input a number between -1 and 1
     * sets the motor speed according to the inputed spd
     * no PID used
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
    public void shootPID(double rpm, double p, double i, double d, double f) {
    	//Setup PID
    	shooterMotor.setP(p);
    	shooterMotor.setI(i);
    	shooterMotor.setD(d);
    	shooterMotor.setF(f);
    	
    	//prevent errors
    	if(rpm>maxShooterMotorRPM) { rpm = maxShooterMotorRPM; }
    	if(rpm<-maxShooterMotorRPM){ rpm = -maxShooterMotorRPM;}
    	
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
    
    
    
    //*** FEED MOTOR *************************************************
    
    /*
     * feed()
     * run the feed motor forward at the rate received from prefs
     */
    public void feed() {
    	feedMotor.set(feedSpeed);
    }
    

    /*
     * feed()
     * run the feed motor forward at the rate given
     */
    public void feed(double spd) {
    	feedMotor.set(spd);
    }
    
    
    /*
     * reverseFeed()
     * run the feed motor in reverse at the rate received from prefs
     */
    public void reverseFeed() {
    	feedMotor.set(-feedSpeed);
    }
    

    /*
     * reverseFeed()
     * run the feed motor in reverse at the rate given
     */
    public void reverseFeed(double spd) {
    	feedMotor.set(spd);
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
    	stopFeed();
    }
    
    
    
    
    //*** CALCULATIONS ***********************************************
    
    /*
     * calcSpeedFromAngle()
     * gets the distance from the camera and compares it to pre-recorded
     * information(Max and Min shot data) to get the speed needed. Also
     * takes in to account the which angle is being used.
     * 
     * gets a percent:    % = d / (dMax - dMin)
     * returns a speed: spd = (spdMax - spdMin) * %
     * returns in RPM
     */    
    public double calcSpeedFromAngle() {
    	double percent = 0.0;
    	
    	if(!whichDeg) {
    		percent = getDistance() / (degOneDistanceMax - degOneDistanceMin);
    		return (degOneSpeedMax - degOneSpeedMin) * percent;
    	} else {
    		percent = getDistance() / (degTwoDistanceMax - degTwoDistanceMin);
    		return (degTwoSpeedMax - degTwoSpeedMin) * percent;    		
    	}
    }

    
    
    //*** VISION GETTER'S ********************************************
    
    /*
     * updateDistance()
     * updates the Distance from the camera to the base of the tower 
     * based on the information received from the Rasp PI through the
     * Network Tables
     */
	public double getDistance() {
		try {
			return Robot.boilerTable.getNumber("distance", 0.0);
		} catch(Exception e) {
    		System.err.println("NetworkTables Error: Failed to get a value for 'distance' from 'boilerTable'");
			return -1.0;
		}
    }
    
    
    /*
     * updateAngle()
     * updates the Rotation Angle of the robot based on the information
     * received from the Rasp PI through the Network Tables
     */
    public double getAngle() {
    	try {
    		return Robot.boilerTable.getNumber("angle", 0.0);
    	} catch(Exception e) {
    		System.err.println("NetworkTables Error: Failed to get a value for 'angle' from 'boilerTable'");
    		return -1.0;
    	}
    }
    
    
    /*
     * eyesOnTarget()
     * returns true if the camera can see the target
     */
    public boolean getEyes() {
    	try {
    		return Robot.boilerTable.getBoolean("eyes", false);
    	} catch(Exception e) {
    		System.err.println("NetworkTables Error: Failed to get a value for 'eyes' from 'boilerTable'");
    		return false;
    	}
    }
}

