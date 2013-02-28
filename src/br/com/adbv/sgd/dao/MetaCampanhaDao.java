package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.MetaCampanha;

@Repository("metaCampanhaDao")
@Transactional
public class MetaCampanhaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MetaCampanha> listAll() {
		return entityManager.createQuery("from MetaCampanha", MetaCampanha.class)
				.getResultList();
	}

	public void save(MetaCampanha metaCampanha) {
		entityManager.persist(metaCampanha);
	}

	public void update(MetaCampanha metaCampanha) {
		entityManager.merge(metaCampanha);
	}

	public void delete(MetaCampanha metaCampanha) {
		entityManager.remove(entityManager.merge(metaCampanha));
	}
	
	public List<MetaCampanha> getListaCampanhas(){
		return listAll();
	}
	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MetaCampanha load(Long id) {
		return entityManager.find(MetaCampanha.class, id);
	}	
}
