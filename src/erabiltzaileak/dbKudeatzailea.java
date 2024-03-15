package erabiltzaileak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: Auto-generated Javadoc
/**
 * The Class dbKudeatzailea.
 */
public class dbKudeatzailea {
	
	/** Datu basearen konexioa egiteko URL-a */
	static final String DB_URL = "jdbc:mysql://localhost:3308/erabiltzaileak?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	
	/** Datu basearen erabiltzailea konstantea. */
	static final String USER = "root";
	
	/** Erabiltzailearen pasahitza datu basean konstantea */
	static final String PASS = "root";

    /**
     * Datu basearekin konexioa egiteko metodoa. URL, USER eta PASS-en arabera.
     *
     * @return the konexioa
     * @throws SQLException --> Konexio erroreak
     */
    public static Connection getKonexioa() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    /**
     * Erabiltzaileak taula egiteko metodoa. Izena, abizena, pasahitza
     * NA, eta erabiltzaile izena atributoaekin. Lehenik konexioa egiten da
     * gure datu basearekin eta gero sql taula sortu eta exekutatzen da script
     * moduan.
     */
    public static void createErabiltzaileakTable() {
        try (Connection conn = getKonexioa();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE Erabiltzaileak (\r\n"
                    + "    izena VARCHAR(50) NOT NULL,\r\n"
                    + "    abizena VARCHAR(50) NOT NULL,\r\n"
                    + "    pasahitza VARCHAR(50) NOT NULL,\r\n"
                    + "    NA char(9) NOT NULL,\r\n"
                    + "    erabiltzaile_izena VARCHAR(50)\r\n"
                    + ");";
            stmt.executeUpdate(sql);
            System.out.println("Taula sortu da.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Erabiltzaile bat gehitzeko metodoa. Parametrotzat erabiltzaile bat
     * pasatzen da, eta honen informazioaren arabera insert bat egiten da datu
     * basearen konexioa gauzatu eta gero, informazio berria sartzeko
     *
     * @param erabiltzailea --> erabiltzaile bat, objektu moduan, datu guztiekin
     * @throws SQLException --> erroreren bat gertatzen bada
     */
    public static void insertErabiltzailea(Erabiltzailea erabiltzailea) throws SQLException {
        try (Connection conn = getKonexioa();
             Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO Erabiltzaileak "
                    + "VALUES ('" + erabiltzailea.getIzena() + "', '" + erabiltzailea.getAbizena() + "', '"
                    + erabiltzailea.getPasahitza() + "', '" + erabiltzailea.getNA() + "', '" + erabiltzailea.getErabiltzaile_izena() + "')";
            stmt.executeUpdate(sql);
            System.out.println("Datuak sartu dira datu basean!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erabiltzaile izen guztiak lortzeko metoda.
     *
     * @return kontsulta bat erabltzaile guztiekin
     * @throws SQLException the SQL exception
     */
    public static ResultSet getErabiltzaileaIzenak() throws SQLException {
        Connection conn = getKonexioa();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT erabiltzaile_izena FROM Erabiltzaileak;");
    }
}

