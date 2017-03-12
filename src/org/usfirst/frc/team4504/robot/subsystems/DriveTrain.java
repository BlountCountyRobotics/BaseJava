package org.usfirst.frc.team4504.robot.subsystems;

import org.usfirst.frc.team4504.robot.subsystems.drivetrain.BaseDriveTrain;


/**
 * On actual robot, extend the 
 * correct DriveTrain and 
 * input real Talons
 */
public class DriveTrain extends BaseDriveTrain {

	public DriveTrain()
	{
		super(null, null, null, null);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

