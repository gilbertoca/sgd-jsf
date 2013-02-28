package br.com.adbv.sgd.util;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import br.com.adbv.sgd.model.Doacao;

public class Util {

	public static boolean estaDentroDoPeriodo(Date hoje, Date inicio, Date fim) {
		return (hoje.compareTo(inicio) >= 0 && hoje.compareTo(fim) <= 0);
	}

	public static String formataMoeda(Double valor) {
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	public static String normalize(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");

		return s;
	}

	public static Double subTotal(List<Doacao> doacoes) {
		Double subTotal = new Double(0);

		for (Doacao doacao : doacoes) {
			subTotal += doacao.getValor();
		}

		return subTotal;
	}
}
