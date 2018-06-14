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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane root;
    @FXML
    private TextField boundColFootNote;
    
//    @FXML
//    private ScrollBar reportsBar;
//    @FXML
//    private ScrollBar boundBar;
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
//        reportsBar = (ScrollBar) reportsTable.lookup(".scroll-bar:vertical");
//        boundBar = (ScrollBar) boundCol.lookup(".scroll-bar:vertical");
        setUpScroller();
        setStyles();

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
            setUpColFootnote();
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
            scroller.setMax(tableManager.getAccepted().size() * (2.3 / 3));
        } else {
            tableManager.setRejected();
            scroller.setMax(tableManager.getRejected().size() * (2.3 / 3));
        }
    }

    private void setUpScroller() {
        scroller.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            boundCol.scrollTo((int) scroller.getValue());
            reportsTable.scrollTo((int) scroller.getValue());
        });
        scroller.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            boundCol.scrollTo((int) scroller.getValue());
            reportsTable.scrollTo((int) scroller.getValue());
        });

        reportsTable.addEventFilter(ScrollEvent.ANY, event -> {
            double amount = event.getDeltaY() * -.25 + scroller.getValue();

            if (amount > scroller.getMax()) {
                amount = scroller.getMax();
            }
            if (amount < scroller.getMin()) {
                amount = scroller.getMin();
            }
            
            scroller.setValue(amount);
            boundCol.scrollTo((int) (amount + .5));
            reportsTable.scrollTo((int) (amount + .5));
        });
        boundCol.addEventFilter(ScrollEvent.ANY, event -> {
            double amount = event.getDeltaY() * -.10 + scroller.getValue();

            if (amount > scroller.getMax()) {
                amount = scroller.getMax();
            }
            if (amount < scroller.getMin()) {
                amount = scroller.getMin();
            }
            
            scroller.setValue(amount);
            boundCol.scrollTo((int) (amount + .5));
            reportsTable.scrollTo((int) (amount + .5));
        });
    }

    private void setStyles() {
        String tableStyle = ".table-view {"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 20;}";
        reportsTable.setStyle(tableStyle);
        boundCol.setStyle(tableStyle);

        fractionsButtons.setStyle("-fx-background-color: orange;"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 18;");

        selectCSVButton.setStyle("-fx-background-color: orange;"
                + "-fx-font-family: \"Times New Roman\";"
                + "-fx-font-size: 18;");
        root.setStyle("-fx-background-color: cadetblue");
    }
    
    private void setUpColFootnote() {
        boundColFootNote.setText(textArray[8][1].trim());
        boundColFootNote.setStyle("-fx-font-size: 18; -fx-background-color: orange");
    }

}
