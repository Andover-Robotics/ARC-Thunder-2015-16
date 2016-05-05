package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.GyroSensor;


/**
 * Created by admin on 10/14/2015.
 */

public class thenderTelop2015 extends OpMode {
    private DcMotor L;//left wheel motor
    private DcMotor R;//right wheel motor
    private DcMotor A;//unwinding of the hanging mechanism
    private DcMotor B;//gives slack & reels in rope
    private UltrasonicSensor U;//was going to be used in autonomous
    private GyroSensor G;//^
    private Servo servo;//arm servo
    private Servo servo2;//left hook
    private Servo servo3;//right hook
    private Servo servo4;//left plow servo
    private Servo servo5;//right plow servo
    private double servoposition = 0;//arms start in robot
    private double servoposition2 = 0;
    private double servoposition3 = 0.45;
    private double servoposition4 = 0.36;//plow starts in robot
    private double servoposition5 = 0.36;

    @Override
    public void init(){
        L = hardwareMap.dcMotor.get("L");//correspondence of hardware map names
        R = hardwareMap.dcMotor.get("R");
        A = hardwareMap.dcMotor.get("A");
        B = hardwareMap.dcMotor.get("B");
        U = hardwareMap.ultrasonicSensor.get("U");
        G = hardwareMap.gyroSensor.get("G");
        servo = hardwareMap.servo.get("servo");
        servo2 = hardwareMap.servo.get("servo2");
        servo3 = hardwareMap.servo.get("servo3");
        servo4 = hardwareMap.servo.get("servo4");
        servo5 = hardwareMap.servo.get("servo5");
        R.setDirection(DcMotor.Direction.REVERSE);//reverses the right motor
    }
    @Override
    public void loop(){
        //setting speeds of motors:
        //drivetrain has logarithmic control - cubed so numbers maintain sign
        double left = Math.pow(gamepad1.left_stick_y, 3);//y value of left stick on gamepad 1 controls the left wheel
        double right = Math.pow(gamepad1.right_stick_y, 3);//y value of right stick on gamepad 1 controls the right wheel
        double motora = -gamepad2.left_stick_y*0.7;//y value of left stick on gamepad 2 controls unwinding of the hanging mechanism
        //setting ranges of motors:
        left = Range.clip(left, -1, 1);//range of motor values is between -1 and 1
        right= Range.clip(right, -1, 1);
        motora=Range.clip(motora, -1, 1);
        //motor b does not need a range because it has set values rather than being controlled by a joystick
        //y and b buttons on gamepad 2 determine whether slack is given or rope is pulled in:
        if (gamepad2.y == true){
            B.setPower(1);
        }
        else if (gamepad2.b == true){
            B.setPower(-1);
        }
        else{
            B.setPower(0);
        }

        //setting ranges of servos:
        servoposition=Range.clip(servoposition, 0, 1);//range of servo values is between 0 and 1
        servoposition2=Range.clip(servoposition2, 0, 0.45);//stress is put on hook servos if they attempt to pass 0.45
        servoposition3=Range.clip(servoposition3, 0, 0.45);
        servoposition4=Range.clip(servoposition4, 0, 1);
        servoposition5=Range.clip(servoposition5, 0, 1);
        //y value of the right stick on gamepad 2 controls the arm:
        if(-gamepad2.right_stick_y < -0.5) {
            servoposition = servoposition - 0.003;
        }
        else if(-gamepad2.right_stick_y > -0.5 && -gamepad2.right_stick_y < -0.1){
            servoposition = servoposition - 0.001;
        }
        else if(-gamepad2.right_stick_y > 0.5){
            servoposition = servoposition + 0.003;
        }
        else if(-gamepad2.right_stick_y < 0.5 && -gamepad2.right_stick_y > 0.1){
            servoposition = servoposition + 0.001;
        }
        //on gamepad 1 x and a control the left hook, and y and b control the right hook:
        if (gamepad1.x == true){
            servoposition2 = servoposition2 - 0.003;
        }
        if (gamepad1.a == true){
            servoposition2 = servoposition2 + 0.003;
        }
        if (gamepad1.y == true){
            servoposition3 = servoposition3 - 0.003;
        }
        if (gamepad1.b == true){
            servoposition3 = servoposition3 + 0.003;
        }
        //d-pad position on gamepad 2 control the plow:
        if (gamepad2.dpad_up == true){
            servoposition4 = servoposition4 - 0.003;
            servoposition5 = servoposition5 - 0.003;
        }
        else if (gamepad2.dpad_down == true){
            servoposition4 = servoposition4 + 0.003;
            servoposition5 = servoposition5 + 0.003;
        }

        //setting ranges of servos again because you can never be too safe (´･ω･`):
        servoposition=Range.clip(servoposition, 0, 1);
        servoposition2=Range.clip(servoposition2, 0, 0.45);
        servoposition3=Range.clip(servoposition3, 0, 0.45);
        servoposition4=Range.clip(servoposition4, 0, 1);
        servoposition5=Range.clip(servoposition5, 0, 1);
        //makes the robot move:
        servo.setPosition(servoposition);
        servo2.setPosition(servoposition2);
        servo3.setPosition(servoposition3);
        servo4.setPosition(servoposition4);
        servo5.setPosition(servoposition5);
        A.setPower(motora);
        L.setPower(left);
        R.setPower(right);
        //shows values of servos on driver station, useful during testing
        telemetry.addData("Text", "WALL-E");
        telemetry.addData("servo2", "servo2: " + servoposition2);
        telemetry.addData("servo3", "servo3: " +servoposition3);
    }
}
