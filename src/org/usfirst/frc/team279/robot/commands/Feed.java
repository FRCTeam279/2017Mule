package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Feed extends Command {

    public Feed() {
        requires(Robot.shooter);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.shooter.feed();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shooter.stopFeed();
    }

    protected void interrupted() {
    	Robot.shooter.stopFeed();
    }
}
