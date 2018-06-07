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
import org.cirdles.SquidReportTable.utilities.StringComparer;

/**
 *
 * @author ryanb
 */
public class TextArrayManager {

    private TableView<ObservableList<String>> table;
    private String[][] array;
    private ObservableList<ObservableList<String>> accepted;
    private ObservableList<ObservableList<String>> rejected;

    public TextArrayManager(TableView<ObservableList<String>> table, String[][] array) {
        this.table = table;
        this.array = array;
        accepted = FXCollections.observableArrayList();
        rejected = FXCollections.observableArrayList();
    }

    public void setHeaders() {
        TableColumn<ObservableList<String>, String> header = new TableColumn<>("");
        for (int i = 2; i < array[0].length - 1; i++) {
            if (!array[0][i - 1].equals(array[0][i])) {
                header = new TableColumn<>(array[0][i].trim());
            }
            String colName = getColumnName(i, array);
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName.trim());
            col.setComparator(new StringComparer());
            final int colNum = i - 2;
            col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colNum)));
            header.getColumns().add(col);
            if (!array[0][i].equals(array[0][i + 1])) {
                table.getColumns().add(header);
            }
        }
    }

    public String getColumnName(int col, String[][] textArray) {
        return textArray[1][col] + textArray[2][col] + textArray[3][col];
    }

    public void setTableItems() {
        table.setItems(FXCollections.observableArrayList());
        int startSpot = Integer.parseInt(array[0][0]);
        
        for (int i = startSpot; i < array.length; i++) {
            if (Boolean.parseBoolean(array[i][0])) {
                ObservableList<String> data = FXCollections.observableArrayList();
                for (int j = 2; j < array[0].length - 1; j++) {
                    data.add(array[i][j].trim());
                }
                accepted.add(data);
            } else {
                ObservableList<String> data = FXCollections.observableArrayList();
                for (int j = 2; j < array[0].length - 1; j++) {
                    data.add(array[i][j].trim());
                }
                rejected.add(data);
            }
        }
    }
    
    public void setAccepted() {
        table.setItems(accepted);
    }
    
    public void setRejected() {
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
