package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;
import frc.robot.commands.LiftCommand;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.OI;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Lift extends Subsystem {

  private TalonSRX liftMotor;
  private TalonSRX reelMotor;
  private PIDController reelpid;
  public Lift() {
    liftMotor = new TalonSRX(RobotMap.REEL_MOTOR);
    liftMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    liftMotor.configPeakOutputForward(1.0);
    liftMotor.configPeakOutputReverse(-1.0);

    reelMotor = new TalonSRX(RobotMap.HOOK_MOTOR);
    reelMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    reelMotor.configPeakOutputForward(0.8);
    reelMotor.configPeakOutputReverse(-0.8);

    reelpid = new PIDController(0.5,0,0);
    reelpid.setTolerance(0.5, 0.5);
    reelpid.setIntegratorRange(-0.1, 0.1);
  }

  public void enableLift() {
    liftMotor.set(ControlMode.PercentOutput, 0.66);
  }

  public void holdLift(double pos) {
    double speed = reelpid.calculate(Robot.reelEncoder.getDistance());
    liftMotor.set(ControlMode.PercentOutput, speed, pos);
  }

  public void reverseLift() {
    liftMotor.set(ControlMode.PercentOutput, -0.2);
  }
  public void dropLift() {
    double speed = reelpid.calculate(Robot.reelEncoder.getRate());
    liftMotor.set(ControlMode.PercentOutput, reelpid.calculate(speed, -0.5));
  }

  public void disableLift() {
    liftMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void runLift() {
    if(OI.getAButton()) {
      enableLift();
      reelMotor.set(ControlMode.PercentOutput, -1.0);
    } else if(OI.getYButton()) {
      reverseLift();
      reelMotor.set(ControlMode.PercentOutput, 1.0);
    } else{
      holdLift(Robot.reelEncoder.getDistance());
      reelMotor.set(ControlMode.PercentOutput, 0.0);
    }

    if(OI.xbox.getStickButton(Hand.kRight))
    reelMotor.set(ControlMode.PercentOutput, -OI.getY(Hand.kRight));
    
    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftCommand());
  }
}
