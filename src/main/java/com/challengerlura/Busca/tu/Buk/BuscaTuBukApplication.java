package com.challengerlura.Busca.tu.Buk;

import com.challengerlura.Busca.tu.Buk.main.Main;
import com.challengerlura.Busca.tu.Buk.reposity.IAutorReposity;
import com.challengerlura.Busca.tu.Buk.reposity.ILibroReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuscaTuBukApplication implements CommandLineRunner {

	@Autowired
	private ILibroReposity libroReposity;
	@Autowired
	private IAutorReposity autorReposity;

	public static void main(String[] args) {
		SpringApplication.run(BuscaTuBukApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(libroReposity, autorReposity);
		main.Menu();
	}
}
