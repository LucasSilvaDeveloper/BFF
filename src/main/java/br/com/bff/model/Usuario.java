package br.com.bff.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@CPF
	private String cpf;
	
	@Email
	private String email;
	
	private String telefone;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@CreationTimestamp
	@Column(name = "data_cadastro")
	private LocalDateTime dataCadastro;
}
