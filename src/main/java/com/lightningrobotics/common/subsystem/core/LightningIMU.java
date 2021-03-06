package com.lightningrobotics.common.subsystem.core;

import java.util.function.Supplier;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Base gyroscope type. Supports the {@link com.kauailabs.navx.frc.AHRS NavX} and
 * the {@link com.ctre.phoenix.sensors.PigeonIMU Pigeon}.
 */
public class LightningIMU extends SubsystemBase {

	/**
	 * Supported IMU types
	 */
	public enum IMUType {
		PIGEON, 
		NAVX, 
		NONE,
	}

	/**
	 * A generic function on the IMU to support lambda structure
	 */
	public interface IMUFunction {
		void exec();
	}

	/**
	 * Creates a new {@link com.ctre.phoenix.sensors.PigeonIMU Pigeon} with 
	 * the given ID.
	 * @param id CAN ID of the {@link com.ctre.phoenix.sensors.PigeonIMU Pigeon}
	 * @return IMU object configured for a {@link com.ctre.phoenix.sensors.PigeonIMU Pigeon}
	 */
	public static LightningIMU pigeon(int id) {
		return new LightningIMU(IMUType.PIGEON, id);
	}

	/**
	 * Creates a new {@link com.kauailabs.navx.frc.AHRS NavX}.
	 * @return IMU object configured for the NavX (SPI)
	 */
	public static LightningIMU navX() {
		return new LightningIMU(IMUType.NAVX);
	}

	/**
	 * Creates a static IMU
	 * @return IMU object that effectively does nothing
	 */
	public static LightningIMU none() {
		return new LightningIMU(IMUType.NONE);
	}

	private IMUType type;

	private AHRS navx = null;

	private PigeonIMU pigeon = null;

	private double[] ypr = null;

	private LightningIMU(IMUType type, int id) {
		this.type = type;
		switch (type) {
			case PIGEON:
				pigeon = new PigeonIMU(id);
				ypr = new double[3];
				break;
			case NAVX:
				navx = new AHRS(SPI.Port.kMXP);
				break;
			case NONE:
			default:
				break;
			}
		
	}

	private LightningIMU(IMUType type) {
		this(type, -1);
	}

	public IMUType getType() {
		return type;
	}

	/**
	 * Get the IMU heading as a {@link edu.wpi.first.wpilibj.geometry.Rotation2d Rotation2d}.
	 * @return The heading
	 */
	public Rotation2d getHeading() {
		if(type == IMUType.NAVX && navx != null) {
			return Rotation2d.fromDegrees(-navx.getAngle());
		}
		if(type == IMUType.PIGEON && ypr != null) {
			Rotation2d.fromDegrees((((ypr[0]+180)%360)-180));
		}
		return Rotation2d.fromDegrees(0d);
	}

	/**
	 * A function that can be used to get the heading of the IMU
	 * @return A supplier of {@link edu.wpi.first.wpilibj.geometry.Rotation2d Rotation2d} objects.
	 */
	public Supplier<Rotation2d> heading() {
		return this::getHeading;
	}

	/**
	 * Reset IMU heading to 0
	 */
	public void reset() {
		if(type == IMUType.NAVX && navx != null) {
			navx.reset();
		}
		if(type == IMUType.PIGEON && pigeon != null) {
			pigeon.setYaw(0d);
		}
	}

	/**
	 * Function to reset IMU heading
	 * @return An {@link LightningIMU.IMUFunction} that zeros the IMU heading when called
	 */
	public IMUFunction zero() {
		return this::reset;
	}

	@Override
	public void periodic() {
		if(type == IMUType.PIGEON && pigeon != null && ypr != null) {
			pigeon.getYawPitchRoll(ypr);
		}
	}

}
