package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.HabitLog
import com.example.data.MoodLog
import com.example.data.RehabRepository
import com.example.data.SoberProfile
import com.example.data.WorkoutLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RehabViewModel(private val repository: RehabRepository) : ViewModel() {

    // Main flows from DB
    val profile: StateFlow<SoberProfile> = repository.profileFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SoberProfile()
        )

    val habitLogs: StateFlow<List<HabitLog>> = repository.allHabitLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val workoutLogs: StateFlow<List<WorkoutLog>> = repository.allWorkoutLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val moodLogs: StateFlow<List<MoodLog>> = repository.allMoodLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Completed refusal practice drills to earn gamified points offline
    private val _completedDrills = MutableStateFlow<Set<String>>(emptySet())
    val completedDrills: StateFlow<Set<String>> = _completedDrills.asStateFlow()

    fun completeRefusalDrill(lessonId: String) {
        val current = _completedDrills.value
        _completedDrills.value = current + lessonId
    }

    val totalPoints: StateFlow<Int> = combine(
        profile,
        workoutLogs,
        habitLogs,
        moodLogs,
        completedDrills
    ) { prof, workouts, habits, moods, drills ->
        val streakMs = System.currentTimeMillis() - prof.streakStartTimestamp
        val days = (streakMs / (1000 * 60 * 60 * 24)).coerceAtLeast(0L)
        
        val streakPoints = days * 100
        val workoutPoints = workouts.size * 50
        val resistPoints = habits.count { it.eventType == "RESIST" } * 20
        val pledgePoints = prof.totalPledgesCount * 30
        val journalPoints = moods.size * 15
        val drillPoints = drills.size * 40
        
        (streakPoints + workoutPoints + resistPoints + pledgePoints + journalPoints + drillPoints).toInt()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    // Educational campaigns
    val campaignLessons = listOf(
        CampaignLesson(
            id = "lesson_drugs",
            title = "Drug Abuse & Vaping",
            subtitle = "Shattering the Illicit Substance Trap",
            emoji = "🚫",
            description = "Substances like marijuana, vapes, and synthetic drugs interfere with neurotransmitters, creating an illusion of escape while blocking your brain's natural potential.",
            resistanceTip = "Action Alternative: Beat mental fog with 20 deep, high-speed Crunches to pull blood flow directly to your core.",
            badgeName = "Brain Shield Guardian",
            causes = listOf("Peer pressure and the desire to fit in.", "Curiosity and exposure on social media.", "Stress, trauma, or lack of parental guidance."),
            effects = listOf("Brain damage, poor concentration, and low grades.", "Addiction and aggressive behavior.", "Risk of severe health complications and legal trouble."),
            solutions = listOf("Find natural dopamine through sports/core workouts.", "Talk to a trusted school counselor.", "Choose friends who value physical and mental self-mastery.")
        ),
        CampaignLesson(
            id = "lesson_bullying",
            title = "Eradicating Bullying",
            subtitle = "Ending Intimidation and Fear in Schools",
            emoji = "🛑",
            description = "Bullying involves repeated, aggressive behavior among school-aged children that involves a real or perceived power imbalance.",
            resistanceTip = "Action Alternative: Stand tall. Embody physical strength and self-respect. Use your voice to report abuse immediately.",
            badgeName = "Shield of Justice",
            causes = listOf("Desire for power or dominance over others.", "A history of being abused or bullied themselves.", "Prejudice, differences in appearance, or social status."),
            effects = listOf("Severe anxiety, depression, and loss of self-esteem.", "Declining grades and school avoidance.", "In extreme cases, self-harm or suicidal thoughts."),
            solutions = listOf("Report incidents immediately to teachers/parents.", "Create an inclusive peer culture.", "Do not be a bystander; support the victim.")
        ),
        CampaignLesson(
            id = "lesson_cyber_bullying",
            title = "Defeating Cyber Bullying",
            subtitle = "Reclaiming the Digital Space",
            emoji = "📱",
            description = "The use of electronic communication to bully a person, typically by sending messages of an intimidating or threatening nature.",
            resistanceTip = "Action Alternative: Put the device down. Do 15 Russian Twists. Log out of toxic spaces and remember your real-world worth.",
            badgeName = "Digital Sovereign",
            causes = listOf("Anonymity provided by the internet.", "Jealousy or revenge.", "Lack of supervision online."),
            effects = listOf("Constant state of anxiety or dread.", "Social isolation and withdrawal from real life.", "Public humiliation on a massive scale."),
            solutions = listOf("Block and report abusers continuously.", "Limit screen time and social media usage.", "Keep evidence (screenshots) and report to authorities.")
        ),
        CampaignLesson(
            id = "lesson_violence",
            title = "Peer & Teacher Violence",
            subtitle = "Restoring Respect and Safety",
            emoji = "🤝",
            description = "Physical or emotional violence between peers or directed at/by teachers destroys the educational sanctuary.",
            resistanceTip = "Action Alternative: Channel anger and frustration into intense physical training, not on others. Discipline your body to discipline your mind.",
            badgeName = "Peace Bringer",
            causes = listOf("Unresolved trauma or anger issues.", "Gang affiliations or drug influence.", "Poor conflict resolution skills."),
            effects = listOf("Physical injuries and hostile learning environments.", "Suspension, expulsion, or criminal records.", "Long-term psychological trauma for victims."),
            solutions = listOf("Implement mandatory anger management programs.", "Promote martial arts or core training for discipline.", "Zero-tolerance policies supported by open communication.")
        ),
        CampaignLesson(
            id = "lesson_dropouts",
            title = "Stopping School Dropouts",
            subtitle = "Securing Your Future",
            emoji = "🎓",
            description = "Leaving the education system prematurely strips youth of fundamental life skills and future economic opportunities.",
            resistanceTip = "Action Alternative: Whenever you feel like quitting, commit to finishing just one small task. Small victories build unstoppable momentum.",
            badgeName = "Future Builder",
            causes = listOf("Academic failure or lack of support.", "Financial difficulties at home.", "Influence of bad associations or drug abuse."),
            effects = listOf("Increased risk of poverty and unemployment.", "Vulnerability to crime and gang recruitment.", "Lost potential and lifetime regret."),
            solutions = listOf("Seek tutoring or mentorship early.", "Set small, manageable academic goals.", "Join supportive study groups.")
        ),
        CampaignLesson(
            id = "lesson_pregnancy_marriage",
            title = "Teen Pregnancy & Early Marriage",
            subtitle = "Protecting the Path of Youth",
            emoji = "🛡️",
            description = "Taking on adult responsibilities as a child severely restricts educational and personal development.",
            resistanceTip = "Action Alternative: Focus entirely on personal growth. Channel your energy into building your mind, body, and career goals first.",
            badgeName = "Path Protector",
            causes = listOf("Lack of sexual education and awareness.", "Poverty and cultural norms.", "Peer pressure or improper association.", "Substance abuse leading to poor decisions."),
            effects = listOf("Immediate discontinuation of education.", "High health risks during pregnancy for young girls.", "A sudden plunge into adult responsibilities and poverty."),
            solutions = listOf("Focus on long-term educational goals.", "Engage in open education on reproductive health.", "Firmly say no to premature relationships and pressure.")
        ),
        CampaignLesson(
            id = "lesson_stigma_suicide",
            title = "Stigma, Emotional Abuse & Suicide",
            subtitle = "Promoting Life and Mental Wealth",
            emoji = "❤️‍🩹",
            description = "Stigma, discrimination, and emotional abuse create silent suffering which can tragically lead to suicide and depression.",
            resistanceTip = "Action Alternative: Breathe deeply. Remember your life has infinite value. Reach out to someone you trust immediately.",
            badgeName = "Life Champion",
            causes = listOf("Enduring chronic bullying or emotional abuse.", "Untreated mental health conditions (depression/anxiety).", "Social stigma and feeling like an outcast."),
            effects = listOf("Deep emotional scarring and isolation.", "Tragic loss of life and devastation to families.", "Toxic and unsafe school environments."),
            solutions = listOf("Normalize seeking mental health counseling.", "Foster environments of aggressive kindness.", "Speak up immediately if you or someone is struggling.")
        ),
        CampaignLesson(
            id = "lesson_gangsterism",
            title = "Rejecting Gangsterism",
            subtitle = "True Loyalty is to Your Future",
            emoji = "⛓️‍💥",
            description = "Gangs lure students with the false promise of 'family' or 'protection', only to demand loyalty through violence and crime.",
            resistanceTip = "Action Alternative: Build your own identity. True power doesn't require a gang; it requires iron discipline and personal sovereignty.",
            badgeName = "Sovereign Leader",
            causes = listOf("Seeking belonging, identity, or protection.", "Socioeconomic struggles and neighborhood influence.", "Lack of positive role models."),
            effects = listOf("Criminal records and incarceration.", "Violence, severe injury, or death.", "Destruction of the community and self."),
            solutions = listOf("Join sports teams or fitness programs instead.", "Seek positive role models and mentors.", "Refuse gang association at the very first contact.")
        )
    )

    // Live workout timer state
    private val _activeExercise = MutableStateFlow<String?>(null)
    val activeExercise: StateFlow<String?> = _activeExercise.asStateFlow()

    private val _timerSecondsLeft = MutableStateFlow(0)
    val timerSecondsLeft: StateFlow<Int> = _timerSecondsLeft.asStateFlow()

    private val _timerOriginalDuration = MutableStateFlow(30)
    val timerOriginalDuration: StateFlow<Int> = _timerOriginalDuration.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    // Tracking current selected screen tabs/index
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    // Daily Pledge Action
    fun signPledge() {
        viewModelScope.launch {
            repository.recordDailyPledge()
        }
    }

    // Reset current Sobriety Streak
    fun resetSoberStreak() {
        viewModelScope.launch {
            repository.resetStreak()
            // Add a slip log automatically to acknowledge the slip and help them reflect
            repository.insertHabitLog(
                HabitLog(
                    habitName = "Streak Restarted",
                    eventType = "SLIP",
                    note = "Streak was reset. A mistake is just a lesson learned. Time to build back stronger with Core workouts!",
                    intensityLevel = 3
                )
            )
        }
    }

    // Set/Update custom motive
    fun updateMotiveText(motive: String) {
        viewModelScope.launch {
            repository.updateMotive(motive)
        }
    }

    // Add Habit interaction (Resist/Slip)
    fun addHabitEvent(habit: String, eventType: String, note: String, intensity: Int) {
        viewModelScope.launch {
            repository.insertHabitLog(
                HabitLog(
                    habitName = habit,
                    eventType = eventType,
                    note = note,
                    intensityLevel = intensity
                )
            )
        }
    }

    fun deleteHabitEvent(id: Int) {
        viewModelScope.launch {
            repository.deleteHabitLog(id)
        }
    }

    // Workout actions
    fun startWorkoutTimer(exerciseName: String, durationSeconds: Int) {
        timerJob?.cancel()
        _activeExercise.value = exerciseName
        _timerOriginalDuration.value = durationSeconds
        _timerSecondsLeft.value = durationSeconds
        _isTimerRunning.value = true

        timerJob = viewModelScope.launch {
            while (_timerSecondsLeft.value > 0) {
                delay(1000)
                if (_isTimerRunning.value) {
                    _timerSecondsLeft.value -= 1
                }
            }
            // Done! Clear timer and auto log workout
            completeWorkoutTimer()
        }
    }

    fun toggleTimerActive() {
        _isTimerRunning.value = !_isTimerRunning.value
    }

    fun stopWorkoutTimer() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        _activeExercise.value = null
        _timerSecondsLeft.value = 0
    }

    private fun completeWorkoutTimer() {
        val exerciseName = _activeExercise.value ?: "Core Routine"
        val duration = _timerOriginalDuration.value
        viewModelScope.launch {
            repository.insertWorkoutLog(
                WorkoutLog(
                    exerciseName = exerciseName,
                    durationSeconds = duration,
                    reps = 0, // timed
                    intensityRating = 3
                )
            )
            // Log a corresponding habit resistance boost
            repository.insertHabitLog(
                HabitLog(
                    habitName = "Core Workout Shield",
                    eventType = "RESIST",
                    note = "Deflected urges by successfully completing: $exerciseName for $duration seconds!",
                    intensityLevel = 4
                )
            )
        }
        _activeExercise.value = null
        _isTimerRunning.value = false
    }

    // Add manual workout log (reps based like crunches)
    fun addCompletedWorkout(exerciseName: String, reps: Int, intensity: Int) {
        viewModelScope.launch {
            repository.insertWorkoutLog(
                WorkoutLog(
                    exerciseName = exerciseName,
                    durationSeconds = 0, // rep-based
                    reps = reps,
                    intensityRating = intensity
                )
            )
            // Save equivalent resist log
            repository.insertHabitLog(
                HabitLog(
                    habitName = "Core Strength Deflection",
                    eventType = "RESIST",
                    note = "Redirected urge by completing $reps reps of $exerciseName!",
                    intensityLevel = intensity
                )
            )
        }
    }

    fun deleteWorkoutLog(id: Int) {
        viewModelScope.launch {
            repository.deleteWorkoutLog(id)
        }
    }

    fun addMoodLog(mood: String, trigger: String, strategy: String, note: String) {
        viewModelScope.launch {
            repository.insertMoodLog(
                MoodLog(
                    mood = mood,
                    triggerName = trigger,
                    copingStrategy = strategy,
                    note = note
                )
            )
        }
    }

    fun deleteMoodLog(id: Int) {
        viewModelScope.launch {
            repository.deleteMoodLog(id)
        }
    }

    fun clearAllUserData() {
        viewModelScope.launch {
            repository.clearAllData()
            stopWorkoutTimer()
        }
    }
}

data class CampaignLesson(
    val id: String,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val description: String,
    val resistanceTip: String,
    val badgeName: String,
    val causes: List<String> = emptyList(),
    val effects: List<String> = emptyList(),
    val solutions: List<String> = emptyList()
)
