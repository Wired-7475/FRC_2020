package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class joystickDrive {
    static String quadrant = "None";
    public static void joyDrive(double xAxis, double yAxis, boolean trig1, boolean trig2, VictorSPX victor1, VictorSPX victor2) {

   if(trig2) {
      yAxis /= 6;
      xAxis /= 6;
   } else if(trig1) { 

   } else {
      xAxis /= 4;
      yAxis /= 4;
   }

      if(yAxis < -OI.deadzone) {
         if(xAxis > 0) {
            victor1.set(ControlMode.PercentOutput, -yAxis);
            victor2.set(ControlMode.PercentOutput, yAxis + xAxis);
            quadrant = "Quadrant One";
         }else {
            victor1.set(ControlMode.PercentOutput, -yAxis + xAxis);
            victor2.set(ControlMode.PercentOutput, yAxis);
            quadrant = "Quadrant Two";
         }
      }else if(yAxis > OI.deadzone) {
         if(xAxis > 0) {
            victor1.set(ControlMode.PercentOutput, -yAxis + xAxis);
            victor2.set(ControlMode.PercentOutput, yAxis); 
            quadrant = "Quadrant Three";
         }else {
            victor1.set(ControlMode.PercentOutput, -yAxis);
            victor2.set(ControlMode.PercentOutput, yAxis + xAxis);
            quadrant = "Quadrand Four";
         }
      } else {
         victor1.set(ControlMode.PercentOutput, xAxis / 2);
         victor2.set(ControlMode.PercentOutput, xAxis / 2);
      }      
   }
         public static String getQuadrant() {
             return quadrant;
         }
        
    }

