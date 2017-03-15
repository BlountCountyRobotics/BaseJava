package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRGyro;
import org.usfirst.frc.team4504.robot.objects.BCRTalon;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;


import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Extend this class for a 
 * robot with mecanum drive
 */ 
 
public abstract class MecanumDrive extends BaseDriveTrain  {
	
	private boolean usingGyro;
	private BCRGyro gyro;
	
	
	public MecanumDrive(BCRTalon frontLeft, BCRTalon backLeft, BCRTalon frontRight, BCRTalon backRight) {
		super(frontLeft, backLeft, frontRight, backRight);
		usingGyro = false;
	}
	public MecanumDrive(BCRTalon frontLeft, BCRTalon backLeft, BCRTalon frontRight, BCRTalon backRight, BCRGyro gyro) {
		super(frontLeft, backLeft, frontRight, backRight);
		this.gyro = gyro;
		usingGyro = true;
	}

	
	// From RobotDrive --- allows driver to always drive from his viewpoint
	protected static double[] rotateVector(double x, double y, double angle) {
		double cosA = Math.cos(angle * (3.14159 / 180.0));
		double sinA = Math.sin(angle * (3.14159 / 180.0));
		double out[] = new double[2];
		out[0] = x * cosA - y * sinA;
		out[1] = x * sinA + y * cosA;
		return out;
	}
	
	public void mecanumDrive(BCRXbox controller)
	{
		input(controller);
	}
	
	public void input(BCRXbox controller)
	{
		double x = controller.getX(Hand.kLeft);
		double y = controller.getY(Hand.kLeft);
		double rotation = controller.getX(Hand.kRight);
		if(joystickInputSquared)
		{
			x = squareWithSign(x);
			y = squareWithSign(y);
			rotation = squareWithSign(rotation);
		}
		
		if(triggerIncreasesSpeed)
		{
			x = effectWithTrigger(x, controller);
			y = effectWithTrigger(y, controller);
			rotation = effectWithTrigger(rotation, controller);
		}
		
		mecanumDrive(x, y, rotation);
	}
	
	// Adapted from RobotDrive
	public void mecanumDrive(double x, double y, double rotation)
	{
		if(nullMotorCheck() == true)
		{
			throw new NullPointerException("CANTalon motors cannot be null.");
		}
		double xInput = x;
		double yInput = y;
		yInput = -yInput;
		
		if(usingGyro && gyro != null)
		{
			double rotated[] = rotateVector(xInput, yInput, gyro.getAngle());
			xInput = rotated[0];
			yInput = rotated[1];
		}
		
		double speeds[] = new double[numMotors];
		speeds[Motors.frontLeft] = xInput + yInput + rotation;
		speeds[Motors.frontRight] = -xInput + yInput - rotation;
		speeds[Motors.backLeft] = -xInput + yInput + rotation;
		speeds[Motors.backRight] = xInput + yInput - rotation;
		normalize(speeds);
		if(usingEncoders)
		{
			for(int i = 0; i < speeds.length; i++)
			{
				// Convert to RPM then 
				speeds[i] = speeds[i] * rpm;
			}
		}
		
		set(Motors.frontRight,speeds[Motors.frontRight] * inverted[Motors.frontRight]);
		set(Motors.backRight,speeds[Motors.backRight] * inverted[Motors.backRight]);
		set(Motors.frontLeft,speeds[Motors.frontLeft] * inverted[Motors.frontLeft]);
		set(Motors.backLeft,speeds[Motors.backLeft] * inverted[Motors.backLeft]);
	}
	
	public boolean usingGyro()
	{
		return usingGyro;
	}
	public void usingGyro(boolean usingGyro)
	{
		this.usingGyro = usingGyro;
	}
}
