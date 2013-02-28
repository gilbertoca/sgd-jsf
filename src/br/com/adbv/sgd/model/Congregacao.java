package br.com.adbv.sgd.model;

import java.io.Serializable;

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

import br.com.adbv.sgd.enuns.TipoCongregacao;

@SuppressWarnings("serial")
@Entity
@Cacheable(true)
public class Congregacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String nome;

	@ManyToOne(optional = true, fetch=FetchType.LAZY)
	private Igreja igreja;

	@Enumerated(EnumType.STRING)
	private TipoCongregacao tipoCongregacao;

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

	public Igreja getIgreja() {
		return igreja;
	}

	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
	}

	public TipoCongregacao getTipoCongregacao() {
		return tipoCongregacao;
	}

	public void setTipoCongregacao(TipoCongregacao tipoCongregacao) {
		this.tipoCongregacao = tipoCongregacao;
	}	
	
	public String getToString(){
		return toString();
	}

	@Override
	public String toString() {
		return nome + " - "	+ tipoCongregacao;
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
		Congregacao other = (Congregacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
