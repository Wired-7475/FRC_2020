package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Scheduler;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends TimedRobot {
  public static Drivetrain drivetrain;
  public static AHRS navX;
  public static Autonomus auto;
  public static Encoder leftReelEncoder = new Encoder(0, 1, false , EncodingType.k2X);
  public static Encoder rightReelEncoder = new Encoder(2,3,false, EncodingType.k2X);

  @Override
  public void robotInit() {
    drivetrain = new Drivetrain();
    navX = new AHRS(I2C.Port.kMXP);
    auto = new Autonomus();
    leftReelEncoder.setDistancePerPulse(0.0006);
    leftReelEncoder.setMaxPeriod(0.1);
    leftReelEncoder.setMinRate(10);
    leftReelEncoder.setSamplesToAverage(7);
    leftReelEncoder.setReverseDirection(true);
   
    rightReelEncoder.setDistancePerPulse(0.0006);
    rightReelEncoder.setMaxPeriod(0.1);
    rightReelEncoder.setMinRate(10);
    rightReelEncoder.setSamplesToAverage(7);
    rightReelEncoder.setReverseDirection(false);
    
   }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {
    auto.straight(10);
    auto.straight(-20);
  }

  @Override
  public void teleopPeriodic() {
  Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
    
  }


}
