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
   public final static int SHOOT_ON = 2;
   public final static int SHOOT_OFF = 3;
   public final static int BAR_ON = 4;
   public final static int BAR_OFF = 5;
   public final static int WRIST_ON = 7;
   public final static int WRIST_OFF = 8;
   public final static int DELAY = 9;
   public final static int INTAKE_ON = 10;
   public final static int INTAKE_OFF = 11;
   double init_time = -1;
   boolean prev_result = false;
   boolean result = false;

   int steps[][];
   int stepcount = 0;
   public Autonomus(Encoder leftEnc, Encoder rightEnc, AHRS gyro, int[][] init_steps){
      leftpid = new PIDController(0.50,0.5,1.0);
      leftpid.setTolerance(0.5, 0.5);

      rightpid = new PIDController(0.50,0.5,1.0);
      rightpid.setTolerance(0.5, 0.5);

      //anglepid = new PIDController(0.057, 0.0, 0.0);
      anglepid = new PIDController(0.03, 0.01, 0.0);
      anglepid.enableContinuousInput(-180, 180);

      leftEncoder = leftEnc;
      rightEncoder = rightEnc;
      navX = gyro;

      steps = init_steps;
   }

  public boolean straight(int target) {
   prev_result = result;
      leftpid.setTolerance(0.1);
      rightpid.setTolerance(0.1);

      double leftdrive = 0;
      double rightdrive = 0;
      leftdrive = (leftpid.calculate(leftEncoder.getDistance(), target));
     rightdrive = (-rightpid.calculate(rightEncoder.getDistance(), target));

     if(leftdrive > 0.5) 
         leftdrive = 0.5;

      if(leftdrive < -0.5)
         leftdrive = -0.5;

      if(rightdrive > 0.5) 
         rightdrive = 0.5;

      if(rightdrive < -0.5)
         rightdrive = -0.5;

      result =  leftpid.atSetpoint() && rightpid.atSetpoint();
      Robot.drivetrain.setLeftdrive(leftdrive);
      Robot.drivetrain.setRightdrive(rightdrive);
   return prev_result && result;
   
  }

  public boolean straight2(int target) {

   leftpid.setPID(0.5, 0.0, 0.0);

   double linear_speed = leftpid.calculate(leftEncoder.getDistance(), target);
   double angular_error;
   if(Math.abs(rightEncoder.getDistance()) < 0.1)
      angular_error = 1;
   else
      angular_error = leftEncoder.getDistance() / rightEncoder.getDistance();


   Robot.drivetrain.setLeftdrive(linear_speed * angular_error / 1.5);
   Robot.drivetrain.setRightdrive(linear_speed * -angular_error / 1.5);

   return leftpid.atSetpoint();

  }

  public boolean turn(int target) {
      anglepid.setTolerance(1.0);

      double speed = anglepid.calculate(navX.getAngle(), target);

   if(speed > 0.5) 
      speed = 0.5;

   if(speed < -0.5)
      speed = -0.5;

      Robot.drivetrain.setLeftdrive(speed);
      Robot.drivetrain.setRightdrive(speed);
      result = anglepid.atSetpoint();
   return result && delay(3);
  }
  
  public boolean delay(double delay) {
     boolean ontarget = false;
     double current_time = Robot.timer.get();

     if(init_time == -1)
      init_time = Robot.timer.get();

      if(init_time + delay < current_time) {
         init_time = -1;
         ontarget = true;
      }
     return ontarget;
  }

  public void bar(boolean state) {
   /*  
   if(state)
         Robot.intake.enableBar();
      else
         Robot.intake.disableBar();
    */
  }

  public void intake(boolean state) {
        /*  
   if(state)
         Robot.intake.enableGate();
         Robot.intake.enableBelt();
      else
         Robot.intake.disableGate();
         Robot.intake.disableBelt();
    */
  }

  public void shooter(boolean state) {
        /*  
   if(state)
         Robot.intake.enableShooter();
      else
         Robot.intake.disableShooter();
    */
  }

  public void wrist(boolean state) {
        /*  
   if(state)
         Robot.intake.enableWrist();
      else
         Robot.intake.disableWrist();
    */
  }

  public void drive() {
     boolean isFinished = false;
     //System.out.println(stepcount +  " : " + steps[stepcount][0] + " : " + steps[stepcount][1]);
   if(stepcount < steps.length) {
     switch(steps[stepcount][0]) {
      case FWD:
         isFinished = straight2(steps[stepcount][1]);
         break;
      case TURN:
         isFinished = turn(steps[stepcount][1]);
         break;
      case SHOOT_ON:
         shooter(true);
         isFinished = true;
         break;
      case SHOOT_OFF:
         shooter(false);
         isFinished = true;
         break;
      case BAR_ON:
      bar(true);
         isFinished = true;
         break;
      case BAR_OFF:
      bar(false);
         isFinished = true;
         break;
      case WRIST_ON:
         wrist(true);
         isFinished = true;
         break;
      case WRIST_OFF:
         wrist(false);
         isFinished = true;
         break;
      case DELAY:
         isFinished = delay(steps[stepcount][1]);
         break;
      case INTAKE_ON:
         intake(true);
         isFinished = true;
         break;
      case INTAKE_OFF:
         intake(false);
         isFinished = true;
         break;
      default:

    }
   }
    if(isFinished) {
      stepcount++;
      leftEncoder.reset();
      rightEncoder.reset();
    }
  }

  public void reset() {
     stepcount = 0;
  }
}