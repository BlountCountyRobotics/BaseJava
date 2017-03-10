package org.usfirst.frc.team4504.robot.objects;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class BCRADXR extends ADXRS450_Gyro{
	
	public BCRADXR(Port port)
	{
		super(port);
	}
	
}
