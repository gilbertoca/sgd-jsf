package br.com.adbv.sgd.enuns;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TipoCongregacao {

	CAPITAL("capital", "CAPITAL"), INTERIOR("interior", "INTERIOR");

	private String key, value;

	private TipoCongregacao(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
	
	public static Map<String, TipoCongregacao> getTipoCongregacaoValues(){
		Map<String, TipoCongregacao> mapTipoCongregacao = new LinkedHashMap<String, TipoCongregacao>();  
        for(TipoCongregacao tipo : TipoCongregacao.values()){  
        	mapTipoCongregacao.put(tipo.getValue(), tipo);  
        }  
        return mapTipoCongregacao;  
	}
}
