package br.com.bff.model.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel
public enum UsuarioFildOrdecacao {
    ID("id"),
    NOME("nome"),
    DATA_CADASTRO("dataCadastro");

    public final String descricao;

    private UsuarioFildOrdecacao(String descricao){
        this.descricao = descricao;
    }

}
