package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.adbv.sgd.dao.CongregacaoDao;
import br.com.adbv.sgd.enuns.TipoCongregacao;
import br.com.adbv.sgd.model.Congregacao;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean
public class CongregacaoBean {

	private Long id;
	private Congregacao congregacao = new Congregacao();
	private List<Congregacao> congregacoes = new ArrayList<Congregacao>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{congregacaoDao}")
	private CongregacaoDao congregacaoDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;

	public void prepareInsert() {
		this.congregacao = new Congregacao();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}
	
	public boolean congregacaoJaExiste() {
		return congregacaoDao.buscaPorTipoENome(congregacao.getNome(), congregacao.getTipoCongregacao());
	}

	public void insert() {
		if(congregacaoJaExiste()) {
			facesUtils
			.adicionaMensagemDeErro("Já existe uma congregação com este nome.");
			return;
		}
		congregacaoDao.save(congregacao);
		facesUtils
				.adicionaMensagemDeInformacao("Congregacao gravada com sucesso.");
		lista();

	}
	
	public void init() {	
		if(id != null) {			
			congregacao = congregacaoDao.load(id);					
			setState(ESTADO_DE_EDICAO);
			id = null;
		}
				
	}

	public Map<String, TipoCongregacao> getTipoCongregacaoValues() {
		return TipoCongregacao.getTipoCongregacaoValues();
	}

	public List<Congregacao> getListaCongregacoes() {
		setState(ESTADO_DE_PESQUISA);
		return congregacaoDao.listAll();
	}

	public void lista() {
		congregacoes = congregacaoDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		congregacaoDao.delete(congregacao);
		facesUtils
				.adicionaMensagemDeInformacao("Congregacao removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Congregacao congregacao) {
		this.congregacao = congregacaoDao.load(congregacao.getId()); // evita
		// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		if(congregacaoJaExiste()) {
			facesUtils
			.adicionaMensagemDeErro("Já existe uma congregação com este nome.");
			return;
		}
		congregacaoDao.update(congregacao);
		facesUtils
				.adicionaMensagemDeInformacao("Congregacao atualizada com sucesso!");
		lista();

	}

	public void voltar() {
		this.congregacao = new Congregacao();
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

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
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

	public void setCongregacaoDao(CongregacaoDao congregacaoDao) {
		this.congregacaoDao = congregacaoDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Congregacao> getCongregacoes() {
		return congregacoes;
	}

	public void setCongregacoes(List<Congregacao> congregacoes) {
		this.congregacoes = congregacoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
