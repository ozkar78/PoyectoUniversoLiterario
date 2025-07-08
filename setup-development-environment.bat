@echo off
echo ========================================
echo CONFIGURACION COMPLETA DEL ENTORNO DE DESARROLLO
echo ========================================
echo.

REM Verificar si Chocolatey está instalado
choco --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Instalando Chocolatey...
    powershell -Command "Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))"
    echo Chocolatey instalado correctamente
) else (
    echo Chocolatey ya está instalado
)

echo.
echo ========================================
echo INSTALANDO HERRAMIENTAS ESENCIALES
echo ========================================

REM Java Development Kit 21
echo Instalando OpenJDK 21...
choco install openjdk21 -y

REM Maven
echo Instalando Maven...
choco install maven -y

REM PostgreSQL
echo Instalando PostgreSQL...
choco install postgresql -y

REM Python
echo Instalando Python...
choco install python -y

REM Node.js (útil para desarrollo web)
echo Instalando Node.js...
choco install nodejs -y

REM Git
echo Instalando Git...
choco install git -y

REM Visual Studio Code
echo Instalando Visual Studio Code...
choco install vscode -y

REM IntelliJ IDEA Community
echo Instalando IntelliJ IDEA Community...
choco install intellijidea-community -y

REM Postman (para testing APIs)
echo Instalando Postman...
choco install postman -y

REM Docker Desktop
echo Instalando Docker Desktop...
choco install docker-desktop -y

echo.
echo ========================================
echo CONFIGURANDO VARIABLES DE ENTORNO
echo ========================================

REM Actualizar PATH
refreshenv

echo.
echo ========================================
echo INSTALANDO EXTENSIONES DE VS CODE
echo ========================================

REM Esperar a que VS Code se instale completamente
timeout /t 10 /nobreak

REM Extensiones para Java
code --install-extension vscjava.vscode-java-pack
code --install-extension redhat.java
code --install-extension vscjava.vscode-maven
code --install-extension vmware.vscode-spring-boot

REM Extensiones para Python
code --install-extension ms-python.python
code --install-extension ms-python.pylint
code --install-extension ms-python.black-formatter

REM Extensiones para desarrollo general
code --install-extension ms-vscode.vscode-json
code --install-extension bradlc.vscode-tailwindcss
code --install-extension esbenp.prettier-vscode
code --install-extension ms-vscode.vscode-typescript-next

REM Extensiones para bases de datos
code --install-extension ms-mssql.mssql
code --install-extension ckolkman.vscode-postgres

REM Extensiones para Git
code --install-extension eamodio.gitlens

echo.
echo ========================================
echo CONFIGURANDO POSTGRESQL
echo ========================================

REM Crear base de datos
echo Configurando PostgreSQL...
timeout /t 5 /nobreak

REM Crear la base de datos alura_literatura
psql -U postgres -c "CREATE DATABASE alura_literatura;" 2>nul

echo.
echo ========================================
echo INSTALANDO PAQUETES PYTHON ESENCIALES
echo ========================================

python -m pip install --upgrade pip
pip install requests
pip install pandas
pip install numpy
pip install matplotlib
pip install seaborn
pip install jupyter
pip install flask
pip install django
pip install fastapi
pip install sqlalchemy
pip install psycopg2-binary
pip install pytest
pip install black
pip install flake8
pip install autopep8

echo.
echo ========================================
echo VERIFICANDO INSTALACIONES
echo ========================================

echo Verificando Java...
java -version

echo.
echo Verificando Maven...
mvn -version

echo.
echo Verificando Python...
python --version

echo.
echo Verificando PostgreSQL...
psql --version

echo.
echo Verificando Git...
git --version

echo.
echo Verificando Node.js...
node --version

echo.
echo ========================================
echo CONFIGURACION COMPLETADA
echo ========================================
echo.
echo Todas las herramientas han sido instaladas:
echo - OpenJDK 21
echo - Maven
echo - PostgreSQL
echo - Python con paquetes esenciales
echo - Node.js
echo - Git
echo - Visual Studio Code con extensiones
echo - IntelliJ IDEA Community
echo - Postman
echo - Docker Desktop
echo.
echo PROXIMOS PASOS:
echo 1. Reinicia tu computadora para aplicar todos los cambios
echo 2. Configura la contraseña de PostgreSQL si es necesario
echo 3. Ejecuta 'mvn spring-boot:run' en tu proyecto
echo.
echo Presiona cualquier tecla para continuar...
pause >nul