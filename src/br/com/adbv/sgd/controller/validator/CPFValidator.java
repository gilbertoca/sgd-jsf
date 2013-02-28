package br.com.adbv.sgd.controller.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.adbv.sgd.util.CustomValidator;

@FacesValidator("cpfValidator")
public class CPFValidator implements Validator {

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {

		String cpf = (String) value;
		if (cpf != null) {
			if (!CustomValidator.validaCpf(cpf.replaceAll("\\D", ""))) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "CPF Inv�lido!", ""));
			}
		}

	}
}
