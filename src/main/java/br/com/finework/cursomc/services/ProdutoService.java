package br.com.finework.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.finework.cursomc.domain.Categoria;
import br.com.finework.cursomc.domain.Pedido;
import br.com.finework.cursomc.domain.Produto;
import br.com.finework.cursomc.repositories.CategoriaRepository;
import br.com.finework.cursomc.repositories.ProdutoRepository;
import br.com.finework.cursomc.services.exceptions.DataIntegrityException;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);

        return obj.orElseThrow(() -> new DataIntegrityException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
        
        //return obj.orElse(null);

    } 
    
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
        
    
    }
}

