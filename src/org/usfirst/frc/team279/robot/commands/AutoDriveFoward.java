package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;


import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class AutoDriveFoward extends Command {

	long TimeMs = 0;
	long currentTime = 0;
	private int target = 3000;
    public AutoDriveFoward() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.mecanumDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	TimeMs = System.currentTimeMillis();
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mecanumDrive.getRoboDrive().tankDrive(0.5, -0.5);
    	currentTime = System.currentTimeMillis();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(currentTime - TimeMs > target){
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
    }
}
