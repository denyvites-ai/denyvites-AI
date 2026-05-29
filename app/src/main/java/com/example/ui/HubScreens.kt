package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HubScreen(viewModel: RehabViewModel, campaignContent: @Composable () -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("13-Week Challenge", "The Novel", "Action Taskforce", "Teacher Tools", "Helplines", "Quiz Center", "Advice Bot", "Calendar")

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 8.dp,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, fontWeight = FontWeight.Bold, maxLines = 1) }
                )
            }
        }

        Box(modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp)) {
            when (selectedTabIndex) {
                0 -> campaignContent() // The original campaign tab logic with countdown
                1 -> NovelScreen()
                2 -> ActionScreen()
                3 -> TeacherScreen()
                4 -> HelplinesScreen()
                5 -> QuizzesScreen()
                6 -> BotScreen()
                7 -> CalendarScreen()
            }
        }
    }
}

@Composable
fun NovelScreen() {
    val chapters = listOf(
        "Chapter 1: The First Bell" to "The morning mist hung low over the schoolyard. A tense energy vibrated through the corridors, a silent witness to the unseen struggles...\n\n[Reflect: Have you ever felt an unspoken tension at school?]",
        "Chapter 2: Shadows in the Hallway" to "Whispers echoed near the locker rooms. A simple push, a harsh word, and the cycle of peer violence continued under the guise of 'just joking around'...\n\n[Reflect: When does a joke cross the line into bullying?]",
        "Chapter 3: The Digital Echo" to "A notification pinged. Then another. Soon, the entire class's phones were buzzing with a meme that wasn't funny, but cruel. The virtual world felt heavier than reality...\n\n[Reflect: How do you protect your peace online?]",
        "Chapter 4: The Pressure Cooker" to "The exams were looming, but the real test was taking place behind the bleachers. 'Just one hit,' they said, 'It'll help you focus.' The temptation of escapes...\n\n[Reflect: What is your strongest defense against pressure?]",
        "Chapter 13: The Thirteenth Bell" to "As the final bell rang, it wasn't a sound of ending, but of a new beginning. A school united, shields raised, standing together against the darkness. The movement had begun...\n\n[Reflect: What will you do to make your school a sanctuary?]"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text(
                "The Thirteenth Bell",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "An exclusive offline novel by Miss C Official.\nEdited by Svobixd.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(chapters) { (title, content) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(content, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 20.sp)
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun ActionScreen() {
    val tasks = listOf(
        "Student" to "Organize a peer chat discussing mental wellness.",
        "Teacher" to "Create a safe 'No-Judgment' box in your classroom.",
        "Parent" to "Have a 10-minute uninterrupted check-in with your child.",
        "Student" to "Put up a positive pledge poster in the hallway."
    )

    val prompts = listOf(
        "If you saw someone sitting alone every day, what stops you from approaching them?",
        "What is the biggest misunderstanding adults have about being a teenager today?",
        "When is 'minding your own business' actually turning a blind eye?"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text("Taskforce Builder", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Role-based missions to improve your school environment.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(tasks) { (role, task) ->
            var checked by remember { mutableStateOf(false) }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)).padding(8.dp)) {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(role, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    Text(task, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Discussion Prompts", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Conversation starters for groups or family talks.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(prompts) { prompt ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ChatBubbleOutline, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(prompt, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
        
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun TeacherScreen() {
    val modules = listOf(
        "Facilitator Guide: Week 1" to "How to address the hidden signs of peer bullying. Start with an icebreaker and set ground rules.",
        "Anonymous Reporting Dashboard" to "Currently 0 active reports. Safe zone established.",
        "Printable Materials Generator" to "Generate high-resolution offline posters for the classroom."
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text("Counsellor & Teacher Tools", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Administrative utilities and guidance resources. (Offline secure mode)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(modules) { (title, desc) ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { /* mocked open */ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                        Text("Access Module")
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun HelplinesScreen() {
    val helplines = listOf(
        "Childline Zimbabwe" to "116 (Free from any line)",
        "Musasa Project (GBV & Violence)" to "08080074 (Toll Free)",
        "ZRP Victim Friendly Unit" to "0242 703631",
        "Tariro Clinic & Counseling" to "0712 333 444"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text("Helplines & Resources", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Local contacts for counseling and emergencies. Available offline.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(helplines) { (name, number) ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.onErrorContainer)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                        Text(number, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun QuizzesScreen() {
    val quizzes = listOf(
        "Is this bullying?" to "A scenario quiz focusing on recognizing passive and active bullying in school settings.",
        "Cyber Safety Challenge" to "Learn how to protect your passwords, report harmful messages, and avoid online scams.",
        "Healthy or Harmful?" to "Test your knowledge on the effects of substance abuse and peer pressure."
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text("Quiz Center & Badges", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Gamify your learning. Earn badges by completing challenges.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            
            // Badges section mock
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)).background(MaterialTheme.colorScheme.tertiaryContainer), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                }
                Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)).background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha=0.3f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha=0.5f))
                }
                Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(32.dp)).background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha=0.3f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha=0.5f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(quizzes) { (title, desc) ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { /* Start Quiz */ }, shape = RoundedCornerShape(8.dp)) {
                        Text("Start Quiz")
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Composable
fun BotScreen() {
    var input by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf("Bot" to "Hi! I am the Offline Advice Bot. Tell me what's on your mind. You can ask about bullying, stress, or peer pressure.")) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("AI Advice Bot (Offline)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
        Text("Quick keyword-based advice. Never leaves your phone.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(chatHistory) { (sender, msg) ->
                val isBot = sender == "Bot"
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(
                                topStart = 16.dp, topEnd = 16.dp,
                                bottomStart = if (isBot) 4.dp else 16.dp,
                                bottomEnd = if (isBot) 16.dp else 4.dp
                            ))
                            .background(if (isBot) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer)
                            .padding(12.dp)
                    ) {
                        Text(msg, style = MaterialTheme.typography.bodyMedium, color = if (isBot) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 80.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type here...") },
                shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (input.isNotBlank()) {
                        val userMsg = input
                        val response = when {
                            userMsg.contains("bull", ignoreCase = true) -> "I'm sorry you are dealing with bullying. Please remember it's not your fault. Speak to a trusted teacher or counselor immediately, or report it anonymously in the Guard tab."
                            userMsg.contains("drug", ignoreCase = true) || userMsg.contains("smoke", ignoreCase = true) || userMsg.contains("vape", ignoreCase = true) -> "Substances trick the brain into fake dopamine. The real high is self-mastery. Check the 13-Week Challenge tab for facts, and talk to someone you trust."
                            userMsg.contains("sad", ignoreCase = true) || userMsg.contains("depress", ignoreCase = true) -> "It takes courage to admit you're struggling. Reach out to Childline at 116 or talk to the school counselor. You matter."
                            else -> "I hear you. This sounds tough. Check out the 'Discussion Prompts' or the 'Helplines' section for more guidance."
                        }
                        chatHistory = chatHistory + ("User" to userMsg) + ("Bot" to response)
                        input = ""
                    }
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun CalendarScreen() {
    val events = listOf(
        "May 12" to "Anti-Bullying Assembly in the Main Hall",
        "May 15" to "Counseling Drop-In Session (Room 104)",
        "May 20" to "Mental Wellness Walk around the sports field",
        "June 1" to "Campaign Pledge Day - Wear your bracelets!"
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Text("School Event Calendar", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text("Keep track of awareness events, counseling availability, and assemblies locally.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(events) { (date, event) ->
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.width(60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Event, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(date, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(event, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}
