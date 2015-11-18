package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by admin on 10/14/2015.
 */

public class thenderTelop2015 extends OpMode {

    private DcMotor L;//left wheel motor
    private DcMotor R;//right wheel motor
    private DcMotor A;//vertical movement of the scoring mechanism
    private DcMotor B;//horizontal movement of the scoring mechanism
    private Servo servo;//slightly tilts scoring mechanism scoop


    @Override
    public void init(){
        L = hardwareMap.dcMotor.get("L");
        R = hardwareMap.dcMotor.get("R");
        A = hardwareMap.dcMotor.get("A");
        B = hardwareMap.dcMotor.get("B");
        servo = hardwareMap.servo.get("servo");

        //reverses the right motor
        R.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop(){
        /*logarithmic control for drivetrain; the gamepad  
        value is cubed so negative numbers stay negative*/
        double left = Math.pow(gamepad1.left_stick_y, 3);
        double right = Math.pow(gamepad1.right_stick_y, 3);

        //gamepad values modified into motor/servo values
        double motora = gamepad2.left_stick_y*0.5;
        double motorb = -gamepad2.left_stick_x*0.25;
        double servoposition = 0;

        //range of motor values is between -1 and 1
        left = Range.clip(left, -1, 1);
        right= Range.clip(right, -1, 1);
        motora=Range.clip(motora, -1, 1);
        motorb=Range.clip(motorb, -1, 1);

        //range of servo values is between 0 and 1
        servoposition=Range.clip(servoposition, 0, 1);

        if (-gamepad2.right_stick_y>0){
            servoposition = servoposition+0.01;
        }
        if (-gamepad2.right_stick_y<0){
            servoposition = servoposition-0.01;
        }
        servo.setPosition(servoposition);
        A.setPower(motora);
        B.setPower(motorb);
        L.setPower(left);
        R.setPower(right);



    }
}
