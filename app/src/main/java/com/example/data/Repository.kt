package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RehabRepository(
    private val profileDao: ProfileDao,
    private val habitDao: HabitDao,
    private val workoutDao: WorkoutDao,
    private val moodDao: MoodDao
) {
    // Sobriety profile flows
    val profileFlow: Flow<SoberProfile> = profileDao.getProfileFlow().map { 
        it ?: SoberProfile(
            id = 1,
            streakStartTimestamp = System.currentTimeMillis(),
            totalPledgesCount = 0,
            lastPledgeTimestamp = 0,
            personalMotive = "To protect my health and build a bright, athletic future"
        )
    }

    suspend fun getProfileDirect(): SoberProfile {
        return profileDao.getProfileDirect() ?: SoberProfile(
            id = 1,
            streakStartTimestamp = System.currentTimeMillis(),
            totalPledgesCount = 0,
            lastPledgeTimestamp = 0,
            personalMotive = "To protect my health and build a bright, athletic future"
        )
    }

    suspend fun saveProfile(profile: SoberProfile) {
        profileDao.saveProfile(profile)
    }

    // Daily pledge handler
    suspend fun recordDailyPledge() {
        val current = getProfileDirect()
        val updated = current.copy(
            totalPledgesCount = current.totalPledgesCount + 1,
            lastPledgeTimestamp = System.currentTimeMillis()
        )
        profileDao.saveProfile(updated)
    }

    // Reset streak handler
    suspend fun resetStreak() {
        val current = getProfileDirect()
        val updated = current.copy(
            streakStartTimestamp = System.currentTimeMillis()
        )
        profileDao.saveProfile(updated)
    }

    // Update motivation text
    suspend fun updateMotive(motive: String) {
        val current = getProfileDirect()
        val updated = current.copy(
            personalMotive = motive.takeIf { it.isNotBlank() } ?: "To protect my health and build a bright, athletic future"
        )
        profileDao.saveProfile(updated)
    }

    // Habit triggers / urges logs
    val allHabitLogs: Flow<List<HabitLog>> = habitDao.getAllHabitLogsFlow()

    suspend fun insertHabitLog(log: HabitLog) {
        habitDao.insertHabitLog(log)
    }

    suspend fun deleteHabitLog(id: Int) {
        habitDao.deleteHabitLogById(id)
    }

    // Workouts completed log
    val allWorkoutLogs: Flow<List<WorkoutLog>> = workoutDao.getAllWorkoutLogsFlow()

    suspend fun insertWorkoutLog(log: WorkoutLog) {
        workoutDao.insertWorkoutLog(log)
    }

    suspend fun deleteWorkoutLog(id: Int) {
        workoutDao.deleteWorkoutLogById(id)
    }

    // Mood and trigger log
    val allMoodLogs: Flow<List<MoodLog>> = moodDao.getAllMoodLogsFlow()

    suspend fun insertMoodLog(log: MoodLog) {
        moodDao.insertMoodLog(log)
    }

    suspend fun deleteMoodLog(id: Int) {
        moodDao.deleteMoodLogById(id)
    }

    suspend fun clearAllData() {
        habitDao.clearAllHabitLogs()
        workoutDao.clearAllWorkoutLogs()
        moodDao.clearAllMoodLogs()
        profileDao.saveProfile(SoberProfile(
            id = 1,
            streakStartTimestamp = System.currentTimeMillis(),
            totalPledgesCount = 0,
            lastPledgeTimestamp = 0,
            personalMotive = "To protect my health and build a bright, athletic future"
        ))
    }
}
