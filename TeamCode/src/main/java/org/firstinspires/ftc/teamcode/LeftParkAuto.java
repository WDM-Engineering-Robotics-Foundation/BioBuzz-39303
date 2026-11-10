package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "LeftParkAuto")
public class LeftParkAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize out auto actions and put them in a var
        FunctionListAuto ListAuto = new FunctionListAuto(hardwareMap, telemetry);
        // waits until the start button gets pressed
        waitForStart();
        //ListAuto.shoot(5_000);
        // 24 is a PLACEHOLDER - please find the correct value for your robot
        ListAuto.driveBackwards(0.5, 98); //hits wall; probably needs to decrease value
        ListAuto.driveRight(0.5, 55);
    }
}
