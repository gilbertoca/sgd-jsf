package br.com.adbv.sgd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adbv.sgd.dao.UsuarioDao;
import br.com.adbv.sgd.model.Usuario;


/*
 * Serviço não mais utilizado
 */
@Service("autenticador")
public class AutenticadorImpl implements Autenticador{

	private UsuarioDao usuarioDao;

	@Autowired
	public AutenticadorImpl(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Override
	public Usuario autentica(String login, String senha) {
		Usuario usuario = usuarioDao.findBy(login, senha);
		return usuario;
	}

}
