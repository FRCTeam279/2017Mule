package org.usfirst.frc.team279.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearGizmo extends Subsystem {
	/* 
	 * Objects:
	 * VictorSP
	 * ULtrasonic
	 * Limit Switches 
	 */
	
	/*
	 * Functions:
	 * Open doors
	 * Close doors
	 * Detect distance from pole
	 * Use limit switches for doors and gear position
	 * 
	 * Use limit switches for when door is open and door is closed
	 * Use a limit switch for gear's position in holder
	 * Use motor for opening and closing door
	 * Use network tables for distance and angle for peg
	 */
	private Talon doorMotor;
	private final double DOOR_SPEED = 0;
	
	private DigitalInput openDoorSwitch;
	public DigitalInput getOpenDoorSwitch() {
		return openDoorSwitch;
	}
	
	private DigitalInput closeDoorSwitch;
	public DigitalInput getCloseDoorSwitch(){
		return closeDoorSwitch;
	}
	
	private DigitalInput gearPositionSwitch;
	public DigitalInput getGearPositionSwitch(){
		return gearPositionSwitch;
	}
	
	
	
	public void init () {
		//doorMotor = new Talon();
		openDoorSwitch = new DigitalInput(5);
		closeDoorSwitch = new DigitalInput(9);
		//gearPositionSwitch = new DigitalInput();
	}
	
	
	
	
	//***LIMIT SWITCHES********************************************
	
	
	
	
	
	
	
	//***DOOR MOTOR************************************************
	public void openDoor() {
		doorMotor.set(DOOR_SPEED);
	}
	
	public void closeDoor () {
		doorMotor.set(-DOOR_SPEED);
	}
	
	public void stopDoor() {
		doorMotor.stopMotor();
	}
	
	//***ESTOP*****************************************************
	public void stopAll() {
		stopDoor();
	}




	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}

