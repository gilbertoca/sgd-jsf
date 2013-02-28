package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Projeto;

@Repository("projetoDao")
@Transactional
public class ProjetoDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Projeto> listAll() {
		return entityManager.createQuery("from Projeto", Projeto.class)
				.getResultList();
	}

	public void save(Projeto projeto) {
		entityManager.persist(projeto);
	}

	public void update(Projeto projeto) {
		entityManager.merge(projeto);
	}

	public void delete(Projeto projeto) {
		entityManager.remove(entityManager.merge(projeto));
	}
	
	public List<Projeto> getListaProjetos(){
		return listAll();
	}
	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Projeto load(Long id) {
		return entityManager.find(Projeto.class, id);
	}	
}
