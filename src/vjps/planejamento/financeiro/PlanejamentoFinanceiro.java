package vjps.planejamento.financeiro;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.gui.IgPlanejamentoFinanceiro;
import vjps.planejamento.financeiro.util.Utilitarios;

public class PlanejamentoFinanceiro {
	
	/* 
	 * Define a URL do banco de dados.
	 */
	private final String URL_BD = "jdbc:postgresql://localhost:5432/orcamentos";
	
	/**
	 * Construtor
	 */
	public PlanejamentoFinanceiro() {
		// Obtém uma conexão com o banco de dados. Essa conexão será fechada automaticamente quando o bloco try abaixo for finalizado. 
		try {
			Database database = new Database(URL_BD, "admin", "pf@orcamento");
			new IgPlanejamentoFinanceiro(database);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Utilitarios.ERRO_BD_CONEXAO, Utilitarios.PLANEJAMENTO_FINANCEIRO, JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Inicializa o software
	 */
	public static void main(String[] args) {
		new PlanejamentoFinanceiro();
	}
	
}// class PlanejamentoFinanceiro
