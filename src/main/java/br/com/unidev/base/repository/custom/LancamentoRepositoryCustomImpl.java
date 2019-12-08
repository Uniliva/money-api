package br.com.unidev.base.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;
import br.com.unidev.base.repository.projection.ResumoLancamento;

public class LancamentoRepositoryCustomImpl implements LancamentoRepositoryCustom {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> buscarPorFiltro(LancamentoFiltro filtro, Pageable pagina) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> lancamentoQuery = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filtro, lancamentoQuery, builder);

		criteria.where(predicates);
		criteria.orderBy(builder.asc(lancamentoQuery.get("codigo")));

		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		adicionarResticaoDePaginacao(query, pagina);

		return new PageImpl<>(query.getResultList(), pagina, total(filtro));

	}
	

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFiltro filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get("codigo")
				, root.get("descricao")
				, root.get("dataVencimento")
				, root.get("dataPagamento")
				, root.get("valor")
				, root.get("tipo")
				, root.get("categoria").get("nome")
				, root.get("pessoa").get("nome")));
		
		Predicate[] predicates = criarRestricoes(filtro, root, builder);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarResticaoDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filtro));
	}

	private long total(LancamentoFiltro filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> lancamentoQuery = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filtro, lancamentoQuery, builder);

		criteria.where(predicates);

		criteria.select(builder.count(lancamentoQuery));

		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarResticaoDePaginacao(TypedQuery<?> query, Pageable pagina) {
		int paginaAtual = pagina.getPageNumber();
		int totalRegistroPorPagina = pagina.getPageSize();
		int primeiroRegistroDaPagina = 0;

		if (paginaAtual != 1)
			primeiroRegistroDaPagina = totalRegistroPorPagina * paginaAtual;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}

	private Predicate[] criarRestricoes(LancamentoFiltro filtro, Root<Lancamento> lancamentoQuery,
			CriteriaBuilder bulder) {

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filtro.getDescricao())) {
			predicates.add(
					bulder.like(bulder.lower(lancamentoQuery.get("descricao")), "%" + filtro.getDescricao() + "%"));
		}

		if (!StringUtils.isEmpty(filtro.getDataLancamentoDe())) {
			predicates.add(
					bulder.greaterThanOrEqualTo(lancamentoQuery.get("dataVencimento"), filtro.getDataLancamentoDe()));
		}

		if (!StringUtils.isEmpty(filtro.getDataLancamentoAte())) {
			predicates
					.add(bulder.lessThanOrEqualTo(lancamentoQuery.get("dataVencimento"), filtro.getDataLancamentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}


}
