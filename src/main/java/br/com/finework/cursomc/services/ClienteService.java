package br.com.finework.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Cliente;
import br.com.finework.cursomc.repositories.ClienteRepository;
import br.com.finework.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repo;
    public Cliente buscar(Integer id) {
        Optional<Cliente> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
        
        //return obj.orElse(null);

    }
}