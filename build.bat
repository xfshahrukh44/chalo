@echo off
setlocal
if "%ANDROID_HOME%"=="" if not "%ANDROID_SDK_ROOT%"=="" set ANDROID_HOME=%ANDROID_SDK_ROOT%
if "%ANDROID_HOME%"=="" (
  echo ERROR: ANDROID_HOME is not set. See README.md for SDK setup.
  exit /b 1
)
where gradle >nul 2>nul
if errorlevel 1 (
  echo ERROR: Gradle 8.9 is not on PATH. See README.md for setup.
  exit /b 1
)
echo Building Chalo debug APK...
gradle --no-daemon assembleDebug
if errorlevel 1 exit /b %errorlevel%
echo.
echo APK created at:
echo %~dp0app\build\outputs\apk\debug\app-debug.apk
endlocal
