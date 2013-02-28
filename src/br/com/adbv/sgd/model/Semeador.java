package br.com.adbv.sgd.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.adbv.sgd.enuns.TipoTelefone;

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
public class Semeador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = true, length = 30)
	private String rg;
	
	@Column(nullable = true, length = 14)
	private String cpf;

	@Column(nullable = true, length = 100)
	private String email;

	@Column(nullable = true, length = 100)
	private String profissao;

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;

	@Column(nullable = false, length = 100, unique = true)
	private String codigo;

	@Column(nullable = true, length = 100)
	private String telefone;

	@Enumerated(EnumType.STRING)
	private TipoTelefone tipoTelefone;

	private boolean ofertaTempo = true;
	private boolean ativo;

	@OneToOne(optional = true, fetch=FetchType.EAGER)
	private Congregacao congregacao;

	@OneToOne(optional = false, cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAlteracao;

	private boolean naoEvangelico;
	
	private boolean belaVista;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public boolean isOfertaTempo() {
		return ofertaTempo;
	}

	public void setOfertaTempo(boolean ofertaTempo) {
		this.ofertaTempo = ofertaTempo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public boolean isNaoEvangelico() {
		return naoEvangelico;
	}

	public void setNaoEvangelico(boolean naoEvangelico) {
		this.naoEvangelico = naoEvangelico;
	}

	public boolean isBelaVista() {
		return belaVista;
	}

	public void setBelaVista(boolean belaVista) {
		this.belaVista = belaVista;
	}

	@PrePersist
	@PreUpdate
	public void onSaveOrUpdate() {
		this.dataUltimaAlteracao = new Date();
	}

		
	@Override
	public String toString() {
		return codigo + " - " + nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Semeador other = (Semeador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
