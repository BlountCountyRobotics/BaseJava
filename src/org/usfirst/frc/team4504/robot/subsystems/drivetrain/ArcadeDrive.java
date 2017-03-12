package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRXbox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Extend this class for a
 * robot with arcade drive
 */

public abstract class ArcadeDrive extends BaseDriveTrain {
	public ArcadeDrive(CANTalon left, CANTalon right)
	{
		super(left, right);
	}
	
	
	public ArcadeDrive(CANTalon frontLeft, CANTalon backLeft, 
			CANTalon frontRight, CANTalon backRight) {
		super(frontLeft,  backLeft, frontRight, backRight);
	}
	public ArcadeDrive(CANTalon frontLeft, CANTalon midLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon midRight, CANTalon backRight) {
		super(frontLeft, midLeft, backLeft, frontRight, midRight, backRight);
	}
	
	public void arcadeDrive(BCRXbox controller)
	{
		double move = controller.getY(Hand.kLeft);
		double rotate = controller.getX(Hand.kLeft);
		
		move = limit(move);
		rotate = limit(rotate);
		
		if(joystickInputSquared)
		{
			move = squareWithSign(move);
			rotate = squareWithSign(rotate);
		}
		
		if(triggerIncreasesSpeed)
		{
			move = effectWithTrigger(move, controller);
			rotate = effectWithTrigger(rotate, controller);
		}
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
