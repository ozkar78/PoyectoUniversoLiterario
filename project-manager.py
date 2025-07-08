#!/usr/bin/env python3
"""
Script de gestión del Proyecto Literatura
Automatiza tareas comunes de desarrollo
"""

import os
import subprocess
import sys
import time

class ProjectManager:
    def __init__(self):
        self.project_dir = os.path.dirname(os.path.abspath(__file__))
        
    def run_command(self, command, description=""):
        """Ejecuta un comando y muestra el resultado"""
        if description:
            print(f"\n🔄 {description}")
        
        try:
            result = subprocess.run(command, shell=True, cwd=self.project_dir, 
                                  capture_output=True, text=True)
            if result.returncode == 0:
                print(f"✅ Éxito: {description}")
                if result.stdout:
                    print(result.stdout)
            else:
                print(f"❌ Error: {description}")
                if result.stderr:
                    print(result.stderr)
            return result.returncode == 0
        except Exception as e:
            print(f"❌ Excepción: {e}")
            return False
    
    def clean_project(self):
        """Limpia el proyecto"""
        return self.run_command("mvn clean", "Limpiando proyecto")
    
    def compile_project(self):
        """Compila el proyecto"""
        return self.run_command("mvn compile", "Compilando proyecto")
    
    def run_tests(self):
        """Ejecuta los tests"""
        return self.run_command("mvn test", "Ejecutando tests")
    
    def run_application(self):
        """Ejecuta la aplicación"""
        print("\n🚀 Iniciando aplicación Spring Boot...")
        print("Presiona Ctrl+C para detener")
        try:
            subprocess.run("mvn spring-boot:run", shell=True, cwd=self.project_dir)
        except KeyboardInterrupt:
            print("\n⏹️ Aplicación detenida")
    
    def check_database(self):
        """Verifica la conexión a la base de datos"""
        return self.run_command('psql -U postgres -d alura_literatura -c "SELECT version();"', 
                               "Verificando conexión a PostgreSQL")
    
    def setup_database(self):
        """Configura la base de datos"""
        commands = [
            'psql -U postgres -c "CREATE DATABASE alura_literatura;"',
        ]
        
        if os.path.exists("database.sql"):
            commands.append('psql -U postgres -d alura_literatura -f database.sql')
        
        for cmd in commands:
            self.run_command(cmd, f"Ejecutando: {cmd}")
    
    def show_menu(self):
        """Muestra el menú principal"""
        print("\n" + "="*50)
        print("🚀 GESTOR DEL PROYECTO LITERATURA")
        print("="*50)
        print("1. Limpiar proyecto")
        print("2. Compilar proyecto")
        print("3. Ejecutar tests")
        print("4. Ejecutar aplicación")
        print("5. Verificar base de datos")
        print("6. Configurar base de datos")
        print("7. Compilar y ejecutar (completo)")
        print("8. Abrir en VS Code")
        print("9. Abrir en IntelliJ IDEA")
        print("0. Salir")
        print("="*50)
    
    def full_build_and_run(self):
        """Proceso completo: limpiar, compilar y ejecutar"""
        steps = [
            (self.clean_project, "Limpieza"),
            (self.compile_project, "Compilación"),
            (self.run_tests, "Tests"),
        ]
        
        for step_func, step_name in steps:
            if not step_func():
                print(f"❌ Falló en: {step_name}")
                return False
        
        print("\n✅ Build completado exitosamente")
        self.run_application()
        return True
    
    def open_vscode(self):
        """Abre el proyecto en VS Code"""
        self.run_command("code .", "Abriendo VS Code")
    
    def open_intellij(self):
        """Abre el proyecto en IntelliJ IDEA"""
        self.run_command("idea .", "Abriendo IntelliJ IDEA")
    
    def run(self):
        """Ejecuta el gestor principal"""
        while True:
            self.show_menu()
            choice = input("\n👉 Selecciona una opción: ").strip()
            
            if choice == "1":
                self.clean_project()
            elif choice == "2":
                self.compile_project()
            elif choice == "3":
                self.run_tests()
            elif choice == "4":
                self.run_application()
            elif choice == "5":
                self.check_database()
            elif choice == "6":
                self.setup_database()
            elif choice == "7":
                self.full_build_and_run()
            elif choice == "8":
                self.open_vscode()
            elif choice == "9":
                self.open_intellij()
            elif choice == "0":
                print("\n👋 ¡Hasta luego!")
                break
            else:
                print("\n❌ Opción inválida")
            
            input("\nPresiona Enter para continuar...")

if __name__ == "__main__":
    manager = ProjectManager()
    manager.run()