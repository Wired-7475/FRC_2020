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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
  VictorSPX shootergateMotor1;
  VictorSPX shootergateMotor2;
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

    shootergateMotor1 = new VictorSPX(RobotMap.WHEEL_GATE1);
    shootergateMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    shootergateMotor1.configPeakOutputForward(1.0);
    shootergateMotor1.configPeakOutputReverse(-1.0);

    shootergateMotor2 = new VictorSPX(RobotMap.WHEEL_GATE2);
    shootergateMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    shootergateMotor2.configPeakOutputForward(1.0);
    shootergateMotor2.configPeakOutputReverse(-1.0);
    }

  public void enableBelt() {
    beltMotor.set(ControlMode.PercentOutput, -0.5);
  }

  public void reverseBelt() {
    beltMotor.set(ControlMode.PercentOutput, 0.5);
  }

  public void disableBelt() {
    beltMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void enableBar() {
    intakeMotor.set(ControlMode.PercentOutput, 0.4);
  }

  public void disableBar() {
    intakeMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void enableWrist() {
    wristMotor.set(ControlMode.PercentOutput, 0.5);
  }

  public void disableWrist() {
    wristMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void enableGate() {
    shootergateMotor1.set(ControlMode.PercentOutput, -1.0);
    shootergateMotor2.set(ControlMode.PercentOutput, 1.0);
  }

  public void disableGate() {
    shootergateMotor1.set(ControlMode.PercentOutput, 0.0);
    shootergateMotor2.set(ControlMode.PercentOutput, 0.0);
  }

  public void ballIntake() {
    if(OI.getBButton()) {
      enableBar();
    } else {
      disableBar();
    }

    if(OI.getBumper(Hand.kLeft))
      reverseBelt();
    else if(OI.getBumper(Hand.kRight))
      enableBelt();
    else
      disableBelt();    

    if(OI.getTrigger(Hand.kRight) > 0.1) {
      enableGate();
    } else {
      disableGate();
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
