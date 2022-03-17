package vjps.planejamento.financeiro.util;

/**
 * A classe Utilitarios possui constantes de Strings, 
 * caractéres e valores comumente utilizadas pela aplicação Planejamento Financeiro, 
 * além de métodos de validação e verificação.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public abstract class Utilitarios {
	
	// Strings Constantes
	public static final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"},
			COLUNAS = {"Data", "Descrição", "Valor", "Situação"}; 
	
	public static final String PLANEJAMENTO_FINANCEIRO = "Planejamento Financeiro",
			ORCAMENTO = "Orçamento",
			PESQUISAR = "Pesquisar",
			AJUDA = "Ajuda",
			NOVO = "Novo",
			ABRIR_RETICENCIAS = "Abrir...",
			GRAVAR_RETICENCIAS = "Gravar...",
			SAIR = "Sair",
			DESPESAS = "Despesa...",
			SOBRE_PLANEJAMENTO_FINANCEIRO = "Sobre o Planejamento Financeiro",
			RECEITA = "Receita",
			DESPESAS_MENSAIS = "Despesas Mensais",
			BALANCO_MENSAL = "Balanço Mensal",
			MES = "Mês",
			TIPO = "Tipo",
			VALOR = "Valor",
			MES_RECEITA = "Mês da receita.",
			TIPO_RECEITA = "Tipo da receita.",
			VALOR_RECEITA = "Valor da receita (R$).",
			TOTAL_MENSAL = "Total Mensal:",
			TOTAL_PAGAR = "Total a Pagar:",
			TOTAL_PAGO = "Total Pago:",
			SALDO = "Saldo:",
			TOTAL_MENSAL_ORCAMENTO = "Total mensal do orçamento (R$).",
			TOTAL_PAGAR_ORCAMENTO = "Total a pagar do orçamento (R$).",
			TOTAL_PAGO_ORCAMENTO = "Total pago do orçamento (R$).",
			SALDO_ORCAMENTO = "Saldo do orçamento (R$).",
			APAGAR_DESPESA = "Deseja apagar a despesa?\nNão pode ser desfeito!",
			FORNECER_NOME_ORCAMENTO = "Forneça um nome para o orcamento",
			ORCAMENTO_SALVO = "Orçamento salvo com sucesso!",
			ERRO_VALOR_DESPESA_INVALIDO = "Valor da Despesa inválido!\nForneça um valor inteiro maior que Zero.",
			ERRO_DATA_DESPESA_INVALIDA = "Data da Despesa inválida!\nForneça uma data no formato DD/MM/AAAA.",
			ERRO_MES_DESPESA_DIFERENTE = "O mês da despesa não corresponde ao mês da receita!\nDeseja salvar a despesa na receita do mês correspondente?",
			ERRO_DESPESA_DUPLICADA = "Já existe uma despesa nesta data com esta descrição!\nO que deseja fazer com o valor?",
			ERRO_BD_CONEXAO = "Não foi possível conectar ao banco de dados.\nO programa será finalizado.",
			ERRO_NAO_HA_ORCAMENTOS = "Não há orçamentos salvos!"
			;
	
	// Caracteres Constantes
	public static final String STR_PONTO = ".",
			STR_VIRGULA = ",",
			STR_VAZIO = "";
	
	public static final char CHAR_PONTO = '.',
			CHAR_VIRGULA = ',',
			CHAR_BACKSPACE = '\b',
			CHAR_SEPARADOR_DATA = '/';
	
	// Outras Constantes
	final static int TAMANHO_DATA = 10,
			TAMANHO_HORA = 5;
	
	public final static String FORMATO_DATA = "DD/MM/AAAA",
			FORMATO_HORA = "HH:MM";
	
	
	// Validadores 
	/**
	 * Valida uma Data (String) no formato "DD/MM/AAAA".
	 * @return Retorna 'true' se for válida ou 'false' se não for.
	 */
	public static Boolean validaData(String data) {
		// Validando o formato da data.
		if(!validaFormatoData(data))
			return false;
			
	    //Transformando de string/char para int.
		int[] dataInt = obterValoresData(data);
	    int dia = dataInt[0];
	    int mes = dataInt[1];
	    int ano = dataInt[2];
	    
	    //Validando o dia, o mês e o ano.
	    if(ano >= 0){
	        switch(mes){
	            case 1:
	            case 3:
	            case 5:
	            case 7:
	            case 8:
	            case 10:
	            case 12:
	                if(dia<1 || dia>31)
	                    return false;
	                break;
	            case 4:
	            case 6:
	            case 9:
	            case 11:
	                if(dia<1 || dia>30)
	                    return false;
	                break;
	            case 2:
	                if((ano%4==0) && ((ano%100!=0) || (ano%400==0))){ //Verifica se o ano é bissexto.
	                    if(dia<01 || dia>29){
	                        return false;
	                    }
	                }else
	                    if(dia<01 || dia>28)
	                        return false;
	                break;
	            default:
	                return false;
	        }
	    }else
	        return false;

	    return true;
	} // validaData()
	
	/**
	 * Obtém os valores inteiros do dia, mês e ano da data fornecida no formato "DD/MM/AAAA".
	 * @param data String da data.
	 * @return int[] Vetor com o valor inteiro do dia, mês e ano nos index 0, 1 e 2, respectivamente. Ou null caso a data seja inválida.
	 */
	public static int[] obterValoresData(String data) {
		// Validando o formato da data.
		if(!validaFormatoData(data))
			return null;
		
		int[] dataInt = new int[3];  
		
		//Transformando de string/char para int.
	    dataInt[0] = Integer.parseInt(data.substring(0, 2));
	    dataInt[1] = Integer.parseInt(data.substring(3, 5));
	    dataInt[2] = Integer.parseInt(data.substring(6));
	    
	    return dataInt;
	} // obterValoresData()
	
	/**
	 * Obtém o valor do dia da data fornecida no formato "DD/MM/AAAA".
	 * @param data String da data.
	 * @return int valor inteiro do dia.
	 */
	public static int obterDiaData(String data) {
		return obterValoresData(data)[0];
	}
	
	/**
	 * Obtém o valor do mês da data fornecida no formato "DD/MM/AAAA".
	 * @param data String da data.
	 * @return int valor inteiro do mês.
	 */
	public static int obterMesData(String data) {
		return obterValoresData(data)[1];
	}
	
	/**
	 * Obtém o valor do ano da data fornecida no formato "DD/MM/AAAA".
	 * @param data String da data.
	 * @return int valor inteiro do ano.
	 */
	public static int obterAnoData(String data) {
		return obterValoresData(data)[2];
	}
	
	/**
	 * Verifica se a data (String) fornecida obedece o formato "DD/MM/AAAA".
	 * @param data String da data.
	 * @return boolean true se o formato for válido e false se não for.
	 */
	public static boolean validaFormatoData(String data) {
		if(data.length() != TAMANHO_DATA)
			return false;
		
		//Verificando se a string possui apenas números e barras.
	    for(int i=0; i < data.length(); i++)
	        if(i!=2 && i!=5) {
	        	if(!Character.isDigit(data.charAt(i)))
	                return false;
	        }else if(data.charAt(i) != CHAR_SEPARADOR_DATA)
	            return false;
	    
	    // Retorna true caso a data passe com sucesso por todas as validações.
	    return true; 
	} // validaFormatoData()

}// class Utilitarios






























