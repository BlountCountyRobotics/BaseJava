package org.usfirst.frc.team4504.robot.objects;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BCRXbox extends XboxController {
	public BCRXbox(int port)
	{
		super(port);
	}
	public int getSmallPOV()
	{
		int pov = getPOV();
		if(pov == -1)
		{
			return -1;
		}
		return pov / getPOVCount(); // instead of pov in angles, pov is 1-8.
	}
	public JoystickButton getAJoystickButton()
	{
		return new JoystickButton(this, 1);
	}
	public JoystickButton getBJoystickButton()
	{
		return new JoystickButton(this, 2);
	}
	public JoystickButton getXJoystickButton()
	{
		return new JoystickButton(this, 3);
	}
	public JoystickButton getYJoystickButton()
	{
		return new JoystickButton(this, 4);
	}
	public JoystickButton getStartJoystickButton()
	{
		return new JoystickButton(this, 8);
	}
	public JoystickButton getBackJoystickButton()
	{
		return new JoystickButton(this, 7);
	}
	public JoystickButton getLeftBumperJoystickButton()
	{
		return new JoystickButton(this, 5);
	}
	public JoystickButton getRightBumperJoystickButton()
	{
		return new JoystickButton(this, 6);
	}
	public JoystickButton getLeftClickJoystickButton()
	{
		return new JoystickButton(this, 9);
	}
	public JoystickButton getRightClickJoystickButton()
	{
		return new JoystickButton(this, 10);
	}
}
