/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Commande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DALI
 */
public class CommandeService {

    private Connection conn = Myconnection.getInstance().getCon();

    public void ajouterCommande(Commande comm) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("INSERT INTO commande (montant,etat) VALUES (?,?)");
            pst.setFloat(1, comm.getMontant());
            pst.setInt(2,0);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);

        }
    }
    public ObservableList<Commande> getCommande() {
        try {
           
            ObservableList<Commande> list = FXCollections.observableArrayList();
            PreparedStatement pt = conn.prepareStatement("SELECT * FROM commande");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                 
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setMontant(rs.getFloat("montant"));
                c.setEtat(rs.getInt("etat"));
                if(rs.getInt("etat")==1)
                {
                    c.setEtats("validé");
                }
               if(rs.getInt("etat")==0)
                {
                   c.setEtats("en cours"); 
                }
               if(rs.getInt("etat")==2)
                {
                   c.setEtats("annulé"); 
                }
                
                list.add(c);
            }
            return list;

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;

    }
        public void Supprimer(int idCommande) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("Delete from commande where id=?");
            pst.setFloat(1, idCommande);
           
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
        public void Modifier(Commande comm) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("Update commande set Etat=? where id=?");
            pst.setInt(1, 1);
            pst.setInt(2, comm.getId()); 
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
     public void Annuler(Commande comm) {
        try {
            PreparedStatement pst;
            pst = conn.prepareStatement("Update commande set Etat=? where id=?");
            pst.setInt(1, 2);
            pst.setInt(2, comm.getId()); 
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
     
     public ObservableList<Commande> search(int etat) throws SQLException 
     {
         ObservableList<Commande> list = FXCollections.observableArrayList();
            PreparedStatement pt = conn.prepareStatement("SELECT * FROM commande where etat ='"+etat+"'");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                
                  Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setMontant(rs.getFloat("montant"));
                c.setEtat(rs.getInt("etat"));
                if(rs.getInt("etat")==1)
                {
                    c.setEtats("validé");
                }
               if(rs.getInt("etat")==0)
                {
                   c.setEtats("en cours"); 
                }
               if(rs.getInt("etat")==2)
                {
                   c.setEtats("annulé"); 
                }
                
                list.add(c);
            }
            return list;
        }
       

    }

