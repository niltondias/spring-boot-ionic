package br.com.finework.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Estado;
import br.com.finework.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAll() {
        return repo.findAllByOrderByNome();

    }

}