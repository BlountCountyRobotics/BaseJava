package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRTalon;
import org.usfirst.frc.team4504.robot.objects.BCRXbox;

import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;


/** 
 * This is the base drive train class for 
 * 4504's robots
 * 
 * This accepts 4 or 6 CANTalons, but 
 * provides only bare bones methods for 
 * their actual use. 
 * 
 * Use the subsystems which extend this 
 * class to actually use the drive train
 * 
 * Extend whichever desired class to quickly
 * create a functional drive train. If desired,
 * add encoders & set their parameters
 * 
 */
public abstract class BaseDriveTrain extends Subsystem implements UsableDriveTrain {
	
	public class DriveType
	{
		public static final int fourWheel = 1;
		public static final int sixWheel = 2;
		public static final int twoWheel = 3;
	}
	
	public class Motors
	{
		// for two motors
		public static final int right = 0;
		public static final int left = 1;
		
		// for four and six
		public static final int frontRight = 0;
		public static final int backRight = 2;
		public static final int frontLeft = 1;
		public static final int backLeft = 3;
		
		// for six 
		public static final int midRight = 4;
		public static final int midLeft = 5;
	}

	protected boolean triggerIncreasesSpeed = true;
	protected double eachTriggerEffect = .2;
	
	protected boolean joystickInputSquared = true;
	protected int driveType;
	protected BCRTalon[] motors;
	protected boolean usingEncoders;
	protected double rpm;
	protected int numMotors;
	protected int[] inverted;
	public BaseDriveTrain(BCRTalon left, BCRTalon right)
	{
		super();
		driveType = DriveType.twoWheel;
		numMotors = 2;
		motors = new BCRTalon[numMotors];
		usingEncoders = false;
		motors[Motors.left] = left;
		motors[Motors.right] = right;
		
		inverted = new int[] {1,1};
		
		
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
			{
				motors = null;
				throw new NullPointerException("Motors inputted to DriveTrain cannot be null");
			}
		}
	}
	
	
	public BaseDriveTrain(BCRTalon frontLeft, BCRTalon backLeft, 
			BCRTalon frontRight, BCRTalon backRight)
	{
		driveType = DriveType.fourWheel;
		numMotors = 4;
		motors = new BCRTalon[numMotors];
		usingEncoders = false;
		motors[Motors.backLeft] = backLeft;
		motors[Motors.backRight] = backRight;
		motors[Motors.frontLeft] = frontLeft;
		motors[Motors.frontRight] = frontRight;
		
		inverted = new int[] {1,1,1,1};
		
		
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
			{
				motors = null;
				throw new NullPointerException("Motors inputted to DriveTrain cannot be null");
			}
		}
	}
	

	
	public BaseDriveTrain(BCRTalon frontLeft, BCRTalon midLeft, 
			BCRTalon backLeft, BCRTalon frontRight, 
			BCRTalon midRight, BCRTalon backRight)
	{
		driveType = DriveType.sixWheel;
		numMotors = 6;
		motors = new BCRTalon[numMotors];
		usingEncoders = false;
		motors[Motors.backLeft] = backLeft;
		motors[Motors.backRight] = backRight;
		motors[Motors.frontLeft] = frontLeft;
		motors[Motors.frontRight] = frontRight;
		motors[Motors.midLeft] = midLeft;
		motors[Motors.midRight] = midRight;
		
		inverted = new int[] {1,1,1,1,1,1};
		
		
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
			{
				motors = null;
				throw new NullPointerException("Motors cannot be null");
			}
		}
	}

	protected boolean nullMotorCheck()
	{
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
				return true;
		}
		return false;
	}
	
	// Adapted from RobotDrive
	public void drive(double input, double curve)
	{
		double leftOutput, rightOutput;
		
		if(curve < 0) {
			double value = Math.log(-curve);
			double ratio = (value - .5) / (value + .5);
			
			if(ratio == 0) {
				ratio = .0000000001;
			}
		
			leftOutput = input / ratio;
			rightOutput = input;
		}else if(curve > 0) {
			double value = Math.log(curve);
			double ratio = (value - .5) / (value + .5);
			
			if(ratio == 0) {
				ratio = .0000000001;
			}
			
			leftOutput = input;
			rightOutput = input/ ratio;
		} else {
			leftOutput = input;
			rightOutput = input;
		}
		
		setLeftRight(leftOutput, rightOutput);
	}
	
	public void drive(double input)
	{
		setLeftRight(input,input);
	}
	
	
	public void setLeftRight(double left, double right)
	{
		if(nullMotorCheck() == true)
		{
			throw new NullPointerException("CANTalon motors cannot be null.");
		}
		double trueRightOutput = limit(right);
		double trueLeftOutput = limit(left);

		if(usingEncoders)
		{
			// Scale to rpm
			trueRightOutput = trueRightOutput * rpm;
			trueLeftOutput = trueLeftOutput * rpm;
		}
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
		{
			set(Motors.backLeft,trueLeftOutput * inverted[Motors.backLeft]);
			set(Motors.frontLeft,trueLeftOutput * inverted[Motors.frontLeft]);
			set(Motors.backRight,trueRightOutput * inverted[Motors.backRight]);
			set(Motors.frontRight,trueRightOutput * inverted[Motors.frontRight]);
			if(driveType == DriveType.sixWheel)
			{
				set(Motors.midLeft,trueLeftOutput * inverted[Motors.midLeft]);
				set(Motors.midRight,trueRightOutput * inverted[Motors.midRight]);
			}
		}else if(driveType == DriveType.twoWheel)
		{
			set(Motors.left,trueLeftOutput * inverted[Motors.left]);
			set(Motors.right,trueRightOutput * inverted[Motors.right]);
		}
	}
	
	public void stop()
	{
		for(int x = 0; x < motors.length; x++)
		{
			motors[x].set(0.0);
		}
	}
	
	protected void set(int motor, double speed)
	{
		//assuming all units pre-scaled
		if(motor < numMotors)
		{
			if(usingEncoders)
			{
				motors[motor].setRPM(speed);
			}else
			{
				motors[motor].setPercent(speed);
			}
		}
	}
	
	
	public int getDriveType() {
		return driveType;
	}
	
	public boolean isUsingEncoders()
	{
		return usingEncoders;
	}
	
	public void isUsingEncoders(boolean usingEncoders)
	{
		this.usingEncoders = usingEncoders;
	}
	public int numMotors()
	{
		return numMotors;
	}
	
	public void setInverted(int motor, boolean inverted)
	{
		if(motor < numMotors)
			this.inverted[motor] = inverted ? -1 : 1;
	}
	
	public void setEncInverted(int motor, boolean inverted)
	{
		if(motor < numMotors)
			motors[motor].reverseSensor(inverted);
	}
	
	public boolean getInverted(int motor)
	{
		if(motor < numMotors)
		{
			return inverted[motor] == -1;
		}
		return false;
	}
	
	public int[] getInverted()
	{
		return inverted.clone();
	}
	
	protected double squareWithSign(double input)
	{
		double squared = input;
		if(squared < 0.0)
		{
			squared = -Math.pow(squared, 2);
		}else
		{
			squared = Math.pow(squared, 2);
		}
		return squared;
	}
	
	//From RobotDrive
	protected void normalize(double wheelSpeeds[])
	{
		if(wheelSpeeds.length != numMotors)
		{
			return;
		}
		double maxMagnitude = Math.abs(wheelSpeeds[0]);
		int i;
		for(i = 1; i < numMotors; i++) {
			double temp = Math.abs(wheelSpeeds[i]);
			if(maxMagnitude < temp) maxMagnitude = temp;
		}
		if(maxMagnitude > 1.0) {
			for(i = 0; i < numMotors; i++) {
				wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
			}
		}
	}
	
	protected double limit(double input)
	{
		double output = input;
		if(output > 1.0)
		{
			output = 1.0;
		}else if(output < -1.0)
		{
			output = -1.0;
		}
		return output;
	}
	
	public boolean isJoystickInputSquared()
	{
		return joystickInputSquared;
	}
	public void setJoystickInputSquared(boolean joystickInputSquared)
	{
		this.joystickInputSquared = joystickInputSquared;
	}
	public void setMaxRpm(double rpm)
	{
		this.rpm = rpm;
	}
	public double getMaxRpm()
	{
		return rpm;
	}
	
	public void setEncoder(FeedbackDevice encoder)
	{
		for(int x = 0; x < motors.length; x++)
		{
			motors[x].setFeedbackDevice(encoder);
		}
	}
	
	public void setEncoderPulsePerRev(int pulsesPerRev)
	{
		for(int x = 0; x < motors.length; x++)
		{
			motors[x].setEncPulsesPerRev(pulsesPerRev);
		}
	}
	
	public boolean triggerIncreasesSpeed()
	{
		return triggerIncreasesSpeed;
	}
	
	public void setTriggerIncreasesSpeed(boolean doesTriggerIncreaseSpeed)
	{
		this.triggerIncreasesSpeed = doesTriggerIncreaseSpeed;
	}
	public double getEachTriggerEffect()
	{
		return eachTriggerEffect;
	}
	public void setEachTriggerEffect(double eachTriggerEffect)
	{
		this.eachTriggerEffect = eachTriggerEffect;
	}
	
	protected double effectWithTrigger(double input, BCRXbox controller)
	{
		double factor = 1.0;
		factor -= 2 * eachTriggerEffect; // there are two triggers

		if(controller.getTriggerAxis(Hand.kLeft) >= .75)
			factor += eachTriggerEffect;		
		if(controller.getTriggerAxis(Hand.kRight) >= .75)
			factor += eachTriggerEffect;
		
		factor = limit(factor); //make sure it isn't below 0.0
		return input * factor;
	}
	public BCRTalon getMotor(int motor)
	{
		if(motor < numMotors)
			return motors[motor];
		return null;
	}
	public double getMotorRPM(int motor)
	{
		if(motor < numMotors)
			return motors[motor].getRPM();
		return 0.0;
	}
	public double getMotorOutput(int motor)
	{
		if(motor < numMotors)
			return motors[motor].getOutputVoltage() / motors[motor].getBusVoltage();
		return 0.0;
	}
}
