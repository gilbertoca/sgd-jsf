package br.com.adbv.sgd.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.adbv.sgd.dao.RoleDao;
import br.com.adbv.sgd.model.Role;

@FacesConverter(forClass = Role.class)
public class RoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value == null)
			return null;		

		RoleDao dao = ctx.getApplication().evaluateExpressionGet(ctx,
				"#{roleDao}", RoleDao.class);

		Role role = dao.load(value);
		return role;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value == null) {
			return null;
		}

		Role role = (Role) value;
		return role.getName();
	}

}
