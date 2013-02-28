package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.adbv.sgd.dao.IgrejaDao;
import br.com.adbv.sgd.model.Igreja;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean
public class IgrejaBean {

	private Igreja igreja = new Igreja();
	private List<Igreja> igrejas = new ArrayList<Igreja>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{igrejaDao}")
	private IgrejaDao igrejaDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;

	public void prepareInsert() {
		this.igreja = new Igreja();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}

	public void insert() {
		// if (!bancoJaCadastrado(igreja)) {
		igrejaDao.save(igreja);
		facesUtils.adicionaMensagemDeInformacao("Igreja gravada com sucesso.");		
		lista();
		// } else {
		// facesUtils
		// .adicionaMensagemDeErro("Verifique os dados informados. Existem valores já cadastrados");
		// }

	}

	public List<Igreja> getListaIgrejas() {			
		setState(ESTADO_DE_PESQUISA);
		return igrejaDao.listAll();
	}

	public void lista() {
		igrejas = igrejaDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {		
		igrejaDao.delete(igreja);
		facesUtils.adicionaMensagemDeInformacao("Igreja removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Igreja igreja) {
		this.igreja = igrejaDao.load(igreja.getId()); // evita
		// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		igrejaDao.update(igreja);
		facesUtils
				.adicionaMensagemDeInformacao("Igreja atualizada com sucesso!");
		lista();

	}

	public void voltar() {
		this.igreja = new Igreja();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		lista();
	}

	public boolean isAdicionando() {
		return ESTADO_DE_NOVO.equals(state);
	}

	public boolean isEditando() {
		return ESTADO_DE_EDICAO.equals(state);
	}

	public boolean isPesquisando() {
		return ESTADO_DE_PESQUISA.equals(state);
	}

	public Igreja getIgreja() {
		return igreja;
	}

	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UIForm getForm() {
		return form;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public void setIgrejaDao(IgrejaDao igrejaDao) {
		this.igrejaDao = igrejaDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Igreja> getIgrejas() {
		return igrejas;
	}

	public void setIgrejas(List<Igreja> igrejas) {
		this.igrejas = igrejas;
	}

}
