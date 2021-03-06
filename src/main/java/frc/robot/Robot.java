/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.spikes2212.genericsubsystems.basicSubsystem.BasicSubsystem;
import com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions.MaxLimit;
import com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions.MinLimit;
import com.spikes2212.genericsubsystems.basicSubsystem.utils.limitationFunctions.TwoLimits;
import com.spikes2212.genericsubsystems.drivetrains.TankDrivetrain;
import com.spikes2212.genericsubsystems.drivetrains.commands.DriveArcade;
import com.spikes2212.utils.InvertedConsumer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

	public static OI oi;
	public static TankDrivetrain drivetrain;
	public static BasicSubsystem gripper;
	public static BasicSubsystem lift;
	public static BasicSubsystem arm;
	public static BasicSubsystem latch;

	@Override
	public void robotInit() {

		drivetrain = new TankDrivetrain(new InvertedConsumer(SubsystemComponents.Drivetrain.LEFT::set),
				SubsystemComponents.Drivetrain.RIGHT::set);

		drivetrain.setDefaultCommand(
				new DriveArcade(drivetrain, () -> oi.getLeftJoystickY(), () -> oi.getRightJoystickX()));

		gripper = new BasicSubsystem((speed) -> {
			SubsystemComponents.Gripper.MOTOR_1.set(speed);
			SubsystemComponents.Gripper.MOTOR_2.set(-speed);
		}, new MinLimit(() -> SubsystemComponents.Gripper.LIMIT.get() || SubsystemComponents.Gripper.LIGHT_SENSOR
				.getVoltage() < SubsystemConstants.Gripper.LIMIT_VOLTAGE.get()));

		lift = new BasicSubsystem(SubsystemComponents.Lift.GEARBOX::set, (Double speed) -> {
			if (speed == 0) // The lift can always move with 0.
				return true;
			// Returns false if the lift tries to move up when its in its upper
			// limit.
			if (SubsystemComponents.Lift.LIMIT_UP.get() && speed > SubsystemConstants.Lift.STAYING_SPEED.get())
				return false;
			// Returns false if the lift tries to move down when its in its
			// lower limit.
			if (SubsystemComponents.Lift.LIMIT_DOWN.get() && speed < SubsystemConstants.Lift.STAYING_SPEED.get())
				return false;
			return true;
		});

		arm = new BasicSubsystem(SubsystemComponents.Arm.MOTOR::set,
				new TwoLimits(SubsystemComponents.Arm.LIMIT1::get, SubsystemComponents.Arm.LIMIT2::get));

		latch = new BasicSubsystem(SubsystemComponents.Latch.MOTOR::set, new MaxLimit(SubsystemComponents.Latch.LIMIT::get));
				
		oi = new OI();
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
