package br.com.finework.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Categoria;
import br.com.finework.cursomc.dto.CategoriaDTO;
import br.com.finework.cursomc.repositories.CategoriaRepository;
import br.com.finework.cursomc.services.exceptions.DataIntegrityException;
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
        Categoria newObj = find(obj.getId());  
        updateData(newObj, obj);
        return repo.save(newObj) ;
    }

    public void delete( Integer id ) {
        find( id );
        try {
            repo.deleteById(id);    
        } catch ( DataIntegrityViolationException e ) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possue produtos");
        }
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Page<Categoria> findPage( Integer page, Integer linesPerPage, String orderBy, String direction ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDTO( CategoriaDTO categoriaDTO ) {
        return new Categoria( categoriaDTO.getId(), categoriaDTO.getNome() );
    }

    private void updateData(Categoria newObj, Categoria obj ) {
        newObj.setNome(obj.getNome());
    }

}