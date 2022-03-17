package vjps.planejamento.financeiro.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.modelo.Despesa;
import vjps.planejamento.financeiro.modelo.Orcamento;
import vjps.planejamento.financeiro.modelo.Receita;

public class OrcamentoDAO {

	Database database;
	private Connection connection;

	public OrcamentoDAO(Database database) {
		this.database = database;
		connection = database.getConnection();
	}

	/**
	 * Adiciona os dados de um novo orçamento ao Banco de Dados.
	 * 
	 * @param orçamento Orçamento
	 * @return String nome do orçamento adicionado
	 */
	public String adicionar(Orcamento orcamento) {
		String sql = "INSERT INTO orcamento (idorcamento, nome) VALUES (?, ?)";
		long idOrcamento = proximoValorSequencia();
		
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, idOrcamento);
			stmt.setString(2, orcamento.getNome());

			stmt.execute();
			
			// Adicionando as receitas e as despesas do orçamento ao BD.
			ReceitaDAO receitaDao = new ReceitaDAO(database);
			for(Receita receita : orcamento.getReceitas())
				receitaDao.adicionar(receita, idOrcamento);
			
			DespesaDAO despesaDao = new DespesaDAO(database);
			for(Despesa despesa : orcamento.getDespesas())
				despesaDao.adicionar(despesa, idOrcamento);
			
			return orcamento.getNome();
		} catch (SQLException e) {
			return null;
		}
	}// adicionar()
	
	/**
	 * Altera os dados de um orçamento do Banco de Dados.
	 * 
	 * @param orçamento Orçamento
	 */
	public void alterar(Orcamento orcamento) {
		// Adicionando as receitas e as despesas do orçamento ao BD.
		ReceitaDAO receitaDao = new ReceitaDAO(database);
		for(Receita receita : orcamento.getReceitas())
			if(receita.getId() == null)
				receitaDao.adicionar(receita, orcamento.getId());
			else 
				receitaDao.alterar(receita);
		
		DespesaDAO despesaDao = new DespesaDAO(database);
		for(Despesa despesa : orcamento.getDespesas())
			if(despesa.getId() == null)
				despesaDao.adicionar(despesa, orcamento.getId());
			else
				despesaDao.alterar(despesa);
		
	}// alterar()
	
	
	/**
	 * Obtém os dados de todos os orçamentos existentes no Banco de Dados.
	 * @return List<Orcamento> lista com todos os orçamentos.
	 */
	public List<Orcamento> getListaOrcamentos(){
		List<Orcamento> orcamentos = new ArrayList<>();
		String sql = "SELECT * FROM orcamento";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			
			ReceitaDAO receitaDao = new ReceitaDAO(database);
			DespesaDAO despesaDao = new DespesaDAO(database);
			while(rs.next()) {
				Orcamento orcamento = new Orcamento(rs.getLong("idorcamento"), rs.getString("nome"));
				orcamento.setReceitas(receitaDao.getDespesasByOrcamentoId(orcamento.getId()));
				orcamento.setDespesas(despesaDao.getDespesasByOrcamentoId(orcamento.getId()));
				
				orcamentos.add(orcamento);
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return orcamentos;
	}// getListaOrcamentos()
	
	/**
	 * Procura no banco de dados um orçamento com o valor da coluna "nome" correspondente ao parâmetro "nome" informado.
	 * @param nome Nome do orcamento.
	 * @return Objeto Orcamento com os dados ou null caso não exista. 
	 */
	public Orcamento getOrcamentoByNome(String nome) {
		String sql = "SELECT * FROM orcamento WHERE nome=?";
		Orcamento orcamento = null;
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			
			ReceitaDAO receitaDao = new ReceitaDAO(database);
			DespesaDAO despesaDao = new DespesaDAO(database);
			while(rs.next()) {
				orcamento = new Orcamento(rs.getLong("idorcamento"), rs.getString("nome"));
				orcamento.setReceitas(receitaDao.getDespesasByOrcamentoId(orcamento.getId()));
				orcamento.setDespesas(despesaDao.getDespesasByOrcamentoId(orcamento.getId()));
			}
		} catch (SQLException e) {
			return null;
		}
		
		return orcamento;
	}// getOrcamentoByNome()
	
	/**
	 * Verifica no Banco de Dados qual o próximo valor de uma sequência.
	 * @param nomeSequencia: String com nome da sequência a ser acessada.
	 * @return próximo valor da sequência;
	 */
	private long proximoValorSequencia() {
		try {
			ResultSet resultSet = database.select("SELECT nextval('seq-orcamento');");
		    return resultSet.next() ? resultSet.getLong(1) : -1;
		} catch (SQLException e) {
			return -1L;
		}
	}

} // class OrcamentoDAO
