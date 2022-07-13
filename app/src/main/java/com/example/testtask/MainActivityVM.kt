package com.example.testtask

import android.app.Notification
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivityVM : ViewModel() {
    private val apiInterface = ApiInterface.create().getActions()
    var actions: MutableLiveData<List<Action>> = MutableLiveData()
    private val calendar = Calendar.getInstance()
    private val day = calendar[Calendar.DAY_OF_WEEK]

    init {
        getAction()
    }

    private fun getAction() {
        apiInterface.enqueue(object : Callback<List<Action>> {
            override fun onResponse(call: Call<List<Action>>, response: Response<List<Action>>) {
                if (response.body() != null) {
                    actions.value = response.body()?.filter { it.enabled }?.filter { it.valid_days.contains(day) }
                    actions.value?.let {
                        val actionsSorted: MutableList<Action> = it.toMutableList()
                        actionsSorted.sortByDescending { it.priority }
                        actions.value = actionsSorted
                    }
                }
            }

            override fun onFailure(call: Call<List<Action>>, t: Throwable) {
                println("Error")
            }
        })
    }

    fun updateActions() {
        val actions = actions.value?.toMutableList()
        val actionsRemoveFirst = actions?.removeFirst()
        actions?.add(actionsRemoveFirst!!)
        this.actions.value = actions
    }

    fun time(hours: Int): Date {
        val sdf = SimpleDateFormat("HH:mm")
        val currentDate = sdf.format(Date())
        val date = sdf.parse(currentDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, hours)
        return calendar.time
    }

    fun actionAnimation(view: View, context: Context) {
        val animationRotateCenter: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        view.startAnimation(animationRotateCenter)
    }

    fun actionShowToast(context: Context) {
        Toast.makeText(context, R.string.action_is_toast, Toast.LENGTH_LONG).show()
    }

    //TODO notification
    fun actionShowNotification(context: Context): Notification {
        return Notification.Builder(context)
            .setContentTitle("New mail from ")
            .setContentText("subject")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }
}