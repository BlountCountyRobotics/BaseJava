
package org.usfirst.frc.team4504.robot;

import org.usfirst.frc.team4504.robot.objects.BCRAHRS;
import org.usfirst.frc.team4504.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4504.robot.subsystems.drivetrain.BaseDriveTrain.DriveType;
import org.usfirst.frc.team4504.robot.subsystems.drivetrain.BaseDriveTrain.Motors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	BCRAHRS ahrs;
	DriveTrain driveTrain = new DriveTrain();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		ahrs = new BCRAHRS(RobotMap.ahrsPort);
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		sendSmartDashboardValues();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		sendSmartDashboardValues();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void sendSmartDashboardValues()
	{
		int driveType = driveTrain.getDriveType();
		if(RobotMap.encodersAvailable)
		{
			if(driveType == DriveType.twoWheel)
			{
				SmartDashboard.putNumber("Left side RPM", 
						driveTrain.getMotorRPM(Motors.left));
				SmartDashboard.putNumber("Right side RPM", 
						driveTrain.getMotorRPM(Motors.right));
			}else if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
			{
				SmartDashboard.putNumber("Front left RPM", 
						driveTrain.getMotorRPM(Motors.frontLeft));
				SmartDashboard.putNumber("Front right RPM", 
						driveTrain.getMotorRPM(Motors.frontRight));
				SmartDashboard.putNumber("Back left RPM", 
						driveTrain.getMotorRPM(Motors.backLeft));
				SmartDashboard.putNumber("Back right RPM", 
						driveTrain.getMotorRPM(Motors.backRight));
				if(driveType == DriveType.sixWheel)
				{
					SmartDashboard.putNumber("Middle left RPM", 
							driveTrain.getMotorRPM(Motors.midLeft));
					SmartDashboard.putNumber("Middle right RPM", 
							driveTrain.getMotorRPM(Motors.midRight));
				}
			}
		}
		if(driveType == DriveType.twoWheel)
		{
			SmartDashboard.putNumber("Left side output", 
					driveTrain.getMotorOutput(Motors.left));
			SmartDashboard.putNumber("Right side output", 
					driveTrain.getMotorOutput(Motors.right));
		}else if(driveType == DriveType.fourWheel || driveType == DriveType.sixWheel)
		{
			SmartDashboard.putNumber("Front left output", 
					driveTrain.getMotorOutput(Motors.frontLeft));
			SmartDashboard.putNumber("Front right output", 
					driveTrain.getMotorOutput(Motors.frontRight));
			SmartDashboard.putNumber("Back left output", 
					driveTrain.getMotorOutput(Motors.backLeft));
			SmartDashboard.putNumber("Back right output", 
					driveTrain.getMotorOutput(Motors.backRight));
			if(driveType == DriveType.sixWheel)
			{
				SmartDashboard.putNumber("Middle left output", 
						driveTrain.getMotorOutput(Motors.midLeft));
				SmartDashboard.putNumber("Middle right output", 
						driveTrain.getMotorOutput(Motors.midRight));
			}
		}
		SmartDashboard.putBoolean("isMagneticDisturbance()", ahrs.isMagneticDisturbance());
		SmartDashboard.putBoolean("isConnected()", ahrs.isConnected());
		SmartDashboard.putNumber("Gyro angle", ahrs.getRealAngle());
		SmartDashboard.putNumber("Gyro rate", ahrs.getRate());
		SmartDashboard.putNumber("Gyro offset", ahrs.getAngleAdjustment());
		SmartDashboard.putNumber("X acceleration", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("Y acceleration", ahrs.getWorldLinearAccelY());
		SmartDashboard.putNumber("Z acceleration", ahrs.getWorldLinearAccelZ());
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		SmartDashboard.putNumber("Roll", ahrs.getRoll());
	}
}
