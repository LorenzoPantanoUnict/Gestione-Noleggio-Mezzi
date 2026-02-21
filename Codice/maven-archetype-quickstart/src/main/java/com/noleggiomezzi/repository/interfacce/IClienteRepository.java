package com.noleggiomezzi.repository.interfacce;

import java.util.List;

import com.noleggiomezzi.model.Cliente;

public interface IClienteRepository {

    public Cliente getClienteById(int id);

    void aggiungiCliente(Cliente cliente);

    List<Cliente> findAll();
}
