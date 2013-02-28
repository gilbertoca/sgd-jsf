package br.com.adbv.sgd.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adbv.sgd.dao.RoleDao;
import br.com.adbv.sgd.dao.UsuarioDao;
import br.com.adbv.sgd.model.Role;
import br.com.adbv.sgd.model.Usuario;

@Service("bootstrap")
public class Bootstrap {

	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private RoleDao perfilDao;

	public void setup() {

		carregaUsuario();

	}

	public void carregaUsuario() {

		Role user = perfilDao.load("SEMEADOR");
		if (user == null) {
			LogUtil.logInfo("Criando perfil SEMEADOR");
			user = new Role();
			user.setName("SEMEADOR");
			perfilDao.save(user);
			LogUtil.logInfo("Perfil SEMEADOR criado");
		}

		Role especial = perfilDao.load("ATENDENTE");
		if (especial == null) {
			LogUtil.logInfo("Criando perfil ATENDENTE");
			especial = new Role();
			especial.setName("ATENDENTE");
			perfilDao.save(especial);
			LogUtil.logInfo("Perfil ATENDENTE criado");
		}

		Role admin = perfilDao.load("ADMINISTRADOR");
		if (admin == null) {
			LogUtil.logInfo("Criando perfil AMINISTRADOR");
			admin = new Role();
			admin.setName("ADMINISTRADOR");
			perfilDao.save(admin);
			LogUtil.logInfo("Perfil AMINISTRADOR criado");
		}

		List<Role> roles = new ArrayList<Role>();
		roles.add(admin);

		LogUtil.logInfo("Criando usuário ADMIN");
		Usuario usuario = new Usuario();
		usuario.setNome("USUÁRIO ADMINISTRADOR");
		usuario.setUsername("admin");
		usuario.setPassword("admin");
		usuario.setEnabled(true);
		usuario.setRoles(roles);		

		usuarioDao.save(usuario);
		LogUtil.logInfo("Usuário ADMINISTRADOR criado");
	}	

}
