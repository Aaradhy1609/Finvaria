# YouthHub - Youth Empowerment Platform

A comprehensive Android app empowering Indian youth with AI-powered guidance on finance, legal
matters, and education - completely offline capable!

## What This App Offers

YouthHub is your all-in-one companion for navigating the critical decisions of youth:

### Finance & Loan Guidance

- **Education Loans**: SBI Scholar Loan, Vidyalakshmi, Central Sector Interest Subsidy
- **Personal & Business Loans**: HDFC, Mudra, and more
- **Government Schemes**: PMAY, Mudra Yojana
- **Smart Filtering**: Find loans by type, interest rate, tenure, and collateral requirements
- **Detailed Information**: Complete eligibility, documents, benefits, and application process

### Legal Advice

- **Consumer Rights**: Defective products, refunds, complaints
- **Employment Law**: Salary disputes, unfair termination
- **Property Matters**: Title verification, disputes
- **Cyber Crime**: Online fraud, phishing, identity theft
- **Education Disputes**: Admission issues, fee refunds
- **Step-by-step guidance** with relevant laws, required documents, and cost estimates

### Education Guidance

- **Course Information**: Engineering, Medical, Skill Development
- **Entrance Exams**: JEE, NEET, state exams
- **Top Institutions**: IITs, NITs, AIIMS, and more
- **Scholarships**: Central Sector, INSPIRE, PM YASASVI, Minority scholarships
- **Career Prospects**: Salary ranges, job opportunities
- **Expert Tips**: From preparation to career planning

### AI Assistant

- **On-device AI**: Powered by RunAnywhere SDK
- **Context-Aware**: Specialized responses for finance, legal, or education queries
- **Offline Capable**: Works without internet after model download
- **Multilingual Support**: English, Hindi, Hinglish, and 8 other Indian languages
- **Smart Knowledge Base**: Falls back to curated information when AI isn't available

### Document Scanner & Simplifier

- **OCR Technology**: Extract text from documents using ML Kit
- **Smart Simplification**: AI explains complex documents in simple language
- **Key Points Extraction**: Highlights important information
- **Warning Detection**: Alerts about critical clauses
- **Support for**: Aadhaar, PAN, Passport, Legal notices, Contracts, and more

### Voice Support

- **Voice Input**: Ask questions naturally
- **Hands-free Operation**: Convenient for on-the-go use
- **Multiple Languages**: Works in your preferred language

### Beautiful UI & Themes

- **Multiple Themes**: Blue (Professional), Purple (Creative), Green (Fresh)
- **Dark Mode**: Easy on the eyes
- **Modern Design**: Material Design 3
- **Smooth Animations**: Powered by Lottie
- **Intuitive Navigation**: Easy-to-use bottom navigation

## Quick Start

### 1. Install and Launch
```bash
./gradlew assembleDebug
# Or open in Android Studio and click Run
```

### 2. Choose Your Language

- Tap the settings icon
- Select from 11 languages including Hindi, Hinglish, and regional languages

### 3. Download AI Model (Optional but Recommended)

1. Tap "Models" in settings
2. Choose "Qwen 2.5 0.5B Instruct" (374 MB)
3. Tap "Download" and wait
4. Tap "Load" to activate AI
5. Now enjoy smart, context-aware responses!

### 4. Start Exploring!

- **Finance**: Browse loans or ask "What education loans are available?"
- **Legal**: Get advice or ask "What to do if product is defective?"
- **Education**: Explore courses or ask "How to prepare for JEE?"
- **Scan Documents**: Use camera to scan and understand complex documents

## Features in Detail

### Smart Filtering System

- **Loans**: Filter by type (Education, Personal, Home, Business), interest rate, amount, tenure
- **Legal**: Filter by category (Consumer Rights, Employment, Cyber Crime)
- **Education**: Filter by type, cost, scholarships availability
- **Sorting**: By relevance, ratings, cost, or name

### Comprehensive Knowledge Base

- **7 Loan Programs**: From ₹20,000 to ₹1.5 Crore
- **5 Legal Scenarios**: With laws, documents, timelines, and costs
- **4 Education Paths**: With entrance exams, institutions, and career prospects
- **4 Major Scholarships**: Worth ₹3,000 to ₹1,25,000 per year

### Multilingual Support

Supports 11 languages:

- English
- हिन्दी (Hindi)
- Hinglish
- தமிழ் (Tamil)
- తెలుగు (Telugu)
- বাংলা (Bengali)
- मराठी (Marathi)
- ગુજરાતી (Gujarati)
- ಕನ್ನಡ (Kannada)
- മലയാളം (Malayalam)
- ਪੰਜਾਬੀ (Punjabi)

## Technical Stack

### Core Technologies

- **Kotlin**: 100% Kotlin codebase
- **Jetpack Compose**: Modern declarative UI
- **Material Design 3**: Beautiful, accessible design
- **Coroutines & Flow**: Reactive state management

### AI & ML

- **RunAnywhere SDK**: On-device LLM inference
- **LlamaCpp**: Optimized for ARM64 processors
- **ML Kit**: Text recognition for document scanning
- **Context-Aware Prompting**: Specialized AI responses by category

### Additional Libraries

- **Navigation Compose**: Seamless screen transitions
- **CameraX**: Document scanning
- **Lottie**: Smooth animations
- **Coil**: Efficient image loading
- **DataStore**: Persistent preferences

## App Statistics

- **Languages Supported**: 11
- **Loan Programs**: 7 major schemes
- **Legal Scenarios**: 5 common situations
- **Education Paths**: 4 comprehensive guides
- **Scholarships**: 4 major programs
- **Document Types**: 11 supported types
- **Themes**: 3 color schemes + dark mode

## Security & Privacy

- **No Data Collection**: All data stays on your device
- **Offline Capable**: Works without internet after setup
- **On-device AI**: Your queries never leave your phone
- **Secure Storage**: Uses AndroidX Security for preferences
- **No Tracking**: Zero analytics or tracking

## Use Cases

### For Students

- "Which education loan has lowest interest rate?"
- "How to prepare for NEET exam?"
- "Show me scholarships for engineering students"
- Scan admission letters and understand terms

### For Young Professionals

- "My employer hasn't paid salary for 2 months, what to do?"
- "Need personal loan for wedding, suggest options"
- "How to file consumer complaint for defective phone?"

### For Entrepreneurs

- "Tell me about Mudra loan for small business"
- "What documents needed for business loan?"
- Scan legal contracts and understand obligations

### For Everyone

- "Education loan vs personal loan for studies?"
- "My Flipkart order is defective, what are my rights?"
- "How to report cyber fraud?"

## Roadmap

Planned features:

- [ ] More loan schemes and banks
- [ ] Video tutorials for complex topics
- [ ] Community forum
- [ ] Personalized recommendations
- [ ] Push notifications for scholarship deadlines
- [ ] Integration with government portals
- [ ] Offline voice support

## Contributing

This is a hackathon project demonstrating AI-powered youth empowerment. Contributions welcome!

## License

This app uses the RunAnywhere SDK. All knowledge base information is curated from public government
sources and is for informational purposes only. Always verify with official sources.

## Disclaimer

YouthHub provides general information and guidance. For legal matters, always consult a qualified
lawyer. For financial decisions, verify current interest rates and terms with banks. For education,
check with institutions directly.

## Acknowledgments

- **RunAnywhere SDK** for on-device AI capabilities
- **Government of India** for public information on schemes
- **Open Source Community** for amazing libraries
- **Indian Youth** - this app is built for you!

---
**Made with ❤️ for Indian Youth**

*Empowering the next generation with knowledge, one query at a time.*
