package com.lightningrobotics.common.subsystem.drivetrain;

import com.lightningrobotics.common.geometry.Rotation;

public class DrivetrainSpeed {

    public double vx;
    public double vy;
    public double omega;

    public DrivetrainSpeed() {}

    public DrivetrainSpeed(double vx, double vy, double omega) {
        this.vx = vx;
        this.vy = vy;
        this.omega = omega;
    }

    public static DrivetrainSpeed fromFieldRelativeSpeeds(double vx, double vy, double omega, Rotation robotAngle) {
        return new DrivetrainSpeed(vx * robotAngle.cos() + vy * robotAngle.sin(), -vx * robotAngle.sin() + vy * robotAngle.cos(), omega);
    }

}
