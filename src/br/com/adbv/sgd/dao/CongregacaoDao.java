package br.com.adbv.sgd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.enuns.TipoCongregacao;
import br.com.adbv.sgd.model.Congregacao;

@Repository("congregacaoDao")
@Transactional
public class CongregacaoDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Congregacao> listAll() {
		return entityManager.createQuery("from Congregacao", Congregacao.class)
				.getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Congregacao> buscaPorNome(String nome) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Congregacao.class);		
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		return criteria.list();
	}	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean buscaPorTipoENome(String nome, TipoCongregacao tipoCongregacao) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Congregacao.class)		
									.add(Restrictions.eq("nome", nome))
									.add(Restrictions.eq("tipoCongregacao", tipoCongregacao));
		return criteria.uniqueResult() != null;
	}	
	
	public void save(Congregacao congregacao) {
		entityManager.persist(congregacao);
	}

	public void update(Congregacao congregacao) {
		entityManager.merge(congregacao);
	}

	public void delete(Congregacao congregacao) {
		entityManager.remove(entityManager.merge(congregacao));
	}
	
	public List<Congregacao> getListaCongregacoes(){
		return listAll();
	}
	

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Congregacao load(Long id) {
		return entityManager.find(Congregacao.class, id);
	}	

}
