package com.paradigmadigital.repository.settings;

import com.paradigmadigital.R;

import android.os.Bundle;

public class GeneralPreferenceFragment extends SettingsFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(true);
    }
}
