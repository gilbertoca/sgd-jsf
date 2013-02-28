package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.adbv.sgd.dao.CampanhaDao;
import br.com.adbv.sgd.dao.DoacaoDao;
import br.com.adbv.sgd.model.Campanha;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean
public class CampanhaBean {

	private Campanha campanha = new Campanha();
	private List<Campanha> campanhas = new ArrayList<Campanha>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{campanhaDao}")
	private CampanhaDao campanhaDao;
	@ManagedProperty("#{doacaoDao}")
	private DoacaoDao doacaoDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;

	public void prepareInsert() {
		this.campanha = new Campanha();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}
	

	public void insert() {

		campanhaDao.save(campanha);
		facesUtils
				.adicionaMensagemDeInformacao("Campanha gravada com sucesso.");
		facesUtils.cleanSubmittedValues(form);
		lista();

	}

	public List<Campanha> getListaCampanhas() {
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_PESQUISA);
		List<Campanha> campanhas = campanhaDao.listAll();
		for (Campanha c : campanhas) {
			Double saldoCampanha = 0.0;
			Double situacao = 0.0;
			Double saldo = doacaoDao.saldoPorCampanha(c.getId());
			saldoCampanha = saldo != null ? saldo : saldoCampanha;
			situacao = saldoCampanha != 0.0 ? (saldoCampanha * 100) / c.getMeta() : situacao; 
			c.setSaldo(saldoCampanha);
			c.setSituacao(situacao);
		}
		return campanhas;
	}

	public void lista() {
		campanhas = getListaCampanhas();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		campanhaDao.delete(campanha);
		facesUtils
				.adicionaMensagemDeInformacao("Campanha removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Campanha campanha) {
		this.campanha = campanhaDao.load(campanha.getId()); // evita
		// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		campanhaDao.update(campanha);
		facesUtils
				.adicionaMensagemDeInformacao("Campanha atualizada com sucesso!");
		lista();

	}

	public void voltar() {
		this.campanha = new Campanha();
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

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
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

	public void setCampanhaDao(CampanhaDao campanhaDao) {
		this.campanhaDao = campanhaDao;
	}

	public void setDoacaoDao(DoacaoDao doacaoDao) {
		this.doacaoDao = doacaoDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

}
