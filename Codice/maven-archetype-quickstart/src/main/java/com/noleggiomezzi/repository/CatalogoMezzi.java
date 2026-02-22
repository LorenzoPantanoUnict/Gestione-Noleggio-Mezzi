package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Mezzo;
import com.noleggiomezzi.model.TipoMezzo;
import com.noleggiomezzi.repository.interfacce.IMezzoRepository;
import com.noleggiomezzi.exceptions.EnitaNonTrovataException;
import com.noleggiomezzi.exceptions.StatoNonValidoException;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CatalogoMezzi implements IMezzoRepository {

    private HashMap<Integer, Mezzo> mappa = new HashMap<>();
    private final CatalogoTipoMezzi catalogoTipoMezzi;

    public CatalogoMezzi(CatalogoTipoMezzi catalogoTipoMezzi) {
        this.catalogoTipoMezzi = catalogoTipoMezzi;
    }

    @Override
    public Mezzo getMezzoById(int id) throws EnitaNonTrovataException {
        Mezzo m = mappa.get(id);
        if (m == null) {
            throw new EnitaNonTrovataException("Mezzo con ID " + id + " non trovato");
        }
        return m;
    }

    @Override
    public void aggiungiMezzo(Mezzo m) {
        mappa.put(m.getId(), m);
    }

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

    // Metodo per il filtro FISICO
    public List<Mezzo> findMezziDisponibiliFisicamente(int sedeId, String nomeTipo) {
        return mappa.values().stream()
                .filter(m -> m.getPuntoNoleggio().getId() == sedeId)
                .filter(m -> m.getTipo().getNome().equalsIgnoreCase(nomeTipo))
                .filter(Mezzo::isDisponibile) 
                .collect(Collectors.toList());
    }

    @Override
    public List<Mezzo> findMezziPerSedeETipo(int sedeId, String nomeTipo) {
        return mappa.values().stream()
                .filter(m -> m.getPuntoNoleggio().getId() == sedeId)
                .filter(m -> m.getTipo().getNome().equalsIgnoreCase(nomeTipo))
                .collect(Collectors.toList());
    }

    public TipoMezzo getTipoMezzo(String nome) {
        return catalogoTipoMezzi.getTipoMezzo(nome);
    }

    public boolean esisteMezzo(int id) {
        return mappa.containsKey(id);
    }

    @Override
    public List<Mezzo> findAll() {
        return new ArrayList<>(mappa.values());
    }
}