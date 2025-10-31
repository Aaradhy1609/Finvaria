package com.runanywhere.startup_hackathon20

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runanywhere.sdk.models.ModelInfo
import com.runanywhere.sdk.public.RunAnywhere
import com.runanywhere.sdk.public.extensions.listAvailableModels
import com.runanywhere.startup_hackathon20.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class YouthHubViewModel : ViewModel() {

    // Chat and AI
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _availableModels = MutableStateFlow<List<ModelInfo>>(emptyList())
    val availableModels: StateFlow<List<ModelInfo>> = _availableModels.asStateFlow()

    private val _downloadProgress = MutableStateFlow<Float?>(null)
    val downloadProgress: StateFlow<Float?> = _downloadProgress.asStateFlow()

    private val _currentModelId = MutableStateFlow<String?>(null)
    val currentModelId: StateFlow<String?> = _currentModelId.asStateFlow()

    private val _statusMessage = MutableStateFlow<String>("Initializing...")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    // User Preferences
    private val _userPreferences = MutableStateFlow(UserPreferences())
    val userPreferences: StateFlow<UserPreferences> = _userPreferences.asStateFlow()

    // Category Selection
    private val _selectedCategory = MutableStateFlow<Category>(Category.ALL)
    val selectedCategory: StateFlow<Category> = _selectedCategory.asStateFlow()

    // Loans
    private val _loans = MutableStateFlow<List<LoanInfo>>(KnowledgeBase.loans)
    val loans: StateFlow<List<LoanInfo>> = _loans.asStateFlow()

    private val _loanFilter = MutableStateFlow(LoanFilter())
    val loanFilter: StateFlow<LoanFilter> = _loanFilter.asStateFlow()

    private val _filteredLoans = MutableStateFlow<List<LoanInfo>>(KnowledgeBase.loans)
    val filteredLoans: StateFlow<List<LoanInfo>> = _filteredLoans.asStateFlow()

    // Legal Advice
    private val _legalAdvice = MutableStateFlow<List<LegalAdvice>>(KnowledgeBase.legalAdvice)
    val legalAdvice: StateFlow<List<LegalAdvice>> = _legalAdvice.asStateFlow()

    private val _legalFilter = MutableStateFlow(LegalFilter())
    val legalFilter: StateFlow<LegalFilter> = _legalFilter.asStateFlow()

    private val _filteredLegalAdvice =
        MutableStateFlow<List<LegalAdvice>>(KnowledgeBase.legalAdvice)
    val filteredLegalAdvice: StateFlow<List<LegalAdvice>> = _filteredLegalAdvice.asStateFlow()

    // Education
    private val _educationGuidance =
        MutableStateFlow<List<EducationGuidance>>(KnowledgeBase.educationGuidance)
    val educationGuidance: StateFlow<List<EducationGuidance>> = _educationGuidance.asStateFlow()

    private val _scholarships = MutableStateFlow<List<Scholarship>>(KnowledgeBase.scholarships)
    val scholarships: StateFlow<List<Scholarship>> = _scholarships.asStateFlow()

    private val _educationFilter = MutableStateFlow(EducationFilter())
    val educationFilter: StateFlow<EducationFilter> = _educationFilter.asStateFlow()

    private val _filteredEducation =
        MutableStateFlow<List<EducationGuidance>>(KnowledgeBase.educationGuidance)
    val filteredEducation: StateFlow<List<EducationGuidance>> = _filteredEducation.asStateFlow()

    // Document Scanning
    private val _scannedDocuments = MutableStateFlow<List<ScannedDocument>>(emptyList())
    val scannedDocuments: StateFlow<List<ScannedDocument>> = _scannedDocuments.asStateFlow()

    // Voice Input
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    init {
        loadAvailableModels()
        addWelcomeMessage()
    }

    private fun addWelcomeMessage() {
        val language = _userPreferences.value.language
        val welcomeMessage = when (language) {
            Language.HINDI -> "नमस्ते! मैं आपका यूथ हब सहायक हूं। मैं वित्त, कानूनी और शिक्षा मामलों में मदद कर सकता हूं।"
            Language.HINGLISH -> "Namaste! Main aapka Youth Hub assistant hun. Finance, legal aur education ke mamle me help kar sakta hun."
            else -> "Welcome to YouthHub! I'm your AI assistant for finance, legal, and education guidance. How can I help you today?"
        }

        _messages.value = listOf(
            ChatMessage(
                text = welcomeMessage,
                isUser = false,
                language = language
            )
        )
    }

    // ========== AI and Model Management ==========

    private fun loadAvailableModels() {
        viewModelScope.launch {
            try {
                val models = listAvailableModels()
                _availableModels.value = models
                _statusMessage.value = if (models.isEmpty()) {
                    "Initializing AI models..."
                } else {
                    "Ready - Please download and load an AI model for smart assistance"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error loading models: ${e.message}"
            }
        }
    }

    fun downloadModel(modelId: String) {
        viewModelScope.launch {
            try {
                _statusMessage.value = "Downloading AI model..."
                RunAnywhere.downloadModel(modelId).collect { progress ->
                    _downloadProgress.value = progress
                    _statusMessage.value = "Downloading: ${(progress * 100).toInt()}%"
                }
                _downloadProgress.value = null
                _statusMessage.value = "Download complete! Please load the model."
                loadAvailableModels()
            } catch (e: Exception) {
                _statusMessage.value = "Download failed: ${e.message}"
                _downloadProgress.value = null
            }
        }
    }

    fun loadModel(modelId: String) {
        viewModelScope.launch {
            try {
                _statusMessage.value = "Loading AI model..."
                val success = RunAnywhere.loadModel(modelId)
                if (success) {
                    _currentModelId.value = modelId
                    _statusMessage.value =
                        "AI Ready! Ask me anything about finance, legal, or education."
                } else {
                    _statusMessage.value = "Failed to load model"
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error loading model: ${e.message}"
            }
        }
    }

    fun refreshModels() {
        loadAvailableModels()
    }

    // ========== Chat and AI Query ==========

    fun sendMessage(text: String, category: Category? = null) {
        val currentCategory = category ?: _selectedCategory.value

        // Add user message
        _messages.value += ChatMessage(
            text = text,
            isUser = true,
            language = _userPreferences.value.language,
            category = currentCategory
        )

        // Check if we should use AI or knowledge base
        val useAI = _currentModelId.value != null

        if (useAI) {
            generateAIResponse(text, currentCategory)
        } else {
            generateKnowledgeBaseResponse(text, currentCategory)
        }
    }

    private fun generateAIResponse(text: String, category: Category) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                // Build context-aware prompt
                val contextPrompt = buildContextPrompt(text, category)

                var assistantResponse = ""
                RunAnywhere.generateStream(contextPrompt).collect { token ->
                    assistantResponse += token

                    // Update assistant message in real-time
                    val currentMessages = _messages.value.toMutableList()
                    if (currentMessages.lastOrNull()?.isUser == false) {
                        currentMessages[currentMessages.lastIndex] =
                            ChatMessage(
                                text = assistantResponse,
                                isUser = false,
                                language = _userPreferences.value.language,
                                category = category
                            )
                    } else {
                        currentMessages.add(
                            ChatMessage(
                                text = assistantResponse,
                                isUser = false,
                                language = _userPreferences.value.language,
                                category = category
                            )
                        )
                    }
                    _messages.value = currentMessages
                }
            } catch (e: Exception) {
                _messages.value += ChatMessage(
                    text = "Error: ${e.message}",
                    isUser = false,
                    language = _userPreferences.value.language
                )
            }

            _isLoading.value = false
        }
    }

    private fun generateKnowledgeBaseResponse(text: String, category: Category) {
        viewModelScope.launch {
            _isLoading.value = true

            // Search knowledge base
            val response = searchKnowledgeBase(text, category)

            _messages.value += ChatMessage(
                text = response,
                isUser = false,
                language = _userPreferences.value.language,
                category = category
            )

            _isLoading.value = false
        }
    }

    private fun buildContextPrompt(userQuery: String, category: Category): String {
        val language = _userPreferences.value.language
        val languageInstruction = when (language) {
            Language.HINDI -> "Please respond in Hindi (हिन्दी)."
            Language.HINGLISH -> "Please respond in Hinglish (Roman Hindi with English words)."
            else -> "Please respond in simple English."
        }

        val categoryContext = when (category) {
            Category.FINANCE -> """
                You are a financial advisor helping Indian youth with:
                - Education loans and student loans
                - Personal finance management
                - Government loan schemes
                - Scholarships and financial aid
                - Basic investment advice
                Provide practical, actionable advice specific to India.
            """.trimIndent()

            Category.LEGAL -> """
                You are a legal advisor helping Indian youth with:
                - Consumer rights and protection
                - Employment law basics
                - Education-related legal issues
                - Cyber crime and online fraud
                - Basic legal procedures in India
                Provide simple, easy-to-understand legal guidance. Always recommend consulting a lawyer for complex issues.
            """.trimIndent()

            Category.EDUCATION -> """
                You are an education counselor helping Indian youth with:
                - Course selection and career guidance
                - Entrance exams and preparation
                - Scholarships and financial aid
                - Study abroad options
                - Skill development programs
                Provide motivating and practical advice.
            """.trimIndent()

            else -> """
                You are YouthHub assistant helping Indian youth with finance, legal, and education matters.
                Provide helpful, accurate, and practical advice.
            """.trimIndent()
        }

        return """
            $categoryContext
            
            $languageInstruction
            
            User Question: $userQuery
            
            Provide a helpful, concise, and actionable response.
        """.trimIndent()
    }

    private fun searchKnowledgeBase(query: String, category: Category): String {
        val lowerQuery = query.lowercase()

        return when (category) {
            Category.FINANCE -> searchLoansKnowledgeBase(lowerQuery)
            Category.LEGAL -> searchLegalKnowledgeBase(lowerQuery)
            Category.EDUCATION -> searchEducationKnowledgeBase(lowerQuery)
            else -> searchAllKnowledgeBase(lowerQuery)
        }
    }

    private fun searchLoansKnowledgeBase(query: String): String {
        val results = KnowledgeBase.searchLoans(query)
        if (results.isEmpty()) {
            return "I couldn't find specific loan information for your query. Try browsing the Loan Advisor section or be more specific about the type of loan you need."
        }

        val topResult = results.first()
        return """
            **${topResult.name}**
            
            ${topResult.description}
            
            **Provider:** ${topResult.provider}
            **Interest Rate:** ${topResult.interestRate}
            **Loan Amount:** ${topResult.minAmount} - ${topResult.maxAmount}
            **Tenure:** ${topResult.tenure}
            
            **Eligibility:** ${topResult.eligibility}
            
            **Key Benefits:**
            ${topResult.benefits.take(3).joinToString("\n") { "• $it" }}
            
            View the Loan Advisor section for complete details and application process.
        """.trimIndent()
    }

    private fun searchLegalKnowledgeBase(query: String): String {
        val results = KnowledgeBase.searchLegalAdvice(query)
        if (results.isEmpty()) {
            return "I couldn't find specific legal advice for your query. Try browsing the Legal Advice section or consult with a legal professional."
        }

        val topResult = results.first()
        return """
            **${topResult.title}**
            
            ${topResult.description}
            
            **Solution:**
            ${topResult.solution}
            
            **Key Tips:**
            ${topResult.tips.take(3).joinToString("\n") { "• $it" }}
            
            **Relevant Laws:**
            ${topResult.relevantLaws.take(2).joinToString("\n") { "• $it" }}
            
            View the Legal Advice section for detailed information. For serious matters, please consult a lawyer.
        """.trimIndent()
    }

    private fun searchEducationKnowledgeBase(query: String): String {
        val results = KnowledgeBase.searchEducation(query)
        if (results.isEmpty()) {
            return "I couldn't find specific education guidance for your query. Try browsing the Education section or be more specific about the course or exam you're interested in."
        }

        val topResult = results.first()
        return """
            **${topResult.title}**
            
            ${topResult.description}
            
            **Eligibility:** ${topResult.eligibility}
            **Duration:** ${topResult.duration}
            **Average Cost:** ${topResult.averageCost}
            
            **Career Prospects:**
            ${topResult.careerProspects.take(3).joinToString("\n") { "• $it" }}
            
            **Top Entrance Exams:**
            ${topResult.entranceExams.take(3).joinToString("\n") { "• $it" }}
            
            View the Education section for complete guidance and scholarship information.
        """.trimIndent()
    }

    private fun searchAllKnowledgeBase(query: String): String {
        val loanResults = KnowledgeBase.searchLoans(query)
        val legalResults = KnowledgeBase.searchLegalAdvice(query)
        val eduResults = KnowledgeBase.searchEducation(query)

        val hasResults =
            loanResults.isNotEmpty() || legalResults.isNotEmpty() || eduResults.isNotEmpty()

        if (!hasResults) {
            return """
                I can help you with:
                
                **💰 Finance & Loans**
                • Education loans
                • Personal loans
                • Government schemes (PMAY, Mudra)
                • Scholarships
                
                **⚖️ Legal Advice**
                • Consumer rights
                • Employment law
                • Cyber crime
                • Education disputes
                
                **📚 Education**
                • Course selection
                • Entrance exams
                • Career guidance
                • Skill development
                
                Please ask a specific question or browse the sections above!
            """.trimIndent()
        }

        // Return first relevant result from any category
        return when {
            loanResults.isNotEmpty() -> searchLoansKnowledgeBase(query)
            legalResults.isNotEmpty() -> searchLegalKnowledgeBase(query)
            eduResults.isNotEmpty() -> searchEducationKnowledgeBase(query)
            else -> "Please try rephrasing your question or browse the specific sections."
        }
    }

    // ========== Category and Filter Management ==========

    fun setCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun updateLoanFilter(filter: LoanFilter) {
        _loanFilter.value = filter
        applyLoanFilter()
    }

    private fun applyLoanFilter() {
        val filter = _loanFilter.value
        var results = KnowledgeBase.loans

        if (filter.types.isNotEmpty()) {
            results = results.filter { it.type in filter.types }
        }

        // Sort
        results = when (filter.sortBy) {
            LoanSortOption.INTEREST_RATE -> results.sortedBy {
                it.interestRate.filter { c -> c.isDigit() || c == '.' }.take(4).toFloatOrNull()
                    ?: 100f
            }

            LoanSortOption.RATING -> results.sortedByDescending { it.rating }
            LoanSortOption.NAME -> results.sortedBy { it.name }
            else -> results
        }

        _filteredLoans.value = results
    }

    fun updateLegalFilter(filter: LegalFilter) {
        _legalFilter.value = filter
        applyLegalFilter()
    }

    private fun applyLegalFilter() {
        val filter = _legalFilter.value
        var results = KnowledgeBase.legalAdvice

        if (filter.categories.isNotEmpty()) {
            results = results.filter { it.category in filter.categories }
        }

        _filteredLegalAdvice.value = results
    }

    fun updateEducationFilter(filter: EducationFilter) {
        _educationFilter.value = filter
        applyEducationFilter()
    }

    private fun applyEducationFilter() {
        val filter = _educationFilter.value
        var results = KnowledgeBase.educationGuidance

        if (filter.types.isNotEmpty()) {
            results = results.filter { it.type in filter.types }
        }

        if (filter.withScholarships) {
            results = results.filter { it.scholarshipsAvailable.isNotEmpty() }
        }

        _filteredEducation.value = results
    }

    // ========== User Preferences ==========

    fun updateLanguage(language: Language) {
        _userPreferences.value = _userPreferences.value.copy(language = language)
        addWelcomeMessage()
    }

    fun updateTheme(theme: ThemePreference) {
        _userPreferences.value = _userPreferences.value.copy(theme = theme)
    }

    fun toggleDarkMode() {
        _userPreferences.value = _userPreferences.value.copy(
            darkMode = !_userPreferences.value.darkMode
        )
    }

    fun toggleVoice() {
        _userPreferences.value = _userPreferences.value.copy(
            voiceEnabled = !_userPreferences.value.voiceEnabled
        )
    }

    // ========== Document Scanning ==========

    fun addScannedDocument(document: ScannedDocument) {
        _scannedDocuments.value += document

        // Add a message about the scanned document
        val simplificationMessage = """
            📄 **Document Scanned: ${document.type.name.replace("_", " ")}**
            
            **Simplified Explanation:**
            ${document.simplified}
            
            **Key Points:**
            ${document.keyPoints.joinToString("\n") { "• $it" }}
            
            ${
            if (document.warnings.isNotEmpty()) "\n⚠️ **Important Warnings:**\n${
                document.warnings.joinToString(
                    "\n"
                ) { "• $it" }
            }" else ""
        }
        """.trimIndent()

        _messages.value += ChatMessage(
            text = simplificationMessage,
            isUser = false,
            language = _userPreferences.value.language
        )
    }

    // ========== Voice Input ==========

    fun startListening() {
        _isListening.value = true
    }

    fun stopListening() {
        _isListening.value = false
    }

    fun onVoiceInput(text: String) {
        sendMessage(text)
    }

    // ========== Utility ==========

    fun clearChat() {
        _messages.value = emptyList()
        addWelcomeMessage()
    }

    fun getLoanById(id: String): LoanInfo? {
        return KnowledgeBase.loans.find { it.id == id }
    }

    fun getLegalAdviceById(id: String): LegalAdvice? {
        return KnowledgeBase.legalAdvice.find { it.id == id }
    }

    fun getEducationGuidanceById(id: String): EducationGuidance? {
        return KnowledgeBase.educationGuidance.find { it.id == id }
    }
}
