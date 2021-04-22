/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.lightningrobotics.quasar;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Robot Application Driver Class.
 * 
 * <p>Starts Robot Application.
 */
public final class Main {
    private Main() {
    }

    /**
     * Main initialization function. 
     * 
     * @param args Command line arguments
     */
    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
