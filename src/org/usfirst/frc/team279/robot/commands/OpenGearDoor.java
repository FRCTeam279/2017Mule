package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class OpenGearDoor extends Command {

    public OpenGearDoor() {
        requires(Robot.geargizmo);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.geargizmo.resetCloseSwitch();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.geargizmo.openDoor();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(Robot.geargizmo.getOpenCount()){
        	return true;
        } else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.geargizmo.stopDoor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.geargizmo.stopDoor();
    }
}
