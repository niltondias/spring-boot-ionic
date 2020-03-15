package br.com.finework.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.finework.cursomc.domain.ItemPedido;
import br.com.finework.cursomc.domain.PagamentoComBoleto;
import br.com.finework.cursomc.domain.Pedido;
import br.com.finework.cursomc.domain.enums.EstadoPagamento;
import br.com.finework.cursomc.repositories.ItemPedidoRepository;
import br.com.finework.cursomc.repositories.PagamentoRepository;
import br.com.finework.cursomc.repositories.PedidoRepository;
import br.com.finework.cursomc.services.exceptions.DataIntegrityException;

@Service
public class PedidoService {

    @Autowired
    private BoletoService boletoService;
    
    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;
    
    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);

        return obj.orElseThrow(() -> new DataIntegrityException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
        
        //return obj.orElse(null);

    }
    
    @Transactional
    public Pedido insert( Pedido obj ) {
        obj.setId( null) ;
        obj.setInstante( new Date() );
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado( EstadoPagamento.PENDENTE );
        obj.getPagamento().setPedido( obj );
        if( obj.getPagamento() instanceof PagamentoComBoleto ) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto( pagto, obj.getInstante() );
        }

        repo.save(obj);
        pagamentoRepository.save( obj.getPagamento()) ;
        for( ItemPedido ip : obj.getItens() ) {
            ip.setDesconto(0.00);
            ip.setProduto(produtoService.find( ip.getProduto().getId() ));
            ip.setPreco( ip.getProduto().getPreco() ) ;
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll( obj.getItens() );

        System.out.println(obj);

        return obj;

    }
}