package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;
import org.usfirst.frc.team279.robot.subsystems.Ultrasonics;
import org.usfirst.frc.team279.util.NavHelper;
import org.usfirst.frc.team279.util.SamplesSystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SquareRobo extends Command implements PIDOutput, PIDSource {
	private boolean useSmartDashBoardValues = false;

	public PIDController pidController;
    private double yaw = 0.0;
    private double targetHeading = 0.0;
    private double kP = 0.000;
    private double kI = 0.000;
    private double kD = 0.000;
    private double kF = 0.000;
    private double kTolerance = 0.0;
    private double minSpeed = -1.0;
    private double maxSpeed = 1.0;
    private double minInput = 0;
    private double maxInput = 360;
    
    private boolean cancel = false;
    private String left;
    private String right;

    public SquareRobo(String left, String right) {
    	super("SquareRobo");
        requires(Robot.mecanumDrive);
        requires(Robot.ultrasonics);
        this.setInterruptible(true);
        this.setRunWhenDisabled(false);
        useSmartDashBoardValues = true;
        this.left = left;
        this.right = right;
       
    }
    public SquareRobo(String left, String right, double p, double i, double d, double tolerance, double minSpeed){
    	super("SquareRobo");
        requires(Robot.mecanumDrive);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(false);
        useSmartDashBoardValues = false;
        this.left = left;
        this.right = right;
        this.kP = p;
        this.kI = i;
        this.kD = d;
        this.kTolerance = tolerance;
        this.minSpeed = minSpeed; 
    }
    // Called just before this Command runs the first time
    protected void initialize() {
this.cancel = false;
    	
    	if(useSmartDashBoardValues) {
			kP = SmartDashboard.getNumber("TurnPID P", 0.005);
			kI = SmartDashboard.getNumber("TurnPID I", 0.00);
			kD = SmartDashboard.getNumber("TurnPID D", 0.0);
			minSpeed = SmartDashboard.getNumber("TurnPID MinSpeed", 0.0);
			kTolerance = Math.abs(SmartDashboard.getNumber("TurnPID Tolerance", 500));
    	} 
    	pidController = new PIDController(kP, kI, kD, kF, this, this);
    	pidController.setInputRange(-180.0f, 180.0f);
    	pidController.setOutputRange(-1.0, 1.0);
    	pidController.setContinuous(false);
    	pidController.setAbsoluteTolerance(kTolerance);
        pidController.setSetpoint(0);
        System.out.println("CMD SquareRobo: Starting - target: "+ ", current: " + Robot.getAhrs().pidGet());

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(pidController == null) {
    		this.initialize();
    	}
    	if(!pidController.isEnabled()){
    		pidController.enable();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return pidController.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("CMD SquareRobo: Ended - target: " + ", current: " + Robot.getAhrs().pidGet());
    	Robot.mecanumDrive.stop();
    	pidController.disable();
    	pidController = null;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("CMD SquareRobo: Interrupted - target: " + ", current: " + Robot.getAhrs().pidGet());
    	Robot.mecanumDrive.stop();
    	pidController.disable();
    	pidController = null;
    }

	@Override
	public void pidWrite(double output) {
		if(this.cancel){ 
    		Robot.mecanumDrive.stop(); 
    	} else {
    		if(Math.abs(output) < this.minSpeed) {
    			//System.out.println("CMD USDD: MinSpeed Reached (Output: " + output + ")");
    			if(output > 0.0) {
    				Robot.mecanumDrive.getRoboDrive().mecanumDrive_Cartesian(0.0, minSpeed, 0.0, 0.0);
    			} else {
    				Robot.mecanumDrive.getRoboDrive().mecanumDrive_Cartesian(0.0, minSpeed * -1.0, 0.0, 0.0);
    			}
    		} else {
    			Robot.mecanumDrive.getRoboDrive().mecanumDrive_Cartesian(0.0, output, 0.0, 0.0);
    		}
    	}
	}

	@Override
	 public PIDSourceType getPIDSourceType() {
    	return PIDSourceType.kDisplacement;
    }
    
    public void setPIDSourceType(PIDSourceType pidSource) {
    	
    }
    
    public double pidGet(){
    	double x = Robot.ultrasonics.getUltrasonics().getReading(left).getDistanceInches();
    	double y = Robot.ultrasonics.getUltrasonics().getReading(right).getDistanceInches();
    	return x-y;
    }
}
