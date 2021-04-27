package edu.uw.stluong.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.stluong.dotify.databinding.ActivitySongListBinding

private const val CURRENT_SONG = "currentSong"
class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private var allSongs: List<Song> = SongDataProvider.getAllSongs()
    private lateinit var currentSong: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }
        allSongs = SongDataProvider.getAllSongs()
        title = getString(R.string.song_list_title)
        with (binding) {
            val adapter = SongListAdapter(allSongs)
            rvSongs.adapter = adapter
            btnShuffle.setOnClickListener {
                allSongs = allSongs.shuffled()
                rvSongs.adapter = adapter.apply { updateSong(allSongs) }
            }
            adapter.onSongClickListener = {currentSongObj: Song -> changeSelectedSong(currentSongObj)}
            tvSelectedSong.setOnClickListener { navigateToPlayerActivity(this@SongListActivity, currentSong)}
        }
        if (savedInstanceState != null) {
            currentSong = savedInstanceState.getParcelable(CURRENT_SONG) ?: return
            changeSelectedSong(currentSong)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(CURRENT_SONG, currentSong)
        super.onSaveInstanceState(outState)
    }

    private fun changeSelectedSong(currentSongObj: Song) {
        this.currentSong = currentSongObj
        val songTitle = currentSong.title
        val artist = currentSongObj.artist
        binding.tvSelectedSong.text = getString(R.string.selected_song, songTitle, artist)
        binding.llMiniPlayer.visibility = View.VISIBLE
    }
}