package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Tankdrive extends Command {
  public Tankdrive() {
     requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
   Robot.drivetrain.drive(Math.abs(OI.drive.getX(Hand.kRight)) < OI.deadzone ? 0.0 : OI.drive.getX(Hand.kRight), Math.abs(OI.drive.getY(Hand.kLeft)) < OI.deadzone ? 0.0 : OI.drive.getY(Hand.kLeft), OI.drive.getBumper(Hand.kRight));
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
