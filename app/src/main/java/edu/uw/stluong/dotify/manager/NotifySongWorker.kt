package edu.uw.stluong.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.stluong.dotify.R
import edu.uw.stluong.dotify.SongListActivity
import kotlin.random.Random

private const val NEW_UPLOADED_MUSIC_CHANNEL = "NEW_UPLOADED_MUSIC"
class NotifySongWorker(
    private val context: Context,
    workerParams: WorkerParameters
    ): CoroutineWorker(context, workerParams) {

    private val notificationManager = NotificationManagerCompat.from(context)

    override suspend fun doWork(): Result {
        initNotificationChannels()
        notifyUser()
        return Result.success()
    }

    private fun notifyUser() {
        val currentSong = SongDataProvider.getAllSongs().random()
        val artist = currentSong.artist
        val songTitle = currentSong.title
        val intent = Intent(context, SongListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, NEW_UPLOADED_MUSIC_CHANNEL)
            .setSmallIcon(R.drawable.ic_music_library)
            .setContentTitle("$artist just released a new song!!!")
            .setContentText("Listen to $songTitle now on Dotify")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.new_song)
            val descriptionText = context.getString(R.string.new_song_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NEW_UPLOADED_MUSIC_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

}