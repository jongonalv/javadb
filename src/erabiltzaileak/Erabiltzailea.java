package erabiltzaileak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;


// TODO: Auto-generated Javadoc
/**
 * The Class Erabiltzailea.
 */
public class Erabiltzailea {
	
	/** izena. */
	private String izena;
	
	/** Abizena. */
	private String abizena;
	
	/** Pasahitza --> 6 luzera baino handiagoa*/
	private String pasahitza;
	
	/** Erabiltzaile izena */
	private String erabiltzaile_izena;
	
	/** Nortasun agiria --> 9 luzerakoa bai ala bai*/
	private String NA;
	
	private static Scanner scanner = new Scanner(System.in);
	
	/**
	 * Erabiltzaile berri bat sortzeko metodo eraikitzailea.
	 *
	 * @param izena 		--> erabiltzailearen izena
	 * @param abizena 		--> erabiltzailearen abizena
	 * @param pasahitza		--> erabiltzailearen pasahitza
	 * @param NA 			--> Erabiltzailearen nortasun agiria
	 * @throws Exception 	--> datuetan erroreren bat dagoenean (pasahitza eta nortasun agiria)
	 */
	public Erabiltzailea(String izena, String abizena, String pasahitza, String NA) throws Exception {
		setIzena(izena);
		setAbizena(abizena);
		setPasahitza(pasahitza);
		setNA(NA);
	}
	
	/**
	 * Izena lortu.
	 *
	 * @return the izena
	 */
	public String getIzena() {
		return izena;
	}
	
	/**
	 * Izena jarri.
	 *
	 * @param izena the new izena
	 */
	public void setIzena(String izena) {
		this.izena = izena;
	}
	
	/**
	 * Abizena lortu.
	 *
	 * @return the abizena
	 */
	public String getAbizena() {
		return abizena;
	}
	
	/**
	 * Abizena jarri.
	 *
	 * @param abizena the new abizena
	 */
	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}
	
	/**
	 * Pashitza lortu.
	 *
	 * @return the pasahitza
	 */
	public String getPasahitza() {
		return pasahitza;
	}
	
	/**
	 * Pasahitza jarri. Luzera minimo 6koa izan behar du, bestela
	 * ez du funtzionatuko
	 *
	 * @param pasahitza the new pasahitza
	 * @throws Exception --> errorea 5=< bada
	 */
	public void setPasahitza(String pasahitza) throws Exception {
		if (pasahitza.length() < 6) {
			throw new Exception("Errorea: Pasahitza 6 luzerakoa baino gehiago izan behar du");
		}	else	{
			this.pasahitza = pasahitza;
		}
	}
	
	/**
	 * Nortasun agiria lortu.
	 *
	 * @return the na
	 */
	public String getNA() {
		return NA;
	}
	
	/**
	 * Nortasun agiria jarri. 9 luzerakoa izen behar du, bestela
	 * ez du funtzionatzen 
	 *
	 * @param NA the new na
	 * @throws Exception --> 8 edo txikiagoa edo 10 baino handiagoa bada.
	 */
	public void setNA(String NA) throws Exception {
		if (NA.length() == 9) {
			this.NA = NA;
		}	else {
			throw new Exception("Nortasun agiria 9 luzerakoa izan behar du");
		}
	}
	
	/**
	 * Erabiltzaile izena jarri.
	 *
	 * @param erabiltzaile_izena the new erabiltzaile izena
	 */
	public void setErabiltzaile_izena(String erabiltzaile_izena) {
		this.erabiltzaile_izena = erabiltzaile_izena;
	}
	
	/**
	 * Erabiltzaile izena lortu.
	 *
	 * @return the erabiltzaile izena
	 */
	public String getErabiltzaile_izena() {
		return erabiltzaile_izena;
	}
	
    public static String getDatua(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
	
	/**
	 * Erabiltzaile izen bat sortzeko metodoa. Izenaren lehen karaketera hartzen du,
	 * eta abizena gehitzen dio. Existitzen bada, bigarren karakterea hartzen du. Berriz
	 * existitzen bada, erabiltzaile01, erabiltzaile02, erabiltzaile03... formatua erabiltzen du
	 * erabiltzaileak sartu duen izena eta abizena erabiliz.
	 *
	 * @param izena		--> erabiltzailearen izena
	 * @param abizena	--> erabiltzailearen abizena
	 * @return erabiltzaile izena konposatua
	 * @throws SQLException --> datu basearekin errorerik gertatzen bada
	 */
	public static String sortuErabiltzaileaIzena(String izena, String abizena) throws SQLException {
		
        StringBuilder erabiltzailea = new StringBuilder();
        erabiltzailea.append(izena.charAt(0)).append(abizena);
        String baseErabiltzailea = erabiltzailea.toString();

        ResultSet rs = dbKudeatzailea.getErabiltzaileaIzenak();

        List<String> sortutakoErabiltzaileak = new ArrayList<>();
        while (rs.next()) {
            sortutakoErabiltzaileak.add(rs.getString("erabiltzaile_izena"));
        }

        if (sortutakoErabiltzaileak.contains(baseErabiltzailea)) {
            erabiltzailea.setLength(0);
            erabiltzailea.append(izena.charAt(0)).append(izena.charAt(1)).append(abizena);
            baseErabiltzailea = erabiltzailea.toString();

            if (sortutakoErabiltzaileak.contains(baseErabiltzailea)) {
                erabiltzailea.setLength(0);
                erabiltzailea.append(izena).append(abizena);
                baseErabiltzailea = erabiltzailea.toString();
                int sufijoa = 1;
                String newErabiltzailea;
                do {
                    newErabiltzailea = baseErabiltzailea + String.format("%02d", sufijoa);
                    sufijoa++;
                } while (sortutakoErabiltzaileak.contains(newErabiltzailea));

                return newErabiltzailea;
            } else {
                return baseErabiltzailea;
            }
        } else {
            return baseErabiltzailea;
        }
    }
}