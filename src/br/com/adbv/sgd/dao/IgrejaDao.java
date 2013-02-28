package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Igreja;

@Repository("igrejaDao")
@Transactional
public class IgrejaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Igreja> listAll() {
		return entityManager.createQuery("from Igreja", Igreja.class)
				.getResultList();
	}

	public void save(Igreja igreja) {
		entityManager.persist(igreja);
	}

	public void update(Igreja igreja) {
		entityManager.merge(igreja);
	}

	public void delete(Igreja igreja) {
		entityManager.remove(entityManager.merge(igreja));
	}
		

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Igreja load(Long id) {
		return entityManager.find(Igreja.class, id);
	}	

}
