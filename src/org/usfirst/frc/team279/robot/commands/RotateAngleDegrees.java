package org.usfirst.frc.team279.robot.commands;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.RobotDrive.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team279.robot.*;
import org.usfirst.frc.team279.robot.subsystems.*;
import org.usfirst.frc.team279.util.NavHelper;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team279.robot.Robot;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team279.robot.Robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class RotateAngleDegrees extends Command { 
//************************************************************************************************************	

	private AHRS ahrs = null;
//************************************************************************************************************
	
	private RobotDrive roboDrive;

	//True = Cw Flase = ccw
	private boolean  turnDirection = true; 
			
	private double targetHeading = 0.0;
	private double angleParam = 30.5;
	private double rotationSpeed = 0.0;

    public RotateAngleDegrees(double rotationAngle, double turnSpeed) {
      requires(Robot.mecanumDrive);
      this.setInterruptible(true);
      this.setRunWhenDisabled(false);
      angleParam = rotationAngle;
      roboDrive = Robot.mecanumDrive.getRoboDrive(); 
      rotationSpeed = turnSpeed;
      
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetHeading = NavHelper.addDegrees(Robot.getAhrs().getAngle(), angleParam);
    	turnDirection = NavHelper.FindTurnDirection(Robot.getAhrs().getAngle(), targetHeading);
  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//The robot will turn at a set speed to a set angle.
    	//Depending on the distance in either direction to the set angle, the robot will turn either clockwise or counterclockwise.
    		 
     	roboDrive.mecanumDrive_Polar(angleParam, targetHeading, ahrs.getAngle());
    		
    
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
