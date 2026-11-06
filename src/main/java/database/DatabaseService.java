package database;

import Model.Cliente;
import Model.Ordine;
import Model.Prodotto;
import dao.ClienteDAO;
import dao.ProdottoDAO;
import dao.OrdineDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseService {
    private ClienteDAO clienteDao;
    private ProdottoDAO prodottoDao;
    private OrdineDAO ordineDao;
    private DatabaseManager db;

    public DatabaseService(ClienteDAO clienteDao, ProdottoDAO prodottoDao, OrdineDAO ordineDao, DatabaseManager db) {
        this.clienteDao = clienteDao;
        this.prodottoDao = prodottoDao;
        this.ordineDao = ordineDao;
        this.db = db;
    }

    public void acquistaProdotti(int clienteId, int prodottoId, int quantita) {
        Connection con = null;

        try {
            con = db.getConnection("negozio3");
            con.setAutoCommit(false);

            Cliente cliente = clienteDao.findByID(clienteId, con);
            Prodotto prodotto = prodottoDao.findByID(prodottoId, con);

            if (cliente == null || prodotto == null) {
                throw new SQLException("Cliente o Prodotto non trovato.");
            }

            int nuovaQuantita = prodotto.getQuantita() - quantita;
            if (nuovaQuantita < 0) {
                throw new SQLException("Quantità insufficiente in magazzino.");
            }

            double spesaTotale = prodotto.getPrezzo() * quantita;
            double nuovoSaldo = cliente.getSaldo() - spesaTotale;
            if (nuovoSaldo < 0) {
                throw new SQLException("Saldo insufficiente per completare l'acquisto.");
            }

            clienteDao.aggiornaSaldoCliente(clienteId, nuovoSaldo, con);
            prodottoDao.aggiornaQuantitaProdotti(prodottoId, nuovaQuantita, con);

            Ordine nuovoOrdine = new Ordine();
            nuovoOrdine.setId_cliente(clienteId);
            nuovoOrdine.setId_prodotto(prodottoId);
            nuovoOrdine.setQuantita(quantita);
            nuovoOrdine.setData(java.sql.Date.valueOf(java.time.LocalDate.now()));
            ordineDao.insert(nuovoOrdine, con);

            con.commit();
            IO.println("Acquisto completato con successo!");

        } catch (SQLException e) {

            IO.println("Errore durante la transazione: " + e.getMessage());

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    IO.println("Errore durante il rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    IO.println("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }
    }
}




