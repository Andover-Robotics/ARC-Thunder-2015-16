package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by admin on 10/14/2015.
 */

public class thenderTelop2015 extends OpMode {

    DcMotor L;
    DcMotor R;

    @Override
    public void init(){
        L = hardwareMap.dcMotor.get("L"); //i dont think we've set up the phones yet so be sure
        R = hardwareMap.dcMotor.get("R");// to name the left and right motors l and r on the hardware map
        R.setDirection(DcMotor.Direction.REVERSE);//im pretty sure you can reverse a motor within the hardware map
        //im not doing that because the phone sucks and we haven't even gotten it to work yet
    }
    @Override
    public void loop(){
        telemetry.addData("left motor", L.getPower());
        telemetry.addData("right motor", R.getPower());
        double left = Math.pow(-gamepad1.left_stick_y, 2); //logarithmic contrail-dont think this is gonna work
        double right = Math.pow(-gamepad1.right_stick_y, 2);//since the range of motor values is -1 to 1
        //itll probably be adjusted once we see what it does
        left = Range.clip(left, -1, 1);
        right= Range.clip(right, -1, 1);
        if(left<0){
            L.setPower(-left);
        }
        else{
            L.setPower(left);
        }
        if(right<0){
            R.setPower(-right);
        }
        else{
            R.setPower(right);
        }

    }
}
