package vjps.planejamento.financeiro.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class IgPesquisarDespesa extends JDialog {
	
	private JTextField itemTextField;
	private int radioButtonSelecionado;
	private int linhaSelecionada;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Cria e exibe a GUI
	 */
	public IgPesquisarDespesa(Component component, JTable tabela) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				itemTextField.requestFocus();
				alterarParametros(radioButtonSelecionado);
			}
		});
		
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		// Painéis
		JPanel pesquisaPanel = new JPanel();
		JPanel pesquisarPorPanel = new JPanel();
		
		pesquisaPanel.setBorder(new TitledBorder(null, "Pesquisa", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pesquisarPorPanel.setBorder(new TitledBorder(null, "Pesquisar por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		pesquisaPanel.setBounds(10, 11, 464, 70);
		pesquisarPorPanel.setBounds(10, 92, 464, 70);
		
		contentPane.add(pesquisaPanel);
		contentPane.add(pesquisarPorPanel);
		
		// Labels
		JLabel itemLabel = new JLabel("Item de despesa");
		itemLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		itemLabel.setDisplayedMnemonic(KeyEvent.VK_I);
		pesquisaPanel.add(itemLabel);
		
		// TextFields
		itemTextField = new JTextField();
		itemLabel.setLabelFor(itemTextField);
		pesquisaPanel.add(itemTextField);
		itemTextField.setColumns(27);
		
		// RadioButtons
		JRadioButton dataRadioButton = new JRadioButton("Data");
		JRadioButton descricaoRadioButton = new JRadioButton("Descrição");
		JRadioButton valorRadioButton = new JRadioButton("Valor");
		
		dataRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		descricaoRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		valorRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		buttonGroup.add(dataRadioButton);
		buttonGroup.add(descricaoRadioButton);
		buttonGroup.add(valorRadioButton);
		
		dataRadioButton.setMnemonic(KeyEvent.VK_D);
		descricaoRadioButton.setMnemonic(KeyEvent.VK_E);
		valorRadioButton.setMnemonic(KeyEvent.VK_V);
		
		pesquisarPorPanel.add(dataRadioButton);
		pesquisarPorPanel.add(descricaoRadioButton);
		pesquisarPorPanel.add(valorRadioButton);
		
		// Define o radioButton Descrição como o selecionado.
		descricaoRadioButton.setSelected(true);
		radioButtonSelecionado = 1;
		
		// Trata a selecção de um radioButton
		dataRadioButton.addItemListener( (itemEvent)  -> alterarParametros(0)); 
		descricaoRadioButton.addItemListener( (itemEvent)  -> alterarParametros(1)); 
		valorRadioButton.addItemListener( (itemEvent)  -> alterarParametros(2)); 
		
		// Botões 
		JButton cancelarButton = new JButton("Cancelar");
		JButton proximaDespesaButton = new JButton("Próxima despesa");
		
		cancelarButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		proximaDespesaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		cancelarButton.setMnemonic(KeyEvent.VK_C);
		proximaDespesaButton.setMnemonic(KeyEvent.VK_P);
		
		cancelarButton.setBounds(384, 172, 90, 28);
		proximaDespesaButton.setBounds(237, 173, 137, 28);
		
		contentPane.add(cancelarButton);
		contentPane.add(proximaDespesaButton);
		
		// Definindo o botão default
		rootPane.setDefaultButton(proximaDespesaButton);
		

		// Tratando os eventos dos botões
		cancelarButton.addActionListener((event) -> dispose());
		proximaDespesaButton.addActionListener((event) -> selecionarDespesa(tabela));
		
		// Janela
		setTitle("Pesquisar Despesa");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds((component != null) ? component.getX():10, (component != null) ? component.getY():10, 500, 250);
		setResizable(false);
		setModal(true);
		setVisible(true);
	} // construtor

	
	/**
	 * Realiza uma varredura na tabela e seleciona (destaca) a linha correspondente ao conteúdo pesquisado, caso exista.
	 * @param tabela Tabela
	 */
	private void selecionarDespesa(JTable tabela) {
		
		int coluna = radioButtonSelecionado;
		
		for(int linha = linhaSelecionada + 1; linha < tabela.getRowCount(); linha++) {
			
			String textoTabela = (String) tabela.getValueAt(linha, coluna),
					textoItem = itemTextField.getText();
			if(textoTabela != null)
				if(textoTabela.equalsIgnoreCase(textoItem)) {
					tabela.setRowSelectionInterval(linha, linha);
					linhaSelecionada = linha;
					return;
				}
		}
		
		tabela.clearSelection();
		alterarParametros(radioButtonSelecionado);
	} // buscarDespesa()
	
	/**
	 * Altera os valores das variáveis utilizadas como parâmetros de pesquisa.
	 * @param valorRadioButton
	 */
	private void alterarParametros(int valorRadioButton) {
		radioButtonSelecionado = valorRadioButton;
		linhaSelecionada = -1;
		itemTextField.requestFocus();
	} // alterarParametros()
	
} // class IgPesquisarDespesa
