/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.cirdles.SquidReportTable.utilities.StringComparer;

/**
 *
 * @author ryanb
 */
public class TextArrayManager {
    
    private TableView<ObservableList<String>> table;
    private TableView<ObservableList<String>> boundCol;
    private String[][] array;
    private ObservableList<ObservableList<String>> accepted;
    private ObservableList<ObservableList<String>> rejected;
    
    public TextArrayManager(TableView<ObservableList<String>> boundCol, TableView<ObservableList<String>> table, String[][] array) {
        this.boundCol = boundCol;
        this.table = table;
        this.array = array;
        accepted = FXCollections.observableArrayList();
        rejected = FXCollections.observableArrayList();
    }
    
    public void setHeaders() {
        table.getColumns().clear();
        TableColumn<ObservableList<String>, String> header = new TableColumn<>("");
        for (int i = 3; i < array[0].length - 1; i++) {
            if (!array[0][i - 1].equals(array[0][i])) {
                header = new TableColumn<>(array[0][i].trim());
            }
            String colName = getColumnName(i, array);
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName.trim());
            col.setComparator(new StringComparer());
            col.setPrefWidth(col.getPrefWidth() + 20);
            final int colNum = i - 2;
            col.setStyle("-fx-font-family: \"Courier New\";"
                    + "\n -fx-font-size: 15");
            col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colNum)));
            header.getColumns().add(col);
            if (!array[0][i].equals(array[0][i + 1])) {
                table.getColumns().add(header);
            }
        }
        setUpBoundCol();
    }
    
    public String getColumnName(int col, String[][] textArray) {
        return textArray[1][col] + "\n" + textArray[2][col] + "\n" + textArray[3][col];
    }
    
    public void setTableItems() {
        accepted.clear();
        rejected.clear();
        int startSpot = Integer.parseInt(array[0][0]);
        for (int i = startSpot; i < array.length; i++) {
            ObservableList<String> data = FXCollections.observableArrayList();
            for (int j = 2; j < array[0].length - 1; j++) {
                data.add(array[i][j].trim());
            }
            if (Boolean.parseBoolean(array[i][0])) {
                accepted.add(data);
            } else {
                rejected.add(data);
            }
        }
    }
    
    private void setUpBoundCol() {
        TableColumn<ObservableList<String>, String> header = new TableColumn<>("Squid");       
        TableColumn<ObservableList<String>, String> col = new TableColumn<>("\n\nFractions");
        col.setComparator(new StringComparer());
        col.setPrefWidth(col.getPrefWidth() + 76);
        col.setStyle("-fx-font-family: \"Courier New\";"
                + "\n -fx-font-size: 15");
        final int num = 0;
        col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(num)));
        header.getColumns().add(col);        
        header.setPrefWidth(header.getMaxWidth());
        boundCol.getColumns().add(header);
    }
    
    public TableView<ObservableList<String>> getBoundCol() {
        return boundCol;
    }
    
    public void setBoundCol(TableView<ObservableList<String>> boundCol) {
        this.boundCol = boundCol;
    }
    
    public void setAccepted() {
        boundCol.setItems(accepted);
        table.setItems(accepted);
    }
    
    public void setRejected() {
        boundCol.setItems(rejected);
        table.setItems(rejected);
    }
    
    public TableView<ObservableList<String>> getTable() {
        return table;
    }
    
    public void setTable(TableView<ObservableList<String>> table) {
        this.table = table;
    }
    
    public String[][] getArray() {
        return array;
    }
    
    public void setArray(String[][] array) {
        this.array = array;
    }
    
    public ObservableList<ObservableList<String>> getAccepted() {
        return accepted;
    }
    
    public void setAccepted(ObservableList<ObservableList<String>> accepted) {
        this.accepted = accepted;
    }
    
    public ObservableList<ObservableList<String>> getRejected() {
        return rejected;
    }
    
    public void setRejected(ObservableList<ObservableList<String>> rejected) {
        this.rejected = rejected;
    }
}
