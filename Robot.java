// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  //private Command m_autonomousCommand;
  public static RobotContainer m_robotContainer;
  private final Timer m_timer = new Timer();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putData("Encoder?", RobotContainer.driveTrain.encoderPosition());
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();

    // schedule the autonomous command (example)

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    if (m_timer.get() < 2.0) {
      m_robotContainer.driveTrain.manualDrive(0.5, 0); // drive forwards half speed
    } else {
      m_robotContainer.driveTrain.manualDrive(0, 0); // stop robot
        if (m_timer.get() > 2.0 && m_timer.get() < 4.6){
          m_robotContainer.driveTrain.manualDrive(0, 0.5);
        } else {
          m_robotContainer.driveTrain.manualDrive(0, 0);
          if (m_timer.get() > 4.5 && m_timer.get() < 6.0){
            m_robotContainer.driveTrain.manualDrive(0.35, 0);
          } else {
            m_robotContainer.driveTrain.manualDrive(0, 0);
          }
        }
    }
    
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double move = -Robot.m_robotContainer.GetJoystickRawAxis(Constants.Y_AXIS_ID);
    double turn = Robot.m_robotContainer.GetJoystickRawAxis(Constants.X_AXIS_ID);
    RobotContainer.driveTrain.manualDrive(move, turn);
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
