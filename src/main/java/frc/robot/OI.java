/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI /*GEVALD*/ {
    public OI() {
    }

    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(0);

    public double adjustInput(double value) {
        return Math.abs(value) * value;
    }
        public double getLeftY(){
            return adjustInput(-leftJoystick.getY());
        }

        public double getRightX(){
            return adjustInput(-rightJoystick.getX());

        }
    }