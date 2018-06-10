/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private TableView<ObservableList<String>> boundCol;
    @FXML
    private ScrollBar scroller;
    @FXML
    private TextArea footnoteText;
    @FXML
    private Button fractionsButtons;

    private String[][] textArray;
    private TextArrayManager tableManager;
    private ButtonTypes buttonState;

    private enum ButtonTypes {
        accepted, rejected
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonState = ButtonTypes.accepted;
        selectCSVButton(new ActionEvent());
        footnoteText.setEditable(false);
        setStyles();
        setUpScroller();
    }

    @FXML
    private void selectCSVButton(ActionEvent event) {
        File fileName = FileHandler.getFile();
        if (fileName != null) {
            textArray = CSVExtractor.extractCSVFile(fileName);
            tableManager = new TextArrayManager(boundCol, reportsTable, textArray);
            tableManager.setHeaders();
            tableManager.setTableItems();
            setTableItems();
            FootnoteManager.setUpFootnotes(footnoteText, textArray);
        }
    }

    @FXML
    private void acceptedRejectedAction(ActionEvent event) {
        if (buttonState.equals(ButtonTypes.accepted)) {
            buttonState = ButtonTypes.rejected;
            fractionsButtons.setText("Rejected");
        } else {
            buttonState = ButtonTypes.accepted;
            fractionsButtons.setText("Accepted");
        }
        setTableItems();
    }

    private void setTableItems() {

        if (buttonState.equals(ButtonTypes.accepted)) {
            tableManager.setAccepted();
            scroller.setMax(tableManager.getAccepted().size());
        } else {
            tableManager.setRejected();
            scroller.setMax(tableManager.getRejected().size());
        }
    }
    
    private void setUpScroller() {
//        scroller.valueProperty().addListener( new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                boundCol.scrollTo((int) scroller.getValue());
//                reportsTable.scrollTo((int) scroller.getValue());
//            }
//        });
        scroller.addEventFilter(MouseEvent.ANY, event -> {
            boundCol.scrollTo((int) scroller.getValue());
            reportsTable.scrollTo((int) scroller.getValue());
        }
                );
    }

    private void setStyles() {
        reportsTable.setStyle(".table-view {"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 20;}"
                + ".table-row-cell:odd {"
                + "-fx-background-color: gray}"
                + ".table-row-cell:even{"
                + "-fx-background-color:blue}");

        boundCol.setStyle(".table-view {"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 20;}"
                + ".table-row-cell:odd {"
                + "-fx-background-color: gray}"
                + ".table-row-cell:even{"
                + "-fx-background-color:blue}");

        fractionsButtons.setStyle("-fx-background-color: orange;"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 18;");

        selectCSVButton.setStyle("-fx-background-color: orange;"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 18;");
    }

}
