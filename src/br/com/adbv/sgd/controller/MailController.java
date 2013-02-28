package br.com.adbv.sgd.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.adbv.sgd.model.Mensagem;
import br.com.adbv.sgd.util.EmailUtil;

@ManagedBean
public class MailController {

	private Mensagem mensagem = new Mensagem();

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public void enviaEmail() {
		try {
			EmailUtil.enviaEmail(mensagem);
		} catch (EmailException ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro! Occoreu um erro ao enviar a mensagem.",
							"Erro"));
			Logger.getLogger(MailController.class.getName()).log(Level.ERROR,null, ex);
		}
	}

	public void limpaCampos() {
		mensagem = new Mensagem();
	}

}
