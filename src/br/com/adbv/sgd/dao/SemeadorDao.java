package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Semeador;

@Repository("semeadorDao")
@Transactional
public class SemeadorDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Semeador> listAll() {
		return entityManager.createQuery("from Semeador where ativo = true", Semeador.class)
				.getResultList();
	}
	
	public boolean verificaCpf(String cpf) {
		Session session = (Session) entityManager.getDelegate();
		
		return session.createCriteria(Semeador.class).add(Restrictions.eq("cpf", cpf)).uniqueResult() != null; 
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Semeador buscaPorCodigo(String codigo) {
		Session session = (Session) entityManager.getDelegate();
		
		return (Semeador) session.createCriteria(Semeador.class).add(Restrictions.eq("codigo", codigo)).uniqueResult(); 
	}

	public void save(Semeador semeador) {
		entityManager.persist(semeador);
	}

	public void update(Semeador semeador) {
		entityManager.merge(semeador);
	}

	public void delete(Semeador semeador) {
		entityManager.remove(entityManager.merge(semeador));
	}

	public List<Semeador> getListaSemeadores() {
		return listAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Semeador load(Long id) {
		return entityManager.find(Semeador.class, id);
	}
	
	
}
