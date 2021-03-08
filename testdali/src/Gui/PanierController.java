/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Commande;
import Entity.Panier;
import Services.CommandeService;
import Services.Myconnection;
import Services.PanierService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author DALI
 */
public class PanierController implements Initializable {

    ObservableList combo = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> produitp;
    @FXML
    private TextField quantitep;
    @FXML
    private Button retourp;
    @FXML
    private Button panierp;
    private Connection conn = Myconnection.getInstance().getCon();
    @FXML
    private TableView<Panier> pantab;
    @FXML
    private TableColumn<Panier, String> id;
    @FXML
    private TableColumn<Panier, String> nomp;
    @FXML
    private TableColumn<Panier, String> quantp;
    @FXML
    private Button supp;
    @FXML
    private Button modifier;
    @FXML
    private Button passerC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillcombo();
            afficher();

        } catch (SQLException ex) {
        }
    }
    PanierService panser = new PanierService();

    @FXML
    private void produitp() throws SQLException {

    }

    @FXML
    private void retourp(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/acceuil.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }

    @FXML
    private void panierp(ActionEvent event) throws SQLException {

        Panier p1 = new Panier(produitp.getValue(), Integer.parseInt(quantitep.getText()));

        if (Integer.parseInt(quantitep.getText()) < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("");
            alert.setContentText("quantite ne peut pas etre negative");

            alert.showAndWait();
        } else if (panser.Gstock(Integer.parseInt(quantitep.getText()), produitp.getValue())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("");
            alert.setContentText("Le stock ne contient pas cette quantite");

            alert.showAndWait();
        } else if (panser.Dproduit(produitp.getValue())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("");
            alert.setContentText("Le produit existe deja dans le panier");

            alert.showAndWait();
        } else {
            PanierService panser = new PanierService();
            Panier p = new Panier(produitp.getValue(), Integer.parseInt(quantitep.getText()));
            panser.ajouterPanier(p);
            afficher();

        }
    }

    public void afficher() {
        PanierService panser = new PanierService();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomp.setCellValueFactory(new PropertyValueFactory<>("nomp"));
        quantp.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        CommandeService CommServ = new CommandeService();
        ObservableList<Panier> list = FXCollections.observableArrayList();
        list = panser.getPanier();
        pantab.setItems(list);

    }

    @FXML
    private void supprimerpanier(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Voulez vous supprimez le produit du panier?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            PanierService panser = new PanierService();
            panser.Supprimer(pantab.getSelectionModel().getSelectedItem().getId());
            afficher();
            System.out.println("produit supprimé");
        } else {

            afficher();

        }

    }

    @FXML
    private void modifierpanier(ActionEvent event) {
        PanierService panser = new PanierService();

        Panier c = pantab.getSelectionModel().getSelectedItem();

        c.setQuantite(Integer.parseInt(quantitep.getText()));

        panser.Modifier(c);
        afficher();

    }

    @FXML
    private void PasserCommande(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/interface.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }

    public void fillcombo() throws SQLException {
        PreparedStatement pst;
        String query = "select name from product";
        pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            combo.add(rs.getString("name"));
            produitp.setItems(combo);

        }

    }

}
