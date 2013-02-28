package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.MetaCampanhaDao;
import br.com.adbv.sgd.model.MetaCampanha;

@FacesConverter(forClass = MetaCampanha.class)
public class MetaCampanhaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		MetaCampanhaDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{metaCampanhaDao}", MetaCampanhaDao.class);

		MetaCampanha meta = dao.load(id);
		return meta;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		MetaCampanha meta = (MetaCampanha) value;
		return meta.getId().toString();
	}

}
