package edu.uw.stluong.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.stluong.dotify.Users.UserRepository

class DotifyApplication: Application() {

    lateinit var userRepository: UserRepository
    lateinit var allSongs: List<Song>

    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository()
        allSongs = SongDataProvider.getAllSongs()
    }
}