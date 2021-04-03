package edu.uw.stluong.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var isEditingUser = false
    private var currentUser = "Baby Yoda"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val changeUserBtn = findViewById<Button>(R.id.btChangeUser)
        changeUserBtn.setOnClickListener {
            changeUserClick(changeUserBtn)
        }
    }

    /**
     * Swaps the text between "CHANGE USER" and "APPLY" and allows the user to change the current user of the app
     * @param changeUserBtn the button we want to run this function when the button is clicked
     */
    fun changeUserClick(changeUserBtn: Button) {
        isEditingUser = !isEditingUser
        val textCurrentUser = findViewById<TextView>(R.id.tCurrentUser)
        val editCurrentUserHandle = findViewById<EditText>(R.id.eCurrentUser)

        if (isEditingUser) {
            editCurrentUserHandle.setText(currentUser)
            changeUserBtn.text = "APPLY"
            textCurrentUser.visibility = View.GONE
            editCurrentUserHandle.visibility = View.VISIBLE
        } else {
            currentUser = editCurrentUserHandle.text.toString()
            textCurrentUser.text = currentUser
            changeUserBtn.text = "CHANGE USER"
            textCurrentUser.visibility = View.VISIBLE
            editCurrentUserHandle.visibility = View.GONE
        }
    }
}