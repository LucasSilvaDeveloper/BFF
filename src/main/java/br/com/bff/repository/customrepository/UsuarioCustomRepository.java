package br.com.bff.repository.customrepository;

import br.com.bff.model.Usuario;
import br.com.bff.model.enums.UsuarioFildOrdecacao;
import br.com.bff.model.filter.UsuarioFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class UsuarioCustomRepository {

    private final EntityManager em;

    public List<Usuario> findByFilter(UsuarioFilter usuarioFilter, UsuarioFildOrdecacao usuarioFildOrdecacao, Sort.Direction ordenacao){

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> from = query.from(Usuario.class);

        Predicate filtros = builder.and();

        if (StringUtils.hasText(usuarioFilter.getNome())) {
            filtros = builder.and(filtros, builder.like(builder.lower(from.get("nome")), builder.lower(builder.literal("%"+usuarioFilter.getNome()+"%"))));
        }

        if (StringUtils.hasText(usuarioFilter.getCpf())) {
            filtros = builder.and(filtros, builder.like(from.get("cpf"), builder.literal("%"+usuarioFilter.getCpf()+"%")));
        }

        if (StringUtils.hasText(usuarioFilter.getEmail())) {
            filtros = builder.and(filtros, builder.like(builder.lower(from.get("email")), builder.lower(builder.literal("%"+usuarioFilter.getEmail()+"%"))));
        }

        if (Objects.nonNull(usuarioFilter.getDataCadastroDe()) || Objects.nonNull(usuarioFilter.getDataCadastroAte())){

            if (Objects.nonNull(usuarioFilter.getDataCadastroDe()) && Objects.nonNull(usuarioFilter.getDataCadastroAte()))
                filtros = builder.and(builder.between(from.get("dataCadastro"), usuarioFilter.getDataCadastroDe().atTime(0, 0, 0), usuarioFilter.getDataCadastroAte().atTime(23, 59, 59)));
            else if (Objects.nonNull(usuarioFilter.getDataCadastroDe())){
                filtros = builder.and(builder.greaterThanOrEqualTo(from.get("dataCadastro"), usuarioFilter.getDataCadastroDe().atTime(0,0, 0)));
            }else if (Objects.nonNull(usuarioFilter.getDataCadastroAte())){
                filtros = builder.and(builder.lessThanOrEqualTo(from.get("dataCadastro"), usuarioFilter.getDataCadastroAte().atTime(23,59, 59)));
            }
        }

        if (Objects.nonNull(usuarioFilter.getDataAtualizacaoDe()) || Objects.nonNull(usuarioFilter.getDataAtualizacaoAte())){

            if (Objects.nonNull(usuarioFilter.getDataAtualizacaoDe()) && Objects.nonNull(usuarioFilter.getDataAtualizacaoAte()))
                filtros = builder.and(builder.between(from.get("dataAtualizacao"), usuarioFilter.getDataAtualizacaoDe().atTime(0, 0, 0), usuarioFilter.getDataAtualizacaoAte().atTime(23, 59, 59)));
            else if (Objects.nonNull(usuarioFilter.getDataCadastroDe())){
                filtros = builder.and(builder.greaterThanOrEqualTo(from.get("dataAtualizacao"), usuarioFilter.getDataAtualizacaoDe().atTime(0,0, 0)));
            }else if (Objects.nonNull(usuarioFilter.getDataAtualizacaoAte())){
                filtros = builder.and(builder.lessThanOrEqualTo(from.get("dataAtualizacao"), usuarioFilter.getDataAtualizacaoAte().atTime(23,59, 59)));
            }
        }

        query.where(filtros);
        query.orderBy(Sort.Direction.DESC.equals(ordenacao) ? builder.desc(from.get(usuarioFildOrdecacao.getDescricao())): builder.asc(from.get(usuarioFildOrdecacao.getDescricao())));
        TypedQuery<Usuario> createQuery = em.createQuery(query);

        return createQuery.getResultList();
    }
}
