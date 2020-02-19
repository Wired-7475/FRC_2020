package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.RobotMap;
import frc.robot.commands.LiftCommand;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.OI;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Lift extends Subsystem {

  private TalonSRX liftMotor1;
  private TalonSRX liftMotor2;
  private TalonSRX reelMotor;

  public Lift() {
    liftMotor1 = new TalonSRX(RobotMap.REEL_MOTOR1);
    liftMotor1.configLimitSwitchDisableNeutralOnLOS(true, 10);
    liftMotor1.configPeakOutputForward(1.0);
    liftMotor1.configPeakOutputReverse(-1.0);

    liftMotor2 = new TalonSRX(RobotMap.REEL_MOTOR2);
    liftMotor2.configLimitSwitchDisableNeutralOnLOS(true, 10);
    liftMotor2.configPeakOutputForward(1.0);
    liftMotor2.configPeakOutputReverse(-1.0);

    reelMotor = new TalonSRX(RobotMap.HOOK_MOTOR);
    reelMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    reelMotor.configPeakOutputForward(0.8);
    reelMotor.configPeakOutputReverse(-0.8);

  }

  public void enableLift() {
    liftMotor1.set(ControlMode.PercentOutput, 0.66);
    liftMotor2.set(ControlMode.PercentOutput, 0.66);
  }

  public void disableLift() {
    liftMotor1.set(ControlMode.PercentOutput, 0.0);
    liftMotor2.set(ControlMode.PercentOutput, 0.0);
  }

  public void runLift() {
    if(OI.getYButton()) {
      enableLift();
    }
    
    reelMotor.set(ControlMode.PercentOutput, -OI.getY(Hand.kRight));
    
    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LiftCommand());
  }
}
