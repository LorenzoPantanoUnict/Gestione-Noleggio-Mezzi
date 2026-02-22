package com.noleggiomezzi;

import com.noleggiomezzi.model.*;
import com.noleggiomezzi.model.tariffe.*;
import com.noleggiomezzi.repository.interfacce.*;
import com.noleggiomezzi.repository.*; 

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("\nSistema di Noleggio avviato su http://localhost:8080/dashboard");
    }

    @Bean
    public CommandLineRunner demo(IClienteRepository clienteRepo, 
                                  IMezzoRepository mezzoRepo, 
                                  INoleggioRepository noleggioRepo,
                                  IPuntoNoleggioRepository puntoRepo,
                                  ITariffaRepository tariffaRepo,
                                  ICassiereRepository cassiereRepo,
                                  CatalogoTipoMezzi tipoMezzoRepo) {
        return (args) -> {
            inizializzaDati(clienteRepo, mezzoRepo, noleggioRepo, puntoRepo, tariffaRepo, cassiereRepo, tipoMezzoRepo);
        };
    }

    private void inizializzaDati(IClienteRepository clienteRepo, 
                                 IMezzoRepository mezzoRepo, 
                                 INoleggioRepository noleggioRepo,
                                 IPuntoNoleggioRepository puntoRepo,
                                 ITariffaRepository tariffaRepo,
                                 ICassiereRepository cassiereRepo,
                                 CatalogoTipoMezzi tipoMezzoRepo) {
        
        System.out.println("🛠️ Inizializzazione dati di test in corso...");

        // 1. Creazione Tipi Mezzo
        TipoMezzo cityCar = new TipoMezzo("CityCar", false, true, 1.0);
        TipoMezzo furgone = new TipoMezzo("Furgone", true, false, 1.5);
        TipoMezzo luxury = new TipoMezzo("Luxury", true, false, 2.5);
        
        tipoMezzoRepo.aggiungiTipoMezzo(cityCar);
        tipoMezzoRepo.aggiungiTipoMezzo(furgone);
        tipoMezzoRepo.aggiungiTipoMezzo(luxury);

        // 2. Creazione Punti Noleggio
        PuntoNoleggio stazione = new PuntoNoleggio(1, "Stazione Centrale", "Via Roma 1", 10);
        PuntoNoleggio aeroporto = new PuntoNoleggio(2, "Aeroporto Malpensa", "Terminal 1", 15);
        puntoRepo.aggiungiPunto(stazione);
        puntoRepo.aggiungiPunto(aeroporto);

        // 3. Creazione Clienti
        Cliente pancrazio = new Cliente("Pancrazio", "Gatto", "gattopancrazio@gmail.com", "pancrazio123");
        clienteRepo.aggiungiCliente(pancrazio);

        Cliente saro = new Cliente("Saro", "Cane", "sarocane@gmail.com", "saro123");
        clienteRepo.aggiungiCliente(saro);

        Cliente alfio = new Cliente("Alfio", "Coniglio", "alfioconiglio@gmail.com", "alfio123");
        clienteRepo.aggiungiCliente(alfio);

        // 4. Creazione Mezzi Originali
        DescrizioneMezzo descPanda = new DescrizioneMezzo("Fiat", "Panda", 2023, 1200, 4, cityCar);
        Mezzo panda = new Mezzo(101, descPanda, stazione);
        
        DescrizioneMezzo descDucato = new DescrizioneMezzo("Fiat", "Ducato", 2022, 2300, 3, furgone);
        Mezzo ducato = new Mezzo(102, descDucato, stazione);
        
        // --- 4.1 NUOVI MEZZI AGGIUNTI PER I TEST ---
        
        // Un'altra CityCar ma in Aeroporto
        DescrizioneMezzo descYpsilon = new DescrizioneMezzo("Lancia", "Ypsilon", 2024, 1200, 5, cityCar);
        Mezzo ypsilon = new Mezzo(103, descYpsilon, aeroporto);
        
        // Un'auto di lusso in Stazione
        DescrizioneMezzo descMercedes = new DescrizioneMezzo("Mercedes", "Classe E", 2023, 3000, 5, luxury);
        Mezzo mercedes = new Mezzo(104, descMercedes, stazione);
        
        // Un altro furgone in Aeroporto
        DescrizioneMezzo descTransit = new DescrizioneMezzo("Ford", "Transit", 2021, 2500, 3, furgone);
        Mezzo transit = new Mezzo(105, descTransit, aeroporto);

        // Salvataggio nel repository
        mezzoRepo.aggiungiMezzo(panda);
        mezzoRepo.aggiungiMezzo(ducato);
        mezzoRepo.aggiungiMezzo(ypsilon);
        mezzoRepo.aggiungiMezzo(mercedes);
        mezzoRepo.aggiungiMezzo(transit);

        // 5. Creazione Tariffe 
        tariffaRepo.aggiungiTariffa("ORARIA", new TariffaOraria(5.0));
        tariffaRepo.aggiungiTariffa("GIORNALIERA", new TariffaGiornaliera(40.0));

        // 6. Noleggio di test
        //ITariffa oraria = tariffaRepo.getTariffaByName("ORARIA");
        //Noleggio testNoleggio = new Noleggio(cliente, panda, oraria, stazione);

        // 7. Cassiere Admin
        Cassiere admin = new Cassiere("admin", "admin123", "Mario", "Rossi");
        cassiereRepo.aggiungiCassiere(admin);

        System.out.println("✅ Dati caricati correttamente: 5 Mezzi distribuiti su 2 Sedi.");
    }
}