/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entity.Commande;
import Services.CommandeService;
import Services.Myconnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
*/
/**
 * FXML Controller class
 *
 * @author DALI
 */
public class BackcommandeController implements Initializable {

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
    private Button modifiercommand;
    @FXML
    private Button retourp;
    @FXML
    private Button btncherch;
    @FXML
    private ComboBox<String> etat;
    @FXML
    private Button statsventes;
    @FXML
    private Button xlid;

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
        // TODO
    }
    private Connection conn = Myconnection.getInstance().getCon();

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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("voulez vous vraiment supprimer la commande ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Commande c = new Commande();
            c = tableCommand.getSelectionModel().getSelectedItem();
            CommandeService CommServ = new CommandeService();
            CommServ.Supprimer(c.getId());
            afficher();
        } else {

            afficher();

        }

    }

    @FXML
    private void modifierCommand(ActionEvent event) {
        Commande c = new Commande();
        c = tableCommand.getSelectionModel().getSelectedItem();
        System.out.println(c.getEtat());
        CommandeService CommServ = new CommandeService();
        CommServ.Modifier(c);
        afficher();
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

    @FXML
    private void statsventes(ActionEvent event) throws IOException {

        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Gui/StatistiquesVentes.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(home_page_scene);
        appStage.show();
    }

  /*  private void Excel(File file) throws FileNotFoundException, IOException {

        try {
            //System.out.println("Clicked,waiting to export....");

            FileOutputStream fileOut;
            fileOut = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("sheet 0");

            workSheet.setColumnWidth(1, 25);

            HSSFFont fontBold = workbook.createFont();
            fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(fontBold);

            Row row1 = workSheet.createRow((short) 0);
            workSheet.autoSizeColumn(7);
            row1.createCell(0).setCellValue("id");
            row1.createCell(1).setCellValue("montant");
            row1.createCell(2).setCellValue("etat");

            Row row2;

            String req = "SELECT * from commande ";

            PreparedStatement ps = conn.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int a = rs.getRow();
                row2 = workSheet.createRow((short) a);

                row2.createCell(0).setCellValue(rs.getInt(1));
                row2.createCell(1).setCellValue(rs.getString(2));
                row2.createCell(2).setCellValue(rs.getString(3));

            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("controllers.CommandeBack.ExcelAction()");

        }
    }

    @FXML
    private void ExcelAction(ActionEvent event) throws IOException {

        FileChooser chooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
        chooser.getExtensionFilters().add(filter);
        // Show save dialog
        File file = chooser.showSaveDialog(xlid.getScene().getWindow());
        if (file != null) {
            Excel(file);

        }

    }*/

}
