package vjps.planejamento.financeiro.modelo;

/**
 * 
 * Classe responsável por armazenar e  manipular os dados de uma Receita.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class Receita {
	
	// Tipos padrões de uma Receita.
	public static final String[] TIPOS = {"Salário",
			"Aluguel", 
			"Bolsa de Estudos", 
			"Bolsa de Pesquisa", 
			"Bonificação", 
			"Comissão", 
			"Investimentos"};
	
	private Long id;

	private Integer mes;
	private String tipo;
	private Float valor;
	
	public Receita() {
	}

	public Receita(Integer mes, String tipo, Float valor) {
		this.mes = mes;
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public Receita(Long id, Integer mes, String tipo, Float valor) {
		this.id = id;
		this.mes = mes;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return String.format("Receita %l, Mês: %d, Tipo: %s, Valor: %.2f", id, mes, tipo, valor);
	}
	
	@Override
	public boolean equals(Object receita) {
		return ((Receita) receita).mes == mes && ((Receita) receita).tipo.equalsIgnoreCase(tipo);
	}
	
} //class Receita
