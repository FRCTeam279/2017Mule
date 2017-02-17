package org.usfirst.frc.team279.robot.commands;

import org.usfirst.frc.team279.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FeedAndShoot extends Command {
	
	private double cT = 0.0; //current time in millis
	private double sT = 0.0; //Start Time
	
	private double       cDelay   = 0.0;
	private final double dStartup = 0.0;
	private final double dFeed    = 0.0;
	private final double dTimeout = 0.0;
	
	private boolean once = false;

    public FeedAndShoot() {
        requires(Robot.shooter);
    }

    protected void initialize() {
    	cT = System.currentTimeMillis();
    	sT = cT;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cT = System.currentTimeMillis();
    	Robot.shooter.shootRPM(Robot.shooter.calcSpeedFromAngle());
    	//run actuator
    	if(cT >= (cT-sT)+dStartup) {
    		if(!once){ cDelay += dStartup; once = true; }
    		if(Robot.shooter.getBallCheckSwitch().get()) {
    			Robot.shooter.feed();
    			if(cT>=(cT-sT)+cDelay+dFeed){
    				cDelay  += dFeed;
    				Robot.shooter.stopFeed();
    			}
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!Robot.shooter.getBallCheckSwitch().get() && cT>=(cT-sT)+cDelay+dTimeout)
    		return true;
    	if(!Robot.shooter.getEyes())
    		return true;
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stopAll();
    	cDelay = 0.0;
    	sT     = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.stopAll();
    	cDelay = 0.0;
    	sT     = 0.0;
    }
}
