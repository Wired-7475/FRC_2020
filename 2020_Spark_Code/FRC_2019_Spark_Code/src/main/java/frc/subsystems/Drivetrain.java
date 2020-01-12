/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.RobotMap;
import frc.commands.TankDrive;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  private Victor leftMotor1;
  private Victor leftMotor2;
  private Victor rightMotor1;
  private Victor rightMotor2;
  private SpeedControllerGroup leftDrive;
  private SpeedControllerGroup rightDrive;
  private DifferentialDrive drive;



  public Drivetrain() {
    System.out.println("Drivetrain Initalized");
    leftMotor1 = new Victor(RobotMap.LEFT_MOTOR_1);
    rightMotor1 = new Victor(RobotMap.RIGHT_MOTOR_1);
    
    leftDrive = new SpeedControllerGroup(leftMotor1);
    rightDrive = new SpeedControllerGroup(rightMotor1);

    drive = new DifferentialDrive(leftDrive, rightDrive);

  }

  public void move(double x, double y, double throttle) {
    drive.arcadeDrive(y * throttle, x * throttle);
  }

  @Override
  public void initDefaultCommand() {
    System.out.println("Command Initalized");
    setDefaultCommand(new TankDrive());
  }

  public void setLeftdrive(double output) {
    leftDrive.set(output);
  }

  public void setRightdrive(double output) {
    rightDrive.set(output);
  }
}
