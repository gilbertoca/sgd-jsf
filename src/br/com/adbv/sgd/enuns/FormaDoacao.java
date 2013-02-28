package br.com.adbv.sgd.enuns;

import java.util.LinkedHashMap;
import java.util.Map;

public enum FormaDoacao {

	COFRE("cofre", "COFRE"), CARNE("carne", "CARNÊ"), ENVELOPE("envelope", "ENVELOPE"), DEPOSITO(
			"deposito", "DEPÓSITO"), OUTRA("outra",
			"OUTRA"),
			CHEQUE("cheque","CHEQUE");

	private String key, value;

	private FormaDoacao(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public static Map<String, FormaDoacao> getFormaDoacaoValues() {
		Map<String, FormaDoacao> mapFormaDoacao = new LinkedHashMap<String, FormaDoacao>();
		for (FormaDoacao tipo : FormaDoacao.values()) {
			mapFormaDoacao.put(tipo.getValue(), tipo);
		}
		return mapFormaDoacao;
	}
}
