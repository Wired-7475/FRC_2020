package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.OI;
import frc.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

public class Autonomus{

   PIDController leftpid;
   PIDController rightpid;

   Encoder leftEncoder;
   Encoder rightEncoder;
   AHRS navX;
   
   public Autonomus(Encoder leftEnc, Encoder rightEnc, AHRS gyro){
      leftpid = new PIDController(0.5,0,0);
      leftpid.setTolerance(0.5, 0.5);
      leftpid.setIntegratorRange(-0.25, 0.25);

      rightpid = new PIDController(0.5,0,0);
      rightpid.setTolerance(0.5, 0.5);
      rightpid.setIntegratorRange(-0.25, 0.25);

      leftEncoder = leftEnc;
      rightEncoder = rightEnc;
      navX = gyro;
   }

  public void straight(int target) {
   do {
      Robot.drivetrain.setLeftdrive(leftpid.calculate(leftEncoder.getDistance(), target));
      Robot.drivetrain.setRightdrive(rightpid.calculate(rightEncoder.getRate(), leftEncoder.getRate()));
   } while(leftpid.atSetpoint() == false || OI.joy.getTrigger() == false);
   Robot.drivetrain.setLeftdrive(0.0);
   Robot.drivetrain.setRightdrive(0.0);
  }

  public void turn(int target) {
   do {
      Robot.drivetrain.setLeftdrive(leftpid.calculate(navX.getAngle(), target));
      Robot.drivetrain.setRightdrive(rightpid.calculate(rightEncoder.getRate(), -leftEncoder.getRate()));
   } while(leftpid.atSetpoint() == false || OI.joy.getTrigger() == false);
      Robot.drivetrain.setLeftdrive(0.0);
      Robot.drivetrain.setRightdrive(0.0);
  }

}