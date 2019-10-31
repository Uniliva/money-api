package br.com.unidev.base.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.unidev.base.model.Lancamento;
import br.com.unidev.base.model.LancamentoFiltro;

public class LancamentoRepositoryCustomImpl implements LancamentoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;



@Override
public List<Lancamento> buscarPorFiltro(LancamentoFiltro filtro) {
	CriteriaBuilder bulder = entityManager.getCriteriaBuilder();
	CriteriaQuery<Lancamento> query = bulder.createQuery(Lancamento.class);
	Root<Lancamento> lancamentoQuery = query.from(Lancamento.class);
	
	Predicate [] predicates = criarRestricoes(filtro, lancamentoQuery, bulder);	
	
	 query.where(predicates);	
	
	return entityManager.createQuery(query).getResultList();
}

private Predicate[] criarRestricoes(LancamentoFiltro filtro, Root<Lancamento> lancamentoQuery,
		CriteriaBuilder bulder) {
	
	List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filtro.getDescricao())) {
			predicates.add(bulder.like(bulder.lower(lancamentoQuery.get("descricao")), "%"+filtro.getDescricao()+"%"));
		}

		if (!StringUtils.isEmpty(filtro.getDataLancamentoDe())) {
			predicates.add(bulder.greaterThanOrEqualTo(lancamentoQuery.get("dataVecimento"), filtro.getDataLancamentoDe()));
		}

		if (!StringUtils.isEmpty(filtro.getDataLancamentoAte())) {
			predicates.add(bulder.lessThanOrEqualTo(lancamentoQuery.get("dataVecimento"), filtro.getDataLancamentoAte()));
		}

	return predicates.toArray(new Predicate[predicates.size()]);
}

}

