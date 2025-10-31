package com.runanywhere.startup_hackathon20.data

object KnowledgeBase {

    // Loan Database
    val loans = listOf(
        // Education Loans
        LoanInfo(
            id = "edu_sbi_scholar",
            name = "SBI Student Loan Scheme",
            type = LoanType.EDUCATION,
            provider = "State Bank of India",
            interestRate = "7.00% - 9.05% p.a.",
            minAmount = "₹20,000",
            maxAmount = "₹1.5 Crore",
            tenure = "Up to 15 years",
            eligibility = "Indian nationals admitted to approved institutions",
            requiredDocuments = listOf(
                "Admission letter",
                "Academic records",
                "ID proof",
                "Address proof",
                "Income proof of parents/guardian",
                "Cost estimate from institution"
            ),
            processingTime = "7-15 days",
            collateral = "Required for loans above ₹7.5 lakhs",
            benefits = listOf(
                "Interest subsidy for economically weaker sections",
                "Moratorium period during study + 1 year",
                "Tax benefits under Section 80E",
                "Covers tuition fees, books, equipment, and living expenses"
            ),
            description = "Comprehensive education loan for studies in India and abroad",
            applicationProcess = "Visit SBI branch or apply online through SBI website",
            rating = 4.5f
        ),
        LoanInfo(
            id = "edu_vidya_lakshmi",
            name = "Vidyalakshmi Education Loan",
            type = LoanType.EDUCATION,
            provider = "Government of India Portal",
            interestRate = "8.5% - 11.5% p.a.",
            minAmount = "₹50,000",
            maxAmount = "₹20 Lakhs",
            tenure = "Up to 10 years",
            eligibility = "Indian students pursuing higher education",
            requiredDocuments = listOf(
                "Aadhaar card",
                "PAN card",
                "Admission proof",
                "Mark sheets",
                "Income certificate"
            ),
            processingTime = "10-20 days",
            collateral = "Not required for loans up to ₹7.5 lakhs",
            benefits = listOf(
                "Single portal for multiple banks",
                "Government scholarship integration",
                "Interest rate subsidy schemes",
                "Digital application process"
            ),
            description = "One-stop portal for education loans from multiple banks",
            applicationProcess = "Register on vidyalakshmi.co.in and apply to multiple banks",
            rating = 4.2f
        ),
        LoanInfo(
            id = "edu_central_govt",
            name = "Central Sector Interest Subsidy Scheme",
            type = LoanType.EDUCATION,
            provider = "Ministry of Education, Govt of India",
            interestRate = "0% during study period",
            minAmount = "₹10,000",
            maxAmount = "₹10 Lakhs",
            tenure = "Study period + moratorium",
            eligibility = "Students from families with annual income below ₹4.5 lakhs",
            requiredDocuments = listOf(
                "Income certificate",
                "Admission letter",
                "Bank loan sanction letter",
                "Caste certificate (if applicable)"
            ),
            processingTime = "15-30 days",
            collateral = "As per bank's requirement",
            benefits = listOf(
                "Government pays interest during study period",
                "Available for technical and professional courses",
                "Reduces overall loan burden",
                "One-year moratorium after course completion"
            ),
            description = "Interest subsidy scheme for economically weaker students",
            applicationProcess = "Apply through National Scholarship Portal",
            rating = 4.7f
        ),

        // Personal Loans
        LoanInfo(
            id = "per_hdfc_instant",
            name = "HDFC Personal Loan",
            type = LoanType.PERSONAL,
            provider = "HDFC Bank",
            interestRate = "10.50% - 21.00% p.a.",
            minAmount = "₹50,000",
            maxAmount = "₹40 Lakhs",
            tenure = "12 to 60 months",
            eligibility = "Salaried/Self-employed, Age 21-60 years",
            requiredDocuments = listOf(
                "Identity proof",
                "Address proof",
                "Income proof (salary slips/ITR)",
                "Bank statements (6 months)"
            ),
            processingTime = "Instant to 48 hours",
            collateral = "Not required",
            benefits = listOf(
                "Quick disbursal",
                "Minimal documentation",
                "Flexible repayment",
                "Pre-approved offers for existing customers"
            ),
            description = "Instant personal loan with competitive rates",
            applicationProcess = "Apply online or visit branch",
            rating = 4.3f
        ),

        // Home Loans
        LoanInfo(
            id = "home_pmay",
            name = "Pradhan Mantri Awas Yojana (PMAY)",
            type = LoanType.HOME,
            provider = "Government of India",
            interestRate = "6.50% p.a. onwards",
            minAmount = "₹3 Lakhs",
            maxAmount = "₹12 Lakhs subsidy",
            tenure = "Up to 20 years",
            eligibility = "EWS/LIG/MIG families; first-time home buyers",
            requiredDocuments = listOf(
                "Aadhaar card",
                "Income proof",
                "Property documents",
                "No existing house ownership proof"
            ),
            processingTime = "30-60 days",
            collateral = "Property being purchased",
            benefits = listOf(
                "Interest subsidy up to ₹2.67 lakhs",
                "Lower interest rates",
                "Easy eligibility for lower income groups",
                "Government-backed scheme"
            ),
            description = "Government scheme for affordable housing",
            applicationProcess = "Apply through participating banks and HFCs",
            rating = 4.6f
        ),

        // Business Loans
        LoanInfo(
            id = "biz_mudra",
            name = "Mudra Loan - Shishu/Kishor/Tarun",
            type = LoanType.BUSINESS,
            provider = "Government of India (via Banks/NBFCs)",
            interestRate = "8.00% - 12.00% p.a.",
            minAmount = "₹50,000",
            maxAmount = "₹10 Lakhs",
            tenure = "Up to 5 years",
            eligibility = "Small businesses, traders, service providers",
            requiredDocuments = listOf(
                "Business plan",
                "Identity proof",
                "Business address proof",
                "ITR/Financial statements",
                "Quotation for equipment (if any)"
            ),
            processingTime = "15-30 days",
            collateral = "Not required for loans up to ₹10 lakhs",
            benefits = listOf(
                "No collateral for small amounts",
                "Easy processing",
                "Government support",
                "Available for new and existing businesses"
            ),
            description = "Micro-credit scheme for small business enterprises",
            applicationProcess = "Visit nearest bank or apply online through Mudra portal",
            rating = 4.4f
        ),

        // Vehicle Loans
        LoanInfo(
            id = "veh_car_loan",
            name = "Two Wheeler & Car Loan",
            type = LoanType.VEHICLE,
            provider = "Various Banks & NBFCs",
            interestRate = "8.50% - 14.00% p.a.",
            minAmount = "₹20,000",
            maxAmount = "₹20 Lakhs",
            tenure = "12 to 84 months",
            eligibility = "Age 21-65, Stable income",
            requiredDocuments = listOf(
                "Identity proof",
                "Address proof",
                "Income proof",
                "Bank statements",
                "Vehicle quotation"
            ),
            processingTime = "2-5 days",
            collateral = "Vehicle hypothecation",
            benefits = listOf(
                "Up to 90% of vehicle cost funded",
                "Quick approval",
                "Flexible tenure",
                "Minimal documentation"
            ),
            description = "Loan for purchasing two-wheelers and four-wheelers",
            applicationProcess = "Apply through dealer or bank",
            rating = 4.1f
        )
    )

    // Legal Advice Database
    val legalAdvice = listOf(
        LegalAdvice(
            id = "legal_consumer_001",
            category = LegalCategory.CONSUMER_RIGHTS,
            title = "Defective Product or Service",
            description = "What to do when you receive defective goods or unsatisfactory services",
            scenario = "You purchased a product that is defective or doesn't match the description. The seller refuses to replace or refund.",
            solution = "File a consumer complaint under the Consumer Protection Act, 2019. You can approach District Consumer Forum (for claims up to ₹50 lakhs), State Commission (₹50 lakhs to ₹2 crore), or National Commission (above ₹2 crore).",
            relevantLaws = listOf(
                "Consumer Protection Act, 2019",
                "Sale of Goods Act, 1930",
                "Indian Contract Act, 1872"
            ),
            requiredDocuments = listOf(
                "Purchase receipt/invoice",
                "Product warranty card",
                "Communication with seller",
                "Photos of defective product",
                "Bank transaction statement"
            ),
            timelineExpectation = "3-6 months for District Forum hearing",
            estimatedCost = "Filing fee: ₹200-500. Legal fees: ₹5,000-20,000",
            commonMistakes = listOf(
                "Not keeping purchase receipts",
                "Delaying complaint beyond limitation period",
                "Not attempting resolution with seller first"
            ),
            tips = listOf(
                "Keep all receipts and communications",
                "Send legal notice before filing complaint",
                "Document everything with photos/videos",
                "Consumer courts don't require lawyers"
            )
        ),
        LegalAdvice(
            id = "legal_employment_001",
            category = LegalCategory.EMPLOYMENT,
            title = "Unfair Termination or Non-Payment of Salary",
            description = "Steps to take if your employer terminates you unfairly or doesn't pay salary",
            scenario = "Your employer terminated you without notice or hasn't paid your salary for months.",
            solution = "Send legal notice demanding dues. If unresolved, file complaint with Labour Commissioner or approach Labour Court. For dues, you can also file case under Section 138 of Negotiable Instruments Act if payment by cheque bounced.",
            relevantLaws = listOf(
                "Industrial Disputes Act, 1947",
                "Payment of Wages Act, 1936",
                "Shops and Establishments Act (State-specific)",
                "Negotiable Instruments Act, 1881"
            ),
            requiredDocuments = listOf(
                "Employment contract/offer letter",
                "Salary slips",
                "Bank statements",
                "Termination letter",
                "Email communications",
                "Attendance records"
            ),
            timelineExpectation = "6-12 months for Labour Court proceedings",
            estimatedCost = "Legal notice: ₹5,000-10,000. Court case: ₹20,000-50,000",
            commonMistakes = listOf(
                "Not having written employment contract",
                "Accepting verbal assurances without documentation",
                "Not keeping copies of important emails"
            ),
            tips = listOf(
                "Always get employment terms in writing",
                "Keep copies of all salary slips and documents",
                "Document all verbal conversations via email",
                "Approach Labour Commissioner first (free of cost)"
            )
        ),
        LegalAdvice(
            id = "legal_property_001",
            category = LegalCategory.PROPERTY,
            title = "Property Dispute and Title Verification",
            description = "How to handle property disputes and verify clear title",
            scenario = "You want to buy property or are facing disputes over property ownership.",
            solution = "Get property title verified by lawyer. Check encumbrance certificate for past 30 years. Verify ownership through revenue records. For disputes, file civil suit for declaration of title and permanent injunction.",
            relevantLaws = listOf(
                "Transfer of Property Act, 1882",
                "Indian Registration Act, 1908",
                "Specific Relief Act, 1963",
                "Limitation Act, 1963"
            ),
            requiredDocuments = listOf(
                "Sale deed",
                "Encumbrance certificate",
                "Property tax receipts",
                "Mutation records",
                "Survey/map documents",
                "Previous sale deeds"
            ),
            timelineExpectation = "Civil suits can take 3-10 years",
            estimatedCost = "Title verification: ₹10,000-25,000. Civil suit: ₹50,000-5 lakhs",
            commonMistakes = listOf(
                "Not verifying title before purchase",
                "Relying only on possession without checking documents",
                "Not registering property properly"
            ),
            tips = listOf(
                "Always verify title for 30 years back",
                "Get encumbrance certificate",
                "Check for any pending litigations",
                "Ensure proper registration of documents",
                "Get legal opinion before purchase"
            )
        ),
        LegalAdvice(
            id = "legal_education_001",
            category = LegalCategory.EDUCATION_RELATED,
            title = "Admission Issues and Fee Refund",
            description = "Legal remedies for admission denial or fee refund disputes",
            scenario = "Your admission was cancelled unfairly or college refuses to refund fees after withdrawal.",
            solution = "File complaint with University Grievance Committee first. If unresolved, approach State Education Department or file consumer complaint if fees were charged for services not provided. For serious violations, writ petition in High Court.",
            relevantLaws = listOf(
                "Right to Education Act, 2009",
                "University Grants Commission (UGC) regulations",
                "Consumer Protection Act, 2019",
                "State Education Acts"
            ),
            requiredDocuments = listOf(
                "Admission letter/receipt",
                "Fee payment receipts",
                "Correspondence with institution",
                "College prospectus/brochure",
                "Withdrawal application"
            ),
            timelineExpectation = "Consumer complaint: 3-6 months. Writ petition: 6-12 months",
            estimatedCost = "Consumer complaint: ₹5,000-15,000. Writ petition: ₹25,000-1 lakh",
            commonMistakes = listOf(
                "Not reading fee refund policy carefully",
                "Not keeping receipts of all payments",
                "Missing deadline for grievance filing"
            ),
            tips = listOf(
                "Read refund policy before admission",
                "Keep all payment receipts",
                "Send emails for all communication",
                "Approach UGC for recognized institutions",
                "File consumer complaint if service deficiency"
            )
        ),
        LegalAdvice(
            id = "legal_cyber_001",
            category = LegalCategory.CYBER_CRIME,
            title = "Online Fraud and Cyber Crimes",
            description = "What to do if you're a victim of online fraud or cyber crime",
            scenario = "You lost money to online fraud, phishing, or your social media account was hacked.",
            solution = "Immediately report to www.cybercrime.gov.in. Block your bank cards if financial fraud. File FIR at local police station. Report to bank for transaction reversal. Change all passwords.",
            relevantLaws = listOf(
                "Information Technology Act, 2000",
                "Indian Penal Code Section 420 (Cheating)",
                "Section 66 (Computer-related offenses)",
                "Section 66C (Identity theft)",
                "Section 66D (Cheating by impersonation)"
            ),
            requiredDocuments = listOf(
                "Screenshots of fraud messages/emails",
                "Bank transaction details",
                "Communication records",
                "Account details of fraudster (if available)",
                "FIR copy"
            ),
            timelineExpectation = "FIR immediate. Investigation: 1-6 months. Recovery uncertain.",
            estimatedCost = "Filing complaint: Free. Legal fees if needed: ₹10,000-50,000",
            commonMistakes = listOf(
                "Delaying complaint filing",
                "Not taking screenshots immediately",
                "Sharing OTP or passwords with anyone",
                "Not blocking cards immediately"
            ),
            tips = listOf(
                "Report within 24 hours for better chance of recovery",
                "Never share OTP, PIN, or passwords",
                "Use cybercrime.gov.in portal for complaints",
                "Keep evidence - screenshots, emails, messages",
                "Block cards and change passwords immediately"
            )
        )
    )

    // Education Guidance Database
    val educationGuidance = listOf(
        EducationGuidance(
            id = "edu_engineering",
            type = EducationType.UNDERGRADUATE,
            title = "Engineering (B.Tech/B.E.)",
            description = "Bachelor's degree in Engineering and Technology",
            eligibility = "10+2 with Physics, Chemistry, and Mathematics. Minimum 60% marks.",
            duration = "4 years",
            averageCost = "₹50,000 - ₹15 lakhs per year (varies by institution)",
            scholarshipsAvailable = listOf(
                "Central Sector Scholarship",
                "State government scholarships",
                "College merit scholarships",
                "Corporate scholarships (TCS, Infosys, etc.)"
            ),
            topInstitutions = listOf(
                "IITs (23 campuses)",
                "NITs (31 campuses)",
                "IIITs",
                "BITS Pilani",
                "VIT, Manipal",
                "State government colleges"
            ),
            careerProspects = listOf(
                "Software Engineer: ₹3-12 LPA",
                "Core Engineering: ₹2.5-8 LPA",
                "Data Scientist: ₹4-15 LPA",
                "Product Manager: ₹6-20 LPA",
                "Higher studies (M.Tech, MBA, MS abroad)"
            ),
            requiredSkills = listOf(
                "Strong analytical thinking",
                "Problem-solving ability",
                "Mathematics proficiency",
                "Programming basics",
                "Communication skills"
            ),
            entranceExams = listOf(
                "JEE Main (for NITs, IIITs, GFTIs)",
                "JEE Advanced (for IITs)",
                "State entrance exams (MHT-CET, KCET, etc.)",
                "BITSAT",
                "VITEEE, SRMJEEE"
            ),
            recommendedFor = "Students interested in technology, problem-solving, and analytical thinking",
            tips = listOf(
                "Start JEE preparation from Class 11",
                "Focus on conceptual clarity",
                "Practice previous year papers",
                "Consider your branch interest carefully",
                "Internships are crucial for placements"
            )
        ),
        EducationGuidance(
            id = "edu_medical",
            type = EducationType.UNDERGRADUATE,
            title = "Medical (MBBS, BDS, BAMS, BHMS)",
            description = "Medical and healthcare professional courses",
            eligibility = "10+2 with Physics, Chemistry, Biology. Minimum 50% marks (40% for reserved categories)",
            duration = "MBBS: 5.5 years, BDS: 5 years, BAMS/BHMS: 5.5 years",
            averageCost = "Government: ₹50,000-5 lakhs total. Private: ₹20-80 lakhs total",
            scholarshipsAvailable = listOf(
                "Central government scholarships",
                "State government merit scholarships",
                "SC/ST/OBC scholarships",
                "College-specific scholarships"
            ),
            topInstitutions = listOf(
                "AIIMS (All India Institute of Medical Sciences)",
                "JIPMER",
                "Government Medical Colleges",
                "Armed Forces Medical College (AFMC)",
                "Christian Medical College (CMC), Vellore"
            ),
            careerProspects = listOf(
                "Doctor in government hospitals: ₹60,000-1.5 lakh/month",
                "Private practice: Variable income",
                "Specialist after PG: ₹1-5 lakh/month",
                "Medical officer positions",
                "Research and academia"
            ),
            requiredSkills = listOf(
                "Strong memory and retention",
                "Empathy and patience",
                "Communication skills",
                "Dedication and hard work",
                "Stress management"
            ),
            entranceExams = listOf(
                "NEET UG (for MBBS, BDS)",
                "NEET PG (for postgraduation)",
                "AIIMS MBBS (discontinued, now through NEET)",
                "State-level entrance exams"
            ),
            recommendedFor = "Students passionate about helping people and dedicated to long study hours",
            tips = listOf(
                "NEET requires consistent 2-3 years preparation",
                "Government colleges offer best value",
                "Focus on NCERT thoroughly",
                "Solve previous year NEET papers",
                "Consider alternatives like BDS, BAMS if MBBS is difficult"
            )
        ),
        EducationGuidance(
            id = "edu_skill_dev",
            type = EducationType.SKILL_DEVELOPMENT,
            title = "Government Skill Development Programs",
            description = "Free and subsidized skill training programs",
            eligibility = "Various programs for 10th pass to graduates",
            duration = "3 months to 2 years",
            averageCost = "Free to ₹20,000",
            scholarshipsAvailable = listOf(
                "Stipend provided in many programs",
                "PM Kaushal Vikas Yojana",
                "Free training and certification"
            ),
            topInstitutions = listOf(
                "National Skill Development Corporation (NSDC)",
                "Industrial Training Institutes (ITI)",
                "Sector Skill Councils",
                "Jan Shikshan Sansthan"
            ),
            careerProspects = listOf(
                "Skilled worker: ₹15,000-30,000/month",
                "Technician roles: ₹20,000-40,000/month",
                "Self-employment opportunities",
                "Placement assistance provided"
            ),
            requiredSkills = listOf(
                "Willingness to learn",
                "Practical aptitude",
                "Basic communication",
                "Discipline"
            ),
            entranceExams = listOf(
                "Some programs have basic tests",
                "Mostly admission-based on qualification"
            ),
            recommendedFor = "Students wanting quick employment or entrepreneurship skills",
            tips = listOf(
                "Choose industry-relevant skills",
                "Look for programs with placement assistance",
                "Visit www.pmkvyofficial.org for courses",
                "ITI trades offer good employment",
                "Consider market demand before choosing course"
            )
        ),
        EducationGuidance(
            id = "edu_online",
            type = EducationType.ONLINE_COURSES,
            title = "Online Certifications and MOOCs",
            description = "Online learning platforms and certifications",
            eligibility = "Open to all, varies by course",
            duration = "1 month to 1 year per course",
            averageCost = "Free to ₹50,000 per course",
            scholarshipsAvailable = listOf(
                "Financial aid on Coursera",
                "Scholarships on edX",
                "Government SWAYAM platform (free)",
                "Company-sponsored programs"
            ),
            topInstitutions = listOf(
                "SWAYAM (Government of India)",
                "Coursera",
                "edX",
                "Udemy",
                "NPTEL",
                "LinkedIn Learning"
            ),
            careerProspects = listOf(
                "Supplement your degree",
                "Career switching opportunities",
                "Freelancing: ₹20,000-1 lakh/month",
                "Better job prospects with certifications"
            ),
            requiredSkills = listOf(
                "Self-discipline",
                "Time management",
                "Internet access",
                "Basic computer skills"
            ),
            entranceExams = listOf("None - open enrollment"),
            recommendedFor = "Working professionals, students wanting to learn new skills, career switchers",
            tips = listOf(
                "Choose recognized platforms",
                "Complete projects for portfolio",
                "Focus on job-relevant skills",
                "SWAYAM offers free government-recognized courses",
                "Combine multiple certificates for better impact"
            )
        )
    )

    // Scholarships Database
    val scholarships = listOf(
        Scholarship(
            id = "sch_central_sector",
            name = "Central Sector Scholarship Scheme",
            provider = "Government of India",
            amount = "₹10,000 - ₹20,000 per year",
            eligibility = "Students with family income < ₹8 lakh p.a., passed 12th in top 20% of board",
            applicableFor = listOf(EducationType.UNDERGRADUATE, EducationType.POSTGRADUATE),
            deadline = "October 31 (Annual)",
            applicationProcess = "Apply online through National Scholarship Portal (scholarships.gov.in)",
            requiredDocuments = listOf(
                "Aadhaar card",
                "Income certificate",
                "12th mark sheet",
                "College admission proof",
                "Bank account details"
            ),
            selectionCriteria = "Merit-based (Board exam percentile) and income limit",
            benefits = listOf(
                "Direct bank transfer",
                "Renewable for course duration",
                "No need to repay",
                "Covers tuition and maintenance"
            ),
            website = "https://scholarships.gov.in"
        ),
        Scholarship(
            id = "sch_pm_yasasvi",
            name = "PM YASASVI Scholarship",
            provider = "Ministry of Social Justice and Empowerment",
            amount = "₹75,000 - ₹1,25,000 per year",
            eligibility = "OBC/EBC/DNT students, Class 9-10 & 11-12, Family income < ₹2.5 lakh p.a.",
            applicableFor = listOf(EducationType.SCHOOL),
            deadline = "August-September (Annual)",
            applicationProcess = "Apply through YASASVI portal, entrance test required",
            requiredDocuments = listOf(
                "Caste certificate (OBC/EBC/DNT)",
                "Income certificate",
                "Aadhaar card",
                "Previous class mark sheet",
                "School bonafide certificate"
            ),
            selectionCriteria = "Entrance test (YET - YASASVI Entrance Test) and merit",
            benefits = listOf(
                "Covers school/hostel fees",
                "Books and stationery",
                "High scholarship amount",
                "Competitive selection process"
            ),
            website = "https://yet.nta.ac.in"
        ),
        Scholarship(
            id = "sch_inspire",
            name = "INSPIRE Scholarship",
            provider = "Department of Science and Technology",
            amount = "₹80,000 per year",
            eligibility = "Top 1% students in Class 12 board exams, pursuing B.Sc./B.S./Int. M.Sc.",
            applicableFor = listOf(EducationType.UNDERGRADUATE),
            deadline = "December-January (Annual)",
            applicationProcess = "Apply online through INSPIRE portal",
            requiredDocuments = listOf(
                "Class 12 mark sheet (must be in top 1%)",
                "Admission proof to science course",
                "Aadhaar card",
                "Bank details"
            ),
            selectionCriteria = "Top 1% in Class 12 boards, pursuing natural sciences",
            benefits = listOf(
                "₹80,000 per year",
                "For 5 years",
                "Encourages science education",
                "Additional mentorship opportunities"
            ),
            website = "https://online-inspire.gov.in"
        ),
        Scholarship(
            id = "sch_moma_minority",
            name = "Pre and Post Matric Scholarship for Minorities",
            provider = "Ministry of Minority Affairs",
            amount = "₹3,000 - ₹20,000 per year",
            eligibility = "Students from minority communities (Muslim, Christian, Sikh, Buddhist, Jain, Parsi)",
            applicableFor = listOf(
                EducationType.SCHOOL,
                EducationType.UNDERGRADUATE,
                EducationType.POSTGRADUATE
            ),
            deadline = "November-December (Annual)",
            applicationProcess = "Apply through National Scholarship Portal",
            requiredDocuments = listOf(
                "Minority community certificate",
                "Income certificate (< ₹2 lakh for pre-matric, < ₹8 lakh for post-matric)",
                "Previous year mark sheet (min 50%)",
                "Admission proof",
                "Aadhaar and bank details"
            ),
            selectionCriteria = "Minority status, income limit, merit (50% marks minimum)",
            benefits = listOf(
                "Covers both pre and post-matric students",
                "Maintenance allowance included",
                "Wide coverage across minority communities"
            ),
            website = "https://scholarships.gov.in"
        )
    )

    // Helper function to search loans
    fun searchLoans(query: String, filter: LoanFilter? = null): List<LoanInfo> {
        var results = loans

        // Apply text search
        if (query.isNotBlank()) {
            results = results.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.provider.contains(query, ignoreCase = true)
            }
        }

        // Apply filters
        filter?.let { f ->
            if (f.types.isNotEmpty()) {
                results = results.filter { it.type in f.types }
            }
        }

        return results
    }

    // Helper function to search legal advice
    fun searchLegalAdvice(query: String, filter: LegalFilter? = null): List<LegalAdvice> {
        var results = legalAdvice

        if (query.isNotBlank()) {
            results = results.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.scenario.contains(query, ignoreCase = true)
            }
        }

        filter?.let { f ->
            if (f.categories.isNotEmpty()) {
                results = results.filter { it.category in f.categories }
            }
        }

        return results
    }

    // Helper function to search education guidance
    fun searchEducation(query: String, filter: EducationFilter? = null): List<EducationGuidance> {
        var results = educationGuidance

        if (query.isNotBlank()) {
            results = results.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }

        filter?.let { f ->
            if (f.types.isNotEmpty()) {
                results = results.filter { it.type in f.types }
            }
        }

        return results
    }
}
