package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Galeria;

@Repository("galeriaDao")
@Transactional
public class GaleriaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Galeria> listAll() {
		return entityManager.createQuery("from Galeria where ativo = true", Galeria.class)
				.getResultList();
	}

	public void save(Galeria galeria) {
		galeria.setAtivo(true);
		entityManager.persist(galeria);
	}

	public void update(Galeria galeria) {
		entityManager.merge(galeria);
	}

	public void delete(Galeria galeria) {
		galeria.setAtivo(false);
		update(galeria);
		//entityManager.remove(entityManager.merge(galeria));
	}
	
	public List<Galeria> getListaGalerias(){
		return listAll();
	}
	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Galeria load(Long id) {
		return entityManager.find(Galeria.class, id);
	}	

}
