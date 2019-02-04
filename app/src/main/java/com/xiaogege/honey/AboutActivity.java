package com.xiaogege.honey;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaogege.honey.sentence.MySentence;

public class AboutActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow ().setStatusBarColor (Color.WHITE);
        }
        setContentView (R.layout.activity_about);
        textView=findViewById (R.id.about_text_view);
        String message=getIntent ().getStringExtra ("about");
        if (message!=null){
            switch (message){
                case "about":
                    StringBuilder builder=new StringBuilder ();
                    for(int i=0;i<MySentence.PICTUREDESCRIPTION.length;i++){
                        builder.append (String.valueOf (i+1)+". "+MySentence.PICTUREDESCRIPTION[i]+"\n");
                    }
                    textView.setText (MySentence.CONTENT_ABOUT_HEAD+builder.toString ()+MySentence.CONTENT_ABOUT_FOOT);
                    break;
                case "github":
                    textView.setText (MySentence.CONTENT_GITHUB);
                    break;
                case "function":
                    textView.setText (MySentence.CONTENT_FUNCTION);
                    break;
                case "feedback":
                    textView.setText (MySentence.CONTENT_FEEDBACK);
                    break;
                case "help":
                    textView.setText (MySentence.CONTENT_HELP);
                    break;
                default:
                    break;
            }
        }

    }
}
