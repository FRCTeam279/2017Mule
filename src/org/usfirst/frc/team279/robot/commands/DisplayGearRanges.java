package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;
import org.usfirst.frc.team279.robot.sensors.UltrasonicsGroup.UltrasonicReading;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DisplayGearRanges extends Command {
		
    public DisplayGearRanges() {
    	super("DisplayGearRanges");
        requires(Robot.ultrasonics);
        
        this.setInterruptible(true);
        this.setRunWhenDisabled(true);
    }
    
    
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	UltrasonicReading leftReading = Robot.ultrasonics.getUltrasonics().getReading("rangeGearLeft");
    	UltrasonicReading rightReading = Robot.ultrasonics.getUltrasonics().getReading("rangeGearRight");
    	
    	System.out.println("Ultrasonics: Gear Distance Inches - Left = " + leftReading.getDistanceInches() + ", right=" + rightReading.getDistanceInches());	
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
