package edu.uw.stluong.dotify.manager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val SONG_NOTIFICATION_WORK_TAG = "SONG_NOTIFICATION_WORK_TAG"

class SongNotificationManager(context: Context) {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    fun notifySong() {
        // if a song is currently in queue to be notified, we return early to prevent us from making
        // another song request
        if (isNotifyingSong()) {
            return
        }
        val request = PeriodicWorkRequestBuilder<NotifySongWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueue(request)
    }

    fun stopPeriodicallyNotifying() {
        workManager.cancelAllWorkByTag(SONG_NOTIFICATION_WORK_TAG)
    }

    private fun isNotifyingSong(): Boolean {
        return workManager.getWorkInfosByTag(SONG_NOTIFICATION_WORK_TAG).get().any {
            when (it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}
