package br.com.adbv.sgd.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.adbv.sgd.model.Mensagem;

public class EmailUtil {

	 private static final String HOSTNAME = "smtp.gmail.com";
	 private static final String USERNAME = "lsaviofreitas";
	 private static final String PASSWORD = "P4$$W0rd";
	 private static final String EMAILORIGEM = "lsaviofreitas@gmail.com";

	 public static Email conectaEmail() throws EmailException {
	 Email email = new SimpleEmail();
	 email.setHostName(HOSTNAME);
	 email.setSmtpPort(465);
	 email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
	 email.setTLS(true);
	 email.setFrom(EMAILORIGEM);
	 return email;
	 }

	 public static void enviaEmail(Mensagem mensagem) throws EmailException {
	 Email email = new SimpleEmail();
	 email = conectaEmail();
	 email.setFrom(mensagem.getEmail(), mensagem.getNome());
	 email.setSubject(mensagem.getAssunto());
	 email.setMsg(mensagem.getMensagem());
	 email.addTo("lsaviofreitas@gmail.com");
	 email.send();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"E-mail enviado com sucesso", "Informação"));
	}
}
