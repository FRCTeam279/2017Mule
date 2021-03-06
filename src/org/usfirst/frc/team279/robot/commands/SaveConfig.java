package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SaveConfig extends Command {
	public SaveConfig() {
    	super("SaveConfig"); 
    	this.setInterruptible(false);
    	this.setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Preferences pref = Preferences.getInstance();
    	pref.save();
    	//Robot.oi.loadPrefs();
    	//Robot.driveLine.loadPrefs();

    	System.out.println("Preferences: Save called...");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

