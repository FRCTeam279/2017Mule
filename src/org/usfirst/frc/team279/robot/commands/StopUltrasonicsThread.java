package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopUltrasonicsThread extends Command {

    public StopUltrasonicsThread() {
    	super("StopUltrasonicsThread");
        requires(Robot.ultrasonics);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(true);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ultrasonics.stopUltrasonics();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
