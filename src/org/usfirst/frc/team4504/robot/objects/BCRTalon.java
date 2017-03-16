package org.usfirst.frc.team4504.robot.objects;

import com.ctre.CANTalon;

public class BCRTalon extends CANTalon {
	
	int pulsesPerRev;
	
	public BCRTalon(int deviceNumber) {
		super(deviceNumber);
	}
	
	public void setEncPulsesPerRev(int pulsesPerRev)
	{
		this.pulsesPerRev = pulsesPerRev * 4; // 4 edges per, so * 4
	}
	
	public void setRPM(double rpm)
	{
		changeControlMode(TalonControlMode.Speed);
		set(RPMToNative(rpm));
	}
	
	public void setPercent(double percent)
	{
		changeControlMode(TalonControlMode.PercentVbus);
		set(limit(percent));
	}
	private static double limit(double input)
	{
		double output = input;
		if(input > 1.0)
		{
			output = 1.0;
		}
		if(input < -1.0)
		{
			output = -1.0;
		}
		return output;
	}
	
	public double getRPM()
	{
		return nativeToRPM(getSpeed()); //getSpeed returns native units
	}
	
	public double RPMToNative(double rpm)
	{
		return rpm * (1.0 / 60.0) * (1.0 / 10.0) * ((double)pulsesPerRev);
		// rotations/minute * min/sec * sec/100ms * native/rotation = native/100ms (native)
	}
	
	public double nativeToRPM(double nativeUnits)
	{
		return nativeUnits * 10.0 * 60.0 * (1.0 / (double)pulsesPerRev);
		// native/100ms * 100ms/sec * sec/minute * rotation/native = rotation/minute (rpm)
	}
	
	// Don't use API scaling; inconsistent
	@Deprecated
	public void configEncoderCodesPerRev(int codesPerRev){return;}
	
}
