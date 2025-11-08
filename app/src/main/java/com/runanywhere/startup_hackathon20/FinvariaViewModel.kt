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

class FinvariaViewModel : ViewModel() {

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
            Language.HINDI -> """नमस्ते! मैं फिनवेरिया हूं, आपका AI सहायक। 🎯

मैं आपकी मदद कर सकता हूं:
• 💰 शिक्षा ऋण, व्यक्तिगत ऋण, सरकारी योजनाएं
• ⚖️ उपभोक्ता अधिकार, रोजगार कानून, साइबर अपराध
• 📚 पाठ्यक्रम चयन, प्रवेश परीक्षा, छात्रवृत्ति

कुछ पूछें जैसे:
- "शिक्षा ऋण के लिए कौन सी योजनाएं हैं?"
- "मेरा वेतन नहीं मिला, मैं क्या करूं?"
- "JEE की तैयारी कैसे करें?"

बस अपना सवाल टाइप करें! 😊"""

            Language.HINGLISH -> """Namaste! Main Finvaria hun, aapka AI assistant। 🎯

Main aapki madad kar sakta hun:
• 💰 Education loans, personal loans, government schemes
• ⚖️ Consumer rights, employment law, cyber crime
• 📚 Course selection, entrance exams, scholarships

Kuch poochiye jaise:
- "Education loan ke liye kaun si schemes hain?"
- "Mera salary nahi mila, main kya karun?"
- "JEE ki taiyaari kaise karein?"

Bas apna question type karein! 😊"""

            else -> """Welcome to Finvaria! I'm your AI assistant for youth empowerment. 🎯

I can help you with:
• 💰 Education loans, personal loans, government schemes
• ⚖️ Consumer rights, employment law, cyber crime
• 📚 Course selection, entrance exams, scholarships

Try asking:
- "What education loans are available for engineering?"
- "My employer hasn't paid my salary, what should I do?"
- "How do I prepare for NEET exam?"

Just type your question below! 😊"""
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
            Language.HINDI -> "कृपया हिन्दी में जवाब दें।"
            Language.HINGLISH -> "Please reply in Hinglish (Roman Hindi with English words)."
            else -> ""
        }

        // Get relevant context from knowledge base
        val contextInfo = buildRelevantContext(userQuery, category)

        val categoryContext = when (category) {
            Category.FINANCE -> """You are an expert financial advisor specializing in Indian banking, loans, and government schemes. You have deep knowledge of education loans, personal finance, government schemes like PMAY and Mudra Yojana, and financial planning for young Indians."""

            Category.LEGAL -> """You are a knowledgeable legal advisor with expertise in Indian law, particularly consumer rights, employment law, cyber crime, and legal procedures. You provide clear, practical legal guidance while emphasizing when professional legal consultation is necessary."""

            Category.EDUCATION -> """You are an experienced education counselor and career advisor with comprehensive knowledge of Indian education systems, entrance exams, scholarships, career paths, and skill development programs. You inspire and guide students towards making informed decisions."""

            else -> """You are Finvaria, an intelligent assistant helping Indian youth navigate finance, legal matters, and education. You provide accurate, practical, and empowering guidance."""
        }

        return """$categoryContext

${if (languageInstruction.isNotEmpty()) "$languageInstruction\n" else ""}
$contextInfo

User: $userQuery

Provide a natural, conversational, and helpful response. Be specific and actionable. If you reference schemes, loans, or programs, mention actual names and key details. Keep your response concise but comprehensive.""".trimIndent()
    }

    private fun buildRelevantContext(query: String, category: Category): String {
        val lowerQuery = query.lowercase()
        val contextParts = mutableListOf<String>()

        when (category) {
            Category.FINANCE -> {
                val relevantLoans = KnowledgeBase.searchLoans(lowerQuery).take(2)
                if (relevantLoans.isNotEmpty()) {
                    val loanInfo = relevantLoans.joinToString("\n") { loan ->
                        "- ${loan.name} by ${loan.provider}: ${loan.interestRate} interest, ${loan.minAmount}-${loan.maxAmount}, ${loan.tenure} tenure"
                    }
                    contextParts.add("Relevant loan schemes:\n$loanInfo")
                }
            }

            Category.LEGAL -> {
                val relevantAdvice = KnowledgeBase.searchLegalAdvice(lowerQuery).take(2)
                if (relevantAdvice.isNotEmpty()) {
                    val adviceInfo = relevantAdvice.joinToString("\n") { advice ->
                        "- ${advice.title}: ${advice.solution.take(150)}..."
                    }
                    contextParts.add("Relevant legal guidance:\n$adviceInfo")
                }
            }

            Category.EDUCATION -> {
                val relevantEdu = KnowledgeBase.searchEducation(lowerQuery).take(2)
                if (relevantEdu.isNotEmpty()) {
                    val eduInfo = relevantEdu.joinToString("\n") { edu ->
                        "- ${edu.title}: ${edu.description}"
                    }
                    contextParts.add("Relevant education paths:\n$eduInfo")
                }
            }

            else -> {
                // Search all categories
                val loans = KnowledgeBase.searchLoans(lowerQuery).take(1)
                val legal = KnowledgeBase.searchLegalAdvice(lowerQuery).take(1)
                val edu = KnowledgeBase.searchEducation(lowerQuery).take(1)

                if (loans.isNotEmpty()) {
                    contextParts.add("Finance: ${loans[0].name} - ${loans[0].description}")
                }
                if (legal.isNotEmpty()) {
                    contextParts.add("Legal: ${legal[0].title} - ${legal[0].description}")
                }
                if (edu.isNotEmpty()) {
                    contextParts.add("Education: ${edu[0].title} - ${edu[0].description}")
                }
            }
        }

        return if (contextParts.isNotEmpty()) {
            "Context from knowledge base:\n${contextParts.joinToString("\n\n")}"
        } else {
            ""
        }
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
            return "I couldn't find specific loan schemes matching your query. Could you be more specific? For example, are you looking for education loans, personal loans, home loans, or business loans? Feel free to browse the Loan Advisor section to see all available options!"
        }

        val topResult = results.first()
        return """Based on your query, I found this relevant loan scheme:

**${topResult.name}** by ${topResult.provider}

${topResult.description}

**Key Details:**
• Interest Rate: ${topResult.interestRate}
• Loan Amount: ${topResult.minAmount} to ${topResult.maxAmount}
• Repayment Period: ${topResult.tenure}
• Processing Time: ${topResult.processingTime}

**Eligibility:** ${topResult.eligibility}

**Top Benefits:**
${topResult.benefits.take(3).joinToString("\n") { "✓ $it" }}

${if (topResult.website.isNotEmpty()) "\nYou can learn more and apply at: ${topResult.website}\n" else ""}
Would you like to know more about the application process or explore other loan options?""".trimIndent()
    }

    private fun searchLegalKnowledgeBase(query: String): String {
        val results = KnowledgeBase.searchLegalAdvice(query)
        if (results.isEmpty()) {
            return """I couldn't find specific legal guidance for your query, but I'm here to help! 

You can try:
• Being more specific about your legal issue
• Browsing the Legal Advice section for common scenarios
• Consulting with a qualified lawyer for personalized advice

Common topics I can help with:
- Consumer rights and defective products
- Employment issues and salary disputes
- Cyber crime and online fraud
- Property matters
- Education-related disputes"""
        }

        val topResult = results.first()
        return """I found relevant legal guidance for your situation:

**${topResult.title}**

**What's happening:** ${topResult.scenario}

**What you can do:** ${topResult.solution}

**Important to know:**
• Timeline: ${topResult.timelineExpectation}
• Estimated Cost: ${topResult.estimatedCost}

**Key Tips:**
${topResult.tips.take(3).joinToString("\n") { "✓ $it" }}

**Relevant Laws:**
${topResult.relevantLaws.take(2).joinToString("\n") { "• $it" }}

${if (topResult.website.isNotEmpty()) "\nFor more information, visit: ${topResult.website}\n" else ""}
⚠️ **Remember:** This is general guidance. For serious legal matters, please consult a qualified lawyer.

Need more details about the documents required or next steps?""".trimIndent()
    }

    private fun searchEducationKnowledgeBase(query: String): String {
        val results = KnowledgeBase.searchEducation(query)
        if (results.isEmpty()) {
            return """I couldn't find specific information about that education path. Let me help you explore! 🎓

Try asking about:
• Engineering (B.Tech/B.E.) - IIT, NIT, or other colleges
• Medical (MBBS, BDS) - NEET preparation
• Skill Development programs
• Online courses and certifications
• Scholarships and financial aid

Or browse the Education section to see all available paths and career options!"""
        }

        val topResult = results.first()
        return """Great question! Here's what you need to know about **${topResult.title}**:

**Overview:** ${topResult.description}

**Quick Facts:**
• Duration: ${topResult.duration}
• Average Cost: ${topResult.averageCost}
• Eligibility: ${topResult.eligibility}

**Career Opportunities:**
${topResult.careerProspects.take(3).joinToString("\n") { "💼 $it" }}

**Key Entrance Exams:**
${topResult.entranceExams.take(3).joinToString("\n") { "📝 $it" }}

**Top Institutions:**
${topResult.topInstitutions.take(3).joinToString("\n") { "🏛️ $it" }}

${
            if (topResult.scholarshipsAvailable.isNotEmpty()) "\n**Scholarships Available:**\n${
                topResult.scholarshipsAvailable.take(
                    2
                ).joinToString("\n") { "🎓 $it" }
            }\n" else ""
        }
${if (topResult.website.isNotEmpty()) "\nLearn more at: ${topResult.website}\n" else ""}
Want to know about entrance exam preparation tips or scholarship applications?""".trimIndent()
    }

    private fun searchAllKnowledgeBase(query: String): String {
        val loanResults = KnowledgeBase.searchLoans(query)
        val legalResults = KnowledgeBase.searchLegalAdvice(query)
        val eduResults = KnowledgeBase.searchEducation(query)

        val hasResults =
            loanResults.isNotEmpty() || legalResults.isNotEmpty() || eduResults.isNotEmpty()

        if (!hasResults) {
            return """Hi! I'm here to help you with finance, legal matters, and education guidance. 😊

**💰 Finance & Loans:**
Ask about education loans, personal loans, government schemes like PMAY or Mudra Yojana, or scholarships.

**⚖️ Legal Advice:**
Get guidance on consumer rights, employment law, cyber crime, property matters, or education disputes.

**📚 Education:**
Explore courses, entrance exams (JEE, NEET), career guidance, scholarships, or skill development programs.

**Try asking questions like:**
• "What are the best education loans for engineering?"
• "My online order is defective, what are my rights?"
• "How do I prepare for JEE Main?"
• "What scholarships are available for undergraduate students?"

Go ahead, ask me anything! 🚀"""
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
