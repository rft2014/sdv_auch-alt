package sdv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

/**
 * First iText example: Hello World.
 */
public class MakePdf {
	
	Date today = new Date();
	SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy" );
//	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.SHORT);

	private static Connection getInstance() {
		if (DBConnection.con == null)
			new DBConnection();
		return DBConnection.con;
	}

	/**
	 * Creates a PDF document.
	 * 
	 * @param filename
	 *            the path to the new PDF document
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createSchuelerDatenBlatt(String filename, String auswahl) {
		auswahl = auswahl.replaceAll("\\D+", "");
		String gender = "";
		String geschwister = "";
		String landkreis = "";
		String religion = "";
		String ga = "";
		String doppel = "";
		Connection con = getInstance();
		if (con != null) {
		}
		try {
			Statement schuelerDatensatz = DBConnection.con.createStatement();
			String fuerPdf = "Select * FROM schuelerdaten WHERE uid = '"
					+ auswahl + "'; ";
			ResultSet rs = schuelerDatensatz.executeQuery(fuerPdf);
			rs.first();
			
			if(rs.getString("maennlich").equals("1")){gender = "männlich";};
			if(rs.getString("weiblich").equals("1")){gender = "weiblich";};
			if(rs.getString("geschwister_ja").equals("1")){geschwister = "ja";};
			if(rs.getString("geschwister_nein").equals("1")){geschwister = "nein";};
			if(rs.getString("landkreis_ja").equals("1")){landkreis = "ja";};
			if(rs.getString("landkreis_nein").equals("1")){landkreis = "nein";};
			if(rs.getString("religion").equals("1")){religion = "evangelische Religion";};
			if(rs.getString("ethik").equals("1")){religion = "Ethik";};
			if(rs.getString("oga_ja").equals("1")){ga = "ja";};
			if(rs.getString("oga_nein").equals("1")){ga = "nein";};
			if(rs.getString("doppel_ja").equals("1")){doppel = "ja";};
			if(rs.getString("doppel_nein").equals("1")){doppel = "nein";};
			final Font title = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
			final Font section = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
			final Font subtitle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
			final Font rufname = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC);
			final Font txt = new Font(Font.FontFamily.HELVETICA, 11);
			Chunk tab1 = new Chunk(new VerticalPositionMark(), 140, true);
		    Chunk tab2 = new Chunk(new VerticalPositionMark(), 240, true);
		    Chunk tab3 = new Chunk(new DottedLineSeparator(), 450, true);

			// step 1
			Document document = new Document();
			document.setMargins(72, 36, 60, 60);

			// step 2
			PdfWriter.getInstance(document, new FileOutputStream(filename));
			// step 3
			document.open();
			// step 4
			document.add(new Paragraph("Schülerdatenblatt",title));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Name: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Vorname: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kvorname"),rufname));
			document.add(new Phrase("  " + rs.getString("kbeiname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Geschlecht: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(gender,txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Geburtstag: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kgeburtstag"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Geburtsort: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kgeburtsort"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("wohnhaft: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kstrasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kplz")+" ",txt));
			document.add(new Phrase(rs.getString("kwohnort")+" - "+rs.getString("kOT"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Staatsangehörigkeit: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kstaat"),txt));
			document.add(new Chunk(tab1));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Religion: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kreligion"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Krankenkasse ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kkrankenkasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Hausarzt: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("khausarzt"),txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Mutter",subtitle));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Name: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Vorname: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mvorname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Anschrift: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mstrasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mplz")+" ",txt));
			document.add(new Phrase(rs.getString("mwohnort"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Telefon privat: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mtelpriv"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Telefon dienstlich: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("mteldienst"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("E-Mail: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("memail"),txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Vater",subtitle));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Name: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Vorname: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vvorname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Anschrift: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vstrasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vplz")+" ",txt));
			document.add(new Phrase(rs.getString("vwohnort"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Telefon privat: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vtelpriv"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Telefon dienstlich: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vteldienst"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("E-Mail: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("vemail"),txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Person des Vertrauens",subtitle));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Name: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("oname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Vorname: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("ovorname"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Anschrift: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("ostrasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("oplz")+" ",txt));
			document.add(new Phrase(rs.getString("owohnort"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Telefon: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("otel"),txt));
			document.add(Chunk.NEWLINE);
			
			document.newPage();
			
			document.add(new Phrase("Stammschule: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("kstammschule"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Zugangsvoraussetzung: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("zugangsvoraussetzung"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Sorgeberechtigung: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("sorgeberechtigung"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Kommt aus Klasse: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("ausklasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Geht in Klasse: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("inklasse"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Zweite Fremdsprache: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(rs.getString("zweiteFremdsprache"),txt));
			//document.add(new Phrase("  (unverbindlich)",txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Geschwister am vBG: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(geschwister,txt));
			document.add(new Phrase(", " + rs.getString("geschwistername"),txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Wohnt im Landkreis: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(landkreis,txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Religion/Ethik: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(religion,txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Teilnahme am GA: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(ga,txt));
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Doppelanmeldung: ",section));
			document.add(new Chunk(tab1));
			document.add(new Phrase(doppel,txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Bemerkungen: ",section));
			document.add(new Chunk(tab1));
			document.add(new Paragraph(rs.getString("bemerkungen"),txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Phrase("Neudietendorf, den " + df.format(today),txt));
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(new Chunk(tab3));
			document.add(new Phrase("(Unterschrift der Eltern)",txt));
			// step 5
			document.close();
		} catch (DocumentException de) {
		} catch (FileNotFoundException fnf) {
		} catch (SQLException se) {
		}

	}

public void createAnmeldungSA(String ausklasse, String inklasse,String filename){
	final Font title = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
	final Font zellinhalt = new Font(Font.FontFamily.HELVETICA, 8);
	final Font header = new Font(Font.FontFamily.HELVETICA, 10,Font.BOLD);
	//String gender = "";
	//String lk = "";
	Connection con = getInstance();
	if (con != null) {
	}
	try {
		Statement listeSA = DBConnection.con.createStatement();
		String fuerListeSA = "Select * FROM schuelerdaten WHERE ausklasse = '"
				+ ausklasse + "' AND  inklasse = '"+ inklasse +"' AND term = '1' ORDER BY kname; ";
		ResultSet rs = listeSA.executeQuery(fuerListeSA);
	
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(36, 36, 36, 36);

		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// PdfPCell cell;

		// step 3
		document.open();
		
		document.add(new Paragraph("Anmeldungen am von-Bülow-Gymnasium für Schuljahr 2015/16",title));
		document.add(new Paragraph("Übergang von Klasse "+ausklasse + " in "+inklasse,title));
		document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(new float[]{1,2,2,1,4,1,2,2});
		table.setHeaderRows(1);
		table.addCell(new Phrase("lfd. Nr.",header));
		table.addCell(new Phrase("Name, Vorname",header));
		table.addCell(new Phrase("Geburtstag",header));
		table.addCell(new Phrase("m/w",header));
		table.addCell(new Phrase("Adresse",header));
		
		//table.addCell(new Phrase("m/w",header));
		table.addCell(new Phrase("LK\nGth",header));
		table.addCell(new Phrase("vorh. Schule",header));
		table.addCell(new Phrase("Doppel- anmeldung",header));
		int lfdNr = 0;
		while (rs.next()){
		lfdNr += 1;	
		String gender = "";
		String lk = "";
		String doppel="";
		if(rs.getString("maennlich").equals("1")){gender = "m";}
		if(rs.getString("weiblich").equals("1")){gender = "w";}
		if(rs.getString("landkreis_ja").equals("1")){lk = "ja";}
		if(rs.getString("landkreis_nein").equals("1")){lk = "nein";}
		if(rs.getString("doppel_ja").equals("1")){doppel = "ja";}
		if(rs.getString("doppel_nein").equals("1")){doppel = "nein";}
		table.addCell(new Phrase(String.valueOf(lfdNr),zellinhalt));	
		table.addCell(new Phrase(rs.getString("kname")+", "+rs.getString("kvorname")+" "+rs.getString("kbeiname"),zellinhalt));
		table.addCell(new Phrase(rs.getString("kgeburtstag"),zellinhalt));
		table.addCell(new Phrase(gender,zellinhalt));
		table.addCell(new Phrase(rs.getString("kplz")+" "+rs.getString("kwohnort")+" "+rs.getString("kOT")+"\n"+rs.getString("kstrasse"),zellinhalt));
		//table.addCell(new Phrase(rs.getString("kgeburtstag") +"\n"+rs.getString("kgeburtsort"),zellinhalt));
		//table.addCell(new Phrase(gender,zellinhalt));
		table.addCell(new Phrase(lk,zellinhalt));
		table.addCell(new Phrase(rs.getString("kstammschule"),zellinhalt));
		table.addCell(new Phrase(doppel,zellinhalt));
		}
		

		document.add(table);
		document.close();
	}
	catch(SQLException sqe){System.out.println(sqe);}
	catch(FileNotFoundException fne){System.out.println(fne);}
	catch(DocumentException de){System.out.println(de);}
	}


public void createAnmeldungSA_probe(String ausklasse, String inklasse,String filename){
	final Font title = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
	final Font zellinhalt = new Font(Font.FontFamily.HELVETICA, 8);
	final Font header = new Font(Font.FontFamily.HELVETICA, 10,Font.BOLD);
	//String gender = "";
	//String lk = "";
	Connection con = getInstance();
	if (con != null) {
	}
	try {
		Statement listeSA = DBConnection.con.createStatement();
		String fuerListeSA = "Select * FROM schuelerdaten WHERE ausklasse = '"
				+ ausklasse + "' AND  inklasse = '"+ inklasse +"' AND zugangsvoraussetzung = 'Probeunterricht' AND term = '1' ORDER BY kname; ";
		ResultSet rs = listeSA.executeQuery(fuerListeSA);
	
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(36, 36, 36, 36);

		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// PdfPCell cell;

		// step 3
		document.open();
		
		document.add(new Paragraph("Anmeldungen zum Probeunterricht \nvon-Bülow-Gymnasium, Schuljahr 2014/15",title));
		document.add(new Paragraph("Übergang von Klasse "+ausklasse + " in "+inklasse,title));
		document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(new float[]{1,2,2,1,4,2});
		table.setHeaderRows(1);
		table.addCell(new Phrase("lfd. Nr.",header));
		table.addCell(new Phrase("Name, Vorname",header));
		table.addCell(new Phrase("Geburtstag",header));
		table.addCell(new Phrase("m/w",header));
		table.addCell(new Phrase("Adresse",header));
		
		table.addCell(new Phrase("Telefon priv./dienstl.",header));
		//table.addCell(new Phrase("LK\nGth",header));
		//table.addCell(new Phrase("vorh. Schule",header));
		//table.addCell(new Phrase("Doppel- anmeldung",header));
		int lfdNr = 0;
		while (rs.next()){
		lfdNr += 1;	
		String gender = "";
		String lk = "";
		String doppel="";
		if(rs.getString("maennlich").equals("1")){gender = "m";}
		if(rs.getString("weiblich").equals("1")){gender = "w";}
	//	if(rs.getString("landkreis_ja").equals("1")){lk = "ja";}
	//	if(rs.getString("landkreis_nein").equals("1")){lk = "nein";}
	//	if(rs.getString("doppel_ja").equals("1")){doppel = "ja";}
	//	if(rs.getString("doppel_nein").equals("1")){doppel = "nein";}
		table.addCell(new Phrase(String.valueOf(lfdNr),zellinhalt));	
		table.addCell(new Phrase(rs.getString("kname")+", "+rs.getString("kvorname")+" "+rs.getString("kbeiname"),zellinhalt));
		table.addCell(new Phrase(rs.getString("kgeburtstag"),zellinhalt));
		table.addCell(new Phrase(gender,zellinhalt));
		table.addCell(new Phrase(rs.getString("kplz")+" "+rs.getString("kwohnort")+" "+rs.getString("kOT")+"\n"+rs.getString("kstrasse"),zellinhalt));
		table.addCell(new Phrase(rs.getString("mtelpriv") +"\n"+rs.getString("mteldienst"),zellinhalt));
		//table.addCell(new Phrase(gender,zellinhalt));
		//table.addCell(new Phrase(lk,zellinhalt));
		//table.addCell(new Phrase(rs.getString("kstammschule"),zellinhalt));
		//table.addCell(new Phrase(doppel,zellinhalt));
		}
		

		document.add(table);
		document.close();
	
	
	}
	
	catch(SQLException sqe){System.out.println(sqe);}
	catch(FileNotFoundException fne){System.out.println(fne);}
	catch(DocumentException de){System.out.println(de);}
	}


public void createKlassenliste(String klasse, String typ ,String filename){
	final Font title = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
	final Font zellinhalt = new Font(Font.FontFamily.HELVETICA, 8);
	final Font header = new Font(Font.FontFamily.HELVETICA, 10,Font.BOLD);
	//String gender = "";
	//String lk = "";
	Connection con = getInstance();
	if (con != null) {
	}
	try {
		Statement klListe = DBConnection.con.createStatement();
		String klassenliste = "Select * FROM schuelerdaten WHERE klasse = '"
				+ klasse + "'  ORDER BY kname; ";
		ResultSet rs = klListe.executeQuery(klassenliste);
	
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(36, 36, 36, 36);

		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// PdfPCell cell;

		// step 3
		document.open();
		document.add(new Paragraph("Klassenliste:  "+klasse ,title));
		
		PdfPTable table = new PdfPTable(new float[]{1,2,2,1,4,1,2,2});
		table.setHeaderRows(1);
		
		
		
		document.add(table);
		document.close();
	
	
	}
	
	catch(SQLException sqe){System.out.println(sqe);}
	catch(FileNotFoundException fne){System.out.println(fne);}
	catch(DocumentException de){System.out.println(de);}
	}
}

