package edu.uw.stluong.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.ericchee.songdataprovider.Song
import edu.uw.stluong.dotify.databinding.ActivityPlayerBinding
import kotlin.random.Random

private const val SONG_KEY = "givenSong"
fun navigateToPlayerActivity(context: Context ,currentSong: Song) {
    val intent = Intent(context, PlayerActivity::class.java)
    val bundle = Bundle().apply {
        putParcelable(SONG_KEY, currentSong)
    }
    intent.putExtras(bundle)
    context.startActivity(intent)
}

class PlayerActivity : AppCompatActivity() {

    private var isEditingUser = false
    private var currentUser = "Baby Yoda"
    private var numSongPlays = Random.nextInt(100, 10000)
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_player)
        binding = ActivityPlayerBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            val givenSong = intent.getParcelableExtra<Song>(SONG_KEY)
            if (givenSong != null) {
                ivSongCover.setImageResource(givenSong.largeImageID)
                tvSongTitle.text = givenSong.title
                tvSongArtist.text = givenSong.artist
            }

            btChangeUser.setOnClickListener { changeUserClick(btChangeUser) }
            tvSongCount.text = getString(R.string.song_play_count, numSongPlays)
            ibPlay.setOnClickListener { incrementPlayCount() }
            ibPrev.setOnClickListener { displayToast(getString(R.string.skip_prev)) }
            ibNext.setOnClickListener { displayToast(getString(R.string.skip_next)) }
            ivSongCover.setOnLongClickListener {
                toggleSongCountColor()
                true
            }
        }
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
     * Swaps the text between "CHANGE USER" and "APPLY" and allows the user to change the current
     * user of the app
     * @param changeUserBtn the button we want to run this function when the button is clicked
     */
    private fun changeUserClick(changeUserBtn: Button) {
        isEditingUser = !isEditingUser
        val textCurrentUser = findViewById<TextView>(R.id.tCurrentUser)
        val editCurrentUserHandle = findViewById<EditText>(R.id.eCurrentUser)

        if (isEditingUser) {
            editCurrentUserHandle.setText(currentUser)
            changeUserBtn.text = getString(R.string.apply)
            textCurrentUser.visibility = View.GONE
            editCurrentUserHandle.visibility = View.VISIBLE
        } else {
            currentUser = editCurrentUserHandle.text.toString()
            textCurrentUser.text = currentUser
            changeUserBtn.text = getString(R.string.change_user)
            textCurrentUser.visibility = View.VISIBLE
            editCurrentUserHandle.visibility = View.GONE
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