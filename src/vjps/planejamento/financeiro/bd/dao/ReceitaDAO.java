package vjps.planejamento.financeiro.bd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.modelo.Receita;

public class ReceitaDAO {
	Database database;
	private Connection connection;

	public ReceitaDAO(Database database) {
		this.database = database;
		connection = database.getConnection();
	}

	/**
	 * Adiciona os dados de uma nova receita ao Banco de Dados.
	 * 
	 * @param receita Receita
	 */
	public boolean adicionar(Receita receita, long idorcamento) {
		String sql = "INSERT INTO receita (idorcamento, mes, tipo, valor) VALUES (?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, idorcamento);
			stmt.setInt(2, receita.getMes());
			stmt.setString(3, receita.getTipo());
			stmt.setFloat(4, receita.getValor());

			stmt.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}// adicionar()
	
	/**
	 * Remove os dados de uma receita o Banco de dados.
	 */
	public void remover(Receita receita) {
		String sql = "DELETE FROM receita WHERE idreceita=?";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, receita.getId());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // remover()
	
	
	/**
	 * Altera os dados de uma Receita do Banco de Dados.
	 * 
	 * @param receita Receita
	 */
	public boolean alterar(Receita receita) {
		String sql = "UPDATE receita SET mes=?, tipo=?, valor=? WHERE idreceita=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, receita.getMes());
			stmt.setString(2, receita.getTipo());
			stmt.setFloat(3, receita.getValor());
			stmt.setLong(4, receita.getId());

			stmt.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}// alterar()
	
	/**
	 * Obtém os dados de todos as Receitas existentes no Banco de Dados.
	 * @return List<Receitas> lista com todos as receitas.
	 */
	public List<Receita> getListaReceitas(){
		List<Receita> receitas = new ArrayList<>();
		String sql = "SELECT * FROM receita";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				receitas.add(new Receita(rs.getLong("idreceita"), rs.getInt("mes"), rs.getString("tipo"), rs.getFloat("valor")));
		} catch (SQLException e) {
			return null;
		}
		
		return receitas;
	}// getListaReceitas()
	
	/**
	 * Procura no banco de dados receitas com o valor da coluna "idorcamento" correspondente ao parâmetro "orcamentoId" informado.
	 * @param id "orcamentoId" do orcamento.
	 * @return Lista Receitas ou null caso não exista nenhuma. 
	 */
	public List<Receita> getDespesasByOrcamentoId(long orcamentoId){
		List<Receita> receitas = new ArrayList<>();
		String sql = "SELECT * FROM receita WHERE idorcamento=?  ORDER BY idreceita";
		
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setLong(1, orcamentoId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				receitas.add(new Receita(rs.getLong("idreceita"), rs.getInt("mes"), rs.getString("tipo"), rs.getFloat("valor")));
			
		} catch (SQLException e) {
			return null;
		}
		
		return receitas;
	}// getReceitasByOrcamentoId()
} // class ReceitaDAO
