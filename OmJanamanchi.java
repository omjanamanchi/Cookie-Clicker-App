package com.example.animationsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OmJanamanchi extends AppCompatActivity
{

    //Basic Game Data + Images + Layouts
    TextView title;
    TextView money;
    TextView moneyPerSecond;
    TextView moneyPerClick;
    ImageView mole_character;
    ConstraintLayout layout;
    int points = 0;
    int moneyPerSecond_rate = 0;
    int moneyPerClick_rate = 1;
    int x = 0;
    TextView plusOne;

    Button wooden_hammer_button;
    TextView wooden_hammer_name;
    int wooden_hammer_cost = 50;
    Button gold_hammer_button;
    TextView gold_hammer_name;
    int gold_hammer_cost = 150;
    Button diamond_hammer_button;
    TextView diamond_hammer_name;
    int diamond_hammer_cost = 500;
    ImageView hammer;
    boolean hammer_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        money = findViewById(R.id.money);
        moneyPerSecond = findViewById(R.id.moneyPerSecond);
        moneyPerClick = findViewById(R.id.moneyPerClick);
        mole_character = findViewById(R.id.mole);
        layout = findViewById(R.id.layout);
        hammer = findViewById(R.id.hammer);
        wooden_hammer_button = findViewById(R.id.wooden_hammer_button);
        wooden_hammer_name = findViewById(R.id.wooden_hammer_name);
        gold_hammer_button = findViewById(R.id.gold_hammer_button);
        gold_hammer_name = findViewById(R.id.gold_hammer_name);
        diamond_hammer_button = findViewById(R.id.diamond_hammer_button);
        diamond_hammer_name = findViewById(R.id.diamond_hammer_name);

        hammer = findViewById(R.id.hammer);
        hammer.setVisibility(View.INVISIBLE);

        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        //Animations
        final ScaleAnimation anim1 = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(200);
        final ScaleAnimation anim2 = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setDuration(150);
        final TranslateAnimation floatUp = new TranslateAnimation(0, 0, 0, -500);
        floatUp.setDuration(1000);
        final RotateAnimation hammer_swing = new RotateAnimation(-20f, 20f, Animation.RELATIVE_TO_SELF, 1f);
        hammer_swing.setDuration(500);

        Thread t = new Thread(){
            @Override
            public void run(){

                while(!isInterrupted()){
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                moneyPerSecond.setText(moneyPerSecond_rate + " $/s"); // setting rate
                                moneyPerClick.setText("$" + moneyPerClick_rate + " per Click"); //setting click tempo

                                if(moneyPerClick_rate > 1)
                                    points+=moneyPerClick_rate; // setting points
                                money.setText("$"+points);


                                if(points >= wooden_hammer_cost)
                                {
                                    wooden_hammer_button.setEnabled(true);
                                }
                                else
                                {
                                    wooden_hammer_button.setEnabled(false);
                                }

                                if(points >= gold_hammer_cost)
                                {
                                    gold_hammer_button.setEnabled(true);
                                }
                                else
                                {
                                    gold_hammer_button.setEnabled(false);
                                }

                                if(points >= diamond_hammer_cost)
                                {
                                    diamond_hammer_button.setEnabled(true);
                                }
                                else
                                {
                                    diamond_hammer_button.setEnabled(false);
                                }

                                if(hammer_check == true)
                                {
                                    hammer.setImageResource(R.drawable.diamond_hammer);
                                    hammer.startAnimation(hammer_swing);
                                }

                            }
                        });
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();


        mole_character.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(plusOne);
                points+=moneyPerClick_rate;
                money.setText("$" + points);
                v.startAnimation(anim1);
                v.startAnimation(anim2);
                plusOne = new TextView(OmJanamanchi.this);
                plusOne.setTextSize(24);
                plusOne.setTextColor(Color.BLACK);
                plusOne.setText("+" + moneyPerClick_rate);
                plusOne.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.FILL_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams)plusOne.getLayoutParams();



                x = (int) (Math.random()*100 + 300);
                if(x%2==0){
                    p.leftMargin = 200;
                }
                else{
                    p.leftMargin = 800;
                }


                plusOne.setLayoutParams(p);
                ((ConstraintLayout) layout).addView(plusOne);
                plusOne.startAnimation(floatUp);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        plusOne.setVisibility(View.GONE);
                    }
                }, 2000);

            }
        });

        wooden_hammer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points-=wooden_hammer_cost;
                wooden_hammer_cost*=2;
                wooden_hammer_name.setText("Wood Hammer");
                wooden_hammer_button.setText("$"+wooden_hammer_cost);
                moneyPerClick_rate+=5;
                Toast.makeText(OmJanamanchi.this, "Wood Hammer", Toast.LENGTH_LONG).show();
                wooden_hammer_button.setEnabled(false);
                hammer.setVisibility(View.VISIBLE);
                hammer_check = true;
            }
        });

        gold_hammer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points-=gold_hammer_cost;
                gold_hammer_cost*=2;
                gold_hammer_name.setText("Gold Hammer");
                gold_hammer_button.setText("$"+gold_hammer_cost);
                moneyPerSecond_rate+=7;
                Toast.makeText(OmJanamanchi.this, "Gold Hammer", Toast.LENGTH_LONG).show();
                gold_hammer_button.setEnabled(false);
                hammer.setVisibility(View.VISIBLE);
                hammer_check = true;
            }
        });

        diamond_hammer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points-=diamond_hammer_cost;
                diamond_hammer_cost*=2;
                diamond_hammer_name.setText("Diamond Hammer");
                diamond_hammer_button.setText("$"+diamond_hammer_cost);
                moneyPerClick_rate+=15;
                moneyPerSecond_rate+=17;
                Toast.makeText(OmJanamanchi.this, "Diamond Hammer", Toast.LENGTH_LONG).show();
                diamond_hammer_button.setEnabled(false);
                hammer.setVisibility(View.VISIBLE);
                hammer_check = true;
            }
        });

    }
}