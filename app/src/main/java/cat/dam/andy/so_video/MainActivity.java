package cat.dam.andy.so_video;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer; //per reproductor d'audio
    private Button btnSound, btnVideo;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        controlMusica();
        controlVideo();
    }

    private void loadViews() {
        btnSound = findViewById(R.id.btnSound);
        btnVideo = this.findViewById(R.id.btnVideo);
        videoView = findViewById(R.id.videoView);
    }

    private void controlMusica() {
        btnSound.setText(R.string.sound_player);
        mediaPlayer = MediaPlayer.create(this, R.raw.mallerenga);
        //control botó
        btnSound.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                //mp.pause(); // podriem utilitzar pausa enlloc d'aturar completament
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release(); //per alliberar memòria
                mediaPlayer = null; //evita que es pugui clicar quan s'està alliberant de memòria
                btnSound.setText(R.string.sound_player);
                btnSound.setAlpha(1f); //sense trasparència
            }
            else {
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.mallerenga);
                mediaPlayer.start();
                btnSound.setText(R.string.listening);
                btnSound.setAlpha(.5f); //amb trasparència
            }
        });
    }

    private void controlVideo() {
        btnVideo.setText(R.string.video_player);
        Uri video = Uri.parse("android.resource://" +
                getPackageName() + "/"
                + R.raw.mallerengacarbonera);
        videoView.setVideoURI(video);
        videoView.setMediaController(new MediaController(this));
        // control quan finalitza el video
        videoView.setOnCompletionListener(mp -> {
            btnVideo.setText(R.string.video_player);
            btnVideo.setAlpha(1f); // no transparència
        });
        //control botó
        btnVideo.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause(); // per pausa
                /*videoView.stopPlayback();
                videoView.resume();*/ //per reiniciar video
                btnVideo.setText(R.string.paused);
                btnVideo.setAlpha(1f); // no transparència
            }
            else {
                videoView.requestFocus();
                videoView.start();
                btnVideo.setText(R.string.showing);
                btnVideo.setAlpha(.5f); // amb transparència
            }
        });
    }
}
