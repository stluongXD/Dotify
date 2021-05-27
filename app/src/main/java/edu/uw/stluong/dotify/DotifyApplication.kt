package edu.uw.stluong.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.stluong.dotify.Users.UserRepository
import edu.uw.stluong.dotify.manager.SongNotificationManager

class DotifyApplication: Application() {

    lateinit var userRepository: UserRepository
    lateinit var allSongs: List<Song>
    lateinit var songNotificationManager: SongNotificationManager

    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository()
        allSongs = SongDataProvider.getAllSongs()
        songNotificationManager = SongNotificationManager(this)
    }
}