package br.com.adbv.sgd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.SelectEvent;

import br.com.adbv.sgd.dao.CongregacaoDao;
import br.com.adbv.sgd.dao.EnderecoDao;
import br.com.adbv.sgd.dao.RoleDao;
import br.com.adbv.sgd.dao.SemeadorDao;
import br.com.adbv.sgd.dao.UsuarioDao;
import br.com.adbv.sgd.enuns.TipoCongregacao;
import br.com.adbv.sgd.enuns.TipoTelefone;
import br.com.adbv.sgd.model.Congregacao;
import br.com.adbv.sgd.model.Role;
import br.com.adbv.sgd.model.Semeador;
import br.com.adbv.sgd.model.Usuario;
import br.com.adbv.sgd.util.Constantes;
import br.com.adbv.sgd.util.DateUtil;
import br.com.adbv.sgd.util.FacesUtils;
import br.com.adbv.sgd.util.Util;

@ManagedBean
public class SemeadorBean {

	private Long id;
	private Congregacao congregacao;
	private Semeador semeador = new Semeador();
	private List<Semeador> semeadores = new ArrayList<Semeador>();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";
	
	private String codigo = null;

	private boolean naoEvangelico;
	private boolean belaVista;

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{semeadorDao}")
	private SemeadorDao semeadorDao;
	@ManagedProperty("#{congregacaoDao}")
	private CongregacaoDao congregacaoDao;
	@ManagedProperty("#{enderecoDao}")
	private EnderecoDao enderecoDao;
	@ManagedProperty("#{usuarioDao}")
	private UsuarioDao usuarioDao;
	@ManagedProperty("#{roleDao}")
	private RoleDao roleDao;
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	
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
	
	
	public void init() {	
		if(id != null) {			
			semeador = semeadorDao.load(id);
			naoEvangelico = semeador.isNaoEvangelico();
			belaVista = semeador.isBelaVista();	
			congregacao = semeador.getCongregacao();
			
			setState(ESTADO_DE_EDICAO);
			id = null;
		}
				
	}

	public void prepareInsert() {
		this.semeador = new Semeador();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}

	public void insert() {
		
		boolean semeadorExistente = false;
		semeador.setCodigo(geraCodigo());
		semeador.setUsuario(defineUsuario());
		semeador.setDataCadastro(new Date());
		semeador.setAtivo(true);
		
		if(semeador.getCpf().equals("") || semeador.getCpf() == null) {
			semeador.setCpf(null);
		}
		
		else {
			semeadorExistente = semeadorDao.verificaCpf(semeador.getCpf());
		}
		
		if(!semeadorExistente) {			
			semeadorDao.save(semeador);
			facesUtils.adicionaMensagemDeInformacao("Semeador gravado com sucesso. Código: " + semeador.getCodigo());
			lista();
		}
		
		else {
			facesUtils.adicionaMensagemDeErro("Já existe um semeador com este cpf");
		}
		
	}

	public void updateUser() {
		for (Semeador semeador : semeadorDao.listAll()) {
			Usuario usuario = semeador.getUsuario();
			if (usuario != null) {
				if (usuario.getNome().equalsIgnoreCase("") ) {
					usuario.setNome(semeador.getNome());
					usuarioDao.update(usuario);
				}
			} else {
				usuario = criaUsuario(semeador);
				usuarioDao.save(usuario);
				semeador.setUsuario(usuario);
				semeadorDao.update(semeador);
			}
		}
		System.out.println("Atualização completa");
	}

	private String geraCodigo() {
		int count = semeadorDao.listAll().size();
		if (semeador.isNaoEvangelico()) {
			return Constantes.PREFIXO_NAO_EVANGELICO + "-" + ++count;
		}

		if (!semeador.isBelaVista()) {
			return Constantes.PREFIXO_NAO_BELA_VISTA + "-" + ++count;
		}

		if (semeador.getCongregacao() != null) {
			if (semeador.getCongregacao().getTipoCongregacao() == TipoCongregacao.CAPITAL) {
				return Constantes.PREFIXO_CAPITAL
						+ semeador.getCongregacao().getId() + "-" + ++count;
			} else {
				return Constantes.PREFIXO_INTERIOR
						+ semeador.getCongregacao().getId() + "-" + ++count;
			}
		}

		return null;
	}

	private Usuario criaUsuario(Semeador semeador) {
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleDao.load("SEMEADOR"));

		Usuario usuario = new Usuario();
		usuario.setNome(semeador.getNome());
		usuario.setEnabled(true);
		usuario.setPassword(geraPassword());
		usuario.setUsername(semeador.getCodigo().toLowerCase());
		usuario.setRoles(roles);

		return usuario;
	}

	private Usuario defineUsuario() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleDao.load("SEMEADOR"));

		Usuario usuario = new Usuario();
		usuario.setNome(semeador.getNome());
		usuario.setEnabled(true);
		usuario.setPassword(geraPassword());
		usuario.setUsername(semeador.getCodigo().toLowerCase());
		usuario.setRoles(roles);

		return usuario;
	}

	private String geraPassword() {
		if (semeador.getCpf() != null) {
			return semeador.getCpf().replaceAll("\\D", "");
		}

		return DateUtil.getDateFormat(semeador.getDataNascimento());
	}

	public Map<String, TipoTelefone> getTipoTelefoneValues() {
		return TipoTelefone.getTipoTelefoneValues();
	}

	public void verificaEndereco() {
		Map<String, String> endereco = enderecoDao.buscarPorCep(semeador
				.getCep());

		semeador.setLogradouro(endereco.get("logradouro"));
		semeador.setBairro(endereco.get("bairro"));
		semeador.setCidade(endereco.get("cidade"));
		semeador.setEstado(endereco.get("uf"));
	}

	public List<Semeador> getListaSemeadores() {
		setState(ESTADO_DE_PESQUISA);
		return semeadorDao.listAll();
	}

	public void lista() {
		semeadores = semeadorDao.listAll();
		setState(ESTADO_DE_PESQUISA);
	}

	public void remove() {
		//semeadorDao.delete(semeador);
		semeador.setAtivo(false);
		semeadorDao.update(semeador);
		facesUtils
				.adicionaMensagemDeInformacao("Semeador removido com sucesso!");
		lista();
	}

	public void prepareUpdate(Semeador semeador) {			
			this.semeador = semeadorDao.load(semeador.getId());			
			facesUtils.cleanSubmittedValues(form); // limpa arvore
			setState(ESTADO_DE_EDICAO);		
		
	}
	
	public void preparaAlteracao() {			
		//this.semeador = semeadorDao.load(semeador.getId());			
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		setState(ESTADO_DE_EDICAO);		
	
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
		congregacao = congregacaoDao.load(Long.parseLong(event.getObject().toString()));
		semeador.setCongregacao(congregacao);
	}
	
	public void loadCongregacao(){		
		semeador.setCongregacao(congregacao);
		semeadorDao.update(semeador);
	}

	public void update() {
		if(semeador.getCpf().equals("")) {
			semeador.setCpf(null);
		}
		semeadorDao.update(semeador);
		facesUtils.adicionaMensagemDeInformacao("Semeador atualizado com sucesso!");
		codigo = null;
		lista();

	}

	public void voltar() {
		this.semeador = new Semeador();
		facesUtils.cleanSubmittedValues(form); // limpa arvore
		lista();
	}

	public void checkCombo() {
		this.naoEvangelico = this.semeador.isNaoEvangelico();
	}

	public void checkComboBv() {
		this.belaVista = this.semeador.isBelaVista();
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

	public Semeador getSemeador() {
		return semeador;
	}

	public void setSemeador(Semeador semeador) {
		this.semeador = semeador;
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

	public void setSemeadorDao(SemeadorDao semeadorDao) {
		this.semeadorDao = semeadorDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setEnderecoDao(EnderecoDao enderecoDao) {
		this.enderecoDao = enderecoDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public List<Semeador> getSemeadores() {
		return semeadores;
	}

	public void setSemeadores(List<Semeador> semeadores) {
		this.semeadores = semeadores;
	}

	public boolean isNaoEvangelico() {
		return naoEvangelico;
	}

	public void setEvangelico(boolean naoEvangelico) {
		this.naoEvangelico = naoEvangelico;
	}

	public boolean isBelaVista() {
		return belaVista;
	}

	public void setBelaVista(boolean belaVista) {
		this.belaVista = belaVista;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
}
