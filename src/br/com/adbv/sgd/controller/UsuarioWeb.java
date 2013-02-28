package br.com.adbv.sgd.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.adbv.sgd.model.Usuario;

@SuppressWarnings("serial")
@Component
@Scope("session")
public class UsuarioWeb implements Serializable {

	private Usuario usuario;
	private String perfil;

	public void loga(Usuario usuario) {
		this.usuario = usuario;
		this.perfil = usuario.getRoles().get(0).getName();
	}
	
	public boolean isUsuarioLimitado(){
		return this.perfil.equalsIgnoreCase("SEMEADOR");
	}
	
	public boolean isUsuarioAdmin(){
		return this.perfil.equalsIgnoreCase("ADMINISTRADOR");
	}

	@Deprecated
	public void logout() {
		this.usuario = null;
	}
	
	public boolean isLogado() {
		return this.usuario != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getPerfil() {
		return perfil;
	}

}
