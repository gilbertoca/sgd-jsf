package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.adbv.sgd.dao.CampanhaDao;
import br.com.adbv.sgd.dao.ProjetoDao;
import br.com.adbv.sgd.model.Projeto;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean
public class ProjetoBean {

	private Projeto projeto = new Projeto();
	private List<Projeto> projetos = new ArrayList<Projeto>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{projetoDao}")
	private ProjetoDao projetoDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{campanhaDao}")
	private CampanhaDao campanhaDao;

	public void prepareInsert() {
		this.projeto = new Projeto();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}

	public void insert() {
		projetoDao.save(projeto);
		facesUtils.adicionaMensagemDeInformacao("Projeto gravada com sucesso.");
		lista();

	}

	// public List<Projeto> getListaProjetos() {
	// setState(ESTADO_DE_PESQUISA);
	// return projetoDao.listAll();
	// }

	public List<Projeto> getListaProjetos() {
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_PESQUISA);
		List<Projeto> projetos = projetoDao.listAll();
		for (Projeto p : projetos) {
			Double saldoProjeto = 0.0;
			Double situacao = 0.0;
			Double saldo = campanhaDao.saldoPorProjeto(p.getId());
			saldoProjeto = saldo != null ? saldo : saldoProjeto;
			situacao = saldoProjeto != 0.0 ? (saldoProjeto * 100) / p.getMeta()
					: situacao;
			p.setSituacao(situacao);
			p.setSaldo(saldoProjeto);
		}
		return projetos;
	}

	public void lista() {
		projetos = projetoDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		projetoDao.delete(projeto);
		facesUtils
				.adicionaMensagemDeInformacao("Projeto removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Projeto projeto) {
		this.projeto = projetoDao.load(projeto.getId()); // evita
		// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		projetoDao.update(projeto);
		facesUtils
				.adicionaMensagemDeInformacao("Projeto atualizada com sucesso!");
		lista();

	}

	public void voltar() {
		this.projeto = new Projeto();
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

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
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

	public void setProjetoDao(ProjetoDao projetoDao) {
		this.projetoDao = projetoDao;
	}

	public void setCampanhaDao(CampanhaDao campanhaDao) {
		this.campanhaDao = campanhaDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

}
