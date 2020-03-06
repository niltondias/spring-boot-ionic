package br.com.finework.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.finework.cursomc.domain.Cliente;
import br.com.finework.cursomc.domain.enums.TipoCliente;
import br.com.finework.cursomc.dto.ClienteNewDTO;
import br.com.finework.cursomc.repositories.ClienteRepository;
import br.com.finework.cursomc.resources.exception.FieldMessage;
import br.com.finework.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Autowired
    ClienteRepository repo;

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if( objDto.getTipo() == TipoCliente.PESSOAFISICA.getCod() && !BR.isValidCPF(objDto.getCpfOuCnpj()) ) {
            list.add( new FieldMessage("CpfOuCnpj", "CPF inválido"));
        }

        if( objDto.getTipo() == TipoCliente.PESSOAJURIDICA.getCod() && !BR.isValidCNPJ(objDto.getCpfOuCnpj()) ) {
            list.add( new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());

        if(aux != null) {
            list.add( new FieldMessage("email", "Email já cadastrado"));
            
        }
            
        // inclua os testes aqui, inserindo erros na lista
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}