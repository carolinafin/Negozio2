package database;

import Model.Cliente;
import Model.Prodotto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {

private final Properties prop;



    public DatabaseManager() {
        prop = new Properties();
        loadProperties();
    }



    public Connection getConnection(String nomeDatabase) {
//        String url = urlBase + nomeDatabase;

        try {
            Connection con = DriverManager.getConnection(getUrl() + nomeDatabase, getUsername(), getPassword());

            return con;
        } catch (SQLException e) {
            IO.println("Connessione non riuscita");
            e.printStackTrace();
            return null;
        }
    }

    private void loadProperties() {

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("app.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "File app.properties non trovato!"
                );
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Errore nel caricamento delle configurazioni", e
            );
        }
    }

    private String getUrl() {
        return prop.getProperty("db.baseUrl");
    }

    private String getUsername() {
        return prop.getProperty("db.user");
    }

    private String getPassword() {
        return prop.getProperty("db.pass");
    }

    }
