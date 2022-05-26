package br.com.bff.service;

import br.com.bff.model.Usuario;
import br.com.bff.model.dto.UsuarioRequestDTO;
import br.com.bff.model.enums.UsuarioFildOrdecacao;
import br.com.bff.model.filter.UsuarioFilter;
import br.com.bff.repository.UsuarioRepository;
import br.com.bff.repository.customrepository.UsuarioCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioCustomRepository usuarioCustomRepository;
    private final RelatorioCSVService relatorioCSVService;
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

    public ResponseEntity findByFilter(UsuarioFildOrdecacao usuarioFildOrdecacao, Sort.Direction ordenacao, String nome, String email, String cpf, LocalDate dataCadastroDe, LocalDate dataCadastroAte) {
        List<Usuario> usuarios = usuarioCustomRepository.findByFilter(
                UsuarioFilter.builder()
                        .nome(nome)
                        .email(email)
                        .cpf(cpf)
                        .dataCadastroDe(dataCadastroDe)
                        .dataCadastroAte(dataCadastroAte)
                        .build(),
                        usuarioFildOrdecacao,
                        ordenacao);

        return usuarios.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    public void generateRelatorioCSV(
            UsuarioFildOrdecacao usuarioFildOrdecacao,
            Sort.Direction ordenacao,
            String nome,
            String email,
            String cpf,
            LocalDate dataCadastroDe,
            LocalDate dataCadastroAte,
            HttpServletResponse response,
            List<String> colunas) {

        relatorioCSVService.generateReportUsuarioToCSV(
                response,
                usuarioCustomRepository.findByFilter(
                        UsuarioFilter.builder()
                                .nome(nome)
                                .email(email)
                                .cpf(cpf)
                                .dataCadastroDe(dataCadastroDe)
                                .dataCadastroAte(dataCadastroAte)
                                .build(),
                        usuarioFildOrdecacao,
                        ordenacao),
                colunas);

    }
}