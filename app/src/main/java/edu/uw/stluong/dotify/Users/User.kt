package edu.uw.stluong.dotify.Users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Data class to represent a user of the Dotify App
 */
@Parcelize
data class User (
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Double,
    val profilePicURL: String
    ) : Parcelable
