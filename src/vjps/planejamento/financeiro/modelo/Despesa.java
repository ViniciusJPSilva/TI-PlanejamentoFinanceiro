package vjps.planejamento.financeiro.modelo;

/**
 * 
 * Classe responsável por armazenar e  manipular os dados de uma Despesa.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class Despesa {
	
	private Long id;
	
	private String data;
	private String descricao;
	private String situacao;
	
	private Float valor;
	
	public Despesa() {
		data = descricao = situacao = "";
	}
	
	public Despesa(String data, String descricao, String situacao, Float valor) {
		this();
		this.data = data;
		this.descricao = descricao;
		this.situacao = situacao;
		this.valor = valor;
	}

	public Despesa(Long id, String data, String descricao, String situacao, Float valor) {
		this();
		this.id = id;
		this.data = data;
		this.descricao = descricao;
		this.situacao = situacao;
		this.valor = valor;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return String.format("Despesa %l, Data: %s, Descrição: %s, Valor: %.2f, Situação: %s", id, data, descricao, valor, situacao);
	}
	
	@Override
	public boolean equals(Object despesa) {
		return ((Despesa) despesa).data.equalsIgnoreCase(data) && ((Despesa) despesa).descricao.equalsIgnoreCase(descricao);
	}
	
	/**
	 * Retorna o inteiro referente ao mês da despesa.
	 * Ex.: Janeiro retorna 1, Fevereiro retorna 2;
	 * 
	 * @return {@code int} valor do mês ou -1 caso a data não esteja devidamente formatada.
	 */
	public int obterMes() {
		int indexBarra = data.indexOf('/'),
				mes;
		try {
			mes = Integer.parseInt(data.substring(indexBarra + 1, data.indexOf('/', indexBarra + 1)));
		}catch (NumberFormatException e) {
			return -1;
		}
		
		return mes;
	}
	
}// class Despesa
