package br.com.finework.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Cidade;
import br.com.finework.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findByEstado( Integer estadoId) {
        return repo.findCidades(estadoId);
    }
    
}