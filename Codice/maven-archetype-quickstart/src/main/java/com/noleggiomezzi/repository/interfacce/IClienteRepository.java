package com.noleggiomezzi.repository.interfacce;

import com.noleggiomezzi.model.Cliente;

public interface IClienteRepository {

    public Cliente getClienteById(int id);

    void aggiungiCliente(Cliente cliente);
}
