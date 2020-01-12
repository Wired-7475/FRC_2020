package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class joystickDrive {
    static String quadrant = "None";
    public static void joyDrive(double xAxis, double yAxis, double throttle, boolean trig, TalonSRX talon1, TalonSRX talon2) {
      throttle = throttle / -2 + 0.5;
     xAxis *= throttle;
     yAxis *= throttle;
      if(trig == false){      
      if(yAxis > OI.deadzone) {
         if(xAxis > OI.deadzone) {
            talon1.set(ControlMode.PercentOutput, -yAxis);
            talon2.set(ControlMode.PercentOutput, yAxis - xAxis);
            quadrant = "Quadrant One";
         }else{
            talon1.set(ControlMode.PercentOutput, -yAxis - xAxis);
            talon2.set(ControlMode.PercentOutput, yAxis);
            quadrant = "Quadrant Two";
         }
      }else {
         if(xAxis > OI.deadzone) {
            talon1.set(ControlMode.PercentOutput, -yAxis);
            talon2.set(ControlMode.PercentOutput, yAxis + xAxis);
            quadrant = "Quadrant Three";
         }else{
            talon1.set(ControlMode.PercentOutput, -yAxis + xAxis);
            talon2.set(ControlMode.PercentOutput, yAxis);
            quadrant = "Quadrand Four";
         }
      } 
   } else{
         talon1.set(ControlMode.PercentOutput, xAxis);
         talon2.set(ControlMode.PercentOutput, xAxis);
      }
   }
         public static String getQuadrant() {
             return quadrant;
         }
        
    }

