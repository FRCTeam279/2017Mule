package org.usfirst.frc.team279.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import org.usfirst.frc.team279.robot.commands.*;
import org.usfirst.frc.team279.util.Attack3Joystick;
//import org.usfirst.frc.team279.lib.*;
//import org.usfirst.frc.team279.lib.SpeedControllerType;
//import org.usfirst.frc.team279.lib.oi.*;
import org.usfirst.frc.team279.util.LF310Controller;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private String prefPrefix = "oi_";

	private boolean debug = false;
	
	
	
	//--------------------------------------------------------------------------
	// Controllers, Joysticks, TACOS, etc..
	//--------------------------------------------------------------------------
	private int leftDriverStickPort = 0;
	private int rightDriverStickPort = 1;
	private int goControllerPort = 2;
	
	private Joystick leftDriverStick = null;
	private Joystick rightDriverStick = null;
	private Joystick goController = null;
	
	public Joystick getLeftDriverStick(){
		return leftDriverStick;
	}
	public Joystick getRightDriverStick(){
		return rightDriverStick;
	}
	public Joystick getGOController(){
		return goController;
	}
	
	private double leftStickNullZone = 0.15;
	private double rightStickNullZone = 0.15;
	private double goControllerNullZone = 0.15;
	
	public double getLeftStickNullZone() {
		return leftStickNullZone;
	}
	public double getRightStickNullZone() {
		return rightStickNullZone;
	}
	public double getgoControllerNullZone() {
		return goControllerNullZone;
	}
	
	
	
	//--------------------------------------------------------------------------
	public void readConfig(){
		System.out.println("OI: Reading Config Started");
		
		System.out.println("OI: Reading Config Complete");
	}
	
	public boolean init() {
		System.out.println("OI: Init Started");
		readConfig();
		
		
		try{
			leftDriverStick = new Attack3Joystick(leftDriverStickPort);
		} catch (Exception e) {
			System.out.println("OI: Error instantiating leftDriverStick: " + e.getMessage());
		}
		
		try{
			rightDriverStick = new Joystick(rightDriverStickPort);
		} catch (Exception e) {
			System.out.println("OI: Error instantiating rightDriverStick: " + e.getMessage());
		}
		
		try{
			goController = new LF310Controller(goControllerPort);
		} catch (Exception e) {
			System.out.println("OI: Error instantiating goController: " + e.getMessage());
		}
		
		

		System.out.println("OI: Init Complete");
		return true;   // all good
	}

}
