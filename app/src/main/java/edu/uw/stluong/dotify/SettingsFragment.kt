package edu.uw.stluong.dotify

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.stluong.dotify.Users.UserRepository
import edu.uw.stluong.dotify.databinding.FragmentSettingsBinding
import edu.uw.stluong.dotify.manager.SongNotificationManager
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }
    private lateinit var userRepository: UserRepository
    private val dotifyApp by lazy {requireActivity().application as DotifyApplication }
    private val songNotifier: SongNotificationManager by lazy {dotifyApp.songNotificationManager}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        with(binding) {
            val currentSong = safeArgs.givenSong
            val songCount = safeArgs.songCounter
            lifecycleScope.launch {
                runCatching {
                    val currentUser = userRepository.getUser()
                    btnProfile.setOnClickListener{
                        navController.navigate(NavGraphDirections.actionGlobalProfileFragment(currentUser))
                    }
                }.onFailure {
                    tvErrorMsg.visibility = View.VISIBLE
                    btnProfile.visibility = View.GONE
                }
            }
            btnProfileStats.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalStatisticsFragment(currentSong, songCount))
            }
            btnAbout.setOnClickListener {
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }
            refresh.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    songNotifier.notifySong()
                } else {
                    songNotifier.stopPeriodicallyNotifying()
                }
            }

        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        val dotifyApp = context.applicationContext as DotifyApplication
        userRepository = dotifyApp.userRepository
        super.onAttach(context)
    }
}