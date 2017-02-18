package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VectoredDrive extends Command {

    public VectoredDrive() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.mecanumDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.mecanumDrive.getRoboDrive().mecanumDrive_Cartesian(1, -1, 0.0, Robot.getAhrs().getAngle());
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
    	Robot.mecanumDrive.getRoboDrive().stopMotor();
    }
}
