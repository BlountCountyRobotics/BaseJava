package org.usfirst.frc.team4504.robot.objects;

/*
 * This class is to simplify the
 * fact that there can be either 
 * the AHRS or the ADXR gyroscope 
 * sent to subsystems such as the 
 * DriveTrain, which just needs 
 * to know the angle and nothing
 * more. 
 */

public class BCRGyro {
	
	private BCRADXR adxr;
	private BCRAHRS ahrs;
	private int gyroType;
	private static class GyroType
	{
		static final int adxr = 1;
		static final int ahrs = 2;
	}
	public BCRGyro(BCRADXR adxr)
	{
		this.adxr = adxr;
		this.gyroType = GyroType.adxr;
	}
	public BCRGyro(BCRAHRS ahrs)
	{
		this.ahrs = ahrs;
		this.gyroType = GyroType.ahrs;
	}
	
	public double getAngle()
	{
		if(gyroType == GyroType.adxr)
		{
			return adxr.getAngle();
		}else
		{
			return ahrs.getAngle();
		}
	}
	public double getRate()
	{
		if(gyroType == GyroType.adxr)
		{
			return adxr.getRate();
		}else
		{
			return ahrs.getRate();
		}
	}
	public double getRealAngle()
	{
		if(gyroType == GyroType.adxr)
		{
			return adxr.getRealAngle();
		}else
		{
			return ahrs.getRealAngle();
		}
		
	}
}