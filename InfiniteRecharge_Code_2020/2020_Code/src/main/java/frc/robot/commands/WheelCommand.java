package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WheelCommand extends Command {
  public WheelCommand() {
    requires(Robot.wheel);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.wheel.spinWheel();
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
