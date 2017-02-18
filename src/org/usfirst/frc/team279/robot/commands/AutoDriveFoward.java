package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class AutoDriveFoward extends Command {


	private int target = 0; 
	private int encoderLeftFront;
	private int encoderRightFront;
	private double speed = 0.5;
	
    public AutoDriveFoward(int target, double speed) {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.mecanumDrive);
         this.target = target;
         this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.mecanumDrive.getEncoderLeftFront().reset();
    	Robot.mecanumDrive.getEncoderRightFront().reset();
    	//Look to see for prod Robot
    	Robot.mecanumDrive.getEncoderRightFront().setReverseDirection(true);
    	Robot.mecanumDrive.getEncoderLeftFront().setReverseDirection(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mecanumDrive.getRoboDrive().tankDrive(speed, -1*speed);
    	SmartDashboard.putNumber("LF Encoder Val", Robot.mecanumDrive.getEncoderLeftFront().get());
    	SmartDashboard.putNumber("RF Encoder Val", Robot.mecanumDrive.getEncoderRightFront().get());
    	encoderLeftFront = Robot.mecanumDrive.getEncoderLeftFront().get();
    	encoderRightFront = Robot.mecanumDrive.getEncoderRightFront().get();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if((encoderRightFront  <= target) && (encoderLeftFront <= target)){ 
        	return true;
        }else{
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mecanumDrive.getRoboDrive().tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.mecanumDrive.getRoboDrive().tankDrive(0, 0);
    }
}
