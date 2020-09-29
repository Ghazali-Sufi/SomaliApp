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

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

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
        /*final: waxaa waaye maadaama uu jiro many Arraylist oo words ah, Activity kasta midkiisa gaar ayuu u ahaanaa,
        hadii kaleoo final aad dhihi weydo  wuu kugu qabasanaa oo waxuu ku leeyahy meel kale ayaa laga isticmaalay ama uu ka declare garesan yahay
         */

        words.add(new Word("Where are you going?","Halkeed usocotaa?",R.raw.number_one));
        words.add(new Word("Iam going to...","Waxaan usocdaa...",R.raw.number_one));
        words.add(new Word("Where do you live?","Halkeed ku nooshahay?",R.raw.number_one));
        words.add(new Word("I live in....","Waxaan ku noolahay...?",R.raw.number_one));
        words.add(new Word("What is your name?","Waa maxay magacaaga?",R.raw.number_one));
        words.add(new Word("My name is...","Magacayga waa...",R.raw.number_one));
        words.add(new Word("How are you feeling?","Sidee dareemaysaa",R.raw.number_one));
        words.add(new Word("I’m feeling good.","Waxaan dareemayaa fiicnaan",R.raw.number_one));
        words.add(new Word("Are you coming?","Miyaad imaneysaa?",R.raw.number_one));
        words.add(new Word("Yes, I’m coming.","Haa, waan imanayaa",R.raw.number_one));
        words.add(new Word("I’m coming.","Waan imaanayaa",R.raw.number_one));
        words.add(new Word("Let’s go.","Aan tagno.",R.raw.number_one));
        words.add(new Word("Come here","inta imaaw",R.raw.number_one));

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //parent: adapterViewga la click gareeyay
            //view: adapterViewga viewga korsaaran
            //position: viewgaas position kiisa
            //id: The row id of the item that was clicked
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();

                Word word = words.get(position);



                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.
                    Log.v("NumbersActivity", "Current word: " + word);
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioPlayback());
                    mediaPlayer.start();
                    //Positionka markaas la taagan yahay Audiogiisa ii soo hel kadibna ha daarmo

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
        }
    }

}
