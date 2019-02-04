package com.xiaogege.honey;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartUpActivity extends AppCompatActivity {
    private ImageView boyImageView;
    private ImageView girlImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }
        setContentView (R.layout.activity_start_up);
        boyImageView=findViewById (R.id.boy);
        girlImageView=findViewById (R.id.girl);
        boyImageView.setImageResource (R.drawable.boy);
        girlImageView.setImageResource (R.drawable.gril);
        new Handler ().postDelayed (new Runnable () {
            @Override
            public void run() {
                Intent intent=new Intent (StartUpActivity.this,MainActivity.class);
                startActivity (intent);
                finish();
            }
        },2000);
    }
}
