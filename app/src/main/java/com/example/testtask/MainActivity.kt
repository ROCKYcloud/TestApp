package com.example.testtask

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testtask.databinding.ActivityMainBinding
import com.example.testtask.enum.Type
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityVM by viewModels()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        button()
    }

    private fun button() {
        viewModel.actions.observe(this, Observer { actions ->
            binding.button.setOnClickListener { button ->
                val action = actions.first()
                action.disableEndTime?.let {
                    if (it >= calendar.time) {
                        typeAction(action, button)
                    } else binding.button.isEnabled = false
                } ?: run { typeAction(action, button) }
                val hours = (actions.first().cool_down / 1000 / 60 / 60).toInt()
                actions.first().disableEndTime = viewModel.time(hours)
                viewModel.updateActions()
            }
        })
    }


    private fun typeAction(action: Action, view: View) {
        when (action.type) {
            Type.ANIMATION.type -> viewModel.actionAnimation(view, this)
            Type.TOAST.type -> viewModel.actionShowToast(this)
            Type.CALL.type -> actionColl()
            Type.NOTIFICATION.type -> viewModel.actionShowNotification(this)
        }
    }

    private fun actionColl() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, 1)
    }
}



