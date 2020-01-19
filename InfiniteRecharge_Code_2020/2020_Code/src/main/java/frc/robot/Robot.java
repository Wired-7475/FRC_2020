package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Ballshooter;
import frc.robot.commands.IntakeCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static Ballshooter shooter;
  public static Intake intake;

  public static AHRS navX;

  public static Encoder leftEncoder = new Encoder(0, 1, false , EncodingType.k4X);
  public static Encoder rightEncoder = new Encoder(2,3,false, EncodingType.k4X);
  public static PIDController testpid = new PIDController(0,0,0);
  public static ShuffleboardTab autoTab;
  public static NetworkTableEntry pidA;
  public static double[] defaultArray = new double[0];
  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    shooter = new Ballshooter();
    intake = new Intake();
    navX = new AHRS(I2C.Port.kMXP);

    leftEncoder.setDistancePerPulse(60 / 8192);
    leftEncoder.setMaxPeriod(0.1);
    leftEncoder.setMinRate(10);
    leftEncoder.setSamplesToAverage(7);
    leftEncoder.setReverseDirection(true);
   
    rightEncoder.setDistancePerPulse(60 / 8192);
    rightEncoder.setMaxPeriod(0.1);
    rightEncoder.setMinRate(10);
    rightEncoder.setSamplesToAverage(7);
    rightEncoder.setReverseDirection(false);
    
    autoTab = Shuffleboard.getTab("PID");
    //pidA = autoTab.add("PID Loop A", testpid).withWidget(BuiltInWidgets.kPIDController).getEntry();

   }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Left Encoder", leftEncoder.get());
    autoTab.add("Left Encoder", leftEncoder.get()).getEntry();
    autoTab.add("Right Encoder", rightEncoder.get()).getEntry();
  //autoTab.add("PID Loop", testpid.calculate(leftEncoder.get(), 10)).getEntry();
  //testpid.calculate(leftEncoder.getDistance(), 10);
  }

  @Override
  public void teleopPeriodic() {
  Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
    
  }


}
