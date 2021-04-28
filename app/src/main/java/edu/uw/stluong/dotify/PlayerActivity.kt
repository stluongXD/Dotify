package edu.uw.stluong.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import com.ericchee.songdataprovider.Song
import edu.uw.stluong.dotify.databinding.ActivityPlayerBinding
import kotlin.random.Random

private const val SONG_KEY = "givenSong"
private const val COUNT_VALUE_KEY = "COUNT_VALUE_KEY"
fun navigateToPlayerActivity(context: Context ,currentSong: Song) {
    val intent = Intent(context, PlayerActivity::class.java)
    val bundle = Bundle().apply {
        putParcelable(SONG_KEY, currentSong)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {

    private var numSongPlays = 0
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }
        if (savedInstanceState != null) {
            numSongPlays = savedInstanceState.getInt(COUNT_VALUE_KEY, 0)
        } else {
            numSongPlays = Random.nextInt(100, 10000)
        }

        with(binding) {
            tvSongCount.text = getString(R.string.song_play_count, numSongPlays)
            ibPlay.setOnClickListener { incrementPlayCount() }
            ibPrev.setOnClickListener { displayToast(getString(R.string.skip_prev)) }
            ibNext.setOnClickListener { displayToast(getString(R.string.skip_next)) }
            ivSongCover.setOnLongClickListener {
                toggleSongCountColor()
                true
            }

            val givenSong = intent.getParcelableExtra<Song>(SONG_KEY)
            if (givenSong != null) {
                ivSongCover.setImageResource(givenSong.largeImageID)
                tvSongTitle.text = givenSong.title
                tvSongArtist.text = givenSong.artist
                btnSettings.setOnClickListener { navigateToSettingsActivity(this@PlayerActivity, givenSong, numSongPlays)}
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT_VALUE_KEY, numSongPlays)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        // handle whatever you want to do when Up button is pressed
        finish()
        return super.onSupportNavigateUp()
    }

    /**
     * Toggles the song title color between black and lavender when the cover image is long clicked
     */
    private fun toggleSongCountColor() {
        val songTitle = findViewById<TextView>(R.id.tvSongCount)
        val currentColor = songTitle.currentTextColor
        val black = getColor(R.color.black)
        val lavender = getColor(R.color.purple_200)
        if (currentColor == black) {
            songTitle.setTextColor(lavender)
        } else {
            songTitle.setTextColor(black)
        }
    }


    /**
     * Increments the counter of the times the current song is played. Updates the display on the
     * screen to reflect the new count
     */
    private fun incrementPlayCount() {
        numSongPlays++
        val tvSongPlayCount = findViewById<TextView>(R.id.tvSongCount)
        tvSongPlayCount.text = getString(R.string.song_play_count, numSongPlays)
    }

    /**
     * Displays a toast with the given message
     * @param message The message we want to display on the toast
     */
    private fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}