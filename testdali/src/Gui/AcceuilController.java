/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DALI
 */
public class AcceuilController implements Initializable {

    @FXML
    private Button commandeback;
    @FXML
    private Button retourp;
    @FXML
    private Button voirpanier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void commandefront(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/interface.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }

    @FXML
    private void commandeback(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/backcommande.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
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
    private void voirpanier(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/Panier.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
         

    }

}
