package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRXbox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TankDrive extends BaseDriveTrain {

	public TankDrive(CANTalon left, CANTalon right)
	{
		super(left, right);
	}
	
	public TankDrive(CANTalon left, CANTalon right, double rpm)
	{
		super(left, right, rpm);
	}
	
	public TankDrive(CANTalon frontLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon backRight, double rpm) {
		super(frontLeft, backLeft, frontRight,  backRight, rpm);
	}
	public TankDrive(CANTalon frontLeft, CANTalon backLeft, 
			CANTalon frontRight, CANTalon backRight) {
		super(frontLeft,  backLeft, frontRight, backRight);
	}
	public TankDrive(CANTalon frontLeft, CANTalon midLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon midRight, CANTalon backRight, double rpm) {
		super(frontLeft, midLeft, backLeft, frontRight, midRight, backRight, rpm);
	}
	public TankDrive(CANTalon frontLeft, CANTalon midLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon midRight, CANTalon backRight) {
		super(frontLeft, midLeft, backLeft, frontRight, midRight, backRight);
	}
	
	public void tankDrive(BCRXbox controller)
	{
		double left = controller.getX(Hand.kLeft);
		double right = controller.getX(Hand.kRight);
		if(joystickInputSquared)
		{
			left = squareWithSign(left);
			right = squareWithSign(right);
		}
		tankDrive(left, right);
	}
	
	public void tankDrive(double left, double right)
	{
		setLeftRight(left, right);
	}
	
}
