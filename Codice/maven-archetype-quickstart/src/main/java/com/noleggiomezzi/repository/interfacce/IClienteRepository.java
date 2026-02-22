package com.noleggiomezzi.repository.interfacce;

import java.util.List;

import com.noleggiomezzi.model.Cliente;

public interface IClienteRepository {

    public Cliente getClienteById(int id);

    public void aggiungiCliente(Cliente cliente);

    public List<Cliente> findAll();

    public Cliente findByEmail(String email);
}
