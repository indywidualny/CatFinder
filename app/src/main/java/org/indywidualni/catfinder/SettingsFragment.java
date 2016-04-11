package org.indywidualni.catfinder;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    private int clickCounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        findPreference("author").setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (clickCounter < 3) {
            // count clicks, 4th click shows a toast
            clickCounter++;
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.meow), Toast.LENGTH_SHORT).show();
            clickCounter = 0;
        }
        return true;
    }

}