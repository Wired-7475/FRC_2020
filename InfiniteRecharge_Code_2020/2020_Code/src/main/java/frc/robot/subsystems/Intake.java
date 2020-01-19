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
import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.IntakeCommand;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Intake extends Subsystem {
  /**
   * Creates a new Intake.
   */

  TalonSRX intakeMotor;
  TalonSRX wristMotor;
  TalonSRX beltMotor;
  Hand handL;

  public Intake() {
    intakeMotor = new TalonSRX(RobotMap.INTAKE_MOTOR);
    intakeMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    intakeMotor.configPeakOutputForward(0.75);
    intakeMotor.configPeakOutputReverse(0.0);

    wristMotor = new TalonSRX(RobotMap.WRIST_MOTOR);
    wristMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    wristMotor.configPeakOutputForward(1.0);
    wristMotor.configPeakOutputReverse(-1.0);
    wristMotor.configOpenloopRamp(0.2);

    beltMotor = new TalonSRX(RobotMap.BELT_MOTOR);
    beltMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    beltMotor.configPeakOutputForward(1.0);
    beltMotor.configPeakOutputReverse(-1.0);

    }

  public void enableBelt() {
    beltMotor.set(ControlMode.PercentOutput, -0.2);
  }

  public void disableBelt() {
    beltMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void ballIntake() {
    if(OI.getBButton()) {
    intakeMotor.set(ControlMode.PercentOutput, 0.4);
    enableBelt();
    } else {
    intakeMotor.set(ControlMode.PercentOutput, 0.0);
    disableBelt();
    }

    if(Math.abs(OI.getY(Hand.kLeft)) >= 0.1)
      wristMotor.set(ControlMode.PercentOutput, OI.getY(Hand.kLeft));
    else
      wristMotor.set(ControlMode.PercentOutput, 0.0);
    }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new IntakeCommand());
  }

}
