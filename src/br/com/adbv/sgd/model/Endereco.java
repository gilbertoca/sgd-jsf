package br.com.adbv.sgd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "endereco")
public class Endereco {

	@Id
	@Column(name = "id", insertable = false)
	private Integer id;

	@Column(name = "cep", insertable = false)
	private String cep;

	@Column(name = "logradouro", insertable = false)
	private String logradouro;

	@Column(name = "bairro", insertable = false)
	private String bairro;

	@Column(name = "cidade", insertable = false)
	private String cidade;

	@Column(name = "uf", insertable = false)
	private String uf;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
