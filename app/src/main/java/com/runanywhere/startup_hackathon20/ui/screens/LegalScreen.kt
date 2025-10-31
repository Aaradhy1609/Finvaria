package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.runanywhere.startup_hackathon20.YouthHubViewModel
import com.runanywhere.startup_hackathon20.data.LegalAdvice
import com.runanywhere.startup_hackathon20.data.LegalCategory
import com.runanywhere.startup_hackathon20.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegalScreen(viewModel: YouthHubViewModel) {
    val filteredAdvice by viewModel.filteredLegalAdvice.collectAsState()
    val legalFilter by viewModel.legalFilter.collectAsState()

    var expandedAdviceId by remember { mutableStateOf<String?>(null) }
    var showFilters by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Legal Advisor",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Badge(
                            containerColor = if (legalFilter.categories.isNotEmpty())
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
            // Warning Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.errorContainer,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "This is general guidance. For serious legal matters, please consult a qualified lawyer.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            AnimatedVisibility(
                visible = showFilters,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LegalFilterCard(
                    selectedCategories = legalFilter.categories,
                    onCategoriesChange = { categories ->
                        viewModel.updateLegalFilter(legalFilter.copy(categories = categories))
                    },
                    onClearFilters = {
                        viewModel.updateLegalFilter(legalFilter.copy(categories = emptySet()))
                    }
                )
            }

            if (filteredAdvice.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.SearchOff,
                    title = "No Legal Advice Found",
                    message = "Try adjusting your filters or browse all categories",
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
                            text = "⚖️ Legal Guidance (${filteredAdvice.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(filteredAdvice) { advice ->
                        AnimatedLegalCard(
                            advice = advice,
                            isExpanded = expandedAdviceId == advice.id,
                            onExpandClick = {
                                expandedAdviceId =
                                    if (expandedAdviceId == advice.id) null else advice.id
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LegalFilterCard(
    selectedCategories: Set<LegalCategory>,
    onCategoriesChange: (Set<LegalCategory>) -> Unit,
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
                    text = "Filter by Category",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (selectedCategories.isNotEmpty()) {
                    TextButton(onClick = onClearFilters) {
                        Text("Clear")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LegalCategory.entries.forEach { category ->
                    FilterChip(
                        selected = category in selectedCategories,
                        onClick = {
                            val newCategories = if (category in selectedCategories) {
                                selectedCategories - category
                            } else {
                                selectedCategories + category
                            }
                            onCategoriesChange(newCategories)
                        },
                        label = { Text(category.name.replace("_", " ")) },
                        leadingIcon = {
                            if (category in selectedCategories) {
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
fun AnimatedLegalCard(
    advice: LegalAdvice,
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = advice.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = advice.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    icon = Icons.Default.Schedule,
                    label = advice.timelineExpectation
                )
                InfoChip(
                    icon = Icons.Default.CurrencyRupee,
                    label = advice.estimatedCost
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AssistChip(
                onClick = { },
                label = { Text(advice.category.name.replace("_", " ")) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailSection(title = "Scenario", content = advice.scenario)
                    DetailSection(title = "Solution", content = advice.solution)

                    if (advice.relevantLaws.isNotEmpty()) {
                        Text(
                            text = "📜 Relevant Laws",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        advice.relevantLaws.forEach { law ->
                            Text(
                                text = "• $law",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    if (advice.requiredDocuments.isNotEmpty()) {
                        Text(
                            text = "📄 Required Documents",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        advice.requiredDocuments.forEach { doc ->
                            Text(
                                text = "• $doc",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    if (advice.tips.isNotEmpty()) {
                        Text(
                            text = "💡 Tips",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        advice.tips.forEach { tip ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = tip,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    if (advice.commonMistakes.isNotEmpty()) {
                        Text(
                            text = "⚠️ Common Mistakes to Avoid",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                        advice.commonMistakes.forEach { mistake ->
                            Text(
                                text = "• $mistake",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { /* Contact lawyer */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContactPage,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Find a Lawyer")
                    }
                }
            }
        }
    }
}
