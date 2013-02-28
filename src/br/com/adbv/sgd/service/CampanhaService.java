package br.com.adbv.sgd.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import br.com.adbv.sgd.model.Doacao;

@Component("campanhaService")
public class CampanhaService {

	@PersistenceContext
	private EntityManager entityManager;

	private Double getSaldo(Long id){
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Doacao.class);
		criteria.add(Restrictions.eq("campanha.id", id));
		
		Double saldo = (Double) criteria.setProjection(Projections.sum("valor")).uniqueResult();
		return saldo;
	}
	
	public static Double saldoPorCampanha(Long id) {
		return new CampanhaService().getSaldo(id);				
	}
}
