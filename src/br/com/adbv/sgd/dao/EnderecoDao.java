package br.com.adbv.sgd.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.adbv.sgd.model.Endereco;

@Repository("enderecoDao")
@Transactional
public class EnderecoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Map<String, String> buscarPorCep(String cep) {
		Map<String, String> resultado = new HashMap<String, String>();

		Session session = ((Session) entityManager.getDelegate());
		Endereco endereco = (Endereco) session.createCriteria(Endereco.class)
				.add(Restrictions.eq("cep", cep.replaceAll("\\D", "")))
				.uniqueResult();

		if (endereco == null) {
			endereco = new Endereco();
		}
		resultado.put("logradouro", endereco.getLogradouro());
		resultado.put("bairro", endereco.getBairro());
		resultado.put("cidade", endereco.getCidade());
		resultado.put("uf", endereco.getUf());

		return resultado;
	}
}
