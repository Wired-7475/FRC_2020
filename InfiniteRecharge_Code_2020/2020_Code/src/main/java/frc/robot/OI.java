package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class OI {

static Joystick joy = new Joystick(RobotMap.JOYSTICK);
public static XboxController xbox = new XboxController(RobotMap.XBOX);
public static XboxController drive = new XboxController(RobotMap.DRIVE_CONTROLLER);
public static double deadzone = 0.1;

    public static double getJoyX() {
        return Math.abs(joy.getX()) < deadzone ? 0.0 : joy.getX();
    }

    public static double getJoyY() {
        return Math.abs(joy.getY()) < deadzone ? 0.0 : joy.getY();
    }

    public static double getX(Hand hand) {
        return Math.abs(xbox.getX(hand)) < deadzone ? 0.0 : xbox.getX(hand);
    }

    public static double getY(Hand hand) {
        return Math.abs(xbox.getY(hand)) < deadzone ? 0.0 : xbox.getY(hand);
    }

    public static boolean getJoyButton(int button) {
        return joy.getRawButton(button);
    }

    public static double getThrottle() {
        return joy.getThrottle();
    }

    public static boolean getJoyTrig() {
        return joy.getTrigger();
    }
    public static boolean getAButton() {
        return xbox.getAButton();
    }

    public static boolean getBButton() {
        return xbox.getBButton();
    }

    public static boolean getXButton() {
        return xbox.getXButton();
    }

    public static boolean getYButton() {
        return xbox.getYButton();
    }

    public static boolean getBumper(Hand hand) {
        return xbox.getBumper(hand);
    }

    public static double getTrigger(Hand hand) {
        return xbox.getTriggerAxis(hand);
    }

}