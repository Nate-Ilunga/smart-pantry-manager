# Smart Pantry Manager

A Java Android application that helps users reduce food waste by tracking their pantry ingredients and suggesting recipes they can cook using **only** what they already have — no shopping trip required.

Built for **Mobile App Development 700**.

## Concept

The core value of the app is its **strict-matching rule**: a recipe is only suggested if every single one of its required ingredients is present in the user's pantry, in at least the required quantity. Partial matches are excluded from the main suggestions list. An optional "Almost There" list surfaces recipes missing exactly one ingredient.

## Features

- Add, edit, and delete pantry items (name, quantity, unit, optional expiry date)
- Persistent pantry list backed by a local database
- Pre-seeded collection of 15-20 recipes, including South African favourites (Chakalaka, Bunny chow, Vetkoek with mince, Boerewors roll)
- Strict recipe matching against current pantry contents
- Detailed recipe view with full ingredient list and preparation steps
- Settings screen with theme override (light / dark / system) and units preference
- Light and dark mode support with a custom Material 3 colour palette

## Technology

| Layer | Choice |
|---|---|
| Language | Java |
| Minimum Android version | API 24 (Android 7.0 Nougat) |
| UI toolkit | Android Views + Material 3 |
| Database | **SQLite** via the Room persistence library |
| Build system | Gradle (Groovy DSL) |
| IDE | Android Studio |

## Database justification

**SQLite via Room** was chosen for three reasons:

1. **On-device, no network dependency.** A personal pantry is a single-user context — cloud sync would add complexity without solving a real problem. Users can add, view, and match recipes offline.
2. **Consistent with the module's persistent data chapter.** Room is Google's official abstraction over SQLite, giving compile-time SQL verification and automatically-generated boilerplate while remaining recognisable as a SQLite implementation.
3. **Low-risk demonstration path.** No external services, accounts, or hosting to fail during marking. The app runs identically on any Android device or emulator.

## Setup and run

### Prerequisites
- Android Studio (Ladybug / 2026.1.4 or newer)
- JDK 21
- Android SDK with API 24 or higher

### Steps
1. Clone the repository: `git clone https://github.com/Nate-Ilunga/smart-pantry-manager.git`
2. Open the project in Android Studio (`File → Open` → select the cloned folder)
3. Let Gradle sync (5-15 minutes on first open)
4. Run on an emulator (API 24 or higher) or a physical Android device with USB debugging enabled

The recipe collection is seeded automatically on first launch — no manual database setup required.



## License
Released under the MIT License — see `LICENSE` for details.