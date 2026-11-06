package org.example;

import Model.Cliente;
import Model.Ordine;
import Model.Prodotto;
import dao.ClienteDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;
import database.DatabaseManager;
import database.DatabaseService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        DatabaseManager db = new DatabaseManager();
        ClienteDAO clienteDao = new ClienteDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        OrdineDAO ordineDAO = new OrdineDAO();


        int idTest = 3;
        IO.println("Ricerca del cliente con ID: " + idTest);
        Cliente cliente = clienteDao.findByID(idTest);

        if (cliente != null) {
            IO.println("! CLIENTE TROVATO !");
            IO.println("ID: " + cliente.getId());
            IO.println("Nome: " + cliente.getNome());
            IO.println("Cognome: " + cliente.getCognome());
            IO.println("Email: " + cliente.getEmail());
            IO.println("Città: " + cliente.getCitta());
            IO.println("Saldo: " + cliente.getSaldo());
        } else {
            IO.println("Nessun cliente trovato con l'ID inserito.");
        }

        IO.println(" ");


        int idTest2 = 2;
        IO.println("Ricerca del prodotto con ID: " + idTest2);
        Prodotto prodotto = prodottoDAO.findByID(idTest2);

        if (prodotto != null) {
            IO.println("! PRODOTTO TROVATO !");
            IO.println("ID: " + prodotto.getId());
            IO.println("Nome: " + prodotto.getNome());
            IO.println("Categoria: " + prodotto.getCategoria());
            IO.println("Prezzo: " + prodotto.getPrezzo());
            IO.println("Quantità attuale in DB: " + prodotto.getQuantita());
        } else {
            IO.println("Nessun prodotto trovato con l'ID inserito.");
        }

        IO.println(" ");


        int idTest3 = 4;
        IO.println("Ricerca dell'ordine con ID: " + idTest3);
        Ordine ordine = ordineDAO.findByID(idTest3);

        if (ordine != null) {
            IO.println("! ORDINE TROVATO !");
            IO.println("ID: " + ordine.getId());
            IO.println("Cliente: " + ordine.getId_cliente());
            IO.println("Prodotto: " + ordine.getId_prodotto());
            IO.println("Quantità: " + ordine.getQuantita());
            IO.println("Data ordine:" + ordine.getData());
        } else {
            IO.println("Nessun ordine trovato con l'ID inserito.");
        }

        IO.println(" ");


        try (Connection con = db.getConnection("negozio3")) {


            clienteDao.aggiornaSaldoCliente(6, 100.00, con);
            IO.println("");
            if (cliente != null) {
                IO.println("Saldo di " + cliente.getNome() + " " + cliente.getCognome() + " aggiornato");
            }

            prodottoDAO.aggiornaQuantitaProdotti(3, 1, con);
            if (prodotto != null) {
                IO.println("Quantità di " + prodotto.getNome() + " aggiornata");
            } else {
                IO.println("Il prodotto non esiste nel database!");
            }

        } catch (SQLException e) {
            IO.println("Errore durante l'aggiornamento amministrativo: " + e.getMessage());
        }

        IO.println(" ");


        IO.println(" --- TEST DI ACQUISTO --- ");

        DatabaseService databaseService = new DatabaseService(clienteDao, prodottoDAO, ordineDAO, db);


        int idClienteTest = 3;
        int idProdottoTest = 1;
        int quantitaTest = 1;

        Cliente clienteTestEffettivo = clienteDao.findByID(idClienteTest);

        if (clienteTestEffettivo != null) {
            IO.println(idClienteTest + " " + clienteTestEffettivo.getNome() + " " + clienteTestEffettivo.getCognome() +
                    " vuole acquistare " + quantitaTest + " quantità del Prodotto " + idProdottoTest);
        }

        databaseService.acquistaProdotti(idClienteTest, idProdottoTest, quantitaTest);
    }
}

