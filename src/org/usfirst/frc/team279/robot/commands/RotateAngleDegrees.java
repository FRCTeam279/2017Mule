package org.usfirst.frc.team279.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team279.util.NavHelper;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team279.robot.Robot;
/**
 *
 */
public class RotateAngleDegrees extends Command { 
	
	private RobotDrive roboDrive;

	//True = Cw False = ccw
	private int  turnDirection    = 1; 
	
	private double targetHeading  = 0.0;
	private double rotationAngle  = 0.0;
	private double rotationSpeed  = 0.0;
	private double currentHeading = 0.0;
	private final int TURNCW      = 1;
	private final int TURNCCW     = -1;


	
    public RotateAngleDegrees(double rotationAngle, double turnSpeed) {
      requires(Robot.mecanumDrive);
      
      this.setInterruptible(true);
      this.setRunWhenDisabled(false);
      
      this.rotationAngle = rotationAngle;
      roboDrive = Robot.mecanumDrive.getRoboDrive(); 
      rotationSpeed = turnSpeed;
      currentHeading = Robot.getAhrs().getAngle();
      
    }

    protected void initialize() {
    	Robot.getAhrs().reset();
    	targetHeading = NavHelper.addDegrees(Robot.getAhrs().getAngle(), rotationAngle);
    	turnDirection = NavHelper.FindTurnDirection(Robot.getAhrs().getAngle(), targetHeading) ?1:-1;
  
    }

  
    protected void execute() {
    	//The robot will turn at a set speed to a set angle.
    	//Depending on the distance in either direction to the set angle, the robot will turn either clockwise or counterclockwise.
    		 
  		
     	
     	
      	currentHeading = Robot.getAhrs().getAngle(); // 0 - 360
      	
     	// if the current heading and target heading are both less than 180, the robot will turn Clockwise
        // if the current heading is less than 180 and the target heading is greater than 180, the robot will turn counterclockwise
        // if the current heading is greater than 180 and the target heading is less than 180, the robot will turn counterclockwise
        // if the current heading and target heading are both greater than 180, the robot will turn clockwise.
     	if(currentHeading < 180 && targetHeading < 180) {
    		turnDirection = TURNCW;
    	} else if(currentHeading < 180 && targetHeading > 180 ){
    		turnDirection = TURNCCW;
    	} else if(currentHeading > 180 && targetHeading < 180 ) {
    		turnDirection = TURNCCW;
    	} else if( currentHeading > 180 && targetHeading > 180) {
    		turnDirection = TURNCW;
    	}
     	
     	roboDrive.mecanumDrive_Polar(0.0, 0, turnDirection * rotationSpeed); 
     	
    }
    	

    protected boolean isFinished() {
    //The robot will keep checking the gyro to see if it matches the target heading.
    //It will keep returning false if the target heading is not reached
     
    	currentHeading = Robot.getAhrs().getAngle();
    	if(currentHeading == targetHeading +- 1) {
    		rotationSpeed = 1.0;
    	}
    	
    	if(currentHeading >= targetHeading && currentHeading <= targetHeading ) {
    		System.out.println("Current heading has reached the target heading");
    		return true;
    	
    	} else {
    		System.out.println ("Current heading is not at target heading");
    		return false;
    	}
    	
    }

    // Called once after isFinished returns true
    protected void end() {
     	roboDrive.mecanumDrive_Polar(0, 0, 0);
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
       	roboDrive.mecanumDrive_Polar(0, 0, 0);
    }
}
