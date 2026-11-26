# Chalo — lightweight Android ride tracker

Chalo packages the existing HTML/CSS/JavaScript app inside a native Android WebView. The app runs locally, stores data in WebView local storage, and uses Android file pickers for JSON restore and CSV/JSON export.

No Android Studio, Node.js, npm, Capacitor, emulator, or third-party Android library is required.

## Windows build setup (same style as Batwa)

Toolchain: JDK 17, Gradle 8.9 installed globally, Android Gradle Plugin 8.7.3, Android SDK platform 35 and Build Tools 35.0.0. Minimum Android version is 8.0 (API 26).

If Batwa already builds on this PC, skip to **Build**.

Otherwise install JDK 17 (https://adoptium.net/temurin/releases/?version=17), Gradle 8.9 binary ZIP (https://services.gradle.org/distributions/gradle-8.9-bin.zip), and Android SDK Command-line Tools (https://developer.android.com/studio#command-line-tools-only). Example folders: `C:\Gradle\gradle-8.9` and `C:\Android\Sdk\cmdline-tools\latest`.

Set `JAVA_HOME` to JDK 17, `ANDROID_HOME` to `C:\Android\Sdk`, and add `%JAVA_HOME%\bin`, `C:\Gradle\gradle-8.9\bin`, and `%ANDROID_HOME%\cmdline-tools\latest\bin` to Path.

In a new Command Prompt:
```bat
sdkmanager.bat --licenses
sdkmanager.bat "platform-tools" "platforms;android-35" "build-tools;35.0.0"
```
No emulator or system image is needed.

## Build

Open PowerShell or Command Prompt in the project folder and run:
```powershell
.\build.bat
```
Or run `gradle --no-daemon assembleDebug`. The first build downloads the Android Gradle Plugin and requires internet access.

APK output: `app\build\outputs\apk\debug\app-debug.apk`.

Copy the APK to your phone and install it. Allow your file manager to install unknown apps if prompted.

## Storage and backups

- App files are bundled in the APK; the app itself works offline.
- Rides, fuel fill-ups, mileage and closing snapshots are stored in the app's private local storage.
- Backup and CSV export open Android's save-file picker; Restore opens the file picker.
- Uninstalling clears local data. Back up before uninstalling or changing phones.
- There is no cloud sync.

## Updating

Pull the latest repository changes and rebuild. For future APK updates, increment `versionCode` in `app/build.gradle` and sign with the same key. Keep application ID `com.chalo.rides`.
