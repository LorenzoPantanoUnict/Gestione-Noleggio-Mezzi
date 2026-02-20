package com.noleggiomezzi.repository;

import com.noleggiomezzi.model.Cliente;

public interface IClienteRepository {

    public Cliente getClienteById(int id);

    void aggiungiCliente(Cliente cliente);
}
