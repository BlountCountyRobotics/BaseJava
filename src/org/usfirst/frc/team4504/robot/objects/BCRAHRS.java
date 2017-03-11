package org.usfirst.frc.team4504.robot.objects;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class BCRAhrs extends AHRS{

	public BCRAhrs(Port spi_port_id) {
		super(spi_port_id);
	}
	
	public BCRAhrs(Port spi_port_id, byte update_rate_hz)
	{
		super(spi_port_id, update_rate_hz);
	} 
	
	public BCRAhrs(edu.wpi.first.wpilibj.SerialPort.Port serial_port_id, SerialDataType data_type, byte update_rate_hz)
	{
		super(serial_port_id, data_type, update_rate_hz);
	}
	
	public BCRAhrs(Port spi_port, int spi_bitrate, byte update_rate_hz)
	{
		super(spi_port, spi_bitrate, update_rate_hz);
	}
	
	public double getRealAngle()
	{
		double angle = getAngle();
		int revolutions = Math.abs((int) angle / 180);
		if(angle < 0.0)
		{
			angle += revolutions * 180.0;
			if(revolutions % 2 != 0)
			{
				angle = 180.0 + angle;
			}
		}else
		{
			angle -= revolutions * 180.0;
			if(revolutions % 2 != 0)
			{
				angle = -180.0 + angle;
			}
		}
		return angle;
	}
}
