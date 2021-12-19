package com.sssoyalan.movieapp.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sssoyalan.movieapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
    }
}