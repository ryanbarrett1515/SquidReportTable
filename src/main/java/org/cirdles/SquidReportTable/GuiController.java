/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.cirdles.SquidReportTable.utilities.CSVExtractor;
import org.cirdles.SquidReportTable.utilities.FileHandler;

/**
 * FXML Controller class
 *
 * @author ryanb
 */
public class GuiController implements Initializable {

    @FXML
    private Button selectCSVButton;
    @FXML
    private TableView<ObservableList<String>> reportsTable;
    @FXML
    private TextField acceptedRejectedTextField;
    @FXML
    private TextArea footnoteText;
    @FXML
    private Button fractionsButtons;

    private String[][] textArray;
    private TextArrayManager tableManager;
    private String tableStyle;
    private ButtonTypes buttonState;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonState = ButtonTypes.accepted;
        tableStyle = "";
        selectCSVButton(new ActionEvent());
    }

    @FXML
    private void selectCSVButton(ActionEvent event) {
        textArray = CSVExtractor.extractCSVFile(FileHandler.getFile());
        tableManager = new TextArrayManager(reportsTable, textArray);
        tableManager.setHeaders();
        tableManager.setTableItems();
        setTableItems();
        FootnoteManager.setUpFootnotes(footnoteText, textArray);
    }

    @FXML
    private void acceptedRejectedAction(ActionEvent event) {
        if (buttonState.equals(ButtonTypes.accepted)) {
            buttonState = ButtonTypes.rejected;
            acceptedRejectedTextField.setText("Rejected");
        } else {
            buttonState = ButtonTypes.accepted;
            acceptedRejectedTextField.setText("Accepted");
        }
        setTableItems();
    }

    private void setTableItems() {
        if (buttonState.equals(ButtonTypes.accepted)) {
            tableManager.setAccepted();
        } else {
            tableManager.setRejected();
        }
    }

    private enum ButtonTypes {
        accepted, rejected
    }

}
