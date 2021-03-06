package org.usfirst.frc.team4504.robot;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// Motors: 
	public static final int frontRight = 0;
	public static final int backRight = 1;
	public static final int frontLeft = 2;
	public static final int backLeft = 3;
	
	// Ports:
	public static final Port ahrsPort = Port.kOnboardCS0;
	
	// Global variables:
	public static final boolean driveTrainEncodersExist = false;
	public static final int encoderType = 0;
	public static final int pulsesPerRev = 128;
	public class EncoderType
	{
		public static final int quadrature = 1;
		public static final int analog = 2;
	}
	// for four or six wheels
	public static final boolean frontLeftInverted = false;
	public static final boolean midLeftInverted = false;
	public static final boolean backLeftInverted = false;
	public static final boolean frontRightInverted = false;
	public static final boolean midRightInverted = false;
	public static final boolean backRightInverted = false;
	
	public static final boolean frontLeftEncInverted = false;
	public static final boolean midLeftEncInverted = false;
	public static final boolean backLeftEncInverted = false;
	public static final boolean frontRightEncInverted = false;
	public static final boolean midRightEncInverted = false;
	public static final boolean backRightEncInverted = false;
	
	//for two wheels
	public static final boolean leftInverted = false;
	public static final boolean rightInverted = false;

	public static final boolean leftEncInverted = false;
	public static final boolean rightEncInverted = false;
}
