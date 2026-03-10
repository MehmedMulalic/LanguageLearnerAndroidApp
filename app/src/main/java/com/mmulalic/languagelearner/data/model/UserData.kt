package com.mmulalic.languagelearner.data.model

data class TasksCount(
    val total: Int = 0,
    val words: Int = 0,
    val tests: Int = 0
)

data class UserData (
    val username: String = "",
    val currentLesson: Int = 0,
    val currentDay: Int = 0,
    val tasksToday: Int = 0,
    val tasksCount: TasksCount = TasksCount()
)