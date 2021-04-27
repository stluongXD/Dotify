package edu.uw.stluong.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.stluong.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private val safeArgs: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(inflater)
        val currentSong = safeArgs.givenSong
        val songCount = safeArgs.songCounter

        with(binding) {
            ivSongCover.setImageResource(currentSong.largeImageID)
            tvPlayCount.text = getString(R.string.song_play_count, songCount)
        }

        return binding.root
    }
}