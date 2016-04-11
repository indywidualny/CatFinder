package org.indywidualni.catfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random randGenerator = new Random();
    private MediaPlayer player;
    private boolean isPlaying;
    private ArrayList<Integer> sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set a toolbar to replace the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // use a beautiful IndieFlower font
        Typeface face = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        TextView textView = (TextView) findViewById(R.id.text);

        if (textView != null)
            textView.setTypeface(face);

        // add sounds to new ArrayList
        addSounds();

        // create a clickable button from an image
        ImageView play = (ImageView) findViewById(R.id.button1);

        if (play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!sounds.isEmpty()) {
                        if (isPlaying) {
                            // if clicked the second time stop the player
                            player.stop();
                            player.release();
                            isPlaying = false;
                        } else {
                            // nothing is played, start the player
                            // animate the button
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.button_click));
                            // generate a random number every time someone clicks
                            int rand = randGenerator.nextInt(sounds.size());
                            // set a random sound file to play
                            player = MediaPlayer.create(getApplicationContext(), sounds.get(rand));

                            // when finished change isPlaying to false
                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // playback completed
                                    player.release();
                                    isPlaying = false;
                                }
                            });

                            player.start();
                            isPlaying = true;
                        }
                    } else {
                        // there is nothing to play
                        Toast.makeText(getApplicationContext(), getString(R.string.no_sounds),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // advertisements
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null)
            mAdView.loadAd(adRequest);
    }

    private void addSounds() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sounds = new ArrayList<>();

        if (preferences.getBoolean("s1", true))
            sounds.add(R.raw.food_jar);
        if (preferences.getBoolean("s2", true))
            sounds.add(R.raw.food_bowl);
        if (preferences.getBoolean("s3", true))
            sounds.add(R.raw.chips_low);
        if (preferences.getBoolean("s4", true))
            sounds.add(R.raw.chips_loud);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}