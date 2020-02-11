package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotMap;
import frc.robot.commands.WheelCommand;
import frc.robot.OI;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class WheelSpinner extends Subsystem {

  private TalonSRX wheelMotor;

  public WheelSpinner() {

    wheelMotor = new TalonSRX(RobotMap.WHEEL_MOTOR);
    wheelMotor.configLimitSwitchDisableNeutralOnLOS(true, 10);
    wheelMotor.configPeakOutputForward(1.0);
    wheelMotor.configPeakOutputReverse(0.0);

  }

  public void spinWheel() {
    if (OI.getTrigger(Hand.kLeft) > 0.05) {
      wheelMotor.set(ControlMode.PercentOutput, OI.getTrigger(Hand.kLeft));
    } else {
      wheelMotor.set(ControlMode.PercentOutput, 0.0);
    }

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new WheelCommand());
  }
}
