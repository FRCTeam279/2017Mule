package org.usfirst.frc.team279.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;

public class GearGizmo {
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
	
	public void init () {
		doorMotor = new Talon(0);
	}
	

	
	//***DOOR MOTOR***********************************************
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
	
}

