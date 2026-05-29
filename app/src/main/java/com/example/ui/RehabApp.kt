package com.example.ui

import android.text.format.DateUtils
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.R
import com.example.data.HabitLog
import com.example.data.WorkoutLog
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RehabApp(viewModel: RehabViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val activeExercise by viewModel.activeExercise.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Elegant top bar alignment from Professional Polish Design
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "RC",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                    Column {
                        Text(
                            text = "RiseUp Core",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.5).sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "OFFLINE SHIELD ACTIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 9.sp,
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent)
                        .clickable { /* Taptic response trigger indicator */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .shadow(12.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Shield Guard") },
                    label = { Text("Guard", fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("tab_guard")
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Core Workout") },
                    label = { Text("Core Work", fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("tab_workouts")
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = { Icon(Icons.Default.BarChart, contentDescription = "Habit Log") },
                    label = { Text("Resist Log", fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("tab_logs")
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    icon = { Icon(Icons.Default.Campaign, contentDescription = "Hub info") },
                    label = { Text("Hub", fontWeight = FontWeight.SemiBold) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.testTag("tab_campaign")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            // Screen content switcher
            when (selectedTab) {
                0 -> GuardScreen(viewModel)
                1 -> WorkoutsScreen(viewModel)
                2 -> LogsScreen(viewModel)
                3 -> HubScreen(viewModel) { CampaignScreen(viewModel) }
            }

            // High-focus overlay for Active Workout Timer
            activeExercise?.let { exercise ->
                ActiveTimerOverlay(
                    exerciseName = exercise,
                    viewModel = viewModel
                )
            }
        }
    }
}

// ---------------- DASHBOARD / GUARD TAB ----------------
@Composable
fun GuardScreen(viewModel: RehabViewModel) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val habitLogs by viewModel.habitLogs.collectAsStateWithLifecycle()
    val workoutLogs by viewModel.workoutLogs.collectAsStateWithLifecycle()
    
    var showResetDialog by remember { mutableStateOf(false) }
    var editedMotive by remember { mutableStateOf("") }
    var isEditingMotive by remember { mutableStateOf(false) }
    
    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current

    // Init motive string
    LaunchedEffect(profile.personalMotive) {
        editedMotive = profile.personalMotive
    }

    // Calculations
    val streakMs = System.currentTimeMillis() - profile.streakStartTimestamp
    val secs = (streakMs / 1000) % 60
    val mins = (streakMs / (1000 * 60)) % 60
    val hours = (streakMs / (1000 * 60 * 60)) % 24
    val days = streakMs / (1000 * 60 * 60 * 24)

    val totalResisted = habitLogs.count { it.eventType == "RESIST" }
    val totalWorkouts = workoutLogs.size

    // Background timer tick refresh triggers every 1s
    var timeTicker by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            timeTicker = System.currentTimeMillis()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
    ) {
        // Hero Streak Card styled from the Professional Polish palette
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "STREAK",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "$days",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 56.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = if (days == 1L) "Day Habits Free" else "Days Habits Free",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.3f))
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = "Streak Trophy",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    val targetMilestone = when {
                        days < 3 -> 3
                        days < 7 -> 7
                        days < 14 -> 14
                        days < 30 -> 30
                        days < 60 -> 60
                        else -> 90
                    }
                    val progressFraction = (days.toFloat() / targetMilestone.toFloat()).coerceIn(0f, 1f)

                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progressFraction)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Next Milestone: $targetMilestone Days Master",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(color = Color.White.copy(alpha = 0.15f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Time countdown segments in the card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TimeSegment(
                            label = "HOURS", 
                            value = String.format("%02d", hours),
                            containerColor = Color.White.copy(alpha = 0.3f),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                        Text(":", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        TimeSegment(
                            label = "MINUTES", 
                            value = String.format("%02d", mins),
                            containerColor = Color.White.copy(alpha = 0.3f),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                        Text(":", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        TimeSegment(
                            label = "SECONDS", 
                            value = String.format("%02d", secs),
                            containerColor = Color.White.copy(alpha = 0.3f),
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Today's Focus Card - Matches exact styling specifications
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Campaign focus",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Today's Focus",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(
                                onClick = {
                                    if (isEditingMotive) {
                                        viewModel.updateMotiveText(editedMotive)
                                        isEditingMotive = false
                                    } else {
                                        isEditingMotive = true
                                    }
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (isEditingMotive) Icons.Default.Check else Icons.Default.Create,
                                    contentDescription = "Edit motive",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        if (isEditingMotive) {
                            OutlinedTextField(
                                value = editedMotive,
                                onValueChange = { editedMotive = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp)
                                    .testTag("motive_input"),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        viewModel.updateMotiveText(editedMotive)
                                        isEditingMotive = false
                                        focusManager.clearFocus()
                                    }
                                )
                            )
                        } else {
                            Text(
                                text = "\"${profile.personalMotive}\"",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 18.sp
                                ),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Daily Check-in / Sobriety Pledge
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Pledge",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "DAILY SOBRIETY CHECK-IN",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Commit yourself today to reject toxic habits and secure your health.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val alreadyPledgedToday = isSameDay(profile.lastPledgeTimestamp, System.currentTimeMillis())

                    Button(
                        onClick = { viewModel.signPledge() },
                        enabled = !alreadyPledgedToday,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("pledge_button")
                    ) {
                        Icon(
                            imageVector = if (alreadyPledgedToday) Icons.Default.CheckCircle else Icons.Default.Verified,
                            contentDescription = "Pledge Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (alreadyPledgedToday) "DAILY PLEDGE SIGNED" else "SIGN DAILY PLEDGE",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Total Signatures completed: ${profile.totalPledgesCount}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Mini Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MiniStatCard(
                    title = "Urges Refused",
                    value = "$totalResisted",
                    color = MaterialTheme.colorScheme.primary,
                    icon = Icons.Default.Check,
                    modifier = Modifier.weight(1f)
                )
                MiniStatCard(
                    title = "Core Workouts",
                    value = "$totalWorkouts",
                    color = MaterialTheme.colorScheme.secondary,
                    icon = Icons.Default.Star,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Gamified Progress & Badges Section
        item {
            val totalPoints by viewModel.totalPoints.collectAsStateWithLifecycle()
            val completedDrills by viewModel.completedDrills.collectAsStateWithLifecycle()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "🏆 SOVEREIGNTY PROGRESS",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$totalPoints PTS",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "LEVEL ${(totalPoints / 250) + 1}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your Badges Unlocked:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Badges List
                    val badges = listOf(
                        Triple("Novice Shield-Bearer 🛡️", "Signed at least 1 daily sobriety pledge.", profile.totalPledgesCount >= 1),
                        Triple("Nicotine Deflector 🚭", "Completed vaping education drill or resisted vaping logs.", completedDrills.contains("vape_drill") || habitLogs.any { it.habitName.lowercase().contains("vape") && it.eventType == "RESIST" }),
                        Triple("Steel Core Pioneer 🧘", "Successfully complete 4 core training workouts.", totalWorkouts >= 4),
                        Triple("Brain Shield Guardian 🧠", "Review substance facts or record positive trigger journal.", completedDrills.contains("drugs_drill") || viewModel.moodLogs.value.isNotEmpty()),
                        Triple("Digital Sovereign 📱", "Complete Screen Detox refusal practice.", completedDrills.contains("screens_drill")),
                        Triple("Relapse Champion 👑", "Overall points score exceeds 500.", totalPoints >= 500),
                        Triple("Designed by Lordyvites Dev Company 👑", "Tap on the contact details card below to check our products & services!", true)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        badges.forEach { (name, desc, isUnlocked) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isUnlocked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    )
                                    .border(
                                        1.dp,
                                        if (isUnlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                        else Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isUnlocked) MaterialTheme.colorScheme.primary
                                            else Color.Gray.copy(alpha = 0.3f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isUnlocked) "🏆" else "🔒",
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUnlocked) MaterialTheme.colorScheme.onPrimary else Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Black,
                                        color = if (isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                    Text(
                                        text = desc,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 👑 PEER LEADERBOARD
        item {
            val totalPoints by viewModel.totalPoints.collectAsStateWithLifecycle()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "👑 OFFLINE SCHOOL LEADERBOARD",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Compare and compete with students resisting bad habits.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val staticPeers = listOf(
                        Pair("Lucas (Steel Core Pioneer)", 420),
                        Pair("Maya (Brain Shield Guardian)", 340),
                        Pair("Jason (Nicotine Deflector)", 210),
                        Pair("Sarah (Novice Shield)", 90)
                    )

                    // Merge user and sort dynamically
                    val allCompetitors = (staticPeers + Pair("You (Independent)", totalPoints))
                        .sortedByDescending { it.second }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        allCompetitors.forEachIndexed { idx, (name, pts) ->
                            val isUser = name.contains("You")
                            val rank = idx + 1
                            val itemColor = if (isUser) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent
                            val borderMod = if (isUser) Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)) else Modifier

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(itemColor)
                                    .then(borderMod)
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "#$rank",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = if (rank == 1) Color(0xFFFFB300) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.width(36.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isUser) FontWeight.Black else FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$pts PTS",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black,
                                    color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // 💎 DEVELOPER AND DESIGNER CREDIT BADGE & CONTACTS
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(1.dp, Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )), 
                        RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            MaterialTheme.colorScheme.secondaryContainer
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "LV",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DESIGNED BY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Lordyvites Dev Company",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Partnering in elite physical self-control & high-performance offline mobile solutions. We craft custom digital platforms, web systems, and mobile applications tailored for your growth.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Website button
                    Text(
                        text = "Official Website:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            try {
                                uriHandler.openUri("https://lordy-vites-webdev.my.canva.site/")
                            } catch (e: Exception) {}
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "Website Link",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "https://lordy-vites-webdev.my.canva.site/",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Team Directory
                    Text(
                        text = "Team Directory & Roles:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val teamList = listOf(
                        Triple("Miss C Official", "Author of the Novel & Workouts", "tel:0774490438" to "077 449 0438"),
                        Triple("LOOP DILL", "Editor of App Layout", "tel:0776377014" to "077 637 7014"),
                        Triple("PEAKDEN", "The Programmer", "mailto:mubayidenver@gmail.com" to "0713264962 / Email"),
                        Triple("Svobixd", "Content Creator (Novel Editor) & Campaign Data", "mailto:svobiblessing@gmail.com" to "077 834 8954 / Email")
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        teamList.forEach { (name, role, contactInfo) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                    .clickable {
                                        try {
                                            uriHandler.openUri(contactInfo.first)
                                        } catch (e: Exception) {}
                                    }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (contactInfo.first.startsWith("tel")) Icons.Default.Call else Icons.Default.Email,
                                    contentDescription = "Contact",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = role,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Text(
                                    text = contactInfo.second,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Company Emails:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val emailsList = listOf(
                        "mubayidenver@gmail.com",
                        "denyvites@gmail.com",
                        "lordyvites@gmail.com",
                        "svobiblessing@gmail.com"
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        emailsList.forEach { email ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                    .clickable {
                                        try {
                                            uriHandler.openUri("mailto:$email")
                                        } catch (e: Exception) {}
                                    }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email address",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = email,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Fresh start button card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.04f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Need a Fresh Start?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "If you slipped up, carry no shame. Restart and log what happened.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { showResetDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("reset_streak_button")
                    ) {
                        Text("RESTART", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    "Restart Sobriety Shield?",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            },
            text = {
                Text("Restarting sets your streak clock back to zero. This will write an exercise-refocus recommendation slip to help guide you. Remember, a setback is just training for a greater comeback.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetSoberStreak()
                        showResetDialog = false
                    },
                    modifier = Modifier.testTag("confirm_reset_button")
                ) {
                    Text("YES, START OVER", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("CANCEL")
                }
            }
        )
    }
}

@Composable
fun TimeSegment(
    label: String,
    value: String,
    containerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
    contentColor: Color = MaterialTheme.colorScheme.primary,
    labelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            colors = CardDefaults.cardColors(containerColor = containerColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = contentColor
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MiniStatCard(
    title: String,
    value: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, color.copy(alpha = 0.2f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.04f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = color
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ---------------- WORKOUTS TAB ----------------
@Composable
fun WorkoutsScreen(viewModel: RehabViewModel) {
    val workoutLogs by viewModel.workoutLogs.collectAsStateWithLifecycle()
    
    val workoutTemplates = listOf(
        WorkoutTemplate(
            name = "Urge Breaker Plank",
            desc = "Keeps body firm as steel. Face triggers by dropping directly onto elbows. Directs nervous impulses to safety.",
            duration = 30,
            repBased = false,
            difficulty = "Easy",
            icon = Icons.Default.PlayArrow
        ),
        WorkoutTemplate(
            name = "Habit Crusher Crunches",
            desc = "Squeeze abdominal wall. Pull fresh oxygen directly to muscles to clear vapor or peer fog.",
            reps = 20,
            repBased = true,
            difficulty = "Medium",
            icon = Icons.Default.Check
        ),
        WorkoutTemplate(
            name = "Russian Sobriety Twist",
            desc = "Rotate trunk left and right. Build solid physical barrier and internal resilience.",
            reps = 30,
            repBased = true,
            difficulty = "Medium",
            icon = Icons.Default.Refresh
        ),
        WorkoutTemplate(
            name = "Leg Raise Deflection",
            desc = "Raise weight of lower limbs off ground. Toughens hip flexors and locks center safely deep.",
            reps = 15,
            repBased = true,
            difficulty = "Hard",
            icon = Icons.Default.Star
        ),
        WorkoutTemplate(
            name = "Advanced Core Endurance",
            desc = "Maximum willpower challenge! Keep body alignment, breathing slowly. Shows you control your focus.",
            duration = 60,
            repBased = false,
            difficulty = "Hard",
            icon = Icons.Default.PlayArrow
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
    ) {
        item {
            Column {
                Text(
                    text = "💪 CORE ATHLETICS SHIELD",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Directing bad urge energy into core strength is clinically proven to restore cognitive control in school youth.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.cartoon_workout_1779971191123),
                        contentDescription = "Cartoon Character Workout Example",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Build strength, defeat the urges!",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "CHOOSE YOUR SHIELD WORKOUT",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 1.sp
            )
        }

        // Workout template items matching "Professional Polish" structure
        items(workoutTemplates) { template ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(24.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = template.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = when (template.difficulty) {
                                    "Easy" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                    "Medium" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                                    else -> MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                                }
                            ),
                            shape = CircleShape
                        ) {
                            Text(
                                text = template.difficulty,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = when (template.difficulty) {
                                    "Easy" -> MaterialTheme.colorScheme.primary
                                    "Medium" -> MaterialTheme.colorScheme.secondary
                                    else -> MaterialTheme.colorScheme.error
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = template.desc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (template.repBased) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Goal: ${template.reps} Reps",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    viewModel.addCompletedWorkout(template.name, template.reps, 3)
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier.testTag("log_reps_${template.name.replace(" ", "_")}")
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("LOG REPS DONE", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Duration: ${template.duration} Seconds",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    viewModel.startWorkoutTimer(template.name, template.duration)
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier.testTag("start_timer_${template.name.replace(" ", "_")}")
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("LAUNCH TIMER", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }

        // Historial listing header
        item {
            Text(
                text = "RECENT CORE ACTIVITIES LOG",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (workoutLogs.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No workouts logged yet.",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Launch a timer or tap finished to record real fitness actions!",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        } else {
            items(workoutLogs) { log ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                log.exerciseName,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = if (log.durationSeconds > 0) "Timed for ${log.durationSeconds}s" else "Completed ${log.reps} reps",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(log.timestamp)),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                        IconButton(
                            onClick = { viewModel.deleteWorkoutLog(log.id) },
                            modifier = Modifier.testTag("delete_workout_${log.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Workout Record",
                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class WorkoutTemplate(
    val name: String,
    val desc: String,
    val duration: Int = 0,
    val reps: Int = 0,
    val repBased: Boolean,
    val difficulty: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

// ---------------- SCREEN TIMER FULLSCREEN POPUP OVERLAY ----------------
@Composable
fun ActiveTimerOverlay(
    exerciseName: String,
    viewModel: RehabViewModel
) {
    val secondsLeft by viewModel.timerSecondsLeft.collectAsStateWithLifecycle()
    val totalSeconds by viewModel.timerOriginalDuration.collectAsStateWithLifecycle()
    val isRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()

    val progress = if (totalSeconds > 0) secondsLeft.toFloat() / totalSeconds.toFloat() else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.94f))
            .clickable(enabled = false) {}, 
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                "🔥 ACTIVE RESISTANCE TRAINING",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                exerciseName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Massive count circular graph presentation
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 10.dp,
                    trackColor = Color.White.copy(alpha = 0.1f)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$secondsLeft",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )
                    Text(
                        "Seconds Left",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            val motivationQuote = when {
                secondsLeft > totalSeconds * 0.7f -> "GET READY... LOCK YOUR ABS! YOUR BODY IS A SHIELD!"
                secondsLeft > totalSeconds * 0.4f -> "BREATH SLOWLY! DEFEAT EVERY COUNTER URGE."
                secondsLeft > totalSeconds * 0.1f -> "FINISHED IS IN SIGHT! PROVE YOUR POWER!"
                else -> "ALMOST THERE... DON'T DROP!"
            }

            Text(
                text = motivationQuote,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.height(48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.toggleTimerActive() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Toggle Timer"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isRunning) "PAUSE" else "RESUME", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.stopWorkoutTimer() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ABORT", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
   // ---------------- TOUGH HABITS & MOOD LOGGER TAB ----------------
@Composable
fun LogsScreen(viewModel: RehabViewModel) {
    val logs by viewModel.habitLogs.collectAsStateWithLifecycle()
    val moodLogs by viewModel.moodLogs.collectAsStateWithLifecycle()

    var activeSubTab by remember { mutableStateOf(0) } // 0: Habit, 1: Mood & Trigger Journal

    // Habit logging form state
    var activeHabitCategory by remember { mutableStateOf("Vaping/Nicotine") }
    var noteInput by remember { mutableStateOf("") }
    var scaleRating by remember { mutableStateOf(3f) }
    var currentTypeSelection by remember { mutableStateOf("RESIST") }

    val focalHabits = listOf(
        "Vaping/Nicotine", "Alcohol Abuse", "Synthetic Drugs/Pills", "Doomscrolling/Gaming", "Bad Circle Peer Influence"
    )

    // Mood logging form state
    var selectedMood by remember { mutableStateOf("😀 Happy/Proud") }
    var selectedTrigger by remember { mutableStateOf("None") }
    var selectedStrategy by remember { mutableStateOf("None") }
    var moodNoteInput by remember { mutableStateOf("") }

    val moodOptions = listOf("😀 Happy/Proud", "🥵 Stressed/Anxious", "🥱 Bored/Fatigued", "😠 Angry/Frustrated", "😐 Neutral")
    val triggerOptions = listOf("School Stress", "Peer Pressure", "Boredom", "Late-night Phones", "Family Tension", "None")
    val strategyOptions = listOf("Held a Plank 🧘", "Deep Breathing 🌬️", "Drank Water 💧", "Talked to Counselor 👥", "None")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
    ) {
        item {
            Column {
                Text(
                    text = "📝 REFOCUS & JOURNAL COMPANION",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Gain deep self-awareness by logging triggers, daily mood fluctuations, and physical coping strategies offline.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Sub-Tab Switcher for subtab_habits, subtab_moods, and subtab_incidents
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = { activeSubTab = 0 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeSubTab == 0) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (activeSubTab == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.BorderColor, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Urges", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                }
                Button(
                    onClick = { activeSubTab = 1 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeSubTab == 1) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (activeSubTab == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Safety", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                }
                Button(
                    onClick = { activeSubTab = 2 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeSubTab == 2) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (activeSubTab == 2) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Incident", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        if (activeSubTab == 0) {
            // Habit Quick Logger (Original component + features preserved)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "ADD NEW EVENT RECORD",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { currentTypeSelection = "RESIST" },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("select_resist"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "I RESISTED!",
                                    color = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            Button(
                                onClick = { currentTypeSelection = "SLIP" },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("select_slip"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (currentTypeSelection == "SLIP") MaterialTheme.colorScheme.error else Color.Gray.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                            ) {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = if (currentTypeSelection == "SLIP") MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "I SLIPPED UP",
                                    color = if (currentTypeSelection == "SLIP") MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Bad Habit Context:", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Box {
                            var expanded by remember { mutableStateOf(false) }
                            Button(
                                onClick = { expanded = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("habit_dropdown_trigger")
                            ) {
                                Text(activeHabitCategory, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f))
                                Text("▼", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            ) {
                                focalHabits.forEach { habit ->
                                    DropdownMenuItem(
                                        text = { Text(habit, fontWeight = FontWeight.Bold) },
                                        onClick = {
                                            activeHabitCategory = habit
                                            expanded = false
                                        },
                                        modifier = Modifier.testTag("habit_option_$habit")
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Trigger/Urge Strength: ${scaleRating.toInt()} / 5", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                        Slider(
                            value = scaleRating,
                            onValueChange = { scaleRating = it },
                            valueRange = 1f..5f,
                            steps = 3,
                            modifier = Modifier.testTag("intensity_slider"),
                            colors = SliderDefaults.colors(
                                activeTrackColor = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                thumbColor = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = noteInput,
                            onValueChange = { noteInput = it },
                            placeholder = { Text("What happened? e.g. Left peer group circle, held forearm planks instead!") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .testTag("note_input"),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.addHabitEvent(
                                    habit = activeHabitCategory,
                                    eventType = currentTypeSelection,
                                    note = noteInput.takeIf { it.isNotBlank() } ?: (if (currentTypeSelection == "RESIST") "Sought self-control through athletic replacement" else "Trigger got intense, will focus on core workouts next time."),
                                    intensity = scaleRating.toInt()
                                )
                                noteInput = ""
                                scaleRating = 3f
                            },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("submit_log_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                contentColor = if (currentTypeSelection == "RESIST") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onError
                            )
                        ) {
                            Text(
                                text = if (currentTypeSelection == "RESIST") "SAVE RESISTANCE RECORD 🛡️" else "LOG SLIP & REFLECT ON STRENGTH ⚠️",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "YOUR RECORD HISTORY LIST",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
            }

            if (logs.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("No history recorded yet.", fontWeight = FontWeight.Bold)
                                Text("Your logs will securely stay only on this device offline.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            } else {
                items(logs) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (item.eventType == "RESIST") MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                                RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.eventType == "RESIST") {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                            } else {
                                MaterialTheme.colorScheme.error.copy(alpha = 0.03f)
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (item.eventType == "RESIST") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                        ),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = if (item.eventType == "RESIST") "RESIST" else "SLIP",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black,
                                            color = if (item.eventType == "RESIST") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onError
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.habitName,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (item.eventType == "RESIST") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Challenge Level: ${item.intensityLevel}/5",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.note,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(item.timestamp)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.deleteHabitEvent(item.id) },
                                modifier = Modifier.testTag("delete_habit_${item.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Log Record",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        } else if (activeSubTab == 1) {
            // 🧠 MOOD & TRIGGER JOURNAL SHEET
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "NEW JOURNAL LOG",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Selected Mood Grid
                        Text("Current Mood Scale:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            moodOptions.forEach { mood ->
                                val isSelected = selectedMood == mood
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                        .border(
                                            1.dp,
                                            if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .clickable { selectedMood = mood }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(mood.take(2), fontSize = 20.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = mood.drop(2).split("/").first(),
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Trigger Context Selector
                        Text("Identified Trigger Context:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Box {
                            var trigExpanded by remember { mutableStateOf(false) }
                            Button(
                                onClick = { trigExpanded = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("trigger_dropdown_trigger")
                            ) {
                                Text(selectedTrigger, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f))
                                Text("▼", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                            }
                            DropdownMenu(
                                expanded = trigExpanded,
                                onDismissRequest = { trigExpanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            ) {
                                triggerOptions.forEach { t ->
                                    DropdownMenuItem(
                                        text = { Text(t, fontWeight = FontWeight.Bold) },
                                        onClick = {
                                            selectedTrigger = t
                                            trigExpanded = false
                                        },
                                        modifier = Modifier.testTag("trigger_option_$t")
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Coping strategy
                        Text("Coping Strategy Selected:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Box {
                            var stratExpanded by remember { mutableStateOf(false) }
                            Button(
                                onClick = { stratExpanded = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("strategy_dropdown_trigger")
                            ) {
                                Text(selectedStrategy, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f))
                                Text("▼", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                            }
                            DropdownMenu(
                                expanded = stratExpanded,
                                onDismissRequest = { stratExpanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            ) {
                                strategyOptions.forEach { s ->
                                    DropdownMenuItem(
                                        text = { Text(s, fontWeight = FontWeight.Bold) },
                                        onClick = {
                                            selectedStrategy = s
                                            stratExpanded = false
                                        },
                                        modifier = Modifier.testTag("strategy_option_$s")
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dynamic Workout Recommendations Card based on selection!
                        val recommendationText = when {
                            selectedMood.contains("Stressed") || selectedMood.contains("Angry") -> {
                                Pair(
                                    "🧘 CALMING SHIELD RECOMMENDATION",
                                    "Your nervous system is alert! Place your elbows down for a 30s 'Urge Breaker Plank' or rotate slowly with the 'Russian Sobriety Twist'. Controlled breathing beats anxiety."
                                )
                            }
                            selectedMood.contains("Bored") -> {
                                Pair(
                                    "⚡ ENERGIZING SHIELD RECOMMENDATION",
                                    "Boredom is the easiest trap for relapse! Trigger custom dopamine directly through active muscles. Power up 20 reps of 'Habit Crusher Crunches' now to wake up focus."
                                )
                            }
                            selectedTrigger == "Peer Pressure" -> {
                                Pair(
                                    "🛡️ GROUNDED RESISTANCE CORE RECOMMENDATION",
                                    "Peers can exert intense pressure. Build absolute spinal strength. Perform 15 'Leg Raise Deflections' to root your posture into stable gravity."
                                )
                            }
                            else -> {
                                Pair(
                                    "💎 ROUTINE STRENGTH PREVIEW",
                                    "Mind is balanced! Sustain this peak sobriety. Perform 'Advanced Core Endurance' to solidify your continuous progress shield."
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f))
                                .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = recommendationText.first,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.secondary,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recommendationText.second,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        viewModel.selectTab(1) // Move to work out screen!
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                    modifier = Modifier.height(32.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                                ) {
                                    Text("VIEW CORE WORKOUTS 💪", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Reflection note
                        OutlinedTextField(
                            value = moodNoteInput,
                            onValueChange = { moodNoteInput = it },
                            placeholder = { Text("What triggers are around? How did you respond? Keep details safe offline...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .testTag("mood_note_input"),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.addMoodLog(
                                    mood = selectedMood,
                                    trigger = selectedTrigger,
                                    strategy = selectedStrategy,
                                    note = moodNoteInput.takeIf { it.isNotBlank() } ?: "Recorded daily mental shield checks. Mood: $selectedMood. Coping action: $selectedStrategy."
                                )
                                moodNoteInput = ""
                                selectedMood = "😀 Happy/Proud"
                                selectedTrigger = "None"
                                selectedStrategy = "None"
                            },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("submit_mood_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                "SAVE JOURNAL CHECK-IN 📚",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "PREVIOUS JOURNAL ENTRIES",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
            }

            if (moodLogs.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.Mood, contentDescription = null, tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("No journal entries yet.", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("Check in daily to build solid cognitive control patterns.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            } else {
                items(moodLogs) { log ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(log.mood.take(2), fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = log.mood.drop(2),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.08f)),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "Trigger: ${log.triggerName}",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                    if (log.copingStrategy != "None") {
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "Coping: ${log.copingStrategy}",
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = log.note,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(log.timestamp)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.deleteMoodLog(log.id) },
                                modifier = Modifier.testTag("delete_mood_${log.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Journal Entry",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        } else if (activeSubTab == 2) {
            // INCIDENT LOG
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha=0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "ANONYMOUS INCIDENT LOG",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Record an incident you witnessed (Bullying, violence, rule breaking). This is 100% anonymous and stays on your device unless you choose to export it to a counselor.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        var incidentNote by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = incidentNote,
                            onValueChange = { incidentNote = it },
                            placeholder = { Text("What happened, when and where? Leave out real names to maintain privacy.") },
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            shape = RoundedCornerShape(12.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                incidentNote = ""
                                // In a full version, this would save to a database.
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("SAVE ANONYMOUS REPORT", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ---------------- EDUCATION CAMPAIGN TAB ----------------
@Composable
fun CampaignScreen(viewModel: RehabViewModel) {
    val lessons = viewModel.campaignLessons

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp, top = 8.dp)
    ) {
        item {
            Column {
                Text(
                    text = "📢 13-WEEK CHALLENGE HUB",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Arm yourself with direct facts. Learn how to stay clean, stop bullying, and protect your peers.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Countdown to 2030
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "COUNTDOWN TO 2030 🌍",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "We're 200 weeks closer to leaving no one behind.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha=0.8f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { 0.4f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
            }
        }
        
        item {
            Text(
                "Myth vs Fact Swipe Cards",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
            val myths = listOf(
                "Myth: Vaping is harmless water vapor." to "Fact: Vapes emit toxic heavy metals and addictive nicotine.",
                "Myth: Bullying builds character." to "Fact: Bullying causes severe trauma, anxiety, and depression.",
                "Myth: Only bad kids get addicted." to "Fact: Addiction is a brain disease, not a moral failing."
            )
            
            // Simple horizontal scroll for swipeable cards
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(myths.size) { index ->
                    var isFlipped by remember { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .width(260.dp)
                            .height(140.dp)
                            .clickable { isFlipped = !isFlipped },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isFlipped) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                            if (!isFlipped) {
                                Text(myths[index].first, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                            } else {
                                Text(myths[index].second, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }

        items(lessons) { lesson ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = lesson.emoji,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = lesson.subtitle,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = lesson.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Athletic refocus strategy box styled beautifully
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                "⚡ CORE REFOCUS STRATEGY:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Black
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = lesson.resistanceTip,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (lesson.causes.isNotEmpty() || lesson.effects.isNotEmpty() || lesson.solutions.isNotEmpty()) {
                        var isExpanded by remember { mutableStateOf(false) }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Deep Dive: Causes, Effects & Solutions",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = if (isExpanded) "▲" else "▼",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (isExpanded) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    if (lesson.causes.isNotEmpty()) {
                                        Text("🚩 Core Causes", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.error)
                                        lesson.causes.forEach { cause ->
                                            Text("• $cause", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 8.dp, bottom = 2.dp))
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    if (lesson.effects.isNotEmpty()) {
                                        Text("⚠️ Negative Effects", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = Color(0xFFFF9800))
                                        lesson.effects.forEach { effect ->
                                            Text("• $effect", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 8.dp, bottom = 2.dp))
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    if (lesson.solutions.isNotEmpty()) {
                                        Text("✅ Sovereign Solutions", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = Color(0xFF4CAF50))
                                        lesson.solutions.forEach { solution ->
                                            Text("• $solution", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 8.dp, bottom = 2.dp))
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // REFUSAL DRILLS PRACTICE BLOCK
                    val completedDrills by viewModel.completedDrills.collectAsStateWithLifecycle()
                    val isCompleted = completedDrills.contains(lesson.id)

                    if (isCompleted) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF4CAF50).copy(alpha = 0.08f))
                                .border(BorderStroke(1.dp, Color(0xFF4CAF50).copy(alpha = 0.3f)), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle, 
                                    contentDescription = "Completed", 
                                    tint = Color(0xFF4CAF50), 
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Refusal Skill Drill Passed! (+40 PTS)",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                        }
                    } else {
                        // Show scenario with answers
                        val drillData = when (lesson.id) {
                            "lesson_drugs" -> Pair(
                                "Scenario: Your classmate hands you a flavored vape or a substance during break: 'Take a rip, it calms your nerves instantly! Everyone does it.'",
                                listOf(
                                    "No thanks, I train my core and lungs to build physical power naturally." to true,
                                    "Alright, maybe just one tiny puff so I fit in today." to false,
                                    "I'll look at it, but let's hide so we don't get suspended." to false
                                )
                            )
                            "lesson_bullying" -> Pair(
                                "Scenario: A group is teasing and embarrassing a smaller student in the hallway. They look to you to join in the laughter.",
                                listOf(
                                    "Tell them to stop and stand next to the student. Real strength protects." to true,
                                    "Laugh a little bit so they don't turn on you instead." to false,
                                    "Walk away quickly and pretend you saw nothing." to false
                                )
                            )
                            "lesson_cyber_bullying" -> Pair(
                                "Scenario: You get tagged in a mean meme making fun of a classmate. Your friends are all commenting toxic things.",
                                listOf(
                                    "Un-tag yourself, decline to comment, and privately offer support to the targeted class mate." to true,
                                    "Reply with a laughing emoji to fit in, but tell yourself it's just a joke online." to false,
                                    "Share it on your story because it's going viral." to false
                                )
                            )
                            "lesson_violence" -> Pair(
                                "Scenario: Someone insults you aggressively in front of the whole class, trying to provoke a physical fight.",
                                listOf(
                                    "Stay completely calm, take a deep breath, and walk away. A sovereign mind cannot be baited." to true,
                                    "Yell back and push them to protect your ego in front of everyone." to false,
                                    "Threaten them quietly with violence after school." to false
                                )
                            )
                            "lesson_dropouts" -> Pair(
                                "Scenario: You failed your math exam and feel completely overwhelmed. A friend says, 'School is useless, let's just skip tomorrow and quit.'",
                                listOf(
                                    "Tell them quitting fixes nothing. You will speak to the teacher for extra help and try again tomorrow." to true,
                                    "Agree with them because school feels too stressful right now." to false,
                                    "Skip tomorrow but tell yourself it's just a one-day break." to false
                                )
                            )
                            "lesson_pregnancy_marriage" -> Pair(
                                "Scenario: Your peers are pressuring you into a serious romantic relationship, saying it's normal for your age and makes you an 'adult'.",
                                listOf(
                                    "Set a firm boundary. I am focused on my education and building my future first." to true,
                                    "Go along with it because you don't want to feel left out." to false,
                                    "Agree, assuming you can just handle adult responsibilities easily." to false
                                )
                            )
                            "lesson_stigma_suicide" -> Pair(
                                "Scenario: You hear a friend talking about feeling hopeless and wanting to 'just disappear forever'.",
                                listOf(
                                    "Take them seriously. Tell a trusted teacher or school counselor immediately, even against their wishes." to true,
                                    "Tell them to 'cheer up' and assume they are just being dramatic." to false,
                                    "Keep it a secret because you promised you wouldn't tell anyone." to false
                                )
                            )
                            "lesson_gangsterism" -> Pair(
                                "Scenario: Older boys from the neighborhood offer you 'protection' and quick money if you deliver a sketchy package for them.",
                                listOf(
                                    "Refuse clearly and walk away. True freedom is avoiding criminal chains." to true,
                                    "Do it just once for the money, planning to quit after." to false,
                                    "Take the package but throw it away so you don't get in trouble." to false
                                )
                            )
                            else -> Pair(
                                "Scenario: You face a difficult urge or pressure from your peers to do something destructive.",
                                listOf(
                                    "Reject it. I'm building high-performance athletic focus instead!" to true,
                                    "I'll pretend to go along with it." to false,
                                    "Just a tiny bit so I don't look weak." to false
                                )
                            )
                        }

                        var selectedOpinion by remember { mutableStateOf<Int?>(null) }
                        var showExplanation by remember { mutableStateOf(false) }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🔥 PRACTICE REFUSAL DRILL",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.secondary,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = drillData.first,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    drillData.second.forEachIndexed { optIdx, (text, isCorrect) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (selectedOpinion == optIdx) {
                                                        if (isCorrect) Color(0xFF4CAF50).copy(alpha = 0.15f)
                                                        else MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
                                                    } else MaterialTheme.colorScheme.surface
                                                )
                                                .border(
                                                    1.dp,
                                                    if (selectedOpinion == optIdx) {
                                                        if (isCorrect) Color(0xFF4CAF50)
                                                        else MaterialTheme.colorScheme.error
                                                    } else MaterialTheme.colorScheme.outlineVariant,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable {
                                                    selectedOpinion = optIdx
                                                    showExplanation = true
                                                    if (isCorrect) {
                                                        viewModel.completeRefusalDrill(lesson.id)
                                                    }
                                                }
                                                .padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = text,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }

                                if (showExplanation) {
                                    val currentSelectionIsCorrect = drillData.second[selectedOpinion ?: 0].second
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (currentSelectionIsCorrect) Color(0xFF4CAF50).copy(alpha = 0.08f)
                                                else MaterialTheme.colorScheme.error.copy(alpha = 0.08f)
                                            )
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = if (currentSelectionIsCorrect) {
                                                "✨ CORRECT! You stood your ground using absolute physical self-mastery. Choosing natural strength and core workouts earns you healthy dopamine and +40 PTS!"
                                            } else {
                                                "❌ NOT OPTIMAL. Faking compliance or surrendering to peer pressure weakens your sovereignty. Try again to choose the sovereign athletic deflection path!"
                                            },
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (currentSelectionIsCorrect) Color(0xFF388E3C) else MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            shape = CircleShape
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = lesson.badgeName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            "Knowledge Gained ✓",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }

        // Secure Off-line guarantee card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏛️ OFFLINE GUARANTEE",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "This application respects your utmost privacy by storing 100% of rehabilitation history securely on your physical device. No servers, no tracking, complete self-sovereign recovery.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ---------------- UTILS / DATE HELPER ----------------
private fun isSameDay(time1: Long, time2: Long): Boolean {
    if (time1 == 0L || time2 == 0L) return false
    val cal1 = Calendar.getInstance().apply { timeInMillis = time1 }
    val cal2 = Calendar.getInstance().apply { timeInMillis = time2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}
