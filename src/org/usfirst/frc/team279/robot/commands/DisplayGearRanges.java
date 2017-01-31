package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DisplayGearRanges extends Command {
    public DisplayGearRanges() {
    	super("DisplayGearRanges");
        requires(Robot.mecanumDrive);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(true);
    }
    
    
    
    // Called just before this Command runs the first time
    protected void initialize() {	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.mecanumDrive.getGearLeftUS().isEnabled()) {
    		Robot.mecanumDrive.getGearLeftUS().setEnabled(true);
    	}
    	if(!Robot.mecanumDrive.getGearRightUS().isEnabled()) {
    		Robot.mecanumDrive.getGearRightUS().setEnabled(true);
    	}
    	
    	if(Robot.mecanumDrive.getGearLeftUS().isRangeValid()){
    		SmartDashboard.putNumber("Gear - Left Range", Robot.mecanumDrive.getGearLeftUS().getRangeInches());	
    	}
    	if(Robot.mecanumDrive.getGearRightUS().isRangeValid()){
    		SmartDashboard.putNumber("Gear - Right Range", Robot.mecanumDrive.getGearRightUS().getRangeInches());
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.mecanumDrive.getGearRightUS().isRangeValid() && Robot.mecanumDrive.getGearLeftUS().isRangeValid()){
    		return true;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mecanumDrive.getGearLeftUS().setEnabled(false);
    	Robot.mecanumDrive.getGearRightUS().setEnabled(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.mecanumDrive.getGearLeftUS().setEnabled(false);
    	Robot.mecanumDrive.getGearRightUS().setEnabled(false);
    }
}
