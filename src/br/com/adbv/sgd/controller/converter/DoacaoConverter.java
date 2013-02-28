package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.DoacaoDao;
import br.com.adbv.sgd.model.Doacao;

@FacesConverter(forClass=Doacao.class)
public class DoacaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		DoacaoDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{doacaoDao}", DoacaoDao.class);

		Doacao doacao = dao.load(id);
		return doacao;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Doacao doacao = (Doacao) value;
		return doacao.getId().toString();
	}

}
