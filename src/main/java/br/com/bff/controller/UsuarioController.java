package br.com.bff.controller;

import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.model.enums.UsuarioFildOrdecacao;
import br.com.bff.service.RelatorioCSVService;
import br.com.bff.service.UsuarioService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "CRUD de usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RelatorioCSVService relatorioCSVService;

    @ApiOperation("Cadastrar novo Usuario")
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        return usuarioService.save(usuarioRequestDTO);
    }

    @ApiOperation("Deletar usuario por id")
    @DeleteMapping
    public ResponseEntity deleteById(@PathVariable Long id){
        return usuarioService.deleteById(id);
    }

    @ApiOperation("Atualizar usuario")
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO,
                                 @PathVariable Long id){
        return usuarioService.update(usuarioRequestDTO, id);
    }

    @ApiOperation("Buscar os usuario por filtro, caso nao passe nenhum filtro ira trazer todos os usuarios da base")
    @GetMapping("/filter")
    public ResponseEntity findByFilter(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) @ApiParam(value = "Data cadastro a partir de || format: dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataCadastroDe,
            @RequestParam(required = false) @ApiParam(value = "Data cadastro ate || format: dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataCadastroAte,
            @RequestParam UsuarioFildOrdecacao usuarioFildOrdecacao,
            @RequestParam Sort.Direction ordenacao){
        return usuarioService.findByFilter(usuarioFildOrdecacao, ordenacao ,nome, email, cpf, dataCadastroDe, dataCadastroAte);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "colunas", value = "Colunas para o relatorio, para selecionar mais de uma coluna seguro o CTRL e selecione as colunas", dataType = "string", allowMultiple= true,
                    allowableValues = "Todas as colunas, Nome,CPF, Email, Telefone, DataAtualizacao, DataCadastro",
                    paramType = "query", defaultValue = "Todas as colunas", required = true)
    })
    @ApiOperation("solicita um relatorio CSV por filtro, caso não seja selecionada nenhuma coluna ele ira trazer todas as colunas")
    @GetMapping(value = "/relatorio-csv-by-filter", produces = "text/csv")
    public void relatorioByFilter(
            HttpServletResponse response,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) @ApiParam(value = "Data cadastro a partir de || format: dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataCadastroDe,
            @RequestParam(required = false) @ApiParam(value = "Data cadastro ate || format: dd/MM/yyyy") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataCadastroAte,
            @RequestParam UsuarioFildOrdecacao usuarioFildOrdecacao,
            @RequestParam Sort.Direction ordenacao,
            @RequestParam(required = false) List<String> colunas){
         usuarioService.generateRelatorioCSV(usuarioFildOrdecacao, ordenacao ,nome, email, cpf, dataCadastroDe, dataCadastroAte, response, colunas);
    }

}
