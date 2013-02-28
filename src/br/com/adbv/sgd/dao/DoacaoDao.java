package br.com.adbv.sgd.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Campanha;
import br.com.adbv.sgd.model.Congregacao;
import br.com.adbv.sgd.model.Doacao;
import br.com.adbv.sgd.model.Semeador;

@Repository("doacaoDao")
@Transactional
public class DoacaoDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doacao> listAll() {
		return entityManager.createQuery("from Doacao order by dataLancamento desc", Doacao.class)
				.getResultList();
	}

	public void save(Doacao doacao) {
		entityManager.persist(doacao);
	}

	public void update(Doacao doacao) {
		entityManager.merge(doacao);
	}

	public void delete(Doacao doacao) {
		entityManager.remove(entityManager.merge(doacao));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doacao> getListaDoacoes() {
		return listAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Doacao load(Long id) {
		return entityManager.find(Doacao.class, id);
	}

	public Double saldoPorCampanha(Long id) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Doacao.class);
		criteria.add(Restrictions.eq("campanha.id", id));

		Double saldo = (Double) criteria
				.setProjection(Projections.sum("valor")).uniqueResult();
		return saldo;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doacao> listaLancamentos(Semeador semeador,
			Congregacao congregacao, Campanha campanha, Date referencia,
			Date inicio, Date fim) {

		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Doacao.class);

		if (semeador != null) {
			criteria.add(Restrictions.eq("semeador", semeador));
		}

		if (campanha != null) {
			criteria.add(Restrictions.eq("campanha", campanha));
		}

		if (referencia != null) {
			criteria.add(Restrictions.eq("periodoReferencia", referencia));
		}

		if (inicio != null) {
			criteria.add(Restrictions.between("dataLancamento", inicio,
					fim != null ? fim : new Date()));
		}

		if (congregacao != null) {
			if (semeador == null) {
				criteria.add(
						Restrictions.or(
								Restrictions.eq("congregacao", congregacao), 
								Restrictions.in("semeador",	semeadoresPorCongregacao(congregacao.getId()))
								)
						);
			}
			else {
				criteria.add(Restrictions.eq("congregacao", congregacao));
			}
		}

		criteria.addOrder(Order.asc("congregacao"));
		criteria.addOrder(Order.asc("campanha"));
		criteria.addOrder(Order.asc("dataLancamento"));
		criteria.addOrder(Order.asc("periodoReferencia"));
		criteria.addOrder(Order.asc("semeador"));

		List<Doacao> doacoes = (List<Doacao>) criteria.list();
		return doacoes;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Semeador> semeadoresPorCongregacao(Long id) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Semeador.class);
		criteria.add(Restrictions.eq("congregacao.id", id));

		List<Semeador> semeadores = (List<Semeador>) criteria.list();
		return semeadores;
	}
		
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Semeador getSemeadorByUsuario(Long id) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Semeador.class);
		criteria.add(Restrictions.eq("usuario.id", id));

		return (Semeador) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Doacao> buscaDoacoesPorSemeador(Long id) {
		Session session = ((Session) entityManager.getDelegate());
		Criteria criteria = session.createCriteria(Doacao.class);
		criteria.add(Restrictions.eq("semeador.id", id));

		List<Doacao> doacoes = (List<Doacao>) criteria.list();
		return doacoes;
	}

}
