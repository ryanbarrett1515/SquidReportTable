/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author ryanb
 */
public class TextArrayManager {
    public static void manageArray(TableView<String> table, String[][] textArray) {
        for(int i = 2; i < textArray[0].length- 1; i++) {
            TableColumn col = new TableColumn(getColumnName(i, textArray));
            table.getColumns().add(col);
        }
    }
    
    public static String getColumnName(int col, String[][] textArray) {
        return textArray[1][col] + textArray[2][col] + textArray[3][col];
    }
}
