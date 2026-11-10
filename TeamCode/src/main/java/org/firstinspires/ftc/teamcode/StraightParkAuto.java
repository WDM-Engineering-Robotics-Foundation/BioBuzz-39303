package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "StraightParkAuto")
public class StraightParkAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        FunctionListAuto ListAuto = new FunctionListAuto(hardwareMap, telemetry);


        waitForStart();
        ListAuto.driveForward(-0.50,350); //still in ticks
        ListAuto.driveRight(0.25,50); //still in ticks
    }
}