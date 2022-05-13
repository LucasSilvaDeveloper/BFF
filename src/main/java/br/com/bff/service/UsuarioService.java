package br.com.bff.service;

import br.com.bff.config.ModelMapperConfig;
import br.com.bff.model.Usuario;
import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity save(UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(modelMapper.map(usuarioRequestDTO, Usuario.class)));
    }

}
