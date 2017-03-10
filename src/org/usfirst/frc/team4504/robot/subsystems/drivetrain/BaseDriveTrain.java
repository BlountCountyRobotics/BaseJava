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
	}
	
	public class Motors
	{
		// for four motors and six
		public static final int frontRight = 0;
		public static final int backRight = 1;
		public static final int frontLeft = 2;
		public static final int backLeft = 3;
		
		// for six motors only
		public static final int midRight = 4;
		public static final int midLeft = 5;
	}

	private int driveType;
	private CANTalon[] motors;
	private boolean usingEncoders;
	private double rpm;
	private int numMotors;
	private int[] inverted;
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
	
	public void drive(double input, double curve)
	{
	    double leftOutput, rightOutput;

	    if (curve < 0) {
	      double value = Math.log(-curve);
	      double ratio = (value - .5) / (value + .5);
	      if (ratio == 0) {
	        ratio = .0000000001;
	      }
	      leftOutput = input / ratio;
	      rightOutput = input;
	    } else if (curve > 0) {
	      double value = Math.log(curve);
	      double ratio = (value - .5) / (value + .5);
	      if (ratio == 0) {
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
		double trueRightOutput = right;
		double trueLeftOutput = left;

		if(usingEncoders)
		{
			trueRightOutput *= rpm; // scale -1.0 to 1.0 to being an rpm setpoint
			trueLeftOutput *= rpm;
		}
		
		motors[Motors.backLeft].set(trueLeftOutput * inverted[Motors.backLeft]);
		motors[Motors.frontLeft].set(trueLeftOutput * inverted[Motors.frontLeft]);
		motors[Motors.backRight].set(trueRightOutput * inverted[Motors.backRight]);
		motors[Motors.frontRight].set(trueRightOutput * inverted[Motors.frontRight]);
		if(driveType == DriveType.sixWheel)
		{
			motors[Motors.midLeft].set(trueLeftOutput * inverted[Motors.midLeft]);
			motors[Motors.midRight].set(trueRightOutput * inverted[Motors.midRight]);
		}
	}
	
	private void setTalonControlMode()
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
	
	public int[] getInverted()
	{
		return inverted.clone();
	}
}

