package edu.uw.stluong.dotify

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import edu.uw.stluong.dotify.Users.User
import edu.uw.stluong.dotify.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val safeArgs: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        val currentUser = safeArgs.currentUser
        with(binding) {
            val firstName = currentUser.firstName
            val lastName = currentUser.lastName
            val username = currentUser.username
            val profilePic = currentUser.profilePicURL
            tvUser.text = getString(R.string.current_user, firstName, lastName)
            tvUserName.text = getString(R.string.username, username)
            ivProfilePic.load(profilePic)
        }
        return binding.root
    }
}