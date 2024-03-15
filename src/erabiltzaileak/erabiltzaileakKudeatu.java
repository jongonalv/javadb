package erabiltzaileak;

import java.sql.SQLException;
import java.util.Scanner;

public class erabiltzaileakKudeatu {
	
	public static void main(String[] args) {

		// Erabiltzaileari datu guztiak eskatu
		Scanner scanner = new Scanner(System.in);

		String izena 		= Erabiltzailea.getDatua("Sartu izena:");
		String abizena 		= Erabiltzailea.getDatua("Sartu abizena:");
		String pasahitza1 	= Erabiltzailea.getDatua("Sartu pasahitza:");
		String pasahitza2 	= Erabiltzailea.getDatua("Sartu berriz:");

		// Konprobatu ea pasahitzak berdinak diren
		if (!pasahitza1.equals(pasahitza2)) {
			System.out.println("Pasahitzak ez dira berdinak.");
			System.exit(0);
		}

		System.out.println("Sartu NA (9 karaktere):");
		String NA = scanner.nextLine();

		Erabiltzailea Erab = null;

		// Erabiltzaile bat sortu eta erabiltzaile izena generatu metodoari deituz
		try {
			Erab = new Erabiltzailea(izena, abizena, pasahitza1, NA);
			Erab.setErabiltzaile_izena(Erabiltzailea.sortuErabiltzaileaIzena(izena, abizena));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Erabiltzaile izena berria gehitu datu basean
		try {
			dbKudeatzailea.insertErabiltzailea(Erab);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scanner.close();

	}
}
