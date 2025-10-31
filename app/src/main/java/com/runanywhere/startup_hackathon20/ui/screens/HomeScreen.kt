package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.runanywhere.startup_hackathon20.YouthHubViewModel
import com.runanywhere.startup_hackathon20.data.Category
import com.runanywhere.startup_hackathon20.data.ChatMessage
import com.runanywhere.startup_hackathon20.ui.components.AnimatedGradientBackground
import com.runanywhere.startup_hackathon20.ui.components.ModelManagementCard
import com.runanywhere.startup_hackathon20.ui.components.PulsingButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: YouthHubViewModel,
    navController: NavController
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val currentModelId by viewModel.currentModelId.collectAsState()
    
    var inputText by remember { mutableStateOf("") }
    var showModelManagement by remember { mutableStateOf(false) }
    
    val listState = rememberLazyListState()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "YouthHub AI",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showModelManagement = !showModelManagement }) {
                        AnimatedIcon(
                            icon = if (showModelManagement) Icons.Default.Close else Icons.Default.SmartToy,
                            selected = showModelManagement
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Status Bar
            AnimatedStatusBar(
                statusMessage = statusMessage,
                currentModelId = currentModelId
            )

            // Model Management Section
            AnimatedVisibility(
                visible = showModelManagement,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                ModelManagementCard(viewModel = viewModel)
            }

            // Category Selector
            AnimatedVisibility(
                visible = messages.isEmpty(),
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                CategorySelector(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        viewModel.setCategory(category)
                    },
                    navController = navController
                )
            }

            // Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    AnimatedMessageBubble(message = message)
                }
                
                if (isLoading) {
                    item {
                        TypingIndicator()
                    }
                }
            }

            // Input Section
            ChatInputSection(
                inputText = inputText,
                onInputChange = { inputText = it },
                onSendClick = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    }
                },
                enabled = !isLoading,
                selectedCategory = selectedCategory
            )
        }
    }
}

@Composable
fun AnimatedStatusBar(
    statusMessage: String,
    currentModelId: String?
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = if (currentModelId != null) 
            MaterialTheme.colorScheme.primaryContainer 
        else 
            MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (currentModelId != null) Icons.Default.CheckCircle else Icons.Default.Info,
                contentDescription = null,
                tint = if (currentModelId != null) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = statusMessage,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Welcome to YouthHub! 🎉",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "How can I help you today?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryChip(
                icon = Icons.Default.AccountBalance,
                label = "Finance",
                selected = selectedCategory == Category.FINANCE,
                onClick = { onCategorySelected(Category.FINANCE) },
                modifier = Modifier.weight(1f)
            )
            CategoryChip(
                icon = Icons.Default.Gavel,
                label = "Legal",
                selected = selectedCategory == Category.LEGAL,
                onClick = { onCategorySelected(Category.LEGAL) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryChip(
                icon = Icons.Default.School,
                label = "Education",
                selected = selectedCategory == Category.EDUCATION,
                onClick = { onCategorySelected(Category.EDUCATION) },
                modifier = Modifier.weight(1f)
            )
            CategoryChip(
                icon = Icons.Default.QuestionAnswer,
                label = "All",
                selected = selectedCategory == Category.ALL,
                onClick = { onCategorySelected(Category.ALL) },
                modifier = Modifier.weight(1f)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Or explore directly:",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionButton(
                text = "Browse Loans",
                icon = Icons.Default.AccountBalance,
                onClick = { navController.navigate("finance") },
                modifier = Modifier.weight(1f)
            )
            QuickActionButton(
                text = "Legal Help",
                icon = Icons.Default.Gavel,
                onClick = { navController.navigate("legal") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CategoryChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "chip_scale"
    )

    Surface(
        onClick = onClick,
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) 
            MaterialTheme.colorScheme.primaryContainer 
        else 
            MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = if (selected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun QuickActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Text(text)
        }
    }
}

@Composable
fun AnimatedMessageBubble(message: ChatMessage) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { if (message.isUser) it else -it }
        ) + fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(
                        start = if (message.isUser) 48.dp else 0.dp,
                        end = if (message.isUser) 0.dp else 48.dp
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isUser)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isUser) 16.dp else 4.dp,
                    bottomEnd = if (message.isUser) 4.dp else 16.dp
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (message.isUser) Icons.Default.Person else Icons.Default.SmartToy,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (message.isUser)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = if (message.isUser) "You" else "AI Assistant",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier.padding(end = 48.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    BouncingDot(delayMillis = index * 200)
                }
            }
        }
    }
}

@Composable
fun BouncingDot(delayMillis: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = delayMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_bounce"
    )

    Box(
        modifier = Modifier
            .offset(y = offsetY.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun ChatInputSection(
    inputText: String,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    enabled: Boolean,
    selectedCategory: Category
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (selectedCategory != Category.ALL) {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Asking about:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    SuggestionChip(
                        onClick = { },
                        label = { Text(selectedCategory.name) },
                        icon = {
                            Icon(
                                imageVector = when (selectedCategory) {
                                    Category.FINANCE -> Icons.Default.AccountBalance
                                    Category.LEGAL -> Icons.Default.Gavel
                                    Category.EDUCATION -> Icons.Default.School
                                    else -> Icons.Default.QuestionAnswer
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    onValueChange = onInputChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { 
                        Text(
                            when (selectedCategory) {
                                Category.FINANCE -> "Ask about loans, schemes..."
                                Category.LEGAL -> "Ask for legal advice..."
                                Category.EDUCATION -> "Ask about courses, exams..."
                                else -> "Ask me anything..."
                            }
                        ) 
                    },
                    enabled = enabled,
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )

                FloatingActionButton(
                    onClick = onSendClick,
                    enabled = enabled && inputText.isNotBlank(),
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean
) {
    val rotation by animateFloatAsState(
        targetValue = if (selected) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "icon_rotation"
    )

    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = rotation
            }
    )
}
