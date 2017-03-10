package org.usfirst.frc.team4504.robot;

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
	
	// Global variables:
	public static final int encoderType = 0;
	public static final int pulsesPerRev = 128;
	public class EncoderType
	{
		public static final int quadrature = 1;
		public static final int analog = 2;
	}
	
	
}
