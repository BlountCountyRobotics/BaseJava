package org.usfirst.frc.team4504.robot.subsystems;

import org.usfirst.frc.team4504.robot.commands.Drive;
import org.usfirst.frc.team4504.robot.subsystems.drivetrain.MecanumDrive;

import com.ctre.CANTalon.FeedbackDevice;

/**
 * On actual robot, extend the 
 * correct DriveTrain and 
 * input real Talons
 */

// Using MecanumDrive as example; change as needed.
public class DriveTrain extends MecanumDrive {

	// Initialize talons here
	
	public DriveTrain()
	{
		// Set talon PIDF here (according to software reference guide)
		super(null, null, null, null);
		this.setTriggerIncreasesSpeed(true);
		this.setJoystickInputSquared(true);
		this.setEncoder(FeedbackDevice.QuadEncoder);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Drive());
    }

}

