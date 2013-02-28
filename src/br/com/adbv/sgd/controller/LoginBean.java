package br.com.adbv.sgd.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.adbv.sgd.dao.UsuarioDao;
import br.com.adbv.sgd.model.Usuario;
import br.com.adbv.sgd.service.AuthenticationService;
import br.com.adbv.sgd.util.Bootstrap;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

	//private static final String INDEX = "/pages/index?faces-redirect=true";
//	private static final String LOGIN = "/auth/login?faces-redirect=true";
	private static final String SITE = "/pages/site?faces-redirect=true";

	private String login;
	private String senha;

	@ManagedProperty("#{authenticationService}")
	private AuthenticationService authenticationService;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{usuarioWeb}")
	private UsuarioWeb usuarioWeb;
	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;
	@ManagedProperty("#{bootstrap}")
	private Bootstrap bootstrap;

	@PostConstruct
	public void prepareBootstrap() {
		if (usuarioDao.listAll().size() == 0) {
			bootstrap.setup();
		}
	}

	public String logar() {
		Usuario usuario = null;
		boolean usuarioAutenticado = authenticationService.login(login, senha);

		if (!usuarioAutenticado) {
			facesUtils.adicionaMensagemDeErro("Usuário ou senha inválida!");
			return null;
		}

		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				usuario = (Usuario) authentication.getPrincipal();
				usuarioWeb.loga(usuario);
			}
		}
		
		//if(usuarioWeb.isUsuarioLimitado()){
			return SITE;
		//}

		//return INDEX;
	}

	public String logout() {
		authenticationService.logout();
		return SITE;
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

}
