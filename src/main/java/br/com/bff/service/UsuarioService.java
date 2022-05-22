package br.com.bff.service;

import br.com.bff.model.Usuario;
import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity save(UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioRepository
                        .save(modelMapper.map(usuarioRequestDTO, Usuario.class)));
    }

    public ResponseEntity deleteById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return usuario.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(usuario.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");

    }

    public ResponseEntity update(UsuarioRequestDTO usuarioRequestDTO, Long id){
        var usuarioAUX = usuarioRepository.findById(id);

        if (usuarioAUX.isPresent()){
            BeanUtils.copyProperties(usuarioRequestDTO, usuarioAUX.get(), "id");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuarioRepository.save(usuarioAUX.get()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
    }

    public ResponseEntity findAll(){
        var usuarios = usuarioRepository.findAll();

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
}