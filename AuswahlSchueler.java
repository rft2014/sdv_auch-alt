package sdv;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

import com.itextpdf.text.DocumentException;

public class AuswahlSchueler extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Erzeugt Auswahlfenster unter Datei > Schüler  um Datensätze auszuwählen
	 * zur Bearbeitung etc.
	 */
	public AuswahlSchueler() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 450, 300);
		setLocationRelativeTo(getParent());
		setTitle("Bearbeitung der Schülerdaten");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][][][grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 1 3,grow");
		
		final JList list = new JList(DBDatenInGui.stringFuerAuswahlMenu());
		scrollPane.setViewportView(list);
		
		JButton bearbeitenBtn = new JButton("Datensatz öffnen");
		contentPane.add(bearbeitenBtn, "flowy,cell 2 3");
		
		JButton aktualisierenBtn = new JButton("Datensatz aktualisieren");
		//contentPane.add(aktualisierenBtn, "cell 2 3");
		
		JButton loeschBtn = new JButton("Datensatz löschen");
		contentPane.add(loeschBtn, "cell 2 3");
		
		JButton datenblattAusdruck = new JButton("Schülerdatenblatt ausdrucken");
		contentPane.add(datenblattAusdruck, "cell 2 3");
		
		JButton btnNewButton_4 = new JButton("New button");
		//contentPane.add(btnNewButton_4, "cell 2 3");
		
		bearbeitenBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			try{
				//System.out.println(list.getSelectedIndex());
				DBDatenInGui.datenInGui(list.getSelectedValue().toString());
			}catch(NullPointerException np){
				
				JOptionPane.showMessageDialog(null,"Sie müssen erst einen Datensatz auswählen um ihn zum Bearbeiten öffnen zu können. Die Systemmeldung lautet: \n"+np,"Upps, eine Fehlermeldung!"
						,JOptionPane.ERROR_MESSAGE); 
			}
			dispose();
			}
		});
		aktualisierenBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int wutsch = JOptionPane.showOptionDialog(null, "Wollen Sie den Datensatz von Schüler\n"+list.getSelectedValue()+ "\n wirklich aktualisieren?","Warnung!",
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.WARNING_MESSAGE, null, 
		                new String[]{"Ja", "Nein"},"");
				
			if (wutsch == 0){
				try{
				GuiDatenInDB.updateDB(list.getSelectedValue().toString());
				Main.clearGui();
				}catch(NullPointerException np){
					
					JOptionPane.showMessageDialog(null,"Sie müssen erst einen Datensatz öffnen. Die Systemmeldung lautet: \n"+np,"Upps, eine Fehlermeldung!"
							,JOptionPane.ERROR_MESSAGE); 
					}
			}else{}
			
			}
		});
		
loeschBtn.addActionListener(new ActionListener() {
	
	
			public void actionPerformed(ActionEvent e) {
				int wutsch = JOptionPane.showOptionDialog(null, "Wollen Sie den Schüler\n"+list.getSelectedValue()+ "\n wirklich löschen?","Warnung!",
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.WARNING_MESSAGE, null, 
		                new String[]{"Ja", "Nein"},"");
				
			if (wutsch == 0){
				try{
					GuiDatenInDB.datensatzLoeschen(list.getSelectedValue().toString());
				}catch(NullPointerException np){
					
					JOptionPane.showMessageDialog(null,"Sie müssen erst einen Datensatz auswählen. Die Systemmeldung lautet: \n"+np,"Upps, eine Fehlermeldung!"
							,JOptionPane.ERROR_MESSAGE); 
					}
			}else{}
			dispose();
			}
		});

datenblattAusdruck.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e) {
		MakePdf mp = new MakePdf();
		
		try{
		mp.createSchuelerDatenBlatt(Main.OutDir +"/schülerdatenblätter/" + list.getSelectedValue().toString() + ".pdf",list.getSelectedValue().toString() );
		}catch(NullPointerException npe)
		{
			JOptionPane.showMessageDialog(null,"Sie müssen erst einen Datensatz auswählen. Die Systemmeldung lautet: \n"+npe,"Upps, eine Fehlermeldung!"
					,JOptionPane.ERROR_MESSAGE); 
		}
		try
		{
			//Runtime.getRuntime().exec("okular /skole/tjener/home0/rolteich/sdv/listen/knachname,krufname-8.pdf");
			Runtime.getRuntime().exec( "okular " + Main.OutDir +"/schülerdatenblätter/"+ list.getSelectedValue().toString() + ".pdf");
		 // Desktop.getDesktop().open( new File(Main.OutDir + list.getSelectedValue().toString() + ".pdf")) ;
		}
		catch ( Exception /* IOException, URISyntaxException */ exc )
		{
		  exc.printStackTrace();
		}
	dispose();
		
	}
});
	}

}
