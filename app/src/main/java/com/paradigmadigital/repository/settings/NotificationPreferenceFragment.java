package com.paradigmadigital.repository.settings;

import com.paradigmadigital.R;

import android.os.Bundle;

public class NotificationPreferenceFragment extends SettingsFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_notification);
        setHasOptionsMenu(true);
    }
}
