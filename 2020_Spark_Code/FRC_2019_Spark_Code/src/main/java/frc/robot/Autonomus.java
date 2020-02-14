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
   PIDController anglepid;

   Encoder leftEncoder;
   Encoder rightEncoder;
   AHRS navX;
   
   public final static int FWD = 0;
   public final static int TURN = 1;

   int steps[][];
   int stepcount = 0;
   public Autonomus(Encoder leftEnc, Encoder rightEnc, AHRS gyro, int[][] init_steps){
      leftpid = new PIDController(1.0,0.5,1.0);
      leftpid.setTolerance(0.5, 0.5);

      rightpid = new PIDController(1.0,0.5,1.0);
      rightpid.setTolerance(0.5, 0.5);

      anglepid = new PIDController(0.5, 0.0, 0.0);

      leftEncoder = leftEnc;
      rightEncoder = rightEnc;
      navX = gyro;

      steps = init_steps;
   }

  public boolean straight(int target) {

      leftpid.setTolerance(0.5);
      rightpid.setTolerance(0.5);
      leftpid.setPID(0.3, 0.0, 0.0);
      rightpid.setPID(0.3, 0.0, 0.0);

      Robot.drivetrain.setLeftdrive(leftpid.calculate(leftEncoder.getRate(), 3.0) * (anglepid.calculate(navX.getAngle(), 0) / 100));
      Robot.drivetrain.setRightdrive(-rightpid.calculate(rightEncoder.getRate(), 3.0) * (anglepid.calculate(navX.getAngle(), 0) / 100));

   return leftEncoder.getDistance() == target && rightEncoder.getDistance() == target;
   
  }

  public boolean turn(int target) {

      leftpid.setTolerance(2.0);
      leftpid.setPID(0.1, 0.0, 0.0);
      double speed = leftpid.calculate(navX.getAngle(), target) / 5;
      Robot.drivetrain.setLeftdrive(speed);
      Robot.drivetrain.setRightdrive(speed);
   return leftpid.atSetpoint();
  }

  public void drive() {
     boolean isFinished = false;
     //System.out.println(stepcount +  " : " + steps[stepcount][0] + " : " + steps[stepcount][1]);
   if(stepcount < steps.length) {
     switch(steps[stepcount][0]) {
      case FWD:
         isFinished = straight(steps[stepcount][1]);
         break;
      case TURN:
         isFinished = turn(steps[stepcount][1]);
         break;
      default:

    }
   }
    if(isFinished)
      stepcount++;
  }

  public void reset() {
     stepcount = 0;
  }
}