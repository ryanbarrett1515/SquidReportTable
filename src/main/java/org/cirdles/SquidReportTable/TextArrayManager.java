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

/**
 *
 * @author ryanb
 */
public class TextArrayManager {

    public static void manageArray(TableView<ObservableList<String>> table, String[][] textArray) {
        TableColumn<ObservableList<String>, String> header = new TableColumn<>("");
        for (int i = 2; i < textArray[0].length - 1; i++) {
            if (!textArray[0][i - 1].equals(textArray[0][i])) {
                header = new TableColumn<>(textArray[0][i]);
            }
            String colName = getColumnName(i, textArray);
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int colNum = i - 2;
            col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colNum)));
            header.getColumns().add(col);
            if (!textArray[0][i].equals(textArray[0][i + 1])) {
                table.getColumns().add(header);
            }
        }
        setItemsInTable(table, textArray);
    }

    public static String getColumnName(int col, String[][] textArray) {
        return textArray[1][col] + textArray[2][col] + textArray[3][col];
    }

    public static void setItemsInTable(TableView<ObservableList<String>> table, String[][] array) { 
        table.setItems(FXCollections.observableArrayList());
        int startSpot = Integer.parseInt(array[0][0]);
        for (int i = startSpot; i < array.length; i++) {
            ObservableList<String> data = FXCollections.observableArrayList();
            for (int j = 2; j < array[0].length - 1; j++) {
                data.add(array[i][j]);
            }
            table.getItems().add(data);
        }
    }
}
