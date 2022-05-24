package br.com.bff.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioRequestDTO {

    @NotNull
    private String nome;

    @CPF
    private String cpf;

    @Email
    private String email;

    private String telefone;

}
