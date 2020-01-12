package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
public class Tankdrive extends Command {
  public Tankdrive() {
     requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
   Robot.drivetrain.drive(OI.getJoyX(), OI.getJoyY(), OI.getThrottle(), OI.getJoyTrig());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
