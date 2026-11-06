package dao;

import Model.Cliente;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements BaseDao<Cliente, Integer> {

    public DatabaseManager db = new DatabaseManager();


    @Override
    public Cliente findByID(Integer id) {
        String query = "SELECT * FROM clienti2 WHERE id = ?";
        Connection con = db.getConnection("negozio3");

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCognome(rs.getString("cognome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCitta(rs.getString("citta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    rs.close();
                    pst.close();
                    con.close();

                    return cliente;
                }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public ArrayList<Cliente> findAll() {
        String query = "SELECT * FROM clienti2";
        Connection con = db.getConnection("negozio3");
        ArrayList<Cliente> listaClienti = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCognome(rs.getString("cognome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCitta(rs.getString("citta"));
                cliente.setSaldo(rs.getDouble("saldo"));

                listaClienti.add(cliente);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaClienti;
    }


    @Override
    public void insert(Cliente entity) {
        String query = "INSERT INTO clienti2 (nome, cognome, email, citta, saldo) VALUES (?, ?, ?, ?, ?)";
        Connection con = db.getConnection("negozio3");

        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, entity.getNome());
            pst.setString(2, entity.getCognome());
            pst.setString(3, entity.getEmail());
            pst.setString(4, entity.getCitta());
            pst.setDouble(5, entity.getSaldo());


            pst.executeUpdate();


            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void aggiornaSaldoCliente (int idcliente, double nuovosaldo, Connection con) throws SQLException  {
        String query = "UPDATE clienti2 SET saldo = ? WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setDouble (1,nuovosaldo);
            pst.setInt (2, idcliente);

            pst.executeUpdate();
        }
    }


    public Cliente findByID(Integer id, Connection con) throws SQLException {
        String query = "SELECT * FROM clienti2 WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCognome(rs.getString("cognome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCitta(rs.getString("citta"));
                    cliente.setSaldo(rs.getDouble("saldo"));
                    return cliente;
                }
            }
        }
        return null;
    }


}


