package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private  AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();

//        Word one = new Word("One","Koow");
//        words.add(one); instead of doing this Do the below way.

        words.add(new Word("One","Koow", R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","Labo", R.drawable.number_two,R.raw.number_one));
        words.add(new Word("Three","Saddex", R.drawable.number_three,R.raw.number_one));
        words.add(new Word("Four","Afar", R.drawable.number_four,R.raw.number_one));
        words.add(new Word("Five","Shan", R.drawable.number_five,R.raw.number_one));
        words.add(new Word("Six","Lix", R.drawable.number_six,R.raw.number_one));
        words.add(new Word("Seven","Toddobo", R.drawable.number_seven,R.raw.number_one));
        words.add(new Word("Eight","Siddeed", R.drawable.number_eight,R.raw.number_one));
        words.add(new Word("Nine","Sagaal", R.drawable.number_nine,R.raw.number_one));
        words.add(new Word("Ten","Toban", R.drawable.number_ten,R.raw.number_one));


        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        final ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Release the mediaplayer if it currently exists because we are about to play a different sound file
                releaseMediaPlayer();

                Word word = words.get(position);



                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.
                    Log.v("NumbersActivity", "Current word: " + word);
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioPlayback());
                    mediaPlayer.start();
                    //markuu codka dhameeyone ka bixi si dab badan hadhow uu u cunin
                    mediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds. and we don't need to worry about behind the scene.
        releaseMediaPlayer();
    }
    //mar walba mediaplayer zero kadhig hadii kale dab badan ayuu cunaa
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
