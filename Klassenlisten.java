package sdv;
import java.awt.BorderLayout;
//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;

public class Klassenlisten extends JFrame {
	private JComboBox klasse;
	private JPanel contentPane;
	
	public Klassenlisten(){
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	setTitle("Klassenlisten");
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new MigLayout("", "[][][][][][][][grow][grow]", "[][][][][][][][]"));
	
	JLabel lblNewLabel = new JLabel("Klasse");
	contentPane.add(lblNewLabel, "cell 5 1");
	
	JComboBox comboBoxKlasse = new JComboBox();
	contentPane.add(comboBoxKlasse, "cell 7 1,growx");
	
	JLabel lblNewLabel_1 = new JLabel("Listentyp");
	contentPane.add(lblNewLabel_1, "cell 5 2");
	
	JComboBox comboBoxListenTyp = new JComboBox();
	contentPane.add(comboBoxListenTyp, "cell 7 2,growx");
	
	JButton btnKlassenlisteErstellen = new JButton("Liste erstellen");
	contentPane.add(btnKlassenlisteErstellen, "cell 8 7");
	
	btnKlassenlisteErstellen.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			//Code zum Erstellen des pdf
		}
	});
	}
}
		
	
	

