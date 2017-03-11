package org.usfirst.frc.team4504.robot.subsystems.drivetrain;


import org.usfirst.frc.team4504.robot.RobotMap;
import org.usfirst.frc.team4504.robot.RobotMap.EncoderType;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;


/**
 * This is the base drive train for 4504's robots
 * 
 * This accepts 4 or 6 CANTalons, but 
 * provides only bare bones methods for 
 * their actual use. 
 * 
 * Use the subsystems which extend this 
 * class to actually use the drive train
 * 
 */
public class BaseDriveTrain {
	
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

	protected boolean joystickInputSquared;
	protected int driveType;
	protected CANTalon[] motors;
	protected boolean usingEncoders;
	protected double rpm;
	protected int numMotors;
	protected int[] inverted;
	public BaseDriveTrain(CANTalon left, CANTalon right)
	{
		driveType = DriveType.twoWheel;
		numMotors = 2;
		motors = new CANTalon[numMotors];
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
	
	@SuppressWarnings("unused")
	public BaseDriveTrain(CANTalon left, CANTalon right, double rpm)
	{
		this(left, right);
		usingEncoders = true;
		this.rpm = rpm;
		setEncInverted(RobotMap.frontLeftEncInverted, RobotMap.backLeftEncInverted, 
				RobotMap.frontRightEncInverted, RobotMap.backRightEncInverted);
		for(int x = 0; x < motors.length; x++)
		{
			if(RobotMap.encoderType == EncoderType.quadrature)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.QuadEncoder); // assuming quad encoders
				motors[x].configEncoderCodesPerRev(RobotMap.pulsesPerRev);
			}else if(RobotMap.encoderType == EncoderType.analog)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.AnalogEncoder);
			}
		}
		
		
		// set these as constants before driving, using the manual
		motors[Motors.frontLeft].setP(0.0);
		motors[Motors.frontLeft].setI(0.0);
		motors[Motors.frontLeft].setD(0.0);
		motors[Motors.frontLeft].setF(0.0);

		motors[Motors.frontRight].setP(0.0);
		motors[Motors.frontRight].setI(0.0);
		motors[Motors.frontRight].setD(0.0);
		motors[Motors.frontRight].setF(0.0);
	
		motors[Motors.backLeft].setP(0.0);
		motors[Motors.backLeft].setI(0.0);
		motors[Motors.backLeft].setD(0.0);
		motors[Motors.backLeft].setF(0.0);
		
		motors[Motors.backRight].setP(0.0);
		motors[Motors.backRight].setI(0.0);
		motors[Motors.backRight].setD(0.0);
		motors[Motors.backRight].setF(0.0);
	}
	
	public BaseDriveTrain(CANTalon frontLeft, CANTalon backLeft, 
			CANTalon frontRight, CANTalon backRight)
	{
		driveType = DriveType.fourWheel;
		numMotors = 4;
		motors = new CANTalon[numMotors];
		usingEncoders = false;
		motors[Motors.backLeft] = backLeft;
		motors[Motors.backRight] = backRight;
		motors[Motors.frontLeft] = frontLeft;
		motors[Motors.frontRight] = frontRight;
		
		inverted = new int[] {1,1,1,1};
		
		setInverted(RobotMap.frontLeftInverted, RobotMap.backLeftInverted, 
				RobotMap.frontRightInverted, RobotMap.backRightInverted);
		
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
			{
				motors = null;
				throw new NullPointerException("Motors inputted to DriveTrain cannot be null");
			}
		}
	}
	
	@SuppressWarnings("unused")
	public BaseDriveTrain(CANTalon frontLeft, CANTalon backLeft, 
			CANTalon frontRight, CANTalon backRight, double rpm)
	{
		this(frontLeft, backLeft, frontRight, backRight);
		usingEncoders = true;
		this.rpm = rpm;
		setEncInverted(RobotMap.frontLeftEncInverted, RobotMap.backLeftEncInverted, 
				RobotMap.frontRightEncInverted, RobotMap.backRightEncInverted);
		for(int x = 0; x < motors.length; x++)
		{
			if(RobotMap.encoderType == EncoderType.quadrature)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.QuadEncoder); // assuming quad encoders
				motors[x].configEncoderCodesPerRev(RobotMap.pulsesPerRev);
			}else if(RobotMap.encoderType == EncoderType.analog)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.AnalogEncoder);
			}
		}
		
		
		// set these as constants before driving, using the manual
		motors[Motors.frontLeft].setP(0.0);
		motors[Motors.frontLeft].setI(0.0);
		motors[Motors.frontLeft].setD(0.0);
		motors[Motors.frontLeft].setF(0.0);

		motors[Motors.frontRight].setP(0.0);
		motors[Motors.frontRight].setI(0.0);
		motors[Motors.frontRight].setD(0.0);
		motors[Motors.frontRight].setF(0.0);
	
		motors[Motors.backLeft].setP(0.0);
		motors[Motors.backLeft].setI(0.0);
		motors[Motors.backLeft].setD(0.0);
		motors[Motors.backLeft].setF(0.0);
		
		motors[Motors.backRight].setP(0.0);
		motors[Motors.backRight].setI(0.0);
		motors[Motors.backRight].setD(0.0);
		motors[Motors.backRight].setF(0.0);
	}
	

	public BaseDriveTrain(CANTalon frontLeft, CANTalon midLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon midRight, CANTalon backRight)
	{
		driveType = DriveType.sixWheel;
		numMotors = 6;
		motors = new CANTalon[numMotors];
		usingEncoders = false;
		motors[Motors.backLeft] = backLeft;
		motors[Motors.backRight] = backRight;
		motors[Motors.frontLeft] = frontLeft;
		motors[Motors.frontRight] = frontRight;
		motors[Motors.midLeft] = midLeft;
		motors[Motors.midRight] = midRight;
		
		inverted = new int[] {1,1,1,1,1,1};
		

		setInverted(RobotMap.frontLeftInverted, RobotMap.midLeftInverted,
				RobotMap.backLeftInverted, 	RobotMap.frontRightInverted, 
				RobotMap.midRightInverted, RobotMap.backRightInverted);
		
		for(int x = 0; x < motors.length; x++)
		{
			if(motors[x] == null)
			{
				motors = null;
				throw new NullPointerException("Motors cannot be null");
			}
		}
	}

	@SuppressWarnings("unused")
	public BaseDriveTrain(CANTalon frontLeft, CANTalon midLeft, 
			CANTalon backLeft, CANTalon frontRight, 
			CANTalon midRight, CANTalon backRight, double rpm)
	{
		this(frontLeft, midLeft, backLeft, frontRight, midRight, backRight);
		usingEncoders = true;
		this.rpm = rpm;
		
		setEncInverted(RobotMap.frontLeftEncInverted, RobotMap.midLeftEncInverted, 
				RobotMap.backLeftEncInverted, RobotMap.frontRightEncInverted, 
				RobotMap.midRightEncInverted, RobotMap.backRightEncInverted);
		
		for(int x = 0; x < motors.length; x++)
		{
			if(RobotMap.encoderType == EncoderType.quadrature)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.QuadEncoder); // assuming quad encoders
				motors[x].configEncoderCodesPerRev(RobotMap.pulsesPerRev);
			}else if(RobotMap.encoderType == EncoderType.analog)
			{
				motors[x].setFeedbackDevice(FeedbackDevice.AnalogEncoder);
			}
		}
		
		
		// set these as constants before driving, using the manual
		motors[Motors.frontLeft].setP(0.0);
		motors[Motors.frontLeft].setI(0.0);
		motors[Motors.frontLeft].setD(0.0);
		motors[Motors.frontLeft].setF(0.0);

		motors[Motors.frontRight].setP(0.0);
		motors[Motors.frontRight].setI(0.0);
		motors[Motors.frontRight].setD(0.0);
		motors[Motors.frontRight].setF(0.0);
	
		motors[Motors.backLeft].setP(0.0);
		motors[Motors.backLeft].setI(0.0);
		motors[Motors.backLeft].setD(0.0);
		motors[Motors.backLeft].setF(0.0);
		
		motors[Motors.backRight].setP(0.0);
		motors[Motors.backRight].setI(0.0);
		motors[Motors.backRight].setD(0.0);
		motors[Motors.backRight].setF(0.0);
		
		motors[Motors.midLeft].setP(0.0);
		motors[Motors.midLeft].setI(0.0);
		motors[Motors.midLeft].setD(0.0);
		motors[Motors.midLeft].setF(0.0);
		
		motors[Motors.midRight].setP(0.0);
		motors[Motors.midRight].setI(0.0);
		motors[Motors.midRight].setD(0.0);
		motors[Motors.midRight].setF(0.0);
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
		setTalonControlMode();
		double trueRightOutput = limit(right);
		double trueLeftOutput = limit(left);

		if(usingEncoders)
		{
			trueRightOutput *= rpm; // scale -1.0 to 1.0 to being an rpm setpoint
			trueLeftOutput *= rpm;
		}
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
		{
			motors[Motors.backLeft].set(trueLeftOutput * inverted[Motors.backLeft]);
			motors[Motors.frontLeft].set(trueLeftOutput * inverted[Motors.frontLeft]);
			motors[Motors.backRight].set(trueRightOutput * inverted[Motors.backRight]);
			motors[Motors.frontRight].set(trueRightOutput * inverted[Motors.frontRight]);
			if(driveType == DriveType.sixWheel)
			{
				motors[Motors.midLeft].set(trueLeftOutput * inverted[Motors.midLeft]);
				motors[Motors.midRight].set(trueRightOutput * inverted[Motors.midRight]);
			}
		}else if(driveType == DriveType.twoWheel)
		{
			motors[Motors.left].set(trueLeftOutput * inverted[Motors.left]);
			motors[Motors.right].set(trueRightOutput * inverted[Motors.right]);
		}
	}
	
	protected void setTalonControlMode()
	{
		if(usingEncoders)
		{
			for(int x = 0; x < motors.length; x++)
			{
				motors[x].changeControlMode(TalonControlMode.Speed);
			}
		}else
		{
			for(int x = 0; x < motors.length; x++)
			{
				motors[x].changeControlMode(TalonControlMode.PercentVbus);
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
	
	public void setInverted(boolean frontLeft, boolean backLeft, 
			boolean frontRight, boolean backRight)
	{
		setFrontLeftInverted(frontLeft);
		setBackLeftInverted(backLeft);
		setFrontRightInverted(frontRight);
		setBackRightInverted(backRight);
	}
	
	public void setInverted(boolean frontLeft, boolean midLeft, 
			boolean backLeft, boolean frontRight,
			boolean midRight, boolean backRight)
	{
		setInverted(frontLeft, backLeft, frontRight, backRight);
		setMidRightInverted(midRight);
		setMidLeftInverted(midLeft);
	}
	
	public void setFrontLeftInverted(boolean inverted)
	{
		this.inverted[Motors.frontLeft] = inverted ? -1 : 1;
	}
	
	public void setBackLeftInverted(boolean inverted)
	{
		this.inverted[Motors.backLeft] = inverted ? -1 : 1;
	}
	
	public void setMidLeftInverted(boolean inverted)
	{
		if(driveType == DriveType.sixWheel)
			this.inverted[Motors.midLeft] = inverted ? -1 : 1;
	}
	
	public void setBackRightInverted(boolean inverted)
	{
		this.inverted[Motors.backRight] = inverted ? -1 : 1;
	}
	
	public void setMidRightInverted(boolean inverted)
	{
		if(driveType == DriveType.sixWheel)
			this.inverted[Motors.midRight] = inverted ? -1 : 1;
	}
	
	public void setFrontRightInverted(boolean inverted)
	{
		this.inverted[Motors.frontRight] = inverted ? -1 : 1;
	}
	
	public void setEncInverted(boolean frontLeft, boolean backLeft, 
			boolean frontRight, boolean backRight)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
		{
			setFrontLeftEncInverted(frontLeft);
			setBackLeftEncInverted(backLeft);
			setFrontRightEncInverted(frontRight);
			setBackRightEncInverted(backRight);
		}
	}
	
	public void setEncInverted(boolean frontLeft, boolean midLeft, 
			boolean backLeft, boolean frontRight,
			boolean midRight, boolean backRight)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
		{
			setEncInverted(frontLeft, backLeft, frontRight, backRight);
			setMidRightEncInverted(midRight);
			if(driveType == DriveType.sixWheel)
				setMidLeftEncInverted(midLeft);
		}
	}
	
	public void setFrontLeftEncInverted(boolean inverted)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
			this.motors[Motors.frontLeft].reverseSensor(inverted);
	}
	
	public void setBackLeftEncInverted(boolean inverted)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
			this.motors[Motors.backLeft].reverseSensor(inverted);
	}
	
	public void setMidLeftEncInverted(boolean inverted)
	{
		if(driveType == DriveType.sixWheel)
			this.motors[Motors.midLeft].reverseSensor(inverted);
	}
	
	public void setBackRightEncInverted(boolean inverted)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
			this.motors[Motors.backRight].reverseSensor(inverted);
	}
	
	public void setMidRightEncInverted(boolean inverted)
	{
		if(driveType == DriveType.sixWheel)
			this.motors[Motors.midRight].reverseSensor(inverted);
	}
	
	public void setFrontRightEncInverted(boolean inverted)
	{
		if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
			this.motors[Motors.frontRight].reverseSensor(inverted);
	}
	
	public void setLeftInverted(boolean inverted)
	{
		this.inverted[Motors.left] = inverted ? -1 : 1;
	}
	public void setRightInverted(boolean inverted)
	{
		this.inverted[Motors.right] = inverted ? -1 : 1;
	}
	
	public void setLeftEncInverted(boolean inverted)
	{
		this.motors[Motors.left].reverseSensor(inverted);
	}
	public void setRightEncInverted(boolean inverted)
	{
		this.motors[Motors.right].reverseSensor(inverted);
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
	
	public boolean joystickInputSquared()
	{
		return joystickInputSquared;
	}
	public void joystickInputSquared(boolean joystickInputSquared)
	{
		this.joystickInputSquared = joystickInputSquared;
	}

}

