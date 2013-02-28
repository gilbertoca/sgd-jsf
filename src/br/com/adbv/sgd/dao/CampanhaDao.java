package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Campanha;

@Repository("campanhaDao")
@Transactional
public class CampanhaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Campanha> listAll() {
		return entityManager.createQuery("from Campanha where ativo = true", Campanha.class)
				.getResultList();
	}

	public void save(Campanha campanha) {
		entityManager.persist(campanha);
	}

	public void update(Campanha campanha) {
		entityManager.merge(campanha);
	}

	public void delete(Campanha campanha) {
		entityManager.remove(entityManager.merge(campanha));
	}
	
	public List<Campanha> getListaCampanhas(){
		return listAll();
	}		

	public Double saldoPorProjeto(Long id) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Campanha.class);
		criteria.add(Restrictions.eq("projeto.id", id));
		
		Double saldo = (Double) criteria.setProjection(Projections.sum("saldo")).uniqueResult();
		return saldo;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Campanha load(Long id) {
		return entityManager.find(Campanha.class, id);
	}	
}
