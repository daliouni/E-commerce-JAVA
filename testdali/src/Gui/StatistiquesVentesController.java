/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Services.Myconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DALI
 */
public class StatistiquesVentesController implements Initializable {

    @FXML
    private Button retourc;
    @FXML
    private BarChart<?, ?> stats;
    @FXML
    private NumberAxis nombre;
    @FXML
    private CategoryAxis montant;
    private Connection conn = Myconnection.getInstance().getCon();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series set1 = new XYChart.Series<>();
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery("SELECT  etat , SUM(montant) as sum  FROM commande group by etat ");
        
            while (rs.next())
            {
                int a = rs.getInt("etat");
                if(a==0){
                    set1.getData().add(new XYChart.Data("en cours",rs.getInt(2)));}
                else if (a==1){set1.getData().add(new XYChart.Data("validé",rs.getInt(2)));}
        
                else if (a==2){set1.getData().add(new XYChart.Data("annulé",rs.getInt(2)));}
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(StatistiquesVentesController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        stats.getData().addAll(set1);
        
    }    
    

        
     
 
    @FXML
    private void retourc(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/backcommande.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }
    
}
