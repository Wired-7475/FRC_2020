package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class joystickDrive {
    static String quadrant = "None";
    public static void joyDrive(double xAxis, double yAxis, double throttle, boolean trig, VictorSPX victor1, VictorSPX victor2) {
      throttle = throttle / -2 + 0.5;
     xAxis *= throttle;
     yAxis *= throttle;
      if(trig == false){      
      if(yAxis > OI.deadzone) {
         if(xAxis > OI.deadzone) {
            victor1.set(ControlMode.PercentOutput, -yAxis);
            victor2.set(ControlMode.PercentOutput, yAxis - xAxis);
            quadrant = "Quadrant One";
         }else{
            victor1.set(ControlMode.PercentOutput, -yAxis - xAxis);
            victor2.set(ControlMode.PercentOutput, yAxis);
            quadrant = "Quadrant Two";
         }
      }else {
         if(xAxis > OI.deadzone) {
            victor1.set(ControlMode.PercentOutput, -yAxis);
            victor2.set(ControlMode.PercentOutput, yAxis + xAxis);
            quadrant = "Quadrant Three";
         }else{
            victor1.set(ControlMode.PercentOutput, -yAxis + xAxis);
            victor2.set(ControlMode.PercentOutput, yAxis);
            quadrant = "Quadrand Four";
         }
      } 
   } else{
         victor1.set(ControlMode.PercentOutput, xAxis);
         victor2.set(ControlMode.PercentOutput, xAxis);
      }
   }
         public static String getQuadrant() {
             return quadrant;
         }
        
    }

