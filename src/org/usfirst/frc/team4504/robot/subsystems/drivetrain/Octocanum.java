package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRGyro;
import org.usfirst.frc.team4504.robot.objects.BCRTalon;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public abstract class Octocanum extends MecanumDrive {

	private OctonacumMode mode;
	private Solenoid[] solenoids;
	
	
	public static class OctocanumMode
	{
		int value;
		
		public static final OctocanumMode mecanum = new OctonacumMode(0);
		public static final OctocanumMode tank = new OctonacumMode(1);

		
		private OctocanumMode(int value)
		{
			this.value = value;
		}
	}
	
	public Octocanum(BCRTalon frontLeft, BCRTalon backLeft, 
			BCRTalon frontRight, BCRTalon backRight, 
			BCRGyro gyro, Solenoid[] solenoids) {
		super(frontLeft, backLeft, frontRight, backRight, gyro);
		this.solenoids = solenoids;
	}
	
	public Octocanum(BCRTalon frontLeft, BCRTalon backLeft, 
			BCRTalon frontRight, BCRTalon backRight,
			Solenoid[] solenoids) {
		super(frontLeft, backLeft, frontRight, backRight);
		this.solenoids = solenoids;
	}

	public void switchMode(OctocanumMode mode)
	{
		if(mode == OctocanumMode.mecanum)
		{
			setSolenoids(false);
			this.mode = mode;
		}else if(mode == OctocanumMode.tank)
		{
			setSolenoids(true);
			this.mode = mode;
		}
	}
	
	private void setSolenoids(boolean input)
	{
		for(int x = 0; x < solenoids.length; x++)
		{
			solenoids[x].set(input);
		}
	}
	
	@Deprecated
	public void mecanumDrive(BCRXbox controller)
	{
		
	}
	
	public void octocanum(BCRXbox controller)
	{
		input(controller);
	}
	
	public void input(BCRXbox controller)
	{
		double xLeft = getJoystickInput(controller.getX(Hand.kLeft), controller);
		double yLeft = getJoystickInput(controller.getY(Hand.kLeft), controller);
		double xRight = getJoystickInput(controller.getX(Hand.kRight), controller);
		double yRight = getJoystickInput(controller.getY(Hand.kRight), controller);
		
		if(mode == OctonacumMode.mecanum)
		{
			mecanumDrive(xLeft, yLeft, xRight);
		}else if(mode == OctonacumMode.tank)
		{
			tankDrive(yLeft, yRight);
		}
	}
	
	public void tankDrive(double left, double right)
	{
		setLeftRight(left, right);
	}

}
