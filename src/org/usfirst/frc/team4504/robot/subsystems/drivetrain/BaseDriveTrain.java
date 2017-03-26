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
	
	public static class DriveType
	{
		int value;
		public static final DriveType fourWheel = new DriveType(1);
		public static final DriveType sixWheel = new DriveType(2);
		public static final DriveType twoWheel = new DriveType(3);
		private DriveType(int value)
		{
			this.value = value;
		}
	}
	
	public static class Motors
	{
		int value;
		// for two motors
		public static final Motors right = new Motors(0);
		public static final Motors left = new Motors(1);
		
		// for four and six
		public static final Motors frontRight = new Motors(0);
		public static final Motors backRight = new Motors(2);
		public static final Motors frontLeft = new Motors(1);
		public static final Motors backLeft = new Motors(3);
		
		// for six 
		public static final Motors midRight = new Motors(4);
		public static final Motors midLeft = new Motors(5);
		private Motors(int value)
		{
			this.value = value;
		}
	}

	protected boolean triggerIncreasesSpeed = true;
	protected double eachTriggerEffect = .2;
	
	protected boolean joystickInputSquared = true;
	protected DriveType driveType;
	protected BCRTalon[] motors;
	protected boolean usingEncoders;
	protected double rpm;
	protected int numMotors;
	protected double[] inverted;
	public BaseDriveTrain(BCRTalon left, BCRTalon right)
	{
		super();
		driveType = DriveType.twoWheel;
		numMotors = 2;
		motors = new BCRTalon[numMotors];
		usingEncoders = false;
		motors[Motors.left.value] = left;
		motors[Motors.right.value] = right;
		
		inverted = new double[] {1.0,1.0};
		
		
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
		motors[Motors.backLeft.value] = backLeft;
		motors[Motors.backRight.value] = backRight;
		motors[Motors.frontLeft.value] = frontLeft;
		motors[Motors.frontRight.value] = frontRight;
		
		inverted = new double[] {1.0,1.0,1.0,1.0};
		
		
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
		motors[Motors.backLeft.value] = backLeft;
		motors[Motors.backRight.value] = backRight;
		motors[Motors.frontLeft.value] = frontLeft;
		motors[Motors.frontRight.value] = frontRight;
		motors[Motors.midLeft.value] = midLeft;
		motors[Motors.midRight.value] = midRight;
		
		inverted = new double[] {1.0,1.0,1.0,1.0,1.0,1.0};
		
		
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
			setMotor(Motors.backLeft,trueLeftOutput * inverted[Motors.backLeft.value]);
			setMotor(Motors.frontLeft,trueLeftOutput * inverted[Motors.frontLeft.value]);
			setMotor(Motors.backRight,trueRightOutput * inverted[Motors.backRight.value]);
			setMotor(Motors.frontRight,trueRightOutput * inverted[Motors.frontRight.value]);
			if(driveType == DriveType.sixWheel)
			{
				setMotor(Motors.midLeft,trueLeftOutput * inverted[Motors.midLeft.value]);
				setMotor(Motors.midRight,trueRightOutput * inverted[Motors.midRight.value]);
			}
		}else if(driveType == DriveType.twoWheel)
		{
			setMotor(Motors.left,trueLeftOutput * inverted[Motors.left.value]);
			setMotor(Motors.right,trueRightOutput * inverted[Motors.right.value]);
		}
	}
	
	public void stop()
	{
		for(int x = 0; x < motors.length; x++)
		{
			motors[x].set(0.0);
		}
	}
	
	protected void setMotor(Motors motor, double speed)
	{
		//assuming all units pre-scaled
		if(motor.value < numMotors)
		{
			if(usingEncoders)
			{
				motors[motor.value].setRPM(speed);
			}else
			{
				motors[motor.value].setPercent(speed);
			}
		}
	}
	
	
	public DriveType getDriveType() {
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
	public int getNumMotors()
	{
		return numMotors;
	}
	
	public void setInverted(int motor, boolean inverted)
	{
		if(motor < numMotors)
			this.inverted[motor] = inverted ? -1.0 : 1.0;
	}
	
	public void setEncInverted(int motor, boolean inverted)
	{
		if(motor < numMotors)
			motors[motor].reverseSensor(inverted);
	}
	
	public boolean isInverted(int motor)
	{
		if(motor < numMotors)
		{
			return inverted[motor] == -1.0;
		}
		return false;
	}
	
	public double[] getInverted()
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
	
	public void setEncoderPulsesPerRev(int pulsesPerRev)
	{
		for(int x = 0; x < motors.length; x++)
		{
			motors[x].setEncPulsesPerRev(pulsesPerRev);
		}
	}
	
	public boolean getTriggerIncreasesSpeed()
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
		factor -= 2.0 * eachTriggerEffect; // there are two triggers

		factor += eachTriggerEffect * controller.getTriggerAxis(Hand.kLeft);
		factor += eachTriggerEffect * controller.getTriggerAxis(Hand.kRight);
		
		factor = limit(factor); //make sure it isn't below 0.0
		return input * factor;
	}
	public BCRTalon getMotor(Motors motor)
	{
		if(motor.value < numMotors)
			return motors[motor.value];
		return null;
	}
	public double getMotorRPM(Motors motor)
	{
		if(motor.value < numMotors)
			return motors[motor.value].getRPM();
		return 0.0;
	}
	protected double[] getJoystickInputs(double[] inputs, BCRXbox controller)
	{
		double[] outputs = new double[inputs.length];
		for(int x = 0; x < inputs.length; x++)
		{
			outputs[x] = getJoystickInput(inputs[x], controller);
		}
		return outputs;
	}
	protected double getJoystickInput(double input, BCRXbox controller)
	{
		if(joystickInputSquared)
		{
			input = squareWithSign(input);
		}
		
		if(triggerIncreasesSpeed)
		{
			input = effectWithTrigger(input, controller);
		}
		return input;
	}
	public double getMotorOutput(Motors motor)
	{
		if(motor.value < numMotors)
			return motors[motor.value].getOutputVoltage() / motors[motor.value].getBusVoltage();
		return 0.0;
	}
	public BCRTalon[] getMotors()
	{
		return motors;
	}
}
