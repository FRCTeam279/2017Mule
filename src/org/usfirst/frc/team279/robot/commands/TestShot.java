package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestShot extends Command {

    public TestShot() {
        requires(Robot.shooter);
    	SmartDashboard.putNumber("setVoltage", 0);
    }

    double speed;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("setVoltage", 0);
    	speed = (double)SmartDashboard.getNumber("setVoltage", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.shootPID(1000, .1, .1, .1);
    	//Robot.shooter.test(speed);
    	SmartDashboard.putNumber("Speed", Robot.shooter.getShooterController().getSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stopShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.stopShooter();
    }
}
