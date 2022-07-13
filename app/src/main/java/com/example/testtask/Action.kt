package com.example.testtask

import java.util.*

data class Action(
    val priority: Int,
    var type: String,
    var enabled: Boolean,
    var valid_days: List<Int>,
    var cool_down: Long,
    var disableEndTime: Date? = null
)
