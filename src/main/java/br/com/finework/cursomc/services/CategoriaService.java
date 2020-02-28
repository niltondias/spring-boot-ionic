package br.com.finework.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Categoria;
import br.com.finework.cursomc.repositories.CategoriaRepository;
import br.com.finework.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto nÃ£o encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
        
        //return obj.orElse(null);

    }

    public Categoria insert( Categoria obj ) {
        obj.setId(null);  // colocando null o método save considera um novo objeto no banco.
        return repo.save(obj);
    }

    public Categoria update( Categoria obj ) {
        find(obj.getId());  
        return repo.save(obj);
    }

}