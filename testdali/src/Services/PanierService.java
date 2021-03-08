/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Panier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DALI
 */
public class PanierService {

    private Connection conn = Myconnection.getInstance().getCon();

    public void ajouterPanier(Panier p) throws SQLException {
        try {
            PreparedStatement pst, pst2;
            pst2 = conn.prepareStatement("update product set quantity = quantity - ? where name = ? ");
            pst = conn.prepareStatement("INSERT INTO panier (nomp,quantite) VALUES (?,?)");
            pst.setString(1, p.getNomp());
            pst.setInt(2, p.getQuantite());
            pst.executeUpdate();
            pst2.setInt(1, p.getQuantite());
            pst2.setString(2, p.getNomp());
            pst2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);

        }
        System.out.println("panier ajouté");
    }

    public ObservableList<Panier> getPanier() {
        try {

            ObservableList<Panier> list = FXCollections.observableArrayList();
            PreparedStatement pt = conn.prepareStatement("SELECT * FROM Panier");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {

                Panier p = new Panier();
                p.setId(rs.getInt("id"));
                p.setNomp(rs.getString("nomp"));
                p.setQuantite(rs.getInt("quantite"));

                list.add(p);
            }
            return list;

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void Supprimer(int idpanier) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("Delete from panier where id=?");
            pst.setFloat(1, idpanier);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void Modifier(Panier pan) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("Update panier set quantite=? where id=?");
            pst.setInt(2, pan.getId());
            pst.setInt(1, pan.getQuantite());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int somme() throws SQLException {
        int s = 0, x = 0;
        ArrayList<String> list = new ArrayList<String>();
        PreparedStatement pst1, pst2;
        pst1 = conn.prepareStatement("Select nomp from panier");
        pst2 = conn.prepareStatement("SELECT quantite , Price FROM panier, product WHERE name = ? AND nomp = ?");
        ResultSet rs1 = pst1.executeQuery();
        ResultSet rs2;

        while (rs1.next()) {
            {
                list.add(rs1.getString("nomp"));
            }

            for (int i = 0; i < list.size(); i++) {
                pst2.setString(1, list.get(i));
                pst2.setString(2, list.get(i));
                rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    int a = rs2.getInt("quantite");

                    int b = rs2.getInt("price");

                    s = a * b;

                }

            }
            x = x + s;

        }

        System.out.println(x);

        return x;
    }

    public void viderp() throws SQLException {
        PreparedStatement pst;
        pst = conn.prepareStatement("Delete from panier");
        pst.executeUpdate();
    }

    public boolean Gstock(int q, String nomp) throws SQLException {
        PreparedStatement pst1;

        pst1 = conn.prepareStatement("SELECT quantity FROM product WHERE name = ? ");

        pst1.setString(1, nomp);
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next()) {
            {
                int a = (rs1.getInt("quantity"));
                if (q > a)
                {
                    System.out.println("quantite hors stock");
                            return true ;
                }
            }
            

        }
                        return false ;

    }
    public boolean Dproduit(String nom)  throws SQLException {
        PreparedStatement pst1;
        pst1 = conn.prepareStatement("SELECT nomp FROM panier");
        ResultSet rs1 = pst1.executeQuery();
        while (rs1.next())
        {if (nom.equals(rs1.getString("nomp")))
        { System.out.println("existe deja");
        return true;
        }
        }
        return false ;
    }
}
