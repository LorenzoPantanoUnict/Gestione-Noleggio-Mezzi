package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.DescrizioneMezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.utility.DateRange;

// Spring 
import org.springframework.stereotype.Repository;

// Core Java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

@Repository
public class CatalogoMezzi implements IMezzoRepository {

    private HashMap<Integer, Mezzo> mappa = new HashMap<>();
    private CatalogoTipoMezzi catalogoTipoMezzi;

    public CatalogoMezzi() {
        this.mappa = new HashMap<>();
        
        // Dati di test
        TipoMezzo tipo = new TipoMezzo("Auto", false, true, 2);
        DescrizioneMezzo desc = new DescrizioneMezzo("Fiat", "Panda", 2023, 1200, 5, tipo);
        PuntoNoleggio punto = new PuntoNoleggio(1, "Sede Centrale", "Via Roma", 50);
        
        Mezzo mezzoDiProva = new Mezzo(1, desc, punto);
        this.mappa.put(mezzoDiProva.getId(), mezzoDiProva);
    }

    public Mezzo getMezzoById(int id) throws EnitaNonTrovataException {
        Mezzo m = mappa.get(id);
        if (m == null) {
            throw new EnitaNonTrovataException("Mezzo con ID " + id + " non trovato");
        }
        return m;
    }

    public void aggiungiMezzo(Mezzo m) {
        mappa.put(m.getId(), m);
    }

    /**
     * 
     */
    public Mezzo getMezzoSeValido(int idMezzo) {
        Mezzo m = getMezzoById(idMezzo);
        
        if (!m.isDisponibile()) {
            throw new StatoNonValidoException("Il mezzo non è disponibile. Stato attuale: " + m.getStato());
        }


        if(m.getLivelloCarica() < 20.0) {
            throw new StatoNonValidoException("Mezzo non disponibile per livello carica insufficiente (minimo 20%)");
        }

        return m;
    }

    public TipoMezzo getTipoMezzo(String nome) {
        return catalogoTipoMezzi.getTipoMezzo(nome);
    }

    public boolean esisteMezzo(int id) {
        return mappa.containsKey(id);
    }

    /**
     * 
     */
    public List<Mezzo> verificaDisponibilita(TipoMezzo tipo, DateRange periodo, int idPuntoNoleggio) {
        List<Mezzo> disponibili = new ArrayList<>();
        
        for (Mezzo m : mappa.values()) {
            if (m.getTipo() != null && m.getTipo().equals(tipo) && m.isDisponibile()) {
                if (m.getPuntoNoleggio() != null && m.getPuntoNoleggio().getId() == idPuntoNoleggio) {
                    disponibili.add(m);
                }
            }
        }
        return disponibili;
    }

    public List<Mezzo> findAll() {
        return new ArrayList<>(mappa.values());
    }
}