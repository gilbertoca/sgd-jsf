package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.IgrejaDao;
import br.com.adbv.sgd.model.Igreja;

@FacesConverter(forClass=Igreja.class)
public class IgrejaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		IgrejaDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{igrejaDao}", IgrejaDao.class);

		Igreja igreja = dao.load(id);
		return igreja;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Igreja igreja = (Igreja) value;
		return igreja.getId().toString();
	}
}
