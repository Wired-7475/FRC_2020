/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.commands.ShooterCommand;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Ballshooter extends Subsystem {
  /**
   * Creates a new Ballshooter.
   */

  private TalonSRX shooterMotor;
  private double delay = 0;
  private boolean running = false;

  public Ballshooter() {
    shooterMotor = new TalonSRX(RobotMap.BALL_MOTOR);
    shooterMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    shooterMotor.configPeakOutputForward(1.0);
    shooterMotor.configPeakOutputReverse(-0.75);
    
  }

  public void disableShooter() {
    shooterMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void enableShooter() {
    shooterMotor.set(ControlMode.PercentOutput, 0.75);
  }

  public void enableSlowShooter() {
    shooterMotor.set(ControlMode.PercentOutput, 0.75);
  }

  public void reverseShooter() {
    shooterMotor.set(ControlMode.PercentOutput, -0.5);
  }

  public void setSpeed(double speed) {
    shooterMotor.set(ControlMode.PercentOutput, speed);
  }

  public void shoot() {
     
  if(OI.getXButton()) {
    enableShooter();
  } else
    disableShooter();
}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterCommand());
  }
}
