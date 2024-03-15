package saioa_hasi;

import java.sql.SQLException;
import java.util.Scanner;

public class erabiltzaileakKudeatu {

	public static void main(String[] args) {

		String erabiltzailea = Erabiltzailea.getDatua("Erabiltzaile izena:");
		String pasahitza1 = Erabiltzailea.getDatua("Pasahitza:");
		
		if(dbKudeatzailea.erabiltzaileaKonprobatu(erabiltzailea, pasahitza1)) {
			System.out.println("Log in!");
		}	else	{
			System.out.println("Erabiltzaile edo pasahitza gaizki. Berriro saiatu, mesedez.");
		}

	}
}
