package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.CongregacaoDao;
import br.com.adbv.sgd.model.Congregacao;

@FacesConverter(forClass = Congregacao.class)
public class CongregacaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;

		Long id = new Long(value);

		CongregacaoDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{congregacaoDao}", CongregacaoDao.class);

		Congregacao congregacao = dao.load(id);
		return congregacao;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Congregacao congregacao = (Congregacao) value;
		return (congregacao.getId() == null)? "" : congregacao.getId().toString();
	}

}
