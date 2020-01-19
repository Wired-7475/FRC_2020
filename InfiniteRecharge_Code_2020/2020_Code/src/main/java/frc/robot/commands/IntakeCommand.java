/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Robot;

public class IntakeCommand extends Command {
  /**
   * Creates a new IntakeCommand.
   */
  public IntakeCommand() {
    requires(Robot.intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    Robot.intake.ballIntake();
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
  
