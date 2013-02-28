package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.SemeadorDao;
import br.com.adbv.sgd.model.Semeador;

@FacesConverter(forClass=Semeador.class)
public class SemeadorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		SemeadorDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{semeadorDao}", SemeadorDao.class);

		Semeador semeador = dao.load(id);
		return semeador;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Semeador semeador = (Semeador) value;
		return (semeador.getId() == null)? "" : semeador.getId().toString();		
	}
}
