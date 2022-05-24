package br.com.bff.model.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UsuarioFilter {

    private String nome;

    private String email;

    private LocalDate dataCadastroDe;

    private LocalDate dataCadastroAte;

    private LocalDate dataAtualizacaoDe;

    private LocalDate dataAtualizacaoAte;

}
