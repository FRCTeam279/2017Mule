package org.usfirst.frc.team279.robot.commands;



import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.RobotDrive;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;


import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MecDriveTeleopDefaultFPS extends Command {
	private RobotDrive roboDrive;
	//private Gyro gyro;
	//private BuiltInAccelerometer accelerometer;
	
	private Joystick leftJoystick;
	private Joystick rightJoystick;
	
	
    public MecDriveTeleopDefaultFPS() {
    	super("MecDriveTeleopDefaultFPS");
        requires(Robot.mecanumDrive);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftJoystick = Robot.oi.getLeftDriverStick();
    	rightJoystick = Robot.oi.getRightDriverStick();
    	roboDrive = Robot.mecanumDrive.getRoboDrive();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
