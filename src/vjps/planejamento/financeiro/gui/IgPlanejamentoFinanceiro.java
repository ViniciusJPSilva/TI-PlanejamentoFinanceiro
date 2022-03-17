package vjps.planejamento.financeiro.gui;

import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;
import static vjps.planejamento.financeiro.util.Utilitarios.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.bd.dao.DespesaDAO;
import vjps.planejamento.financeiro.bd.dao.OrcamentoDAO;
import vjps.planejamento.financeiro.modelo.Despesa;
import vjps.planejamento.financeiro.modelo.Orcamento;
import vjps.planejamento.financeiro.modelo.Receita;

public class IgPlanejamentoFinanceiro extends JFrame {
	
	private final int NUMERO_LINHAS_TABELA = 30;
	
	private Database database;
	
	private Orcamento orcamento;
	private Despesa despesa;
	private int linhaDespesa;
	
	private boolean modificado;
	
	private JTextField valorTextField;
	private JComboBox<String> mesComboBox;
	private JComboBox<String> tipoComboBox;
	private JScrollPane scrollPane;
	private JTextField totalMensalTextField;
	private JTextField totalPagarTextField;
	private JTextField totalPagoTextField;
	private JTextField saldoTextField;
	private DefaultTableModel defaultTableModel;
	private JTable despesasTable;
	
	/**
	 * Cria e exibe a GUI.
	 */
	public IgPlanejamentoFinanceiro(Database database) {
		
		this.database = database;
		orcamento = new Orcamento();
		despesa = null;
		
		addWindowListener(new WindowAdapter() {	
			@Override
			public void windowClosing(WindowEvent e) {
				finalizar();
			}
		});
		
		// ContentPane
		Container contentPane = getContentPane();
		
		/**
		 * MENU
		 */
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Criando Menus
		JMenu orcamentoMenu = new JMenu(ORCAMENTO);
		JMenu pesquisarMenu = new JMenu(PESQUISAR);
		JMenu ajudaMenu = new JMenu(AJUDA);

		// Criando Menu Items
		JMenuItem novoMenuItem = new JMenuItem(NOVO);
		JMenuItem abrirMenuItem = new JMenuItem(ABRIR_RETICENCIAS);
		JMenuItem gravarMenuItem = new JMenuItem(GRAVAR_RETICENCIAS);
		JMenuItem sairMenuItem = new JMenuItem(SAIR);
		JMenuItem despesaMenuItem = new JMenuItem(DESPESAS);
		JMenuItem sobreMenuItem = new JMenuItem(SOBRE_PLANEJAMENTO_FINANCEIRO);
		
		// Criando separadores
		JSeparator separator = new JSeparator();
		
		// Definindo a tecla mnemônica.
		orcamentoMenu.setMnemonic(KeyEvent.VK_O);
		pesquisarMenu.setMnemonic(KeyEvent.VK_P);
		ajudaMenu.setMnemonic(KeyEvent.VK_A);

		novoMenuItem.setMnemonic(KeyEvent.VK_N);
		abrirMenuItem.setMnemonic(KeyEvent.VK_A);
		gravarMenuItem.setMnemonic(KeyEvent.VK_G);
		sairMenuItem.setMnemonic(KeyEvent.VK_S);
		despesaMenuItem.setMnemonic(KeyEvent.VK_D);
		sobreMenuItem.setMnemonic(KeyEvent.VK_S);
		
		// Definindo o atalho.
		abrirMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		gravarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		despesaMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		
		// Adicionando os menus à menuBar
		menuBar.add(orcamentoMenu);
		menuBar.add(pesquisarMenu);
		menuBar.add(ajudaMenu);
		
		// Adicionando os menuItems e os separadores aos menus
		orcamentoMenu.add(novoMenuItem);
		orcamentoMenu.add(abrirMenuItem);
		orcamentoMenu.add(gravarMenuItem);
		
		
		orcamentoMenu.add(separator);
		orcamentoMenu.add(sairMenuItem);
		pesquisarMenu.add(despesaMenuItem);
		ajudaMenu.add(sobreMenuItem);
		
		
		
		/**
		 * CONTEÚDO
		 */
		// Paineis
		JPanel receitaPanel = new JPanel();
		JPanel despesasPanel = new JPanel();
		JPanel balancoPanel = new JPanel();
		
		FlowLayout receitaFlowLayout = (FlowLayout) receitaPanel.getLayout();
		FlowLayout balancoFlowLayout = (FlowLayout) balancoPanel.getLayout();
		
		receitaFlowLayout.setAlignment(FlowLayout.LEFT);
		balancoFlowLayout.setAlignment(FlowLayout.LEFT);
		
		receitaPanel.setBorder(new TitledBorder(null, RECEITA, TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		despesasPanel.setBorder(new TitledBorder(null, DESPESAS_MENSAIS, TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		balancoPanel.setBorder(new TitledBorder(null, BALANCO_MENSAL, TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		contentPane.setLayout(new BorderLayout(0, 0));
		despesasPanel.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(receitaPanel, BorderLayout.NORTH);
		contentPane.add(despesasPanel, BorderLayout.CENTER);
		contentPane.add(balancoPanel, BorderLayout.SOUTH);
		
		// ReceitaPanel - Labels
		JLabel mesLabel = new JLabel(MES);
		JLabel tipoLabel = new JLabel(TIPO);
		JLabel valorLabel = new JLabel(VALOR);
		
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		tipoLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		valorLabel.setDisplayedMnemonic(KeyEvent.VK_V);
		
		mesLabel.setToolTipText(MES_RECEITA);
		tipoLabel.setToolTipText(TIPO_RECEITA);
		valorLabel.setToolTipText(VALOR_RECEITA);
		
		// ReceitaPanel - ComboBox
		mesComboBox = new JComboBox<>();
		tipoComboBox = new JComboBox<>();
		
		mesComboBox.setModel(new DefaultComboBoxModel<>(MESES));
		tipoComboBox.setModel(new DefaultComboBoxModel<>(Receita.TIPOS));
		
		mesComboBox.setMaximumRowCount(12);
		tipoComboBox.setMaximumRowCount(7);
		
		mesComboBox.setSelectedIndex(0);
		tipoComboBox.setSelectedIndex(0);
		
		// ReceitaPanel - TextField
		valorTextField = new JTextField();
		valorTextField.setColumns(10);
		
		// Definindo o LabelFor
		mesLabel.setLabelFor(mesComboBox);
		tipoLabel.setLabelFor(tipoComboBox);
		valorLabel.setLabelFor(valorTextField);
		
		// Insere os componentes no Painel Receita
		receitaPanel.add(mesLabel);
		receitaPanel.add(mesComboBox);
		receitaPanel.add(tipoLabel);
		receitaPanel.add(tipoComboBox);
		receitaPanel.add(valorLabel);
		receitaPanel.add(valorTextField);
		
		// BalancoPanel - Labels
		JLabel totalMensalLabel = new JLabel(TOTAL_MENSAL);
		JLabel totalPagarLabel = new JLabel(TOTAL_PAGAR);
		JLabel totalPagoLabel = new JLabel(TOTAL_PAGO);
		JLabel saldoLabel = new JLabel(SALDO);
		
		totalMensalLabel.setToolTipText(TOTAL_MENSAL_ORCAMENTO);
		totalPagarLabel.setToolTipText(TOTAL_PAGAR_ORCAMENTO);
		totalPagoLabel.setToolTipText(TOTAL_PAGO_ORCAMENTO);
		saldoLabel.setToolTipText(SALDO_ORCAMENTO);
		
		// BalancoPanel - TextFields
		totalMensalTextField = new JTextField();
		totalPagarTextField = new JTextField();
		totalPagoTextField = new JTextField();
		saldoTextField = new JTextField();
		
		totalMensalTextField.setColumns(10);
		totalPagarTextField.setColumns(10);
		totalPagoTextField.setColumns(10);
		saldoTextField.setColumns(10);
		
		totalMensalTextField.setEditable(false);
		totalPagarTextField.setEditable(false);
		totalPagoTextField.setEditable(false);
		saldoTextField.setEditable(false);
		
		// Insere os componentes no Painel Receita
		balancoPanel.add(totalMensalLabel);
		balancoPanel.add(totalMensalTextField);
		balancoPanel.add(totalPagarLabel);
		balancoPanel.add(totalPagarTextField);
		balancoPanel.add(totalPagoLabel);
		balancoPanel.add(totalPagoTextField);
		balancoPanel.add(saldoLabel);
		balancoPanel.add(saldoTextField);
		
		// Cria a tabela.
		despesasTable = new JTable();
		defaultTableModel = new DefaultTableModel(COLUNAS, NUMERO_LINHAS_TABELA);
		despesasTable.setShowVerticalLines(true);
		despesasTable.setShowHorizontalLines(true);
		
		despesasTable.setModel(defaultTableModel);
		despesasTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		despesasTable.getColumnModel().getColumn(1).setPreferredWidth(160);
		despesasTable.getColumnModel().getColumn(2).setPreferredWidth(275);
		despesasTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		
		// Adiciona a tabela ao painel.
		scrollPane = new JScrollPane(despesasTable);
		despesasPanel.add(scrollPane, BorderLayout.CENTER);
		
		// Eventos dos menus
		novoMenuItem.addActionListener((event) -> novo());
		
		abrirMenuItem.addActionListener((event) -> abrir());
		
		gravarMenuItem.addActionListener((event) -> gravar());
		
		sairMenuItem.addActionListener((event) -> finalizar());
		
		despesaMenuItem.addActionListener((event) -> new IgPesquisarDespesa(this, despesasTable));
		
		sobreMenuItem.addActionListener((event) -> new IgSobrePlanejamentoOrcamento(this));
		

		// Trata os evento de teclado do valorTextField
		valorTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				Character tecla = event.getKeyChar();
				String valor = valorTextField.getText();
				boolean temPonto;
				
				if(!valor.isBlank()) { // Verifica se o campo está em branco.
					
					// Verifica se o valor já possui um ponto ou uma vírgula.
					temPonto = valor.substring(0, valor.length() - 1).contains(STR_PONTO) || valor.substring(0, valor.length() - 1).contains(STR_VIRGULA);
				
					// Verifica se a tecla pressinada é um dígito, ponto, vírgula ou backspace.
					if(Character.isDigit(tecla) || ((tecla == CHAR_PONTO || tecla == CHAR_VIRGULA) && !temPonto) || tecla == CHAR_BACKSPACE) {
						// Suvstitui a vírgula pelo ponto.
						if(tecla == CHAR_VIRGULA)
							valorTextField.setText(valor.replace(CHAR_VIRGULA, CHAR_PONTO));
					}else 
						// Apaga o último caracter da string do campo.
						if(!valor.isBlank())
							valorTextField.setText(valor.substring(0, valor.length() - 1));
					
					atualizarReceita();
				}
				modificado = true;
			}
		});
		
		// Trata os eventos de seleção das ComboBox.
		mesComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    atualizarGUI();
                }
			}
		});
		
		tipoComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    atualizarGUI();
                }
			}
		});
		
		// Trata os evento de inserção da tabela despesasTable
		despesasTable.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				buscarDespesa();
			}
			
			@Override
			public void componentRemoved(ContainerEvent event) {
				adicionarDespesas();
			}
		});
		
		setBounds(100, 100, 850, 600);
		setMinimumSize(new Dimension(850, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(PLANEJAMENTO_FINANCEIRO);
		setResizable(true);
		setVisible(true);
	} // construtor
	
	/**
	 * Cria um novo arquivo
	 */
	private void novo() {
		verificarModificado();
		orcamento = new Orcamento();
		despesa = null;
		linhaDespesa = -1;
		atualizarGUI();
		modificado = false;
	}

	/**
	 * Realiza uma busca no BD e apresenta os dados do orçamento selecionado pelo usuário.
	 */
	private void abrir() {
		OrcamentoDAO dao = new OrcamentoDAO(database);
		if(dao.getListaOrcamentos().size() > 0) {
			new IgAbrirOrcamento(this, database, orcamento);
			
			// Verifica se o usuário selecionou algum orçamento.
			if(orcamento.getNome() != null)
				orcamento = dao.getOrcamentoByNome(orcamento.getNome());
			
			atualizarGUI();
		} else
			showMessageDialog(this, ERRO_NAO_HA_ORCAMENTOS, PLANEJAMENTO_FINANCEIRO, WARNING_MESSAGE);
	} // abrir()

	/**
	 * Grava os dados do orçamento no BD.
	 */
	private void gravar() {
		OrcamentoDAO dao = new OrcamentoDAO(database);
		
		// Verifica se o orçamento possui um ID.
		if(orcamento.getId() == null) {
			String nome = JOptionPane.showInputDialog(this, FORNECER_NOME_ORCAMENTO, PLANEJAMENTO_FINANCEIRO, INFORMATION_MESSAGE);
			
			if(nome != null) {
				orcamento.setNome(nome);
				nome = dao.adicionar(orcamento);
				
				orcamento = dao.getOrcamentoByNome(nome);
				atualizarGUI();
			} else
				return;
		} else 
			// Se o orcamento possui um ID, o programa realiza as alterações necessárias.
			dao.alterar(orcamento);
		
		
		showMessageDialog(this, ORCAMENTO_SALVO, PLANEJAMENTO_FINANCEIRO, INFORMATION_MESSAGE);
		modificado = false;
	} // gravar()

	/**
	 * Atualiza toda a interface gráfica com os dados do orçamento.
	 */
	private void atualizarGUI() {
		int mes = mesComboBox.getSelectedIndex() + 1;
		String tipo = (String) tipoComboBox.getSelectedItem();
		
		limparTodosCampos();
		
		// Modifica o título
		if(orcamento.getNome() != null)
			setTitle(String.format("%s - %s", PLANEJAMENTO_FINANCEIRO, orcamento.getNome()));
		else 
			setTitle(PLANEJAMENTO_FINANCEIRO);
		
		Receita receita = orcamento.obterReceita(mes, tipo);
		if(receita != null)
			valorTextField.setText(String.format("%.2f", receita.getValor()));
			
		atualizarBalanco();
        atualizarDespesas();
        valorTextField.requestFocus();
        modificado = true;
	} // atualizarGUI()
	

	/**
	 * Limpa todos os campos da GUI.
	 */
	private void limparTodosCampos() {
		valorTextField.setText(STR_VAZIO);
		totalMensalTextField.setText(STR_VAZIO);
		totalPagarTextField.setText(STR_VAZIO);
		totalPagoTextField.setText(STR_VAZIO);
		saldoTextField.setText(STR_VAZIO);
		
		defaultTableModel.setRowCount(0);
		defaultTableModel.setRowCount(NUMERO_LINHAS_TABELA);
		
	} // limparTodosCampos()

	
	
	/**
	 * Verifica se uma nova despesa inserida é igual a alguma anterior.
	 */
	protected void buscarDespesa() {
		// Obtém o index da linha (row) selecionada/modificada.
		int linha = despesasTable.getSelectedRow(); 
		
		if(linha >= 0) {
			// Armazena as informações da linha selecionada em strings.
			String dataTabela = (String) despesasTable.getValueAt(linha, 0);
			String descricao = (String) defaultTableModel.getValueAt(linha, 1);
			
			if(dataTabela != null && descricao != null) {
				despesa = orcamento.obterDespesa(dataTabela, descricao);
				linhaDespesa = orcamento.obterIndexDespesa(dataTabela, descricao);
			} else {
				despesa = null;
				linhaDespesa = -1;
			}
		}
	} // buscarDespesa() 
	
	/**
	 * Valida os dados da linha selecionada da tabela despesaTable e cria uma nova despesa na lista de despesas do orçamento.
	 */
	private void adicionarDespesas() {
		// Obtém o index da linha (row) selecionada/modificada.
		int linha = despesasTable.getSelectedRow(); 
		linha = (linha < 0) ? 0 : linha;
		
		// Armazena as informações da linha selecionada em strings.
		String dataTabela = (String) despesasTable.getValueAt(linha, 0);
		
		// Valida a data fornecida pelo usuário.
		if(dataTabela != null && despesa == null)
			if(!validaData(dataTabela)) {
				// Apaga ou restaura data da tabela
				defaultTableModel.setValueAt((despesa == null) ? STR_VAZIO : despesa.getData(), linha, 0);
				
				// Mostra uma mensagem de erro informando para o usuário que a data fornecido é inválida.
				showMessageDialog(this, ERRO_DATA_DESPESA_INVALIDA, PLANEJAMENTO_FINANCEIRO, ERROR_MESSAGE);
				return;
			}
			
		String descricao = (String) defaultTableModel.getValueAt(linha, 1);
		descricao = (descricao == null) ? STR_VAZIO : descricao;
		
		String valorString = (String) defaultTableModel.getValueAt(linha, 2);
	
		// Verifica se a coluna 'situação' está vazia e, caso esteja, atribui uma string vazia para a mesma. 
		// Isso evita que a string receba null.
		String situacao = (String) defaultTableModel.getValueAt(linha, 3);
		situacao = (situacao == null) ? STR_VAZIO : situacao;
		
		Float valor = 0F;
		// Verifica se a coluna 'valor' foi preenchida (null == não preenchida)
		if(valorString != null)
			try {
				// Verifica se a string do valor possui o caracter de vírgula e o substitui pelo ponto antes de realizar o parsing.
				if(valorString.contains(STR_VIRGULA))
					valorString = valorString.replace(CHAR_VIRGULA, CHAR_PONTO);
				
				// Realiza o parsing na string do valor.
				valor = Float.parseFloat(valorString);
				
				// Dispara uma excessão caso o valor seja menor que zero.
				if(valor <= 0)
					throw new NumberFormatException();
				
				// Reescreve o valor na tebela, por motivos estéticos estéticos.
				defaultTableModel.setValueAt(valorString, linha, 2);
			} catch (NumberFormatException e) {
				// Apaga o valor da tabela
				defaultTableModel.setValueAt((despesa == null) ? STR_VAZIO : String.format("%.2f", despesa.getValor()), linha, 2);
				
				// Mostra uma mensagem de erro informando para o usuário que o valor fornecido é inválido.
				showMessageDialog(this, ERRO_VALOR_DESPESA_INVALIDO, PLANEJAMENTO_FINANCEIRO, ERROR_MESSAGE);
				return;
			}
		
		// Verifica se há dados suficientes para armazenar a despesa na lista de despesas do orçamento.
		if(dataTabela != null && (!descricao.isBlank() || despesa != null) && valorString != null) {
			int mes = mesComboBox.getSelectedIndex() + 1;
			
			// Verifica se o mês da data fornecida corresponde ao mês da receita.
			if(despesa == null)
				if(obterMesData(dataTabela) != mes) {
					int opcao = showConfirmDialog(this, ERRO_MES_DESPESA_DIFERENTE, PLANEJAMENTO_FINANCEIRO, YES_NO_OPTION, WARNING_MESSAGE);
					if(opcao == NO_OPTION) {
						despesa = null;
						atualizarGUI();
						return;
					}
				}
				
			Despesa novaDespesa = new Despesa(dataTabela, descricao, situacao, valor);
			
			if(despesa == null) {
				orcamento.adicionarDespesa(novaDespesa);
			} else {
				// Verifica se a despesa selecionada foi removida.
				if(dataTabela.isBlank() || descricao.isBlank()) {
					int opcao = showConfirmDialog(this, APAGAR_DESPESA, PLANEJAMENTO_FINANCEIRO, YES_NO_OPTION, WARNING_MESSAGE);
					if(opcao == YES_OPTION) {
						// Remove a despesa do Banco de Dados.
						if(despesa.getId() != null) {
							DespesaDAO dao = new DespesaDAO(database);
							dao.remover(despesa);
						}
						
						// Remove a despesa da lista.
						orcamento.removerDespesa(despesa);
						despesa = null;
					}
					atualizarGUI();
					return;
				}
				
				// Verifica se a despesa já existe.
				if(linha != linhaDespesa) {
					String[] opcoes = {"Atualizar", "Somar"};
					
					despesasTable.setRowSelectionInterval(linhaDespesa, linhaDespesa);
					int opcao = showOptionDialog(this, ERRO_DESPESA_DUPLICADA, PLANEJAMENTO_FINANCEIRO, DEFAULT_OPTION, INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
					if(opcao == 1)
						novaDespesa.setValor(valor + despesa.getValor());
				}
				orcamento.atualizarDespesa(linhaDespesa, novaDespesa);
			}
			
			atualizarGUI();
		}
		modificado = true;
	} // adicionarDespesas()
	
	
	
	/**
	 * Adiciona ou atualiza os dados de uma receita do orçamento.
	 */
	private void atualizarReceita() {
		try {
			int mes = mesComboBox.getSelectedIndex() + 1;
			String tipo = (String) tipoComboBox.getSelectedItem();
			
			String texto = valorTextField.getText();
			
			// Verifica se a string do valor possui o caracter de vírgula e o substitui pelo ponto antes de realizar o parsing.
			if(texto.contains(STR_VIRGULA))
				texto = texto.replace(CHAR_VIRGULA, CHAR_PONTO);
			
			Float valor = (!texto.isBlank()) ? Float.parseFloat(texto) : 0F;
			
			orcamento.adicionarReceita(new Receita(mes, tipo, valor));
			
			atualizarBalanco();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // atualizaReceita()
	
	

	/**
	 * Adiciona ou atualiza os dados dos campos Balanço.
	 */
	private void atualizarBalanco() {
		int mes = mesComboBox.getSelectedIndex() + 1;
		
		List<Receita> receitasMes = orcamento.obterReceitasMes(mes);
		List<Despesa> despesasMes = orcamento.obterDespesasMes(mes);
		
		Float totalReceita = 0F,
				totalMensal = 0F,
				totalPagar = 0F,
				totalPago = 0F;
		
		for(Receita receita : receitasMes)
			totalReceita += receita.getValor();
		
		for(Despesa despesa : despesasMes) {
			if(despesa.getSituacao().isBlank())
				totalPagar += despesa.getValor();
			else
				totalPago += despesa.getValor();
			
			totalMensal += despesa.getValor();
		}
		
		
		totalMensalTextField.setText(String.format("%.2f", totalMensal));
		totalPagarTextField.setText(String.format("%.2f", totalPagar));
		totalPagoTextField.setText(String.format("%.2f", totalPago));
		saldoTextField.setText(String.format("%.2f", totalReceita - totalPago));
	} // atualizarBalanco()
	
	
	
	/**
	 * Adiciona ou atualiza os dados da tabela de despesas.
	 */
	private void atualizarDespesas() {
		int mes = mesComboBox.getSelectedIndex() + 1;
		
		List<Despesa> despesasMes = orcamento.obterDespesasMes(mes);
		int linha = 0;
		
		for(Despesa despesa : despesasMes) {
			defaultTableModel.setValueAt(despesa.getData(), linha, 0);
			defaultTableModel.setValueAt(despesa.getDescricao(), linha, 1);
			defaultTableModel.setValueAt(String.format("%.2f", despesa.getValor()), linha, 2);
			defaultTableModel.setValueAt(despesa.getSituacao(), linha, 3);
			
			linha ++;
		}
		
		atualizarBalanco();
	} // atualizarDespesas()
	
	/**
	 * Verifica se houveram modificações e finaliza o programa.
	 */
	private void finalizar() {
		verificarModificado();
		System.exit(0);
	} // finalizar()

	/**
	 * Verifica se o arquivo foi modificado e salva o mesmo, caso o usuário queira.
	 */
	private void verificarModificado() {
		if(modificado) {
			int opcao = showConfirmDialog(this, "Há dados não salvos! Deseja salva-los?", PLANEJAMENTO_FINANCEIRO, YES_NO_OPTION, WARNING_MESSAGE);
			if(opcao == YES_OPTION) {
				gravar();
			}
		}
	}
	

} // class IgPlanejamentoFinanceiro
