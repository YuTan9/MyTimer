package edu.sjsu.android.mytimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import edu.sjsu.android.mytimer.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CountDownTimer timer;
    private boolean paused = false;

    private long time_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
        time_left = 0L;
        timer = startNewTimer(time_left, false);
//        binding.stop2.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.play,0, 0, 0);
        binding.start1.setOnClickListener(this::start);
        binding.add601.setOnClickListener(this::add60);
        binding.stop1.setOnClickListener(this::stop);
        binding.reset1.setOnClickListener(this::reset);
        binding.start2.setOnClickListener(this::start);
        binding.add602.setOnClickListener(this::add60);
        binding.stop2.setOnClickListener(this::stop);
        binding.reset2.setOnClickListener(this::reset);
    }


    private CountDownTimer startNewTimer(long time, boolean vibrate){
        return new CountDownTimer(time, 100L) {
            @Override
            public void onTick(long l) {
                time_left = l;
                binding.timer.setTextColor(Color.WHITE);
                binding.timer2.setTextColor(Color.WHITE);
                if(l >= 60000L){
                    int minute = (int) (l / 60000L);
                    int second= (int) (l / 1000L) - 60 * minute;
                    int mil = (int) (l/100L) - (int)(l/1000L) * 10;
                    String output = String.format("%d:%02d.%d", minute, second, mil);
                    binding.timer.setText(output);
                    binding.timer2.setText(output);
                } else if (l <= 10000L) {
                    binding.timer.setTextColor(Color.YELLOW);
                    binding.timer2.setTextColor(Color.YELLOW);
                    int mil = (int) (l/100L) - (int)(l/1000L) * 10;
                    String output = String.format(":%02d.%d", (int)(l / 1000), mil);
                    binding.timer.setText(output);
                    binding.timer2.setText(output);
                } else {
                    int mil = (int) (l/100L) - (int)(l/1000L) * 10;
                    String output = String.format(":%02d.%d", (int)(l / 1000), mil);
                    binding.timer.setText(output);
                    binding.timer2.setText(output);
                }
            }

            @Override
            public void onFinish() {
                if(vibrate) {
                    binding.timer.setTextColor(Color.RED);
                    binding.timer2.setTextColor(Color.RED);
                    binding.timer.setText(":00.0");
                    binding.timer2.setText(":00.0");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    long[] timings = new long[]{400L, 400L, 400L};
                    int[] amplitudes = new int[]{255, 255, 255};
                    v.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1));
                }
            }
        };
    }
    private void reset(View view) {
        timer.cancel();
        time_left = 0L;
        timer = startNewTimer(time_left, false);
        binding.timer.setText(":00.0");
        binding.timer2.setText(":00.0");
        binding.timer.setTextColor(Color.WHITE);
        binding.timer2.setTextColor(Color.WHITE);
    }

    public void start(View view){
        timer.cancel();
        time_left = 30000L;
        timer = startNewTimer(time_left, true);
        timer.start();
    }
    public void add60(View view){
        timer.cancel();
        time_left += 60000L;
        timer = startNewTimer(time_left, true);
        timer.start();
    }
    public void stop(View view){
        timer.cancel();
        time_left += 10000L;
        timer = startNewTimer(time_left, true);
        timer.start();
    }
}