package pradyumna.simhansapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Player extends AppCompatActivity {
    //Variables Declaration ;
    TextView playerPosition , playerDuration;
    SeekBar seekBar;
    ImageView play_Btn , pause_Btn;

    MediaPlayer mediaPlayer;
    Handler handler  = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
// Assign Variables
        playerPosition =  findViewById(R.id.player_time_start);
        playerDuration = findViewById(R.id.player_time_end);
        seekBar = findViewById(R.id.seekBar);
        play_Btn =  findViewById(R.id.play_button);
        pause_Btn = findViewById(R.id.pause_button);

        //Initialize Media Player
        mediaPlayer = new MediaPlayer();

        seekBar.setMax(100);

        play_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    play_Btn.setImageResource(R.drawable.ic_play_btn);
                }else{
                    mediaPlayer.start();
                    play_Btn.setImageResource(R.drawable.ic_pause_btn);
                    updateSeekBar();
                }
            }
        });

        prepareMediaPlayer();
    }


    private void prepareMediaPlayer (){
        try {
            mediaPlayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
            mediaPlayer.prepare();
            playerPosition.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }

    private  Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration =  mediaPlayer.getCurrentPosition();
            playerPosition.setText(milliSecondToTimer(currentDuration));
        }
    };

    private  void  updateSeekBar() {
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondToTimer(long milliSeconds){
        String timerString = "";
        String secondString;

        int hours  = (int)(milliSeconds / (1000 * 60 *60));
        int minutes = (int)(milliSeconds % (1000 * 60 *60)) / (1000 * 60);
        int seconds = (int)(milliSeconds % (1000 * 60 *60) / (1000 * 60) / 1000);

        if(hours > 0){
            timerString = hours + ":";
        }
        if (minutes < 10){
            secondString = "0" + seconds;
        }else{
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return  timerString;
    }

}