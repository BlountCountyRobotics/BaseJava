package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRGyro;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class MecanumDrive extends BaseDriveTrain {
	
	private boolean usingGyro;
	private BCRGyro gyro;
	
	
	public MecanumDrive(CANTalon frontLeft, CANTalon backLeft, CANTalon frontRight, CANTalon backRight) {
		super(frontLeft, backLeft, frontRight, backRight);
		usingGyro = false;
	}
	public MecanumDrive(CANTalon frontLeft, CANTalon backLeft, CANTalon frontRight, CANTalon backRight, BCRGyro gyro) {
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
		double x = controller.getX(Hand.kLeft);
		double y = controller.getY(Hand.kLeft);
		double rotation = controller.getX(Hand.kRight);
		if(joystickInputSquared)
		{
			x = squareWithSign(x);
			y = squareWithSign(y);
			rotation = squareWithSign(rotation);
		}
		mecanumDrive(x, y, rotation);
	}
	
	// Adapted from RobotDrive
	public void mecanumDrive(double x, double y, double rotation)
	{
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
		setTalonControlMode(); 
		if(usingEncoders)
		{
			for(int i = 0; i < speeds.length; i++)
			{
				speeds[i] *= rpm;
			}
		}
		
		motors[Motors.frontRight].set(speeds[Motors.frontRight] * inverted[Motors.frontRight]);
		motors[Motors.backRight].set(speeds[Motors.backRight] * inverted[Motors.backRight]);
		motors[Motors.frontLeft].set(speeds[Motors.frontLeft] * inverted[Motors.frontLeft]);
		motors[Motors.backLeft].set(speeds[Motors.backLeft] * inverted[Motors.backLeft]);
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
