package br.com.adbv.sgd;

import javax.persistence.EntityManager;

import br.com.adbv.sgd.model.Usuario;
import br.com.adbv.sgd.util.JpaUtils;

public class Main {

	public static void main(String[] args) {
		
		JpaUtils utils = JpaUtils.newInstance();
		EntityManager entityManager = utils.createEntityManager();
		
		Usuario usuario = entityManager.find(Usuario.class, 1l);
		
		System.out.println(usuario.getUsername());
	}
	
	
}
