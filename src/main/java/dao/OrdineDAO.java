package dao;

import Model.Ordine;
import database.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class OrdineDAO implements BaseDao <Ordine, Integer> {

    DatabaseManager db = new DatabaseManager();

    @Override
    public Ordine findByID(Integer id) {
        String query = "SELECT * FROM ordini2 WHERE id = ?";
        Connection con = db.getConnection("negozio3");

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("id"));
                ordine.setId_cliente(rs.getInt("id_cliente"));
                ordine.setId_prodotto(rs.getInt("id_prodotto"));
                ordine.setQuantita(rs.getInt("quantita"));
                ordine.setData(rs.getDate("data_ordine"));

                rs.close();
                pst.close();
                con.close();

                return ordine;
            }

            rs.close();
            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Ordine> findAll() {
        String query = "SELECT * FROM ordini2";
        Connection con = db.getConnection("negozio3");
        ArrayList<Ordine> listaOrdini = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("id"));
                ordine.setId_cliente(rs.getInt("id_cliente"));
                ordine.setId_prodotto(rs.getInt("id_prodotto"));
                ordine.setQuantita(rs.getInt("quantita"));
                ordine.setData(rs.getDate("data"));

                listaOrdini.add(ordine);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOrdini;
    }

    @Override
    public void insert(Ordine entity) {
        String query = "INSERT INTO ordini2 (id_cliente, id_prodotto, quantita, data) VALUES (?, ?, ?, ?)";

        try (Connection con = db.getConnection("negozio3");
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, entity.getId_cliente());
            pst.setInt(2, entity.getId_prodotto());
            pst.setInt(3, entity.getQuantita());
            pst.setDate(4, entity.getData());

            pst.executeUpdate();

        } catch (SQLException e) {

            System.err.println("Errore:");
            e.printStackTrace();
        }
    }

    public void insert (Ordine entity, Connection con) throws SQLException {
        String query = "INSERT INTO ordini2 (id_cliente, id_prodotto, quantita, data_ordine) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, entity.getId_cliente());
            pst.setInt(2, entity.getId_prodotto());
            pst.setInt(3, entity.getQuantita());
            pst.setDate(4, entity.getData());
            pst.executeUpdate();
        }
    }
}




