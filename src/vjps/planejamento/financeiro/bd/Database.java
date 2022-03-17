package vjps.planejamento.financeiro.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * Classe que fornece métodos para permitir as operações CRUD no banco de dados.
 */
public class Database implements AutoCloseable {
	private String url, user, password;
	
	// Uma conexão (sessão) com um banco de dados específico.
	private Connection connection;
	
	// Objeto usado para executar uma instrução SQL e retornar os resultados produzidos.
	private Statement statement;
	
	// Tabela ResultSet
	private ResultSet resultSet; 
	
	/**
	  * Obtém uma conexão com o banco de dados.  
	  */
	public Database(String url, String user, String password) throws SQLException {
		this.url = url;
		this.user = user;
		this.password = password;
			
		// Cria uma conexão com o banco de dados.
		connection = DriverManager.getConnection(url, user, password);
			
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	} 

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		return connection;
	}
	
	public Statement getStatement() {
		return statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	/** 
	 * Fecha a conexão com o banco de dados. 
	 */
	@Override
	public void close() throws SQLException { 
		if (statement != null) statement.close();
		if (connection != null) connection.close();
		if (resultSet != null) resultSet.close();
	} 

	/** 
	 * Executa uma consulta SQL do tipo SELECT sobre o banco de dados.
	 * @return ResultSet: o resultado da consulta.
	 */
	public ResultSet select(String instructionSql) throws SQLException {
		try { 
			resultSet = statement.executeQuery(instructionSql);
			return resultSet;
		} catch (SQLException sqlException) {
			throw new SQLException("Erro ao executar uma consulta SQL SELECT.", sqlException);
		}	
	} 
	
//	/** 
//	 * Executa uma consulta SQL do tipo INSERT, UPDATE ou DELETE sobre o banco de dados.
//	 * @return ResultSet: o número de linhas resultante da consulta SQL ou zero para instruções SQL que não retornam nenhum resultado. 
//	 */
//	public int query(String instructionSql) throws SQLException {
//		try {  
//			return statement.executeUpdate(instructionSql);
//		} catch (SQLException sqlException) {
//			throw new SQLException("Erro ao executar uma consulta SQL INSERT, UPDATE ou DELETE.");
//		}
//	}
} // class Database 
