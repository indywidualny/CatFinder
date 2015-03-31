package org.indywidualni.catfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class MainActivity extends ActionBarActivity {

    private MediaPlayer mp;
    private boolean isPlaying = false;
    final Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get shared preferences
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // create array list for chosen files
        final ArrayList<Integer> sounds = new ArrayList<Integer>();

        if(preferences.getBoolean("s1", true))
            sounds.add(R.raw.food_jar);
        if(preferences.getBoolean("s2", true))
            sounds.add(R.raw.food_bowl);
        if(preferences.getBoolean("s3", true))
            sounds.add(R.raw.chips_low);
        if(preferences.getBoolean("s4", true))
            sounds.add(R.raw.chips_loud);

        // use a beautiful IndieFlower font
        Typeface face = Typeface.createFromAsset(getAssets(), "IndieFlower.ttf");
        TextView textView = (TextView)this.findViewById(R.id.text1);
        textView.setTypeface(face);

        // create a clickable button from an image
        ImageView play = (ImageView)this.findViewById(R.id.button1);
        play.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(!sounds.isEmpty()) {
                    if (isPlaying) {
                        // if clicked the second time stop the player
                        //Log.v("isPlaing", "second click to stop");
                        mp.stop();
                        isPlaying = false;
                    } else {
                        // nothing is played, start the player
                        //Log.v("!isPlaing", "just started");

                        // animate the button
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_click));
                        // generate a random number every time someone clicks
                        int rndm = r.nextInt(sounds.size());
                        // set a random sound file to play
                        mp = MediaPlayer.create(getApplicationContext(), sounds.get(rndm));

                        // when finished change isPlaying to false
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override public void onCompletion(MediaPlayer mp) {
                                // playback completed
                                //Log.v("onCompletion", "playback completed");
                                isPlaying = false;
                            }
                        });

                        mp.start();
                        isPlaying = true;
                    }
                } else {
                    // there is nothing to play
                    Toast.makeText(getApplicationContext(), getString(R.string.no_sounds), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // advertisements
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // delaying the recreate() call for one millisecond
        // the activity gets properly resumed and it is only then killed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // recreate the Activity to reload all checked sounds
                recreate();
            }
        }, 1);
    }

}