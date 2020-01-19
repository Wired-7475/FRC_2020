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

public class Ballshooter extends Subsystem {
  /**
   * Creates a new Ballshooter.
   */

  private TalonSRX shooterMotor;

  public Ballshooter() {
    shooterMotor = new TalonSRX(RobotMap.BALL_MOTOR);
    shooterMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    shooterMotor.configPeakOutputForward(0.75);
    shooterMotor.configPeakOutputReverse(0.0);
    
  }

  public void shoot() {
    if(OI.getXButton()) {
      shooterMotor.set(ControlMode.PercentOutput, 0.75);
      Robot.intake.enableBelt();
    } else {
      shooterMotor.set(ControlMode.PercentOutput, 0.0);
      Robot.intake.disableBelt();
    }
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterCommand());
  }
}
