/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Commande;
import Entity.Panier;
import Services.CommandeService;
import Services.PanierService;
/*import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
*/
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DALI
 */
public class InterfaceController implements Initializable {

    @FXML
    private TextField montant;
    @FXML
    private Button ajout;
    @FXML
    private TableView<Commande> tableCommand;
    @FXML
    private TableColumn<Commande, String> idColumn;
    @FXML
    private TableColumn<Commande, String> montantColumn;
    @FXML
    private TableColumn<Commande, String> etatColumn;
    @FXML
    private Button supp;
    @FXML
    private Button retourp;
    @FXML
    private Button sum;
    @FXML
    private Button btncherch;
    @FXML
    private ComboBox<String> etat;
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            afficher();
            ObservableList<String> stats = FXCollections.observableArrayList();
            stats.add("validé");
            stats.add("en cours");
            stats.add("annulé");

            etat.setValue("choisir etat");
            etat.setItems(stats);

        }
        );

    }

    @FXML
    private void ajoutCommande(ActionEvent event) throws SQLException {
        Commande comm = new Commande(Float.parseFloat(montant.getText()), 0);
        CommandeService CommServ = new CommandeService();
        CommServ.ajouterCommande(comm);

        afficher();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        alert.setContentText("Votre commande a ete bien passé verifiez votre facture PDF");

        alert.showAndWait();

    }

    public void afficher() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etats"));
        CommandeService CommServ = new CommandeService();
        ObservableList<Commande> list = FXCollections.observableArrayList();
        list = CommServ.getCommande();
        tableCommand.setItems(list);

    }

    @FXML
    private void suppAction(ActionEvent event) {
        Commande c = new Commande();
        c = tableCommand.getSelectionModel().getSelectedItem();
        System.out.println(c.getEtat());
        CommandeService CommServ = new CommandeService();
        CommServ.Annuler(c);
        afficher();
    }

    @FXML
    private void retourp(ActionEvent event) throws IOException {
           
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/Panier.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }

    @FXML
    private void loadtotal(ActionEvent event) throws IOException {

        PanierService p = new PanierService();
        try {

            montant.setText(Integer.toString(p.somme()));

        } catch (SQLException ex) {
        }
    }

    @FXML
    private void recchercher(ActionEvent event) throws SQLException {
        int n = 0;
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etats"));
        CommandeService CommServ = new CommandeService();
        ObservableList<Commande> list = FXCollections.observableArrayList();

        if (etat.getValue().equals("en cours")) {
            n = 0;
        } else if (etat.getValue().equals("validé")) {
            n = 1;
        } else if (etat.getValue().equals("annulé")) {
            n = 2;
        }

        tableCommand.setItems(CommServ.search(n));

    }

    /*@FXML
    private void pdffact(ActionEvent event) throws SQLException, IOException {
        

         

        ObservableList<Panier> list = FXCollections.observableArrayList();
        PanierService panserv = new PanierService();
        
        list=panserv.getPanier();
        

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("facture.pdf"));
            document.open();
            Paragraph p = new Paragraph("|-----------------------------------------------------------------------|");
            Paragraph p1 = new Paragraph("|Votre commande a ete bien passé ceci votre facture  |");
            Paragraph p0 = new Paragraph("|-----------------------------------------------------------------------|\n \n");
            Paragraph p2 = new Paragraph("Voici le montant a payé => " + montant.getText()+" DT\n");
            Paragraph p3 = new Paragraph("Voici la liste de vos produits => \n"+list);
            Paragraph p4 = new Paragraph("Merci pour utilisez notre service PEPINIERE  \n");
            document.add(p);
            document.add(p1);           
            document.add(p0);
            document.add(p2);
            document.add(p3);
            document.add(p4);
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        panserv.viderp();
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/Panier.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
       
    }*/
}
