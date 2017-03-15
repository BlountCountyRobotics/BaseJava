package org.usfirst.frc.team4504.robot.objects;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BCRDriverPanel extends Joystick
{
	public BCRDriverPanel(int port)
	{
		super(port);
	}
	
	public boolean getTopLeft()
	{
		return this.getRawButton(2);
	}
	public boolean getTopRight()
	{
		return this.getRawButton(4);
	}
	public boolean getMidLeft()
	{
		return this.getRawButton(10);
	}
	public boolean getMidMid()
	{
		return this.getRawButton(1);
	}
	public boolean getMidRight()
	{
		return this.getRawButton(5);
	}
	public boolean getBottomLeft()
	{
		return this.getRawButton(8);
	}
	public boolean getBottomMid()
	{
		return this.getRawButton(11);
	}
	public boolean getLeverForward()
	{
		return this.getRawButton(6);
	}
	public boolean getLeverBack()
	{
		return this.getRawButton(7);
	}
	
	public JoystickButton getTopLeftButton()
	{
		return new JoystickButton(this, 2);
	}
	public JoystickButton getTopRightButton()
	{
		return new JoystickButton(this, 4);
	}
	public JoystickButton getMidLeftButton()
	{
		return new JoystickButton(this, 10);
	}
	public JoystickButton getMidMidButton()
	{
		return new JoystickButton(this, 1);
	}
	public JoystickButton getMidRightButton()
	{
		return new JoystickButton(this, 5);
	}
	public JoystickButton getBottomLeftButton()
	{
		return new JoystickButton(this, 8);
	}
	public JoystickButton getBottomRightButton()
	{
		return new JoystickButton(this, 11);
	}
	public JoystickButton getLeverForwardButton()
	{
		return new JoystickButton(this, 6);
	}
	public JoystickButton getLeverBackButton()
	{
		return new JoystickButton(this, 7);
	}
}
