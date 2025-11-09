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
                // Build context-aware prompt with conversation history
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

        // Analyze the query intent
        val queryIntent = analyzeQueryIntent(userQuery, category)
        
        // Get relevant context from knowledge base
        val contextInfo = buildRelevantContext(userQuery, category)
        
        // Get conversation history for context
        val conversationHistory = getRecentConversationHistory()

        // Build dynamic system prompt based on query intent
        val systemPrompt = buildSystemPrompt(category, queryIntent)
        
        // Build specific instructions based on intent
        val responseInstructions = buildResponseInstructions(queryIntent, userQuery)

        // Assemble the final prompt
        val promptParts = mutableListOf<String>()

        // System role
        promptParts.add("ROLE: $systemPrompt")

        // Language instruction
        if (languageInstruction.isNotEmpty()) {
            promptParts.add("\nLANGUAGE: $languageInstruction")
        }

        // Conversation history
        if (conversationHistory.isNotEmpty()) {
            promptParts.add("\nCONVERSATION HISTORY:\n$conversationHistory")
        }

        // Knowledge base context
        promptParts.add("\n$contextInfo")

        // Response instructions
        promptParts.add("\n$responseInstructions")

        // User query
        promptParts.add("\nUSER QUESTION: $userQuery")

        // Final instructions
        promptParts.add(
            """

IMPORTANT GUIDELINES:
- Answer the user's question directly and specifically
- Use actual names, numbers, and details from the knowledge base
- Keep your response concise yet comprehensive (aim for 150-300 words)
- Be conversational and encouraging
- If you mention schemes/programs, include key details like amounts, eligibility, timelines
- End with a helpful follow-up question or offer to help further

Now provide your response:""".trimIndent()
        )

        return promptParts.joinToString("\n")
    }

    private fun buildRelevantContext(query: String, category: Category): String {
        val lowerQuery = query.lowercase()
        val contextParts = mutableListOf<String>()

        when (category) {
            Category.FINANCE -> {
                val relevantLoans = KnowledgeBase.searchLoans(lowerQuery).take(3)
                if (relevantLoans.isNotEmpty()) {
                    contextParts.add("=== Relevant Loan Schemes ===")
                    relevantLoans.forEachIndexed { index, loan ->
                        contextParts.add("""
${index + 1}. ${loan.name} (${loan.provider})
   - Type: ${loan.type.name.replace("_", " ")}
   - Interest: ${loan.interestRate}
   - Amount: ${loan.minAmount} - ${loan.maxAmount}
   - Tenure: ${loan.tenure}
   - Eligibility: ${loan.eligibility}
   - Key Benefit: ${loan.benefits.firstOrNull() ?: loan.description}
   - Collateral: ${loan.collateral}
                        """.trimIndent())
                    }
                }
            }

            Category.LEGAL -> {
                val relevantAdvice = KnowledgeBase.searchLegalAdvice(lowerQuery).take(2)
                if (relevantAdvice.isNotEmpty()) {
                    contextParts.add("=== Relevant Legal Information ===")
                    relevantAdvice.forEachIndexed { index, advice ->
                        contextParts.add("""
${index + 1}. ${advice.title} (${advice.category.name.replace("_", " ")})
   - Scenario: ${advice.scenario}
   - Solution Summary: ${advice.solution.take(200)}${if (advice.solution.length > 200) "..." else ""}
   - Relevant Laws: ${advice.relevantLaws.take(2).joinToString(", ")}
   - Timeline: ${advice.timelineExpectation}
   - Estimated Cost: ${advice.estimatedCost}
                        """.trimIndent())
                    }
                }
            }

            Category.EDUCATION -> {
                val relevantEdu = KnowledgeBase.searchEducation(lowerQuery).take(2)
                val relevantScholarships = KnowledgeBase.scholarships.filter { scholarship ->
                    scholarship.name.contains(lowerQuery, ignoreCase = true) ||
                    scholarship.eligibility.contains(lowerQuery, ignoreCase = true)
                }.take(2)
                
                if (relevantEdu.isNotEmpty()) {
                    contextParts.add("=== Relevant Education Paths ===")
                    relevantEdu.forEachIndexed { index, edu ->
                        contextParts.add("""
${index + 1}. ${edu.title}
   - Duration: ${edu.duration}
   - Average Cost: ${edu.averageCost}
   - Eligibility: ${edu.eligibility}
   - Top Entrance Exams: ${edu.entranceExams.take(2).joinToString(", ")}
   - Career Prospects: ${edu.careerProspects.take(2).joinToString(", ")}
   - Top Institutions: ${edu.topInstitutions.take(3).joinToString(", ")}
                        """.trimIndent())
                    }
                }
                
                if (relevantScholarships.isNotEmpty()) {
                    contextParts.add("\n=== Available Scholarships ===")
                    relevantScholarships.forEachIndexed { index, scholarship ->
                        contextParts.add("""
${index + 1}. ${scholarship.name}
   - Amount: ${scholarship.amount}
   - Eligibility: ${scholarship.eligibility}
   - Deadline: ${scholarship.deadline}
                        """.trimIndent())
                    }
                }
            }

            else -> {
                // Search all categories for general queries
                val loans = KnowledgeBase.searchLoans(lowerQuery).take(2)
                val legal = KnowledgeBase.searchLegalAdvice(lowerQuery).take(1)
                val edu = KnowledgeBase.searchEducation(lowerQuery).take(1)

                if (loans.isNotEmpty()) {
                    contextParts.add("=== Finance Options ===")
                    loans.forEach { loan ->
                        contextParts.add("- ${loan.name}: ${loan.interestRate} interest, ${loan.minAmount}-${loan.maxAmount}")
                    }
                }
                if (legal.isNotEmpty()) {
                    contextParts.add("\n=== Legal Guidance ===")
                    legal.forEach { advice ->
                        contextParts.add("- ${advice.title}: ${advice.solution.take(150)}...")
                    }
                }
                if (edu.isNotEmpty()) {
                    contextParts.add("\n=== Education Info ===")
                    edu.forEach { education ->
                        contextParts.add("- ${education.title}: ${education.description}")
                    }
                }
            }
        }

        return if (contextParts.isNotEmpty()) {
            "Knowledge Base Information:\n${contextParts.joinToString("\n\n")}"
        } else {
            "No specific matching information found in knowledge base. Use your general knowledge to help the user."
        }
    }

    private fun searchKnowledgeBase(query: String, category: Category): String {
        val lowerQuery = query.lowercase()

        return when (category) {
            Category.FINANCE -> searchLoansKnowledgeBase(query)
            Category.LEGAL -> searchLegalKnowledgeBase(query)
            Category.EDUCATION -> searchEducationKnowledgeBase(query)
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

    private fun getRecentConversationHistory(): String {
        // Get last 4 messages (excluding welcome message and current query) for context
        val recentMessages = _messages.value
            .filter { it.text.length < 500 } // Exclude very long messages
            .takeLast(5) // Get last 5 messages
            .dropLast(1) // Exclude the current user message that was just added
            .take(4) // Keep max 4 messages for context
        
        if (recentMessages.isEmpty()) return ""
        
        return recentMessages.joinToString("\n") { msg ->
            if (msg.isUser) "User: ${msg.text}" else "Assistant: ${msg.text.take(200)}${if (msg.text.length > 200) "..." else ""}"
        }
    }

    private fun analyzeQueryIntent(query: String, category: Category): QueryIntent {
        val lowerQuery = query.lowercase()
        
        // Detect specific intents based on keywords
        return when {
            // Comparison queries
            lowerQuery.contains("vs") || lowerQuery.contains("versus") || 
            lowerQuery.contains("compare") || lowerQuery.contains("difference between") ||
            lowerQuery.contains("better") || lowerQuery.contains("which is best") -> QueryIntent.COMPARISON
            
            // How-to queries
            lowerQuery.startsWith("how to") || lowerQuery.startsWith("how can") || 
            lowerQuery.startsWith("how do") || lowerQuery.contains("kaise") ||
            lowerQuery.contains("process") || lowerQuery.contains("steps") -> QueryIntent.HOW_TO
            
            // What/Which queries - information seeking
            lowerQuery.startsWith("what") || lowerQuery.startsWith("which") ||
            lowerQuery.contains("kya hai") || lowerQuery.contains("kaun sa") ||
            lowerQuery.contains("tell me about") || lowerQuery.contains("explain") -> QueryIntent.INFORMATION
            
            // Problem/Issue queries
            lowerQuery.contains("problem") || lowerQuery.contains("issue") ||
            lowerQuery.contains("not working") || lowerQuery.contains("help") ||
            lowerQuery.contains("what should i do") || lowerQuery.contains("what to do") -> QueryIntent.PROBLEM_SOLVING
            
            // Eligibility queries
            lowerQuery.contains("eligible") || lowerQuery.contains("can i") ||
            lowerQuery.contains("qualify") || lowerQuery.contains("am i") -> QueryIntent.ELIGIBILITY
            
            // Cost/Budget queries
            lowerQuery.contains("cost") || lowerQuery.contains("fees") ||
            lowerQuery.contains("price") || lowerQuery.contains("budget") ||
            lowerQuery.contains("afford") || lowerQuery.contains("expensive") -> QueryIntent.COST
            
            // Recommendation queries
            lowerQuery.contains("recommend") || lowerQuery.contains("suggest") ||
            lowerQuery.contains("best") || lowerQuery.contains("top") ||
            lowerQuery.contains("should i") -> QueryIntent.RECOMMENDATION
            
            // Document/Requirements queries
            lowerQuery.contains("documents") || lowerQuery.contains("required") ||
            lowerQuery.contains("need") || lowerQuery.contains("requirements") -> QueryIntent.REQUIREMENTS
            
            // Timeline queries
            lowerQuery.contains("when") || lowerQuery.contains("how long") ||
            lowerQuery.contains("duration") || lowerQuery.contains("time") -> QueryIntent.TIMELINE
            
            else -> QueryIntent.GENERAL
        }
    }

    private fun buildSystemPrompt(category: Category, queryIntent: QueryIntent): String {
        val basePrompt = when (category) {
            Category.FINANCE -> """You are an expert financial advisor specializing in Indian banking, loans, and government schemes. You have deep knowledge of education loans, personal finance, government schemes like PMAY and Mudra Yojana, and financial planning for young Indians."""
            
            Category.LEGAL -> """You are a knowledgeable legal advisor with expertise in Indian law, particularly consumer rights, employment law, cyber crime, and legal procedures. You provide clear, practical legal guidance while emphasizing when professional legal consultation is necessary."""
            
            Category.EDUCATION -> """You are an experienced education counselor and career advisor with comprehensive knowledge of Indian education systems, entrance exams, scholarships, career paths, and skill development programs. You inspire and guide students towards making informed decisions."""
            
            else -> """You are Finvaria, an intelligent assistant helping Indian youth navigate finance, legal matters, and education. You provide accurate, practical, and empowering guidance."""
        }
        
        // Add intent-specific guidance to system prompt
        val intentGuidance = when (queryIntent) {
            QueryIntent.COMPARISON -> " Focus on providing clear, objective comparisons with specific details."
            QueryIntent.HOW_TO -> " Provide step-by-step instructions that are easy to follow."
            QueryIntent.PROBLEM_SOLVING -> " Understand the user's problem and provide practical solutions."
            QueryIntent.RECOMMENDATION -> " Give personalized recommendations based on the user's situation."
            else -> ""
        }
        
        return basePrompt + intentGuidance
    }

    private fun buildResponseInstructions(queryIntent: QueryIntent, userQuery: String): String {
        return when (queryIntent) {
            QueryIntent.COMPARISON -> """
Instructions for your response:
- Create a clear comparison between the options mentioned
- Use bullet points or a structured format
- Highlight key differences in: interest rates, eligibility, amounts, benefits
- End with a recommendation based on common scenarios
- Be objective and balanced
            """.trimIndent()
            
            QueryIntent.HOW_TO -> """
Instructions for your response:
- Provide a numbered step-by-step guide
- Start with prerequisites if any
- Include estimated timelines for each step
- Mention required documents or resources
- Add tips to avoid common mistakes
- Keep steps simple and actionable
            """.trimIndent()
            
            QueryIntent.INFORMATION -> """
Instructions for your response:
- Start with a brief, clear definition or overview
- Provide key details: eligibility, requirements, benefits
- Include specific numbers (amounts, percentages, timelines)
- Add practical examples if relevant
- Mention where to find more information
- Keep it structured and easy to scan
            """.trimIndent()
            
            QueryIntent.PROBLEM_SOLVING -> """
Instructions for your response:
- Acknowledge the problem empathetically
- Provide immediate actionable steps
- Prioritize solutions (what to do first, second, etc.)
- Mention relevant laws, rights, or schemes that can help
- Include contact information for relevant authorities
- Warn about time-sensitive actions if any
            """.trimIndent()
            
            QueryIntent.ELIGIBILITY -> """
Instructions for your response:
- Start by directly answering if they're likely eligible (Yes/No/Maybe)
- List specific eligibility criteria as bullet points
- Explain each criterion clearly
- Mention any age, income, or education requirements
- Suggest alternatives if they might not qualify
- Tell them how to verify their eligibility officially
            """.trimIndent()
            
            QueryIntent.COST -> """
Instructions for your response:
- Start with the cost range or specific amount
- Break down costs: tuition/fees, processing, hidden costs
- Mention any discounts, subsidies, or waivers available
- Compare with alternatives if relevant
- Suggest scholarship or financial aid options
- Include payment timeline information
            """.trimIndent()
            
            QueryIntent.RECOMMENDATION -> """
Instructions for your response:
- Provide 2-3 specific recommendations
- For each recommendation, explain WHY it's suitable
- Include key details: rates, amounts, eligibility, benefits
- Rank them if possible (Best, Good, Alternative)
- Consider the user's perspective (student, young professional, etc.)
- End with next steps to pursue the recommendation
            """.trimIndent()
            
            QueryIntent.REQUIREMENTS -> """
Instructions for your response:
- List all required documents as bullet points
- For each document, briefly explain its purpose
- Mention where to obtain each document
- Note any specific formats or validity requirements
- Highlight critical documents vs optional ones
- Include typical processing time if relevant
            """.trimIndent()
            
            QueryIntent.TIMELINE -> """
Instructions for your response:
- Provide specific timeframes (days, weeks, months)
- Break timeline into phases if it's a multi-step process
- Mention deadlines if any are critical
- Note factors that might extend or shorten the timeline
- Add tips to speed up the process
- Include when to start for time-sensitive matters
            """.trimIndent()
            
            QueryIntent.GENERAL -> """
Instructions for your response:
- Understand what the user is really asking
- Provide a helpful, conversational response
- Be specific with names, numbers, and details
- Offer to help with related questions
- Keep the tone friendly and encouraging
            """.trimIndent()
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

enum class QueryIntent {
    COMPARISON,       // "X vs Y", "difference between", "which is better"
    HOW_TO,          // "how to", "steps to", "process"
    INFORMATION,     // "what is", "tell me about", "explain"
    PROBLEM_SOLVING, // "I have a problem", "what should I do", "help"
    ELIGIBILITY,     // "am I eligible", "can I apply", "qualify"
    COST,            // "how much", "fees", "price", "budget"
    RECOMMENDATION,  // "suggest", "recommend", "best option"
    REQUIREMENTS,    // "documents needed", "requirements"
    TIMELINE,        // "how long", "when", "deadline"
    GENERAL          // Default/other queries
}
