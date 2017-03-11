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
	public JoystickButton getBumperJoystickButton(Hand hand)
	{
		if(hand.value == Hand.kLeft.value)
			return new JoystickButton(this, 5);
		if(hand.value == Hand.kRight.value)
			return new JoystickButton(this, 6);
		
		return null;
	}
	public JoystickButton getStickJoystickButton(Hand hand)
	{
		if(hand.value == Hand.kLeft.value)
			return new JoystickButton(this, 9);
		if(hand.value == Hand.kRight.value)
			return new JoystickButton(this, 10);
		
		return null;
	}
}
