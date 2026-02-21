package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.PuntoNoleggio;
import com.noleggiomezzi.model.DateRange;
import com.noleggiomezzi.model.DescrizioneMezzo;
import com.noleggiomezzi.model.StatoMezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;

//Spring 
import org.springframework.stereotype.Repository;

//Core Java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

@Repository
public class CatalogoMezzi implements IMezzoRepository {

    private HashMap<Integer, Mezzo> mappa = new HashMap<>();
    private CatalogoTipoMezzi catalogoTipoMezzi;

    //Costruttore test
    public CatalogoMezzi() {
        this.mappa = new HashMap<>();
        
        // Inseriamo una macchina di prova visibile ad ogni avvio!
        TipoMezzo tipo = new TipoMezzo("Auto", false, true);
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

    public Mezzo getMezzoSeValido(int idMezzo) {
        Mezzo m = getMezzoById(idMezzo);
        if (m == null || !m.isDisponibile() ) {
            throw new EnitaNonTrovataException("Mezzo non trovato o non disponibile");
        }
        if(m.getLivelloCarica() < 0.2 ) {
            throw new StatoNonValidoException("Mezzo non disponibile per livello carica insufficiente o stato non valido");
        }
        if(m.getStato() != StatoMezzo.DISPONIBILE) {
            throw new StatoNonValidoException("Mezzo non disponibile per stato non valido");
        }

        return m;
    }

    public TipoMezzo getTipoMezzo(String nome) {
        return catalogoTipoMezzi.getTipoMezzo(nome);
    }

    public boolean esisteMezzo(int id) {
        return mappa.containsKey(id);
    }

    public List<Mezzo> verificaDisponibilita(TipoMezzo tipo, DateRange periodo, int idPuntoNoleggio) {
        List<Mezzo> disponibili = new ArrayList<>();
        
        for (Mezzo m : mappa.values()) {
            if (m.getTipo() != null && m.getTipo().equals(tipo) && m.getStato() == StatoMezzo.DISPONIBILE) {
                
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