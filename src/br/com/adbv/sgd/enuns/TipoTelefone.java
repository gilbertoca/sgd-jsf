package br.com.adbv.sgd.enuns;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TipoTelefone {

	RESIDENCIAL("residencial", "RESIDENCIAL"), CELULAR("celular", "CELULAR"), 
	TRABALHO("trabalho", "TRABALHO"), RECADO("celular", "RECADO");

	private String key, value;

	private TipoTelefone(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static Map<String, TipoTelefone> getTipoTelefoneValues() {
		Map<String, TipoTelefone> mapTipoTelefone = new LinkedHashMap<String, TipoTelefone>();
		for (TipoTelefone tipo : TipoTelefone.values()) {
			mapTipoTelefone.put(tipo.getValue(), tipo);
		}
		return mapTipoTelefone;
	}
}
