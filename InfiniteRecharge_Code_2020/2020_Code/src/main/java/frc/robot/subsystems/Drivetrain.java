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
  private TalonSRX leftMotor1;
  private VictorSPX leftMotor2;
  private TalonSRX rightMotor1;
  private VictorSPX rightMotor2;

  public Drivetrain() {
    leftMotor1 = new TalonSRX(RobotMap.LEFT_MOTOR1);
    leftMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    leftMotor1.configPeakOutputForward(0.75);
    leftMotor1.configPeakOutputReverse(-0.75);
    leftMotor1.configOpenloopRamp(0.2);
    
    leftMotor2 = new VictorSPX(RobotMap.LEFT_MOTOR2);
    leftMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    leftMotor2.configPeakOutputForward(0.75);
    leftMotor2.configPeakOutputReverse(-0.75);
    leftMotor2.configOpenloopRamp(0.2);

    rightMotor1 = new TalonSRX(RobotMap.RIGHT_MOTOR1);
    rightMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    rightMotor1.configPeakOutputForward(0.75);
    rightMotor1.configPeakOutputReverse(-0.75);
    rightMotor1.configOpenloopRamp(0.2);

    rightMotor2 = new VictorSPX(RobotMap.RIGHT_MOTOR2);
    rightMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    rightMotor2.configPeakOutputForward(0.75);
    rightMotor2.configPeakOutputReverse(-0.75);
    rightMotor2.configOpenloopRamp(0.2);
  }

  public void drive(double x, double y, double throttle, boolean trig) {
    joystickDrive.joyDrive(x, y, throttle, trig, leftMotor1, rightMotor1);
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
    System.out.println("Driving2");
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
