package dao;

import Model.Prodotto;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO implements BaseDao <Prodotto, Integer> {

    public DatabaseManager db = new DatabaseManager();

    @Override
    public Prodotto findByID(Integer id) {
        String query = "SELECT * FROM prodotti2 WHERE id = ?";
        Connection con = db.getConnection("negozio3");

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setCategoria(rs.getString("categoria"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setQuantita(rs.getInt("quantita"));

                rs.close();
                pst.close();
                con.close();

                return prodotto;
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
    public ArrayList<Prodotto> findAll() {
        String query = "SELECT * FROM prodotti2";
        Connection con = db.getConnection("negozio3");
        ArrayList<Prodotto> listaProdotti = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setCategoria(rs.getString("categoria"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setQuantita(rs.getInt("quantita"));


                listaProdotti.add(prodotto);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProdotti;
    }

    @Override
    public void insert(Prodotto entity) {

        String query = "INSERT INTO prodotti2 (nome, categoria, prezzo, quantita) VALUES (?, ?, ?)";
        Connection con = db.getConnection("negozio3");

        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, entity.getNome());
            pst.setString(2, entity.getCategoria());
            pst.setDouble(3, entity.getPrezzo());
            pst.setInt(4, entity.getQuantita());

            pst.executeUpdate();

            pst.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aggiornaQuantitaProdotti(int idProdotto, int NuovaQuantita, Connection con) throws SQLException {
        String query = "UPDATE prodotti2 SET quantita = ? WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, NuovaQuantita);
            pst.setInt(2, idProdotto);

            pst.executeUpdate();
        }
    }


    public Prodotto findByID(Integer id, Connection con) throws SQLException {
        String query = "SELECT * FROM prodotti2 WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Prodotto prodotto = new Prodotto();
                    prodotto.setId(rs.getInt("id"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setCategoria(rs.getString("categoria"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));
                    prodotto.setQuantita(rs.getInt("quantita"));
                    return prodotto;
                }
            }
        }
        return null;
    }


}

