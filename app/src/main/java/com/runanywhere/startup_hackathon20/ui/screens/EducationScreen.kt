package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.runanywhere.startup_hackathon20.FinvariaViewModel
import com.runanywhere.startup_hackathon20.data.EducationGuidance
import com.runanywhere.startup_hackathon20.data.EducationType
import com.runanywhere.startup_hackathon20.data.Scholarship
import com.runanywhere.startup_hackathon20.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationScreen(viewModel: FinvariaViewModel) {
    val filteredEducation by viewModel.filteredEducation.collectAsState()
    val scholarships by viewModel.scholarships.collectAsState()
    val educationFilter by viewModel.educationFilter.collectAsState()

    var expandedEducationId by remember { mutableStateOf<String?>(null) }
    var expandedScholarshipId by remember { mutableStateOf<String?>(null) }
    var showFilters by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Education Guide",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    if (selectedTab == 0) {
                        IconButton(onClick = { showFilters = !showFilters }) {
                            Badge(
                                containerColor = if (educationFilter.types.isNotEmpty())
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Icon(
                                    imageVector = if (showFilters) Icons.Default.Close else Icons.Default.FilterList,
                                    contentDescription = "Filter"
                                )
                            }
                        }
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
            // Tab Row
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Courses") },
                    icon = { Icon(Icons.Default.School, null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Scholarships") },
                    icon = { Icon(Icons.Default.CardGiftcard, null) }
                )
            }

            when (selectedTab) {
                0 -> {
                    AnimatedVisibility(
                        visible = showFilters,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        EducationFilterCard(
                            selectedTypes = educationFilter.types,
                            onTypesChange = { types ->
                                viewModel.updateEducationFilter(educationFilter.copy(types = types))
                            },
                            withScholarships = educationFilter.withScholarships,
                            onWithScholarshipsChange = { value ->
                                viewModel.updateEducationFilter(
                                    educationFilter.copy(
                                        withScholarships = value
                                    )
                                )
                            },
                            onClearFilters = {
                                viewModel.updateEducationFilter(
                                    educationFilter.copy(
                                        types = emptySet(),
                                        withScholarships = false
                                    )
                                )
                            }
                        )
                    }

                    if (filteredEducation.isEmpty()) {
                        EmptyState(
                            icon = Icons.Default.SearchOff,
                            title = "No Courses Found",
                            message = "Try adjusting your filters",
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Text(
                                    text = "📚 Education Paths (${filteredEducation.size})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            items(filteredEducation) { education ->
                                AnimatedEducationCard(
                                    education = education,
                                    isExpanded = expandedEducationId == education.id,
                                    onExpandClick = {
                                        expandedEducationId =
                                            if (expandedEducationId == education.id) null else education.id
                                    }
                                )
                            }
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = "🎓 Scholarships (${scholarships.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        items(scholarships) { scholarship ->
                            AnimatedScholarshipCard(
                                scholarship = scholarship,
                                isExpanded = expandedScholarshipId == scholarship.id,
                                onExpandClick = {
                                    expandedScholarshipId =
                                        if (expandedScholarshipId == scholarship.id) null else scholarship.id
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EducationFilterCard(
    selectedTypes: Set<EducationType>,
    onTypesChange: (Set<EducationType>) -> Unit,
    withScholarships: Boolean,
    onWithScholarshipsChange: (Boolean) -> Unit,
    onClearFilters: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter Courses",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (selectedTypes.isNotEmpty() || withScholarships) {
                    TextButton(onClick = onClearFilters) {
                        Text("Clear")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = withScholarships,
                    onCheckedChange = onWithScholarshipsChange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Only with scholarships")
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EducationType.entries.take(6).forEach { type ->
                    FilterChip(
                        selected = type in selectedTypes,
                        onClick = {
                            val newTypes = if (type in selectedTypes) {
                                selectedTypes - type
                            } else {
                                selectedTypes + type
                            }
                            onTypesChange(newTypes)
                        },
                        label = { Text(type.name.replace("_", " ")) },
                        leadingIcon = {
                            if (type in selectedTypes) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedEducationCard(
    education: EducationGuidance,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        onClick = onExpandClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = education.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = education.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    icon = Icons.Default.Schedule,
                    label = education.duration
                )
                InfoChip(
                    icon = Icons.Default.CurrencyRupee,
                    label = education.averageCost
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailSection(title = "Eligibility", content = education.eligibility)
                    DetailSection(title = "Recommended For", content = education.recommendedFor)

                    if (education.entranceExams.isNotEmpty()) {
                        Text(
                            text = "📝 Entrance Exams",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        education.entranceExams.forEach { exam ->
                            Text(
                                text = "• $exam",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    if (education.topInstitutions.isNotEmpty()) {
                        Text(
                            text = "🏛️ Top Institutions",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        education.topInstitutions.take(5).forEach { institution ->
                            Text(
                                text = "• $institution",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    if (education.careerProspects.isNotEmpty()) {
                        Text(
                            text = "💼 Career Prospects",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        education.careerProspects.forEach { career ->
                            Text(
                                text = "• $career",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    if (education.scholarshipsAvailable.isNotEmpty()) {
                        Text(
                            text = "🎓 Scholarships Available",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        education.scholarshipsAvailable.forEach { scholarship ->
                            Text(
                                text = "• $scholarship",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedScholarshipCard(
    scholarship: Scholarship,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        onClick = onExpandClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = scholarship.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scholarship.provider,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    icon = Icons.Default.AttachMoney,
                    label = scholarship.amount
                )
                InfoChip(
                    icon = Icons.Default.Event,
                    label = scholarship.deadline
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailSection(title = "Eligibility", content = scholarship.eligibility)
                    DetailSection(
                        title = "Selection Criteria",
                        content = scholarship.selectionCriteria
                    )

                    if (scholarship.benefits.isNotEmpty()) {
                        Text(
                            text = "✨ Benefits",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        scholarship.benefits.forEach { benefit ->
                            Text(
                                text = "• $benefit",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    Button(
                        onClick = { /* Open website */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Icon(Icons.Default.OpenInNew, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Apply Now")
                    }
                }
            }
        }
    }
}
