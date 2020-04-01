package br.com.finework.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.finework.cursomc.domain.Estado;

public class EstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório")
    private String nome;

    public EstadoDTO( Estado obj ) {
        id = obj.getId();
        nome = obj.getNome();
    }

    public EstadoDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}