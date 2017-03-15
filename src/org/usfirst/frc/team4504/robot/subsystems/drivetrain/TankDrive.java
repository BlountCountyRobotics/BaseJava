package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRTalon;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;


import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Extend this class for a
 * robot with tank drive
 */

public abstract class TankDrive extends BaseDriveTrain {

	public TankDrive(BCRTalon left, BCRTalon right)
	{
		super(left, right);
	}
	
	
	public TankDrive(BCRTalon frontLeft, BCRTalon backLeft, 
			BCRTalon frontRight, BCRTalon backRight) {
		super(frontLeft,  backLeft, frontRight, backRight);
	}
	public TankDrive(BCRTalon frontLeft, BCRTalon midLeft, 
			BCRTalon backLeft, BCRTalon frontRight, 
			BCRTalon midRight, BCRTalon backRight) {
		super(frontLeft, midLeft, backLeft, frontRight, midRight, backRight);
	}
	
	public void tankDrive(BCRXbox controller)
	{
		input(controller);
	}
	
	public void input(BCRXbox controller)
	{
		double left = controller.getX(Hand.kLeft);
		double right = controller.getX(Hand.kRight);
		if(joystickInputSquared)
		{
			left = squareWithSign(left);
			right = squareWithSign(right);
		}
		
		if(triggerIncreasesSpeed)
		{
			left = effectWithTrigger(left, controller);
			right = effectWithTrigger(right, controller);
		}
		
		tankDrive(left, right);
	}
	
	public void tankDrive(double left, double right)
	{
		setLeftRight(left, right);
	}
	
}
