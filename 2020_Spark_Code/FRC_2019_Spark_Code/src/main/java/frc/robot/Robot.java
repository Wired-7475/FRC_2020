/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
  public static Encoder leftReelEncoder = new Encoder(0, 1, false , EncodingType.k4X);
  public static Encoder rightReelEncoder = new Encoder(2,3,false, EncodingType.k4X);

  public static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
  Command drive;
  AnalogInput dio0 = new AnalogInput(0);

  ShuffleboardTab visionTab;
  NetworkTable table;
  static double[] camX;
  static double[] camY;
  static double[] camSize;
  double[] defaultNum = new double[0];
  FuelBallPipeline ballCam;
  CvSink cvSink;
  CvSource cvSource;
  Mat imgSource;
  CvSource outputStream;

  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    navX = new AHRS(I2C.Port.kMXP);

    auto = new Autonomus();
   
    leftReelEncoder.setDistancePerPulse(0.0006);
    leftReelEncoder.setMaxPeriod(0.1);
    leftReelEncoder.setMinRate(1);
    leftReelEncoder.setSamplesToAverage(7);
    leftReelEncoder.setReverseDirection(true);
  
    rightReelEncoder.setDistancePerPulse(0.0006);
    rightReelEncoder.setMaxPeriod(0.1);
    rightReelEncoder.setMinRate(0.5);
    rightReelEncoder.setSamplesToAverage(7);
    rightReelEncoder.setReverseDirection(false);

    visionTab = Shuffleboard.getTab("Vision");
    table = NetworkTableInstance.getDefault().getTable("GRIP/myBlobsReport");
    ballCam = new FuelBallPipeline();
    cvSink = CameraServer.getInstance().getVideo();
    imgSource = new Mat();
    outputStream = CameraServer.getInstance().putVideo("Mask", 640, 480);
  }
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {
    if(cvSink.grabFrame(imgSource) != 0)
      ballCam.process(imgSource);
    camX = table.getEntry("x").getDoubleArray(defaultNum);
    camY = table.getEntry("y").getDoubleArray(defaultNum);
    camSize = table.getEntry("area").getDoubleArray(defaultNum);
    outputStream.putFrame(ballCam.maskOutput());

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
    leftReelEncoder.reset();
  }
}
