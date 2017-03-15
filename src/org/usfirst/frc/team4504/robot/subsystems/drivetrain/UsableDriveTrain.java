package org.usfirst.frc.team4504.robot.subsystems.drivetrain;

import org.usfirst.frc.team4504.robot.objects.BCRXbox;

// All necessary functions for DriveTrain usage

public interface UsableDriveTrain {
	public void input(BCRXbox controller);
	public void stop();
	public void drive(double input);
	public void drive(double input, double curve);
	public void setLeftRight(double left, double right);
}
