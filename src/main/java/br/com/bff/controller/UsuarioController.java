package br.com.bff.controller;

import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "CRUD de usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

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

    @ApiOperation("Buscar todos os usuario")
    @GetMapping
    public ResponseEntity findAll(){
        return usuarioService.findAll();
    }
}
