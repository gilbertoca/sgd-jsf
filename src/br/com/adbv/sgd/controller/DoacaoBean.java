package br.com.adbv.sgd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.SelectEvent;

import br.com.adbv.sgd.dao.CongregacaoDao;
import br.com.adbv.sgd.dao.DoacaoDao;
import br.com.adbv.sgd.dao.SemeadorDao;
import br.com.adbv.sgd.enuns.FormaDoacao;
import br.com.adbv.sgd.model.Campanha;
import br.com.adbv.sgd.model.Congregacao;
import br.com.adbv.sgd.model.Doacao;
import br.com.adbv.sgd.model.Semeador;
import br.com.adbv.sgd.util.FacesUtils;
import br.com.adbv.sgd.util.Util;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

@ManagedBean
public class DoacaoBean {

	private Long id;
	private Doacao doacao = new Doacao();
	private Semeador semeador;
	private Congregacao congregacao;
	private Campanha campanha;
	private List<Doacao> doacoes = new ArrayList<Doacao>();
	private Double subTotal = new Double(0);
	private Date competencia = null;
	private Date dataInicio = null;
	private Date dataFim = null;

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	//private String state = ESTADO_DE_PESQUISA;
	private String state = ESTADO_DE_NOVO;

	private boolean doacaoCongregacao;

	private UIForm form;

	@ManagedProperty("#{doacaoDao}")
	private DoacaoDao doacaoDao;
	@ManagedProperty("#{semeadorDao}")
	private SemeadorDao semeadorDao;
	@ManagedProperty("#{congregacaoDao}")
	private CongregacaoDao congregacaoDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{usuarioWeb}")
	private UsuarioWeb usuarioWeb;

	public List<Doacao> getDoacoesDoSemeador() {
		Semeador semeador = doacaoDao.getSemeadorByUsuario(usuarioWeb
				.getUsuario().getId());

		return semeador != null ? doacaoDao.buscaDoacoesPorSemeador(semeador
				.getId()) : new ArrayList<Doacao>();
	}

	public List<Congregacao> buscaCongregacaoPorNome(String nome) {
		List<Congregacao> congregacoesEncontradas = new ArrayList<Congregacao>();
		List<Congregacao> congregacoes = congregacaoDao.listAll();

		for (Congregacao congregacao : congregacoes) {
			if (Util.normalize(congregacao.getNome().trim()).startsWith(
					Util.normalize(nome))) {
				congregacoesEncontradas.add(congregacao);
			}
		}

		return congregacoesEncontradas;
	}

	public void handleSelectCongregacao(SelectEvent event) {
		congregacao = congregacaoDao.load(Long.parseLong(event.getObject()
				.toString()));
		doacao.setCongregacao(congregacao);

	}

	public List<Semeador> buscaSemeadorPorNome(String nome) {
		List<Semeador> semeadoresEncontrados = new ArrayList<Semeador>();
		List<Semeador> semeadores = semeadorDao.listAll();

		for (Semeador semeador : semeadores) {
			if (Util.normalize(semeador.getNome()).startsWith(
					Util.normalize(nome))) {
				semeadoresEncontrados.add(semeador);
			}
		}

		return semeadoresEncontrados;
	}

	public void handleSelectSemeador(SelectEvent event) {
		semeador = semeadorDao.load(Long
				.parseLong(event.getObject().toString()));
		doacao.setSemeador(semeador);
	}

	public void executaRelatorioLancamento() {
		listaDoacoesBasico(this.semeador, this.congregacao, this.campanha,
				this.competencia, this.dataInicio, this.dataFim);
	}

	public void listaDoacoesBasico(Semeador semeador, Congregacao congregacao,
			Campanha campanha, Date competencia, Date inicio, Date fim) {

		doacoes = doacaoDao.listaLancamentos(semeador, congregacao, campanha,
				competencia, inicio, fim);

		if (doacoes.size() > 0) {
			this.subTotal = Util.subTotal(doacoes);
		} else {
			this.subTotal = new Double(0);
		}
	}

	public void prepareInsert() {
		this.doacao = new Doacao();
		semeador = new Semeador();
		congregacao = new Congregacao();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}
	
	public void init() {	
		if(id != null) {			
			doacao = doacaoDao.load(id);	
			semeador = doacao.getSemeador();
			congregacao = doacao.getCongregacao();
			setState(ESTADO_DE_EDICAO);
			id = null;
		}
				
	}

	public void insert() {
		if (doacao.getValor() == 0.0) {
			facesUtils
					.adicionaMensagemDeErro("É necessário informar algum valor para a doação.");
			return;
		}

		if (doacao.getSemeador() == null && doacao.getCongregacao() == null) {
			facesUtils
					.adicionaMensagemDeErro("É necessário informar um semeador ou congregação.");
			return;
		}

		doacao.setLancadoPor(usuarioWeb.getUsuario());
		doacaoDao.save(doacao);
		facesUtils.adicionaMensagemDeInformacao("Doação gravada com sucesso.");
		//lista();
		prepareInsert();
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public void preProcessPDF(Object document) throws IOException,
			BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4.rotate());

		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String logo = servletContext.getRealPath("") + File.separator
				+ "resources/images/oficial" + File.separator + "logo_sgd.jpg";

		pdf.add(Image.getInstance(logo));
	}

	public Map<String, FormaDoacao> getFormaDoacaoValues() {
		return FormaDoacao.getFormaDoacaoValues();
	}

	public List<Doacao> getListaDoacoes() {
		setState(ESTADO_DE_PESQUISA);
		lista();
		//return doacaoDao.listAll();
		return doacoes;
	}

	public void lista() {
//		if ((doacoes.size() == 0)) {
//		}
		doacoes = doacaoDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		doacaoDao.delete(doacao);
		facesUtils.adicionaMensagemDeInformacao("Doação removida com sucesso!");
		lista();
	}

	public void prepareUpdate(Doacao doacao) {
		this.doacao = doacaoDao.load(doacao.getId()); // evita
		// LazyInitializationException
		semeador = this.doacao.getSemeador();
		congregacao = this.doacao.getCongregacao();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);
	}

	public void update() {

		doacaoDao.update(doacao);
		facesUtils
				.adicionaMensagemDeInformacao("Doação atualizada com sucesso!");
		//lista();
		prepareInsert();

	}

	public void voltar() {
		this.doacao = new Doacao();
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

	public Doacao getDoacao() {
		return doacao;
	}

	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
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

	public void setDoacaoDao(DoacaoDao doacaoDao) {
		this.doacaoDao = doacaoDao;
	}

	public void setSemeadorDao(SemeadorDao semeadorDao) {
		this.semeadorDao = semeadorDao;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Doacao> getDoacoes() {
		return doacoes;
	}

	public void setDoacoes(List<Doacao> doacoes) {
		this.doacoes = doacoes;
	}

	public boolean isDoacaoCongregacao() {
		return doacaoCongregacao;
	}

	public void setDoacaoCongregacao(boolean doacaoCongregacao) {
		this.doacaoCongregacao = doacaoCongregacao;
	}

	public Semeador getSemeador() {
		return semeador;
	}

	public void setSemeador(Semeador semeador) {
		this.semeador = semeador;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public void setCongregacaoDao(CongregacaoDao congregacaoDao) {
		this.congregacaoDao = congregacaoDao;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Date getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Date competencia) {
		this.competencia = competencia;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
