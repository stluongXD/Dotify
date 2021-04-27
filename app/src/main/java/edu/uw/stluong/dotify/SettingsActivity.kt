package edu.uw.stluong.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.ericchee.songdataprovider.Song
import edu.uw.stluong.dotify.databinding.ActivitySettingsBinding

private const val SONG_KEY = "givenSong"
private const val SONG_PLAY_COUNT = "songCounter"

fun navigateToSettingsActivity(context: Context, currentSong: Song, songCounter : Int) {
    val intent = Intent(context, SettingsActivity::class.java)
    val bundle = Bundle().apply {
        putParcelable(SONG_KEY, currentSong)
        putInt(SONG_PLAY_COUNT, songCounter)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply { setContentView(root) }

        val extras: Bundle? = intent.extras
        if (extras != null) {
            navController.setGraph(R.navigation.nav_graph, intent.extras)
        }
    }
}