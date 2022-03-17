package vjps.planejamento.financeiro.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Classe responsável por armazenar e  manipular os dados de um Orçamento.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class Orcamento {
	
	private Long id;
	
	private String nome;
	
	private List<Receita> receitas;
	private List<Despesa> despesas;
	
	public Orcamento() {
		receitas = new ArrayList<>();
		despesas = new ArrayList<>();
	}

	public Orcamento(Long id, String nome) {
		this();
		this.id = id;
		this.nome = nome;
	}

	public Orcamento(Long id, String nome, List<Receita> receitas, List<Despesa> despesas) {
		this();
		this.id = id;
		this.nome = nome;
		this.receitas = receitas;
		this.despesas = despesas;
	}

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

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public List<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}

	@Override
	public String toString() {
		return "Orcamento [id=" + id + ", nome=" + nome + ", receitas=" + receitas + ", despesas=" + despesas + "]";
	}
	
	/**
	 * Adiciona uma nova receita à lista de receitas do orçamento.
	 */
	public void adicionarReceita(Receita receita) {
		if(!receitas.contains(receita))
			receitas.add(receita);
		else
			receitas.set(receitas.indexOf(receita), receita);
	}
	
	/**
	 * Remove uma receita da lista de receitas do orçamento.
	 */
	public void removerReceita(Receita receita) {
		receitas.remove(receita);
	}
	
	/**
	 * Adiciona uma nova despesa à lista de despesas do orçamento.
	 */
	public void adicionarDespesa(Despesa despesa) {
		if(despesas.size() == 0)
			despesas.add(despesa);
		else
			if(!despesas.contains(despesa))
				despesas.add(despesa);
			else
				despesas.set(despesas.indexOf(despesa), despesa);
	}
	
	/**
	 * atualiza uma despesa da lista de despesas do orçamento.
	 */
	public boolean atualizarDespesa(int index, Despesa despesa) {
		try {
			despesas.set(index, despesa);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Remove uma despesa da lista de despesas do orçamento.
	 */
	public void removerDespesa(Despesa despesa) {
		despesas.remove(despesa);
	}
	
	/**
	 * 
	 */
	public List<Receita> obterReceitasMes(int mes) {
		List<Receita> receitasMes = new ArrayList<>();
		
		for(Receita receita : receitas)
			if(receita.getMes() == mes)
				receitasMes.add(receita);
		
		return receitasMes;
	}
	
	/**
	 * 
	 */
	public List<Despesa> obterDespesasMes(int mes) {
		List<Despesa> despesasMes = new ArrayList<>();
		
		for(Despesa despesa : despesas)
			if(despesa.obterMes() == mes)
				despesasMes.add(despesa);
		
		return despesasMes;
	}
	
	
	/**
	 * Realiza uma busca na lista de receitas utilizando o mês e o tipo da mesma.
	 * 
	 * @param mes Mês da receita (int).
	 * @param tipo Tipo da receita (String).
	 * 
	 *  @return {@code Receita} - receita encontrada ou null caso não encontre.
	 */
	public Receita obterReceita(int mes, String tipo) {
		try {
			return receitas.get(receitas.indexOf(new Receita(mes, tipo, null)));
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Realiza uma busca na lista de despesas utilizando a data e a descrição da mesma.
	 * 
	 * @param data Data da despesa (String).
	 * @param descricao Descrição da despesa (String).
	 * 
	 *  @return {@code Despesa} - despesa encontrada ou null caso não encontre.
	 */
	public Despesa obterDespesa(String data, String descricao) {
		try {
			return despesas.get(despesas.indexOf(new Despesa(data, descricao, null, null)));
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Realiza uma busca na lista de despesas utilizando a data e a descrição da mesma.
	 * 
	 * @param data Data da despesa (String).
	 * @param descricao Descrição da despesa (String).
	 * 
	 *  @return {@code int} - index da despesa encontrada na lista ou -1 caso não encontre.
	 */
	public int obterIndexDespesa(String data, String descricao) {
		try {
			return despesas.indexOf(new Despesa(data, descricao, null, null));
		}catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}
}// class Orcamento
