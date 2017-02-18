package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;
import org.usfirst.frc.team279.robot.subsystems.Ultrasonics;
import org.usfirst.frc.team279.util.SamplesSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SquareRobo extends Command {
	 private SamplesSystem ssL;
	 private SamplesSystem ssR;
	 private int sampleAmount;
	 private int distLeft;
	 private int distRight;
	 
    public SquareRobo() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.mecanumDrive);
        requires(Robot.ultrasonics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	sampleAmount = (int) SmartDashboard.getNumber("Set Square Sample Amount", 10);
    	ssL = new SamplesSystem(sampleAmount);
    	ssR = new SamplesSystem(sampleAmount);
    	double distLeft = Robot.ultrasonics.getUltrasonics().getReading("rangeGearLeft").getDistanceInches();
		double distRight = Robot.ultrasonics.getUltrasonics().getReading("rangeGearRight").getDistanceInches();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ultrasonics.startUltrasonics();
    	if((distRight > distLeft) || (distLeft > distRight) ){
    		
    		
    	}else{
    		int taco = 5;
    	}
    	
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
