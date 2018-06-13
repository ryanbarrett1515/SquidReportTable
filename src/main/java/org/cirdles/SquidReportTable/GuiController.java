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
        setStyles();
//        reportsBar = (ScrollBar) reportsTable.lookup(".scroll-bar:vertical");
//        boundBar = (ScrollBar) boundCol.lookup(".scroll-bar:vertical");
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
            scroller.setMax(tableManager.getAccepted().size() * (2.3 / 3));
        } else {
            tableManager.setRejected();
            scroller.setMax(tableManager.getRejected().size() * (2.3 / 3));
        }
    }

    private void setUpScroller() {
        scroller.addEventFilter(MouseEvent.ANY, event -> {
            boundCol.scrollTo((int) scroller.getValue());
            reportsTable.scrollTo((int) scroller.getValue());
        });
//        reportsTable.addEventFilter(ScrollEvent.SCROLL_STARTED, event ->{
//        });
//        getVerticalScrollbar(reportsTable).addEventFilter(MouseEvent.ANY, event -> {
//            boundCol.scrollTo((int) scroller.getValue());
//        });
//        getVerticalScrollbar(boundCol).addEventFilter(MouseEvent.ANY, event -> {
//            reportsTable.scrollTo((int) scroller.getValue());
//        });

//        reportsTable.addEventFilter(ScrollEvent.ANY, event -> {
//            boundCol.scrollTo((int) reportsBar.getValue());
//        });
//        boundCol.addEventFilter(ScrollEvent.ANY, event -> {
//            reportsTable.scrollTo((int) boundBar.getValue());
//        });
//        reportsTable.
//        ObservableList<Node> list = reportsTable.getChildrenUnmodifiable();
//        for (int i = 0; i < list.size(); i++) {
//            Node node = list.get(i);
//            if (node instanceof VirtualFlow) {
//                VirtualFlow flow = (VirtualFlow) reportsTable.getChildrenUnmodifiable().get(1);
//                for (final Node subNode : flow.getChildrenUnmodifiable()) {
//                    if (subNode instanceof ScrollBar && ((ScrollBar) subNode).getOrientation() == Orientation.VERTICAL) {
//                        reportsBar = (ScrollBar) subNode;
//                    }
//                }
//            }
//        }
//
//         list = boundCol.getChildrenUnmodifiable();
//        for (int i = 0; i < list.size(); i++) {
//            Node node = list.get(i);
//            if (node instanceof VirtualFlow) {
//                VirtualFlow flow = (VirtualFlow) reportsTable.getChildrenUnmodifiable().get(1);
//                for (final Node subNode : flow.getChildrenUnmodifiable()) {
//                    if (subNode instanceof ScrollBar && ((ScrollBar) subNode).getOrientation() == Orientation.VERTICAL) {
//                        boundBar = (ScrollBar) subNode;
//                    }
//                }
//            }
//        }
//        scroller.valueProperty().bindBidirectional(boundBar.valueProperty());
//        scroller.valueProperty().bindBidirectional(reportsBar.valueProperty());
//        reportsTable.onScrollToProperty().addListener(new ChangeListener< EventHandler<ScrollToEvent<Integer>>>() {
//            @Override
//            public void changed(ObservableValue<? extends EventHandler<ScrollToEvent<Integer>>> ob, EventHandler<ScrollToEvent<Integer>> ov, EventHandler<ScrollToEvent<Integer>> nv) {
//            }
//        });
//        boundCol.onScrollToProperty().addListener(new ChangeListener< EventHandler<ScrollToEvent<Integer>>>() {
//            @Override
//            public void changed(ObservableValue<? extends EventHandler<ScrollToEvent<Integer>>> ob, EventHandler<ScrollToEvent<Integer>> ov, EventHandler<ScrollToEvent<Integer>> nv) {
//
//            }
//        });
//        boundCol.onScrollProperty().bindBidirectional(reportsTable.onScrollProperty());
//        reportsTable.onScrollProperty().bindBidirectional(boundCol.onScrollProperty());
    }

//    public static ScrollBar getVerticalScrollbar(TableView<?> table) {
//        ScrollBar result = null;
//        for (Node n : table.lookupAll(".scroll-bar")) {
//            if (n instanceof ScrollBar) {
//                ScrollBar bar = (ScrollBar) n;
//                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
//                    result = bar;
//                }
//            }
//        }
//        return result;
//    }
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

}
