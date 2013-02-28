package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.CampanhaDao;
import br.com.adbv.sgd.model.Campanha;

@FacesConverter(forClass = Campanha.class)
public class CampanhaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		CampanhaDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{campanhaDao}", CampanhaDao.class);

		Campanha campanha = dao.load(id);
		return campanha;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Campanha campanha = (Campanha) value;
		return campanha.getId().toString();
	}

}
