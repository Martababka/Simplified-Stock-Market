@echo off
if "%1"=="" (
    echo Usage: start.bat ^<PORT^>
    exit /b 1
)
set APP_PORT=%1
docker-compose up --build -d
echo Stock Market Simulator started and highly available at http://localhost:%APP_PORT%