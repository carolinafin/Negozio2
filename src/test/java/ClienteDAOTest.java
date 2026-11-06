import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Cliente;
import dao.ClienteDAO;
import database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ClienteDAOTest {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final DatabaseManager db = new DatabaseManager();

    private final int ID_TEST = 9999;
    private final String EMAIL_TEST_INSERT = "test_test@es.com";

    @BeforeEach
    public void setUp() throws SQLException {

        cleanupDatabase();


        try (Connection con = db.getConnection("negozio3");
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO clienti2 (id, nome, cognome, email, citta, saldo) VALUES (?, ?, ?, ?, ?, ?)")) {

            pst.setInt(1, ID_TEST);
            pst.setString(2, "NomeTest");
            pst.setString(3, "CognomeTest");
            pst.setString(4, "test_test@es.com");
            pst.setString(5, "Milano");
            pst.setDouble(6, 500.0);
            pst.executeUpdate();
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        cleanupDatabase();
    }

    private void cleanupDatabase() throws SQLException {
        try (Connection con = db.getConnection("negozio3");
             Statement st = con.createStatement()) {

            st.executeUpdate("DELETE FROM clienti2 WHERE id = " + ID_TEST);

            st.executeUpdate("DELETE FROM clienti2 WHERE email = '" + EMAIL_TEST_INSERT + "'");
        }
    }

    @Test
    public void testFindByID_Success() {

        Cliente cliente = clienteDAO.findByID(ID_TEST);


        assertNotNull(cliente);
        assertEquals(ID_TEST, cliente.getId());
        assertEquals("NomeTest", cliente.getNome());
        assertEquals(100.0, cliente.getSaldo());
    }

    @Test
    public void testFindByID_NotFound() {

        Cliente cliente = clienteDAO.findByID(-1);

        assertNull(cliente);
    }



}

