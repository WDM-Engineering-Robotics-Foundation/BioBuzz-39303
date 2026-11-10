package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//48.78
public class FunctionListAuto {
    final double WHEEL_DIAMETER_INCHES = 4.09449;
    final double ENCODER_TICKS_PER_REV = 537.7;
    final double TICK_PER_INCH = ENCODER_TICKS_PER_REV/(WHEEL_DIAMETER_INCHES * Math.PI);
    //number gotten from average 1000 ticks distance/1000
    final double TICKS_PER_INCH_STRAFE = 43.4782608696;
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;

    private Telemetry telemetry;


    public final int LAUNCHER_TARGET_VELOCITY = 1200; //2678 RPM
    public final int LAUNCHER_MIN_VELOCITY = 1000; //2571 RPM

    // Constructor - runs to "initialize" the file
    public FunctionListAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmillServo");
        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        /*
         * To drive forward, most robots need the motor on one side to be reversed,
         * because the axles point in opposite directions. Pushing the left stick forward
         * MUST make robot go forward. So adjust these two lines based on your first test drive.
         */
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        /*
         * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
         * slow down much faster when it is coasting, making the drivetrain much more controllable.
         */
        leftFrontDrive.setZeroPowerBehavior(BRAKE);
        rightFrontDrive.setZeroPowerBehavior(BRAKE);
        leftBackDrive.setZeroPowerBehavior(BRAKE);
        rightBackDrive.setZeroPowerBehavior(BRAKE);
        intake.setZeroPowerBehavior(BRAKE);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        windmillServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
        this.telemetry = telemetry;
    }

    // Drive Forward using encoder ticks
    public void driveForward(double speed, double inches) {
        int initialPosition = leftFrontDrive.getCurrentPosition();

        int ticks = (int) (TICK_PER_INCH * inches);
        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(speed);

        // Since power is negative, encoder counts decrease. Loop while above target.
        while (leftFrontDrive.getCurrentPosition() > initialPosition - ticks) {
            // do nothing while waiting
        }

        stopMotors();
    }

    // Drive Backwards using encoder ticks
    public void driveBackwards(double speed, double inches) {
        int initialPosition = leftFrontDrive.getCurrentPosition();

        int ticks = (int) (TICK_PER_INCH * inches);
        leftFrontDrive.setPower(-speed);
        rightFrontDrive.setPower(-speed);
        leftBackDrive.setPower(-speed);
        rightBackDrive.setPower(-speed);

        // Since power is negative, encoder counts decrease. Loop while above target.
        while (leftFrontDrive.getCurrentPosition() < initialPosition + ticks) {
            telemetry.addData("Current Pos", leftFrontDrive.getCurrentPosition());
            telemetry.addData("Initial Pos", initialPosition);
            telemetry.addData("Goal Ticks", ticks);
            telemetry.update();

            // do nothing while waiting
        }

        stopMotors();
    }

    // Drive Left (Strafe Left) using encoder ticks
    public void driveLeft(double speed, double inches) {
        int intialPosition = leftFrontDrive.getCurrentPosition();
        int ticks = (int) (TICKS_PER_INCH_STRAFE * inches);
        leftFrontDrive.setPower(-speed);
        rightFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(-speed);

        while (leftFrontDrive.getCurrentPosition() < intialPosition + ticks) {
            // do nothing while waiting
        }

        stopMotors();
    }

    // Drive Right (Strafe Right) using encoder ticks
    public void driveRight(double speed, double inches) {
        int intialPosition = leftFrontDrive.getCurrentPosition();
        int ticks = (int) (TICKS_PER_INCH_STRAFE * inches);
        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(-speed);
        leftBackDrive.setPower(-speed);
        rightBackDrive.setPower(speed);

        while (leftFrontDrive.getCurrentPosition() > intialPosition - ticks) {
            // do nothing while waiting
        }

        stopMotors();
    }

    // Shoot function
    public void shoot(long milliseconds) {
        launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);

        while (launcher.getVelocity() < LAUNCHER_MIN_VELOCITY) {
            // do nothing
        }

        windmillServo.setPower(-1);
        waitMillis(milliseconds);

        windmillServo.setPower(0);
        launcher.setVelocity(0);
    }

    // Helper method to stop all drivetrain motors cleanly
    public void stopMotors() {
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
    }

    private void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}