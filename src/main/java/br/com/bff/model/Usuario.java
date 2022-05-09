package br.com.bff.model;

import java.time.LocalDate;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    //FIXME add atributos apra o usuario
    // nome, data de cadastro, data de nascimento, cpf, email, telefone, data de atualização, id
    // criar notações de geet e set build to string usando o lombok

	@Id
	private Long id;
	
	@NotNull
	private String nome;
	
	@CPF
	private String cpf;
	
	@Email
	private String email;
	
	private String telefone;
	
	private LocalDate dataAtualizacao;
}
