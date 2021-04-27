package edu.uw.stluong.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.stluong.dotify.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        with(binding) {
            val currentSong = safeArgs.givenSong
            val songCount = safeArgs.songCounter
            btnProfile.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
            }
            btnProfileStats.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalStatisticsFragment(currentSong, songCount))
            }
            btnAbout.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }
        }
        return binding.root
    }
}