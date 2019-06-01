package com.example.vasug.timerintro;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int count;

    TextView text ;
    SeekBar seekBar;
    Button but ;
    boolean countactive = false;
    CountDownTimer ctdown;
    public void onCLick(View view){

        if(countactive){
            but.setText("Start");
            count = 30;
            seekBar.setEnabled(true);
            seekBar.setProgress(30);
            settext(30);
            countactive = false;
            ctdown.cancel();
            return;
        }

        seekBar.setEnabled(false);
        countactive = true;
        but.setText("Stop");
        ctdown = new CountDownTimer(count*1000,1000){
            public void onTick(long milisecondsuntildone){
                settext((int) (milisecondsuntildone/1000));

            }

            public void onFinish(){
                playmedia();
                seekBar.setEnabled(true);
                but.setText("Start");
                count = 30;
                seekBar.setProgress(30);
                settext(30);
                countactive = false;
            }
        };


        ctdown.start();
    }

    public void playmedia(){
        MediaPlayer media = MediaPlayer.create(this, R.raw.airhorn);
        media.start();

    }

    public void settext(int i){
        Log.i("hello", "onTick: "+i);
        int min = i/60;
        int sec = i%60;
        String str = min+":";
        if(sec==0){
            str+="00";
        }
        else if(sec<10){
            str+="0"+sec;
        }else{
            str+= sec;
        }

        text.setText(str);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 30;
        text = findViewById(R.id.textView);
        but = findViewById(R.id.button);
        int maxcount = 600;

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(maxcount);
        seekBar.setProgress(30);
        settext(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = progress;
                settext(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(countactive){
                    return;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
