package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartUltrasonicsThread extends Command {

    public StartUltrasonicsThread() {
    	super("StartUltrasonicsThread");
        requires(Robot.ultrasonics);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(true);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ultrasonics.startUltrasonics();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
