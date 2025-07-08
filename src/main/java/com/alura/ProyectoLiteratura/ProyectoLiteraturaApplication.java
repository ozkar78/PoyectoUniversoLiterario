package com.alura.ProyectoLiteratura;

import com.alura.ProyectoLiteratura.ui.MenuPrincipal;
import com.alura.ProyectoLiteratura.util.ConsoleColors;
import com.alura.ProyectoLiteratura.util.LoadingBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class,
    org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class
})
public class ProyectoLiteraturaApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private MenuPrincipal menuPrincipal;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProyectoLiteraturaApplication.class);
		app.setBannerMode(org.springframework.boot.Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Limpiar la consola
		System.out.print("\033[H\033[2J");
		System.out.flush();

		// Mostrar mensaje de bienvenida
		System.out.println();
		System.out.println();
		System.out.println(ConsoleColors.PURPLE_BOLD + "============= INICIANDO PROYECTO LITERATURA =============" + ConsoleColors.RESET);
		System.out.println();
		System.out.println();

		// Mostrar barra de carga
		LoadingBar.display("Cargando sistema...", 1500);
		System.out.println();
		LoadingBar.displaySpinner("Conectando a la base de datos...", 1000);
		System.out.println();
		LoadingBar.display("Inicializando servicios...", 800);
		System.out.println();

		// Iniciar el menú principal
		menuPrincipal.mostrarMenu();
	}
}
