package frc.robot;

import java.net.SecureCacheResponse;

import com.kauailabs.navx.frc.AHRS;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.OI;
import frc.subsystems.Drivetrain;
import frc.commands.TankDrive;


public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static AHRS navX;
  public static Autonomus auto;
  public static Encoder leftEncoder = new Encoder(0, 1, false , EncodingType.k4X);
  public static Encoder rightEncoder = new Encoder(2,3,false, EncodingType.k4X);

  public static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
  Command drive;
  AnalogInput dio0 = new AnalogInput(0);

  ShuffleboardTab visionTab;
  ShuffleboardTab autoTab;
  NetworkTable table;
  NetworkTableEntry automode;
  static double[] camX;
  static double[] camY;
  static double[] camSize;
  double[] defaultNum = new double[0];
  FuelBallPipeline ballCam;
  CvSink cvSink;
  CvSource cvSource;
  Mat imgSource;
  CvSource outputStream;
  static Timer timer;
  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    navX = new AHRS(I2C.Port.kMXP);
   
    leftEncoder.setDistancePerPulse(1.0 / 2042.0);
    leftEncoder.setMaxPeriod(0.1);
    leftEncoder.setMinRate(5);
    leftEncoder.setSamplesToAverage(4);
    leftEncoder.setReverseDirection(false);
  
    rightEncoder.setDistancePerPulse(1.0 / 2042.0);
    rightEncoder.setMaxPeriod(0.1);
    rightEncoder.setMinRate(5);
    rightEncoder.setSamplesToAverage(4);
    rightEncoder.setReverseDirection(true);

  

    int[][] steps = 
    {{Autonomus.FWD, 5},
    {Autonomus.DELAY, 5},
    {Autonomus.TURN, -90},
    {Autonomus.FWD, 4},
    {Autonomus.TURN, 180},
    {Autonomus.FWD, -2},
    {Autonomus.TURN, 180},
    {Autonomus.FWD, 6}
  
  }
    ;
    auto = new Autonomus(leftEncoder, rightEncoder, navX, steps);

    visionTab = Shuffleboard.getTab("Vision");
    table = NetworkTableInstance.getDefault().getTable("GRIP/myBlobsReport");
    ballCam = new FuelBallPipeline();
    cvSink = CameraServer.getInstance().getVideo();
    imgSource = new Mat();
    outputStream = CameraServer.getInstance().putVideo("Mask", 640, 480);

    timer = new Timer();
    timer.start();
  }
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    
    //auto.straight(-10);
    leftEncoder.reset();
    rightEncoder.reset();
    navX.reset();
    auto.reset();
  }

  @Override
  public void autonomousPeriodic() {

    auto.drive();

    /*if(cvSink.grabFrame(imgSource) != 0)
      ballCam.process(imgSource);
    camX = table.getEntry("x").getDoubleArray(defaultNum);
    camY = table.getEntry("y").getDoubleArray(defaultNum);
    camSize = table.getEntry("area").getDoubleArray(defaultNum);
    outputStream.putFrame(ballCam.maskOutput());
    */

  }

  @Override
  public void teleopInit() {
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    SmartDashboard.putNumber("Throttle", OI.joy.getThrottle() / -2 + 0.5);
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
   leftEncoder.reset();
    rightEncoder.reset();
    auto.reset();
  }
}
