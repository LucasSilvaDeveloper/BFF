package br.com.bff.controller;

import br.com.bff.model.Usuario;
import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
