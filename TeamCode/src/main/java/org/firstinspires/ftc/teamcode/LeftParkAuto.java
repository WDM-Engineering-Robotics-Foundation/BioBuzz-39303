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
        ListAuto.shoot(5_000);
        ListAuto.driveBackwards(0.75, 10);
        ListAuto.driveRight(0.75, 50);
        ListAuto.driveForward(0.33, 20);
        ListAuto.UseIntake(3000);
        ListAuto.driveBackwards(0.75,10);
        ListAuto.driveLeft(0.75, 52);
        // 24 is a PLACEHOLDER - please find the correct value for your robot
        ListAuto.driveBackwards(0.75, 85);
        ListAuto.rotateBot(1, 750);
        ListAuto.shoot(5_000);
        ListAuto.driveLeft(0.75, 55);
    }
}