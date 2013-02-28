package br.com.adbv.sgd.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.adbv.sgd.enuns.FormaDoacao;

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
public class Doacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, precision = 2, scale=2)
	private Double valor;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	private Campanha campanha;
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	private Semeador semeador;
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	private Usuario lancadoPor;
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	private Congregacao congregacao;
	
	@Temporal(TemporalType.DATE)
	private Date periodoReferencia;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLancamento;
	
	@Enumerated(EnumType.STRING)
	private FormaDoacao formaDoacao;

	@PrePersist
	@PreUpdate
	public void onSaveOrUpdate() {
		this.dataLancamento = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Semeador getSemeador() {
		return semeador;
	}

	public void setSemeador(Semeador semeador) {
		this.semeador = semeador;
	}

	public Usuario getLancadoPor() {
		return lancadoPor;
	}

	public void setLancadoPor(Usuario lancadoPor) {
		this.lancadoPor = lancadoPor;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Date getPeriodoReferencia() {
		return periodoReferencia;
	}

	public void setPeriodoReferencia(Date periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public FormaDoacao getFormaDoacao() {
		return formaDoacao;
	}

	public void setFormaDoacao(FormaDoacao formaDoacao) {
		this.formaDoacao = formaDoacao;
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
		Doacao other = (Doacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
