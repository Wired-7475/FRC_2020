package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.joystickDrive;
import frc.robot.commands.Tankdrive;

public class Drivetrain extends Subsystem {
  private VictorSPX leftMotor1;
  private VictorSPX leftMotor2;
  private VictorSPX rightMotor1;
  private VictorSPX rightMotor2;

  public Drivetrain() {
    leftMotor1 = new VictorSPX(RobotMap.LEFT_DRIVE1);
    leftMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    leftMotor1.configPeakOutputForward(0.75);
    leftMotor1.configPeakOutputReverse(-0.75);
    leftMotor1.configOpenloopRamp(0.4);
    
    leftMotor2 = new VictorSPX(RobotMap.LEFT_DRIVE2);
    leftMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    leftMotor2.configPeakOutputForward(0.75);
    leftMotor2.configPeakOutputReverse(-0.75);
    leftMotor2.configOpenloopRamp(0.4);

    rightMotor1 = new VictorSPX(RobotMap.RIGHT_DRIVE1);
    rightMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    rightMotor1.configPeakOutputForward(0.75);
    rightMotor1.configPeakOutputReverse(-0.75);
    rightMotor1.configOpenloopRamp(0.4);

    rightMotor2 = new VictorSPX(RobotMap.RIGHT_DRIVE2);
    rightMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    rightMotor2.configPeakOutputForward(0.75);
    rightMotor2.configPeakOutputReverse(-0.75);
    rightMotor2.configOpenloopRamp(0.4);
  }

  public void drive(double x, double y, boolean trig1, boolean trig2) {
    joystickDrive.joyDrive(x, y, trig1, trig2, leftMotor1, rightMotor1);
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
  }

  public double getLeftTarget() {
    return leftMotor1.getMotorOutputPercent();
  }

  public double getRightTarget() {
    return rightMotor1.getMotorOutputPercent();
  }

  public void setRightdrive(double demand) {
    rightMotor1.set(ControlMode.PercentOutput, demand);
    rightMotor2.follow(rightMotor1);
  }

  public void setLeftdrive(double demand) {
    leftMotor1.set(ControlMode.PercentOutput, demand);
    leftMotor2.follow(leftMotor1);
  }
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Tankdrive());
  }
}
