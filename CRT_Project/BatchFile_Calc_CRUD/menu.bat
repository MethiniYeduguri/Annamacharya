@echo off
set JAVAFX=E:\CRT\JavaFX\javafx-sdk-17.0.15\lib
set MYSQLJAR=E:\CRT\mysql-connector-j-9.3.0.jar

:menu
cls
echo ================================
echo          MAIN MENU
echo ================================
echo 1. Console Calculator
echo 2. Console CRUD
echo 3. JavaFX CRUD
echo 4. Exit
echo ================================
set /p choice=Enter your choice (1-4): 

if "%choice%"=="1" (
    java -cp . Calculator1.Menu_Calculator
) else if "%choice%"=="2" (
    java -cp ".;%MYSQLJAR%" CRUD.Menu_CRUD
) else if "%choice%"=="3" (
    java --module-path %JAVAFX% --add-modules javafx.controls,javafx.fxml -cp ".;%MYSQLJAR%" JavaFX.FrontCRUD
) else if "%choice%"=="4" (
    exit
)  else (
    echo Invalid choice. Try again.
)

pause
goto menu
