package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.Autonomus;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static Ballshooter shooter;
  public static Intake intake;
  public static Lift lift;
  public static WheelSpinner wheel;

  public static AHRS navX;

  public static Encoder leftEncoder;
  public static Encoder rightEncoder;
  public static Encoder reelEncoder;
  public static PIDController testpid;
  public static ShuffleboardTab autoTab;
  public static NetworkTableEntry pidA;
  public static double[] defaultArray = new double[0];
  NetworkTableEntry leftEncoderData;
  NetworkTableEntry rightEncoderData;
  public static Timer timer;
  public static UsbCamera camera1;
  public static UsbCamera camera2;
  public static Autonomus auto;

  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    shooter = new Ballshooter();
    intake = new Intake();
    lift = new Lift();
    wheel = new WheelSpinner();

    camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera2 = CameraServer.getInstance().startAutomaticCapture(1);

    navX = new AHRS(I2C.Port.kMXP);

    leftEncoder = new Encoder(0, 1, false , EncodingType.k4X);
    leftEncoder.setDistancePerPulse(1.0 / 2042.0);
    leftEncoder.setMaxPeriod(0.1);
    leftEncoder.setMinRate(5);
    leftEncoder.setSamplesToAverage(4);
    leftEncoder.setReverseDirection(false);
   
    rightEncoder = new Encoder(2,3,false, EncodingType.k4X);
    rightEncoder.setDistancePerPulse(1.0 / 2042.0);
    rightEncoder.setMaxPeriod(0.1);
    rightEncoder.setMinRate(1);
    rightEncoder.setSamplesToAverage(4);
    rightEncoder.setReverseDirection(false);

    reelEncoder = new Encoder(4,5,false, EncodingType.k4X);
    reelEncoder.setDistancePerPulse(1.0 / 2042.0);
    reelEncoder.setMaxPeriod(0.1);
    reelEncoder.setMinRate(1);
    reelEncoder.setSamplesToAverage(4);
    reelEncoder.setReverseDirection(false);
    
    testpid = new PIDController(0.5,0,0);
    testpid.setTolerance(0.5, 0.5);
    testpid.setIntegratorRange(-0.25, 0.25);

    autoTab = Shuffleboard.getTab("PID");
    leftEncoderData = autoTab.add("Left Encoder", leftEncoder.getDistance()).getEntry();
    rightEncoderData = autoTab.add("Right Encoder", rightEncoder.get()).getEntry();
    autoTab.add("PID Loop A", testpid);
    timer = new Timer();
    timer.start();

    int[][] steps = 
    {{Autonomus.WRIST_ON, 0},
    {Autonomus.DELAY, 1},
    {Autonomus.WRIST_OFF, 0},
    {Autonomus.FWD, -3},
    {Autonomus.SHOOT_ON, 0},
    {Autonomus.FWD, 1},
    {Autonomus.DELAY, 3},
    {Autonomus.INTAKE_ON, 0},
    {Autonomus.DELAY, 5},
    {Autonomus.SHOOT_OFF, 0},
    {Autonomus.INTAKE_OFF, 0}/* ,
    {Autonomus.TURN, 90},
    {Autonomus.FWD, 3},
    {Autonomus.TURN, 0},
    {Autonomus.BAR_ON, 0},
    {Autonomus.FWD, 3},
    {Autonomus.BAR_OFF, 0*/
  };
    auto = new Autonomus(leftEncoder, rightEncoder, navX, steps);
   }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    auto.drive();
  }

  @Override
  public void teleopPeriodic() {
    leftEncoderData.setDouble(leftEncoder.getRate());
  Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
    
  }

  @Override
  public void disabledInit() {
    leftEncoder.reset();
    rightEncoder.reset();
    testpid.reset();
    timer.reset();
    auto.reset();
  }

  @Override
  public void disabledPeriodic() {
  }


}
