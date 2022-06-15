package br.com.bff.model.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel
public enum UsuarioFieldOrdecacao {
    ID("id"),
    NOME("nome"),
    DATA_CADASTRO("dataCadastro");

    public final String descricao;

    private UsuarioFieldOrdecacao(String descricao){
        this.descricao = descricao;
    }

}
