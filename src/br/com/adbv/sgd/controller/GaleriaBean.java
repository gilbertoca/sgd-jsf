package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.adbv.sgd.dao.GaleriaDao;
import br.com.adbv.sgd.model.Galeria;
import br.com.adbv.sgd.util.FacesUtils;

@ManagedBean
public class GaleriaBean {

	private Galeria galeria = new Galeria();
	private List<Galeria> galerias = new ArrayList<Galeria>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;	

	@ManagedProperty("#{galeriaDao}")
	private GaleriaDao galeriaDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	
	@PostConstruct
	public void init(){
		galeria = galeriaDao.load(1l);		
	}
		

	public void prepareInsert() {
		this.galeria = new Galeria();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}

	public void insert() {
		// if (!bancoJaCadastrado(galeria)) {
		galeriaDao.save(galeria);
		facesUtils.adicionaMensagemDeInformacao("Galeria gravada com sucesso.");
		lista();
		// } else {
		// facesUtils
		// .adicionaMensagemDeErro("Verifique os dados informados. Existem valores já cadastrados");
		// }

	}

	public List<Galeria> getListaGalerias() {
		setState(ESTADO_DE_PESQUISA);
		return galeriaDao.listAll();
	}

	public void lista() {
		galerias = galeriaDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		galeriaDao.delete(galeria);
		facesUtils.adicionaMensagemDeInformacao("Galeria removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Galeria galeria) {
		this.galeria = galeriaDao.load(galeria.getId()); // evita
		// LazyInitializationException
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		galeriaDao.update(galeria);
		facesUtils
				.adicionaMensagemDeInformacao("Galeria atualizada com sucesso!");
		lista();

	}

	public void voltar() {
		this.galeria = new Galeria();
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

	public Galeria getGaleria() {
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
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

	public void setGaleriaDao(GaleriaDao galeriaDao) {
		this.galeriaDao = galeriaDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Galeria> getGalerias() {
		return galerias;
	}

	public void setGalerias(List<Galeria> galerias) {
		this.galerias = galerias;
	}

}
