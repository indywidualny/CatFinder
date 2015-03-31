package org.indywidualni.catfinder;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragment {

    private int clickCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // listener for author preference
        findPreference("author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(clickCounter < 3) {
                    // count clicks, 4th click shows a toast
                    clickCounter++;
                } else {
                    Toast.makeText(getActivity(), getString(R.string.meow), Toast.LENGTH_SHORT).show();
                    clickCounter = 0;
                }
                return true;
            }
        });
    }

}