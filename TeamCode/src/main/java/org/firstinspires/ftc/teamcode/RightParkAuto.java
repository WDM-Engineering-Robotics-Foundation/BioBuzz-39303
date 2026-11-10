package org.firstinspires.ftc.teamcode;
// Example method to strafe in Autonomous (Time-based snippet)


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name="RightParkAuto")
public class RightParkAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize out auto actions and put them in a var
        FunctionListAuto ListAuto = new FunctionListAuto(hardwareMap, telemetry);
        // waits until the start button gets pressed
        waitForStart();

        ListAuto.shoot(5_000);
        // 24 is a PLACEHOLDER - please find the correct value for your robot
        ListAuto.driveForward(0.25,24);
        ListAuto.driveLeft(-0.25,-24);
    }
}
