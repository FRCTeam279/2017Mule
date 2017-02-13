package org.usfirst.frc.team279.robot.subsystems;

import org.usfirst.frc.team279.util.Config;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearGizmo extends Subsystem {
	
	private String prefPrefix = "gg_";

	private int doorMotorPort = 0;
	private int openDoorSwitchPort = 8;
	private int closeDoorSwitchPort = 9;
	private int gearPositionSwitchPort = 0;
	
	
	
	
	
	private Talon doorMotor;
	private double doorSpeed = 0;
	
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
		openDoorSwitch = new DigitalInput(8);
		closeDoorSwitch = new DigitalInput(9);
		//gearPositionSwitch = new DigitalInput();
		loadPrefs();
	}
	
	
	
	
	public void loadPrefs() {
		Config c = new Config();
		
		doorSpeed              = c.load(prefPrefix + "doorSpeed", doorSpeed);
		doorMotorPort          = c.load(prefPrefix + "doorMotorPort", doorMotorPort);
		openDoorSwitchPort     = c.load(prefPrefix + "openDoorSwitchPort", openDoorSwitchPort);
		closeDoorSwitchPort    = c.load(prefPrefix + "closeDoorSwitchPort", closeDoorSwitchPort);
		gearPositionSwitchPort = c.load(prefPrefix + "gearPositionSwitchPort", gearPositionSwitchPort);
	}
	
	
	
	
	
	
	
	//***DOOR MOTOR************************************************
	public void openDoor() {
		doorMotor.set(doorSpeed);
	}
	
	public void closeDoor () {
		doorMotor.set(-doorSpeed);
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

