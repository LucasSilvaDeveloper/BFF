package br.com.bff.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
