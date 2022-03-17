package vjps.planejamento.financeiro.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import vjps.planejamento.financeiro.bd.Database;
import vjps.planejamento.financeiro.bd.dao.OrcamentoDAO;
import vjps.planejamento.financeiro.modelo.Orcamento;

public class IgAbrirOrcamento extends JDialog {
	
	private JList<String> orcamentoList;
	private OrcamentoDAO dao;
	
	/**
	 * Cria e exibe a GUI.
	 */
	public IgAbrirOrcamento(Component component, Database database, Orcamento orcamento) {
		
		dao = new OrcamentoDAO(database);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				orcamentoList.setModel(new AbstractListModel<>() {
					// Itens que são exibidos na caixa de listagem.
					String[] orcamentos = obterNomesOrcamentos(database);
					
					public int getSize() { return orcamentos.length; }
					public String getElementAt(int index) { return orcamentos[index]; }
				});
				
			}
		});
		
		JPanel buscaPanel = new JPanel();
		getContentPane().add(buscaPanel, BorderLayout.CENTER);
			buscaPanel.setLayout(new BorderLayout(0, 0));
		
		// Cria um painel rolável para a caixa de listagem.
			JScrollPane scrollPane = new JScrollPane();
		
		// Cria a caixa de listagem.
		orcamentoList = new JList<>();
		
		// Define o número de linhas visíveis na caixa de listagem.
		orcamentoList.setVisibleRowCount(5);
		
		// Define a caixa de listagem como o objeto a ser exibido no painel rolável.  
		scrollPane.setViewportView(orcamentoList);
		
		// Define o mode de seleção de itens da caixa de listagem.
		orcamentoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Define o terceiro item (a cor branco) da caixa de listagem como um item selecionado (default).
		orcamentoList.setSelectedIndex(0);
		buscaPanel.add(scrollPane);
		
		
		JPanel botoesPanel = new JPanel();
		FlowLayout fl_botoesPanel = (FlowLayout) botoesPanel.getLayout();
		fl_botoesPanel.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(botoesPanel, BorderLayout.SOUTH);
		
		JButton abrirButton = new JButton("Abrir");
		abrirButton.setMnemonic(KeyEvent.VK_A);
		abrirButton.setToolTipText("Abre o orçamento selecionado");
		botoesPanel.add(abrirButton);
		
		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.setMnemonic(KeyEvent.VK_C);
		cancelarButton.setToolTipText("Cancela a operação.");
		botoesPanel.add(cancelarButton);
		
		
		abrirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orcamento.setNome(orcamentoList.getSelectedValue());
				dispose();
			}
		});
		
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Abrir Orçamento");
		setBounds((component != null) ? component.getX():10, (component != null) ? component.getY():10, 460, 176);
		setModal(true);
		setVisible(true);
	} // construtor

	/**
	 * Obtém e retorna os nomes de todos os orçamentos armazenados no BD.
	 * @param database
	 * @return nomes dos orçamentos.
	 */
	private String[] obterNomesOrcamentos(Database database) {
		List<Orcamento> orcamentos = dao.getListaOrcamentos();
		
		String[] nomes = new String[orcamentos.size()];
		
		int i = 0;
		for(Orcamento orcamento : orcamentos) {
			nomes[i] = orcamento.getNome();
			i++;
		}
			
		return nomes;
	} // obterNomesOrcamentos()

} // class IgAbrirOrcamento
