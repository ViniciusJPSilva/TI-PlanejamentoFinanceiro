package vjps.planejamento.financeiro.gui;

import javax.swing.JDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;

public class IgSobrePlanejamentoOrcamento extends JDialog {

	/**
	 * Cria e exibe a GUI.
	 */
	public IgSobrePlanejamentoOrcamento(Component component) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		getContentPane().setLayout(null);
		
		JLabel nomeLabel = new JLabel("Planejamento Financeiro");
		nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		nomeLabel.setBounds(10, 11, 414, 30);
		getContentPane().add(nomeLabel);
		
		JLabel versaoLabel = new JLabel("Versão 1.0");
		versaoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		versaoLabel.setBounds(54, 59, 86, 23);
		getContentPane().add(versaoLabel);
		
		JLabel anoLabel = new JLabel("2022");
		anoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		anoLabel.setBounds(54, 93, 69, 18);
		getContentPane().add(anoLabel);
		
		JLabel devLabel = new JLabel("Desenvolvido por Vinícius José Pires Silva");
		devLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		devLabel.setBounds(10, 157, 414, 23);
		getContentPane().add(devLabel);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Sobre o Planejamento Financeiro");
		setBounds((component != null) ? component.getX():10, (component != null) ? component.getY():10, 450, 230);
		setModal(true);
		setVisible(true);
	} // construtor
} // class IgSobrePlanejamentoOrcamento
