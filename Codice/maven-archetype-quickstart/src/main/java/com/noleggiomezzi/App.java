package com.noleggiomezzi;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.tariffe.*;
import com.noleggiomezzi.repository.interfacce.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("✅ Sistema di Noleggio avviato su http://localhost:8080");
    }

    @Bean
    public CommandLineRunner demo(IClienteRepository clienteRepo, 
                                  IMezzoRepository mezzoRepo, 
                                  INoleggioRepository noleggioRepo,
                                  IPuntoNoleggioRepository puntoRepo,
                                  ITariffaRepository tariffaRepo) {
        return (args) -> {
            //Crazione di un Noleggio di Test
            PuntoNoleggio stazione = new PuntoNoleggio(1, "Stazione Centrale", "Via Roma 1", 10);
            puntoRepo.aggiungiPunto(stazione);

            Cliente cliente = new Cliente("Mario", "Rossi", "mario.rossi@email.com");
            clienteRepo.aggiungiCliente(cliente);

            TipoMezzo cityCar = new TipoMezzo("CityCar", false, true);
            DescrizioneMezzo desc = new DescrizioneMezzo("Fiat", "Panda", 2023, 1200, 4, cityCar);
            Mezzo panda = new Mezzo(101, desc, stazione);
            mezzoRepo.aggiungiMezzo(panda);

            ITariffa oraria = tariffaRepo.getTariffaByName("ORARIA");

            Noleggio testNoleggio = new Noleggio(cliente, panda, oraria, stazione);
            panda.noleggia(); // Cambia lo stato del mezzo in NOLEGGIATO
            noleggioRepo.aggiungiNoleggio(testNoleggio);

            System.out.println("🚀 Dati di test caricati: Noleggio ID " + testNoleggio.getId() + " per Mario Rossi pronto per essere concluso!");
        };
    }
}