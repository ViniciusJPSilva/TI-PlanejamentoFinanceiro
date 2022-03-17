package vjps.planejamento.financeiro.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.modelo.Despesa;

public class DespesaDAO {
	Database database;
	private Connection connection;

	public DespesaDAO(Database database) {
		this.database = database;
		connection = database.getConnection();
	}

	/**
	 * Adiciona os dados de uma nova Despesa ao Banco de Dados.
	 * 
	 * @param despesa Despesa
	 */
	public boolean adicionar(Despesa despesa, long idorcamento) {
		String sql = "INSERT INTO despesa (idorcamento, data, descricao, situacao, valor) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, idorcamento);
			stmt.setString(2, despesa.getData());
			stmt.setString(3, despesa.getDescricao());
			stmt.setString(4, despesa.getSituacao());
			stmt.setFloat(5, despesa.getValor());

			stmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}// adicionar()
	
	/**
	 * Remove os dados de uma despesa o Banco de dados.
	 */
	public void remover(Despesa despesa) {
		String sql = "DELETE FROM despesa WHERE iddespesa=?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, despesa.getId());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // remover()
	
	
	/**
	 * Altera os dados de uma Despesa do Banco de Dados.
	 * 
	 * @param despesa Despesa
	 */
	public boolean alterar(Despesa despesa) {
		String sql = "UPDATE despesa SET data=?, descricao=?, situacao=?, valor=? WHERE iddespesa=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, despesa.getData());
			stmt.setString(2, despesa.getDescricao());
			stmt.setString(3, despesa.getSituacao());
			stmt.setFloat(4, despesa.getValor());
			stmt.setLong(5, despesa.getId());

			stmt.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}// alterar()
	
	
	/**
	 * Obtém os dados de todos as Despesas existentes no Banco de Dados.
	 * @return List<Despesa> lista com todos as despesas.
	 */
	public List<Despesa> getListaDespesas(){
		List<Despesa> despesas = new ArrayList<>();
		String sql = "SELECT * FROM despesa";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				despesas.add(new Despesa(rs.getLong("iddespesa"), rs.getString("data"), rs.getString("descricao"), rs.getString("situacao"), rs.getFloat("valor")));
			
		} catch (SQLException e) {
			return null;
		}
		
		return despesas;
	}// getListaDespesas()
	
	/**
	 * Procura no banco de dados uma despesa com o valor da coluna "idorcamento" correspondente ao parâmetro "orcamentoId" informado.
	 * @param id "orcamentoId" do orcamento.
	 * @return Lista Despesa ou null caso não exista nenhuma.
	 */
	public List<Despesa> getDespesasByOrcamentoId(long orcamentoId){
		List<Despesa> despesas = new ArrayList<>();
		String sql = "SELECT * FROM despesa WHERE idorcamento=?  ORDER BY iddespesa";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, orcamentoId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				despesas.add(new Despesa(rs.getLong("iddespesa"), rs.getString("data"), rs.getString("descricao"), rs.getString("situacao"), rs.getFloat("valor")));
			
		} catch (SQLException e) {
			return null;
		}
		
		return despesas;
	}// getDespesasByOrcamentoId()

	
} // class DespesaDAO
