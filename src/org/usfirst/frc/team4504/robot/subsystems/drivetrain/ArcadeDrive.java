package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRTalon;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;


import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Extend this class for a
 * robot with arcade drive
 */ 

public abstract class ArcadeDrive extends BaseDriveTrain {
	public ArcadeDrive(BCRTalon left, BCRTalon right)
	{
		super(left, right);
	}
	
	
	public ArcadeDrive(BCRTalon frontLeft, BCRTalon backLeft, 
			BCRTalon frontRight, BCRTalon backRight) {
		super(frontLeft,  backLeft, frontRight, backRight);
	}
	public ArcadeDrive(BCRTalon frontLeft, BCRTalon midLeft, 
			BCRTalon backLeft, BCRTalon frontRight, 
			BCRTalon midRight, BCRTalon backRight) {
		super(frontLeft, midLeft, backLeft, frontRight, midRight, backRight);
	}
	
	public void arcadeDrive(BCRXbox controller)
	{
		input(controller);
	}
	
	public void input(BCRXbox controller)
	{
		double move = getJoystickInput(controller.getY(Hand.kLeft), controller);
		double rotate = getJoystickInput(controller.getX(Hand.kLeft), controller);
		
		arcadeDrive(move, rotate);
	}
	
	public void arcadeDrive(double move, double rotate)
	{
		double left;
		double right;
		
		double moveValue = limit(move);
		double rotateValue = limit(rotate);

		if(moveValue > 0.0) {
			if(rotateValue > 0.0) {
				left = moveValue - rotateValue;
				right = Math.max(moveValue, rotateValue);
			} else {
				left = Math.max(moveValue, -rotateValue);
				right = moveValue + rotateValue;
			}
		} else {
			if(rotateValue > 0.0) {
				left = -Math.max(-moveValue, rotateValue);
				right = moveValue + rotateValue;
			} else {
				left = moveValue - rotateValue;
				right = -Math.max(-moveValue, -rotateValue);
			}
		}
		setLeftRight(left, right);
	}
	
}
