package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "sober_profiles")
data class SoberProfile(
    @PrimaryKey val id: Int = 1,
    val streakStartTimestamp: Long = System.currentTimeMillis(), // 0 means haven't started streak or reset
    val totalPledgesCount: Int = 0,
    val lastPledgeTimestamp: Long = 0,
    val personalMotive: String = "To protect my health and build a bright, athletic future"
)

@Entity(tableName = "habit_logs")
data class HabitLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitName: String, // e.g., "Vaping", "Alcohol", "Smoking", "Gaming/Social Media", "Peer Pressure"
    val eventType: String, // "RESIST" or "SLIP"
    val note: String,
    val intensityLevel: Int, // 1 (Very Low Challenge) to 5 (Extreme Challenge)
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "workout_logs")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseName: String, // e.g., "Plank Anchor", "V-Up Shield", "Crunches Force", "Russian Twist Resist"
    val durationSeconds: Int,
    val reps: Int,
    val intensityRating: Int, // 1 (Easy) to 5 (Extremely Intense)
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "mood_logs")
data class MoodLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mood: String, // e.g. "😀 Happy/Proud", "🥵 Stressed/Anxious", "🥱 Bored/Fatigued", "😠 Angry/Frustrated", "😐 Neutral"
    val triggerName: String, // e.g. "School Stress", "Peer Pressure", "Boredom", "Late-night Phones", "Family Tension"
    val copingStrategy: String, // e.g. "Held a Plank 🧘", "Deep Breathing 🌬️", "Drank Water 💧", "Talked to Counselor 👥"
    val note: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM sober_profiles WHERE id = 1 LIMIT 1")
    fun getProfileFlow(): Flow<SoberProfile?>

    @Query("SELECT * FROM sober_profiles WHERE id = 1 LIMIT 1")
    suspend fun getProfileDirect(): SoberProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: SoberProfile)
}

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_logs ORDER BY timestamp DESC")
    fun getAllHabitLogsFlow(): Flow<List<HabitLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(log: HabitLog)

    @Query("DELETE FROM habit_logs WHERE id = :id")
    suspend fun deleteHabitLogById(id: Int)

    @Query("DELETE FROM habit_logs")
    suspend fun clearAllHabitLogs()
}

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    fun getAllWorkoutLogsFlow(): Flow<List<WorkoutLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLog)

    @Query("DELETE FROM workout_logs WHERE id = :id")
    suspend fun deleteWorkoutLogById(id: Int)

    @Query("DELETE FROM workout_logs")
    suspend fun clearAllWorkoutLogs()
}

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_logs ORDER BY timestamp DESC")
    fun getAllMoodLogsFlow(): Flow<List<MoodLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoodLog(log: MoodLog)

    @Query("DELETE FROM mood_logs WHERE id = :id")
    suspend fun deleteMoodLogById(id: Int)

    @Query("DELETE FROM mood_logs")
    suspend fun clearAllMoodLogs()
}

@Database(
    entities = [SoberProfile::class, HabitLog::class, WorkoutLog::class, MoodLog::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun habitDao(): HabitDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun moodDao(): MoodDao
}
