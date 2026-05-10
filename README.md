# Aspect Ratio Calculator

An Android utility for calculating image and display dimensions.

## 🛠 Technical Stack

- **UI Framework:** Jetpack Compose for a fully declarative UI.
- **Language:** Kotlin with Coroutines and Flows.
- **Dependency Injection:** Hilt for robust, testable dependency management.
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** AndroidX Navigation 3 with type-safe route definitions for state-driven navigation.
- **Build System:** Gradle (Kotlin DSL) with custom **Convention Plugins** for centralized build logic.

## 🏗 Project Structure

The project follows a modular, feature-based architecture to promote scalability and maintainability:

```text
├── app/                  # Application entry point & Hilt setup
├── build-logic/          # Custom Gradle Plugins (Convention Plugins)
├── core/                 # Shared foundation modules
│   ├── common/           # Generic utilities & extensions
│   ├── designsystem/     # Design tokens, themes, and reusable UI components
│   └── navigation/       # Global navigation definitions
└── feature/              # Functional modules (Feature-on-Feature isolation)
    ├── calculator/       # Core math logic and calculation UI
    └── info/             # Informational/About screens
```

## 🚀 Key Engineering Highlights

- **Multi-Module Architecture:** Separation of concerns across `app`, `feature`, and `core` modules for better scalability and faster build times.
- **Unidirectional Data Flow (UDF):** Clean state management using `StateFlow` for UI state and `Channels` for one-time side effects (navigation, alerts).
- **Convention Plugins:** Centralized build configuration using custom Gradle plugins in `build-logic`, ensuring consistency and reducing boilerplate.
- **Unit Testing:** Comprehensive test coverage for domain logic and ViewModels to ensure reliability and facilitate safe refactoring.

## 🤖 AI Agent Ready

This repository is explicitly optimized for AI-assisted development. It utilizes a "Single Source of Truth" architecture for LLM guidelines, ensuring that any AI coding assistant (Gemini, Claude, etc.) automatically adheres to strict project conventions without hallucinating architectures.

- **Centralized Rules:** All architectural constraints, tech stack mandates, and security rules are maintained in `/.agents/GUIDELINES.md`.
- **Tool-Agnostic Routing:** Contains native config files (e.g., `GEMINI.md` and `.claude/README.md`) that automatically point different AI assistants to the central guidelines.
- **Custom Agent Skills:** Includes a `.agents/skills` directory providing operational scripts and context specifically tailored for LLM agents working within this codebase.
    - **Note on `android-cli`:** This project includes the `android-cli` skill which grants AI agents advanced operational capabilities (such as deploying to emulators, executing tests, and deep layout inspection). For agents to fully utilize this skill, the host machine should have the `android` CLI tool installed.

## 🛠 Setup & AdMob Configuration

This project uses AdMob for advertisements. For security reasons, production AdMob IDs are not stored in the repository.

### For Local Development
The project is configured to use **official Google Test IDs** by default. You can clone and run the app immediately without any extra setup.

### For Production/Release Builds
If you want to build the release version with your own AdMob production keys, follow these steps:

1. Open (or create) the `local.properties` file in the root directory of the project.
2. Add your production keys as follows:
   ```properties
   # Admob keys
   ADMOB_APP_ID=ca-app-pub-xxxxxxxxxxxxxxxx~xxxxxxxxxx
   BANNER_AD_UNIT_ID=ca-app-pub-xxxxxxxxxxxxxxxx/xxxxxxxxxx
   ```
