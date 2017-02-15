package org.usfirst.frc.team279.robot.subsystems;

import org.usfirst.frc.team279.util.Config;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearGizmo extends Subsystem {
	
	private String prefPrefix = "gg_";

	private int doorMotorPort = 4;
	private int openDoorSwitchPort = 7;
	private int closeDoorSwitchPort = 6;
//	private int gearPosSwitchPort = 0;	
	
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
	
	private Counter openDoorCounter;
	private Counter closeDoorCounter;
//	private Counter gearPosCounter;
	
	private boolean invertDoorMotor = false;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void init() throws RuntimeException {
		
		loadPrefs();
		System.out.println("GG: Preferences loaded");
		
		doorMotor = new Talon(doorMotorPort);
		doorMotor.setInverted(invertDoorMotor);
		System.out.println("GG: Speed Controllers Configured");
		
		openDoorSwitch = new DigitalInput(openDoorSwitchPort);
		openDoorCounter = new Counter(openDoorSwitch);
		
		closeDoorSwitch = new DigitalInput(closeDoorSwitchPort);
		closeDoorCounter = new Counter(closeDoorSwitch);
		System.out.println("GG: Limit Switches Setup");
	}
	
	public void loadPrefs() {
		Config c = new Config();
		
		doorSpeed              = c.load(prefPrefix + "doorSpeed", doorSpeed);
		doorMotorPort          = c.load(prefPrefix + "doorMotorPort", doorMotorPort);
		openDoorSwitchPort     = c.load(prefPrefix + "openDoorSwitchPort", openDoorSwitchPort);
		closeDoorSwitchPort    = c.load(prefPrefix + "closeDoorSwitchPort", closeDoorSwitchPort);
		invertDoorMotor        = c.load(prefPrefix + "invertDoorMotor", invertDoorMotor);
//		gearPosSwitchPort = c.load(prefPrefix + "gearPositionSwitchPort", gearPosSwitchPort);
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
	
	
	//***DOOR SWITCHES*********************************************
	public boolean getOpenCount() {
		return openDoorCounter.get() > 0;
	}
	
	public boolean getCloseCount() {
		return closeDoorCounter.get() > 0;
	}
	
	public void resetOpenSwitch() {
		openDoorCounter.reset();
	}
	
	public void resetCloseSwitch() {
		closeDoorCounter.reset();
	}
	
	
	//***ESTOP*****************************************************
	public void stopAll() {
		stopDoor();
	}	
}

