package com.example.drenu.testdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.drenu.testdrawer.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private ImageView iv,iv1,iv2;
    Animation fromleft;
    Animation fromtop;
    Animation rotate;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv=(ImageView)findViewById(R.id.sp2);
        iv1=(ImageView)findViewById(R.id.sp1);

        fromleft= AnimationUtils.loadAnimation(this,R.anim.fromleft);
        iv.setAnimation(fromleft);

        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        iv1.setAnimation(fromtop);

        timer= new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000 );

    }
}
