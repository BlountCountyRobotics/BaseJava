package org.usfirst.frc.team4504.robot;

import org.usfirst.frc.team4504.robot.commands.StopDriving;
import org.usfirst.frc.team4504.robot.objects.BCRDriverStation;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface
 *  to the commands and command groups that allow control of the robot.
 */ 

public class OI {
	
	private BCRXbox controller = new BCRXbox(0);
	private BCRDriverStation driverStation = new BCRDriverStation(1);
	
	public OI()
	{
		controller.getBackJoystickButton().toggleWhenActive(new StopDriving());
	}
	
	public BCRXbox getXboxController()
	{
		return controller;
	}
	public BCRDriverStation getDriverStation()
	{
		return driverStation;
	}
}
