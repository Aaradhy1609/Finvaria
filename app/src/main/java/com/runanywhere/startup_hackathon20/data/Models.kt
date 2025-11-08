package com.runanywhere.startup_hackathon20.data

import androidx.compose.ui.graphics.vector.ImageVector

// Main Categories
enum class Category {
    FINANCE, LEGAL, EDUCATION, ALL
}

// Language Support
enum class Language(val displayName: String, val code: String) {
    ENGLISH("English", "en"),
    HINDI("हिन्दी", "hi"),
    HINGLISH("Hinglish", "hi-en"),
    TAMIL("தமிழ்", "ta"),
    TELUGU("తెలుగు", "te"),
    BENGALI("বাংলা", "bn"),
    MARATHI("मराठी", "mr"),
    GUJARATI("ગુજરાતી", "gu"),
    KANNADA("ಕನ್ನಡ", "kn"),
    MALAYALAM("മലയാളം", "ml"),
    PUNJABI("ਪੰਜਾਬੀ", "pa")
}

// Chat Message
data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val language: Language = Language.ENGLISH,
    val category: Category? = null
)

// Loan Types
enum class LoanType {
    EDUCATION,
    PERSONAL,
    HOME,
    BUSINESS,
    VEHICLE,
    AGRICULTURE,
    GOLD,
    MICRO_CREDIT
}

// Loan Data
data class LoanInfo(
    val id: String,
    val name: String,
    val type: LoanType,
    val provider: String,
    val interestRate: String,
    val minAmount: String,
    val maxAmount: String,
    val tenure: String,
    val eligibility: String,
    val requiredDocuments: List<String>,
    val processingTime: String,
    val collateral: String,
    val benefits: List<String>,
    val description: String,
    val applicationProcess: String,
    val rating: Float = 0f,
    val website: String = ""
)

// Legal Advisory Categories
enum class LegalCategory {
    CONSUMER_RIGHTS,
    EMPLOYMENT,
    PROPERTY,
    FAMILY,
    CRIMINAL,
    TAXATION,
    BUSINESS,
    EDUCATION_RELATED,
    CYBER_CRIME,
    TENANT_RIGHTS
}

// Legal Advisory Data
data class LegalAdvice(
    val id: String,
    val category: LegalCategory,
    val title: String,
    val description: String,
    val scenario: String,
    val solution: String,
    val relevantLaws: List<String>,
    val requiredDocuments: List<String>,
    val timelineExpectation: String,
    val estimatedCost: String,
    val commonMistakes: List<String>,
    val tips: List<String>,
    val website: String = ""
)

// Education Types
enum class EducationType {
    SCHOOL,
    UNDERGRADUATE,
    POSTGRADUATE,
    VOCATIONAL,
    ONLINE_COURSES,
    COMPETITIVE_EXAMS,
    SCHOLARSHIPS,
    SKILL_DEVELOPMENT,
    STUDY_ABROAD
}

// Education Guidance
data class EducationGuidance(
    val id: String,
    val type: EducationType,
    val title: String,
    val description: String,
    val eligibility: String,
    val duration: String,
    val averageCost: String,
    val scholarshipsAvailable: List<String>,
    val topInstitutions: List<String>,
    val careerProspects: List<String>,
    val requiredSkills: List<String>,
    val entranceExams: List<String>,
    val recommendedFor: String,
    val tips: List<String>,
    val website: String = ""
)

// Scholarship Data
data class Scholarship(
    val id: String,
    val name: String,
    val provider: String,
    val amount: String,
    val eligibility: String,
    val applicableFor: List<EducationType>,
    val deadline: String,
    val applicationProcess: String,
    val requiredDocuments: List<String>,
    val selectionCriteria: String,
    val benefits: List<String>,
    val website: String
)

// Document Type for Scanning
enum class DocumentType {
    AADHAAR,
    PAN_CARD,
    PASSPORT,
    DRIVING_LICENSE,
    VOTER_ID,
    BANK_STATEMENT,
    SALARY_SLIP,
    PROPERTY_DOCUMENT,
    EDUCATIONAL_CERTIFICATE,
    LEGAL_NOTICE,
    CONTRACT,
    OTHER
}

// Scanned Document
data class ScannedDocument(
    val id: String,
    val type: DocumentType,
    val text: String,
    val timestamp: Long,
    val simplified: String,
    val keyPoints: List<String>,
    val warnings: List<String>
)

// User Preferences
data class UserPreferences(
    val language: Language = Language.ENGLISH,
    val theme: ThemePreference = ThemePreference.BLUE,
    val darkMode: Boolean = false,
    val voiceEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true
)

// Theme Preference
enum class ThemePreference {
    BLUE, PURPLE, GREEN, SYSTEM
}

// Filter Options for Loans
data class LoanFilter(
    val types: Set<LoanType> = emptySet(),
    val minInterestRate: Float = 0f,
    val maxInterestRate: Float = 100f,
    val minAmount: Long = 0,
    val maxAmount: Long = 100000000,
    val maxTenure: Int = 30,
    val requiresCollateral: Boolean? = null,
    val sortBy: LoanSortOption = LoanSortOption.INTEREST_RATE
)

enum class LoanSortOption {
    INTEREST_RATE,
    AMOUNT,
    TENURE,
    RATING,
    NAME
}

// Filter Options for Legal Advice
data class LegalFilter(
    val categories: Set<LegalCategory> = emptySet(),
    val maxCost: Long = 1000000,
    val urgency: Urgency = Urgency.NORMAL
)

enum class Urgency {
    LOW, NORMAL, HIGH, CRITICAL
}

// Filter Options for Education
data class EducationFilter(
    val types: Set<EducationType> = emptySet(),
    val maxCost: Long = 10000000,
    val duration: String? = null,
    val withScholarships: Boolean = false
)
