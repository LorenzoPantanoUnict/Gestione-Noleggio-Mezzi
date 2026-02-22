package com.noleggiomezzi.service;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.model.DescrizioneMezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.Prenotazione;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.repository.interfacce.IPuntoNoleggioRepository;
import com.noleggiomezzi.repository.CatalogoTipoMezzi;
import com.noleggiomezzi.repository.RegistroPrenotazioni;
import com.noleggiomezzi.utility.DateRange;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MezzoService {

    private final IMezzoRepository mezzoRepo;
    private final RegistroPrenotazioni prenotazioniRepo;
    private final IPuntoNoleggioRepository sedeRepo;
    private final CatalogoTipoMezzi tipiRepo;

    public MezzoService(IMezzoRepository mezzoRepo, 
                        RegistroPrenotazioni prenotazioniRepo,
                        IPuntoNoleggioRepository sedeRepo,
                        CatalogoTipoMezzi tipiRepo) {
        this.mezzoRepo = mezzoRepo;
        this.prenotazioniRepo = prenotazioniRepo;
        this.sedeRepo = sedeRepo;
        this.tipiRepo = tipiRepo;
    }

    // Metodi per la prenotazione

    public List<Mezzo> verificaDisponibilitaCompleta(TipoMezzo tipo, DateRange periodoRichiesto, int sedeId) {
        List<Mezzo> mezziFisicamentePresenti = mezzoRepo.findMezziDisponibiliFisicamente(sedeId, tipo.getNome());

        return mezziFisicamentePresenti.stream()
                .filter(mezzo -> !isMezzoImpegnatoInPeriodo(mezzo, periodoRichiesto))
                .collect(Collectors.toList());
    }

    private boolean isMezzoImpegnatoInPeriodo(Mezzo m, DateRange periodoRichiesto) {
        List<Prenotazione> tutteLePrenotazioni = prenotazioniRepo.findAll();
        return tutteLePrenotazioni.stream()
                .filter(p -> p.getMezzo().getId() == m.getId())
                .anyMatch(p -> p.getPeriodo().sovrappone(periodoRichiesto)); 
    }

    // Metodi utili al controller

    public List<Mezzo> getTuttiMezzi() {
        return mezzoRepo.findAll();
    }

    public List<PuntoNoleggio> getTutteSedi() {
        return sedeRepo.findAll();
    }

    public void aggiungiNuovoMezzo(int idMezzo, String marca, String modello, int anno, 
                                   int cilindrata, int nPosti, String tipoMezzoNome, int idSede) {
        
        TipoMezzo tipo = tipiRepo.getTipoMezzo(tipoMezzoNome);
        DescrizioneMezzo desc = new DescrizioneMezzo(marca, modello, anno, cilindrata, nPosti, tipo);
        PuntoNoleggio sede = sedeRepo.getPuntoById(idSede);
        
        Mezzo nuovoMezzo = new Mezzo(idMezzo, desc, sede);
        mezzoRepo.aggiungiMezzo(nuovoMezzo);
    }
}