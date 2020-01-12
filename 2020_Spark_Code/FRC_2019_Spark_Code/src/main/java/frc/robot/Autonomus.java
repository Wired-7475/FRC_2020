package frc.robot;

/**
 * Some basic autonomus code.
 */
import frc.robot.basePID;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Robot;
public class Autonomus{
   public basePID leftPID;
   public basePID rightPID;
   public boolean leftDisabled = false;
   public  boolean rightDisabled = false;
public  String mode;
    public void straight(int distance) {
      leftPID = new basePID(2, 0, 1, Robot.leftReelEncoder);
      leftPID.setOutputRange(-0.15, 0.25);
      leftPID.setTolerance(1);

      rightPID = new basePID(2, 0, 1, Robot.leftReelEncoder);
      rightPID.setOutputRange(-0.15, 0.25);
      rightPID.setTolerance(1);

       leftPID.setPoint(distance);
       rightPID.setPoint(distance);

       leftPID.start();
       rightPID.start();
      leftDisabled = false;
      rightDisabled = false;
       if(distance > 0) {
          mode = "forward";
       }else if (distance < 0) {
            mode = "reverse";
       }
       drive();
    }

    public void turn(int angle) {
      leftPID = new basePID(1, 0, 2, Robot.navX);
      leftPID.setOutputRange(-0.25, 0.25);
      leftPID.setTolerance(1);

      rightPID = new basePID(1, 0, 2, Robot.navX);
      rightPID.setOutputRange(-0.25, 0.25);
      rightPID.setTolerance(1);

      leftPID.setPoint(angle);
      rightPID.setPoint(angle);

       leftPID.start();
       rightPID.start();
       leftDisabled = false;
       rightDisabled = false;

       if(angle > 0) {
         mode = "right";
      }else if (angle < 0) {
           mode = "left";
      }
      drive();

    }
    
    public void drive() {
       while(true) {
       switch (mode) {
          case "forward":
          Robot.drivetrain.setLeftdrive(-Robot.auto.getLeftOutput());
          Robot.drivetrain.setRightdrive(Robot.auto.getRightOutput());
             break;
         case "reverse":
         Robot.drivetrain.setLeftdrive(Robot.auto.getLeftOutput());
         Robot.drivetrain.setRightdrive(-Robot.auto.getRightOutput());
             break;
         case "left":
         Robot.drivetrain.setLeftdrive(-Robot.auto.getLeftOutput());
         Robot.drivetrain.setRightdrive(-Robot.auto.getRightOutput());
             break;
         case "right":
         Robot.drivetrain.setLeftdrive(Robot.auto.getLeftOutput());
          Robot.drivetrain.setRightdrive(Robot.auto.getRightOutput());
             break;
         default:
         Robot.drivetrain.setLeftdrive(0.0);
          Robot.drivetrain.setRightdrive(0.0);
             break;
       }
       
      if(leftPID.onTarget()) {
         leftPID.stop();
         leftDisabled = true;
      }
      if(rightPID.onTarget()) {
         rightPID.stop();
         rightDisabled = true;
      }

      if(leftDisabled && rightDisabled)
         return;
      }
   }
    public double getLeftOutput() {
       return leftPID.getOutput();
    }
    public double getRightOutput() {
        return rightPID.getOutput();
    }
}