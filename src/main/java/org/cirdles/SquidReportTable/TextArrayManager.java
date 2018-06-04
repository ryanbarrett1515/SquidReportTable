/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ryanb
 */
public class TextArrayManager {

    public static void manageArray(TableView<ObservableList<SimpleStringProperty>> table, String[][] textArray) {
        for (int i = 2; i < textArray[0].length - 1; i++) {
            String colName = getColumnName(i, textArray);
            TableColumn col = new TableColumn(colName);
            col.setCellValueFactory(new PropertyValueFactory<SimpleStringProperty, String>("name"));
            table.getColumns().add(col);
        }
        setItemsInTable(table, textArray);
    }

    public static String getColumnName(int col, String[][] textArray) {
        return textArray[1][col] + textArray[2][col] + textArray[3][col];
    }

    public static void setItemsInTable(TableView<ObservableList<SimpleStringProperty>> table, String[][] array) {
        int startSpot = Integer.parseInt(array[0][0]);

        ObservableList<SimpleStringProperty> data = FXCollections.observableArrayList();
        for (int i = startSpot; i < array.length; i++) {
            for (int j = 2; j < array[0].length; j++) {
                data.add(new SimpleStringProperty(array[i][j]));
            }
            table.getItems().add(data);
        }
        table.refresh();
    }
}
