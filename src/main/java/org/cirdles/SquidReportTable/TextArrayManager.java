/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.SquidReportTable;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private TableView<ObservableList<String>> boundCol;
    private String[][] array;
    private ObservableList<ObservableList<String>> accepted;
    private ObservableList<ObservableList<String>> rejected;
    private String colStyle;
    private final int characterSize;
    private final int columnHeaderCharacterSize;

    public TextArrayManager(TableView<ObservableList<String>> boundCol, TableView<ObservableList<String>> table, String[][] array) {
        this.boundCol = boundCol;
        this.table = table;
        this.array = array;
        accepted = FXCollections.observableArrayList();
        rejected = FXCollections.observableArrayList();
        colStyle = "-fx-font-family: \"Courier New\";"
                + "-fx-font-size: 14; -fx-alignment: center-right;";
        characterSize = 10;
        columnHeaderCharacterSize = 11;
    }

    public void setHeaders() {
        table.getColumns().clear();

        List<TableColumn<ObservableList<String>, String>> headers = new ArrayList<>();
        List<TableColumn<ObservableList<String>, String>> columns = new ArrayList<>();

        TableColumn<ObservableList<String>, String> header = new TableColumn<>("");
        for (int i = 3; i < array[0].length - 1; i++) {
            if (i == 3 || !array[0][i - 1].equals(array[0][i])) {
                header = new TableColumn<>(array[0][i].trim());
            }
            String colName = getColumnName(i, array);
            int colLength = getMaxColumnHeaderLength(colName);
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            col.setComparator(new StringComparer());
            col.setPrefWidth(colLength * columnHeaderCharacterSize + 20);
            col.setStyle(colStyle);
            final int colNum = i - 2;
            col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(colNum)));
            header.getColumns().add(col);
            columns.add(col);
            if (!array[0][i].equals(array[0][i + 1])) {
                table.getColumns().add(header);
                headers.add(header);            
                columnReorder2(header, columns);
                columns = new ArrayList<>();
            }
        }
        columnReorder(table, headers);
        setUpBoundCol();
    }

    public static void columnReorder(TableView table, List<TableColumn<ObservableList<String>, String>> columns) {
        table.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    table.getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });
    }

    public static void columnReorder2(TableColumn col, List<TableColumn<ObservableList<String>, String>> columns) {
        col.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    col.getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });
    }

    public int getMaxColumnHeaderLength(String input) {
        int max = 0;
        String[] levels = input.split("\n");
        for (int i = 0; i < levels.length; i++) {
            max = (levels[i].length() > max) ? levels[i].length() : max;
        }
        return max;
    }

    public static String getColumnName(int col, String[][] textArray) {
        String retVal = "";
        for (int i = 1; i <= 4; i++) {
            String currVal = textArray[i][col].trim();
            retVal += currVal;
            if (i != 4) {
                retVal += "\n";
            }
        }

        if (col == textArray[0].length - 2) {
            retVal = "\n\n\nFractions";
        }

        return retVal;
    }

    public void setTableItems() {
        accepted.clear();
        rejected.clear();
        int startSpot = Integer.parseInt(array[0][0]);
        for (int i = startSpot; i < array.length; i++) {
            ObservableList<String> data = FXCollections.observableArrayList();
            for (int j = 2; j < array[0].length - 1; j++) {
                data.add(array[i][j]);
            }
            if (Boolean.parseBoolean(array[i][0])) {
                accepted.add(data);
            } else {
                rejected.add(data);
            }
        }
    }

    private void setUpBoundCol() {
        boundCol.getColumns().clear();
        TableColumn<ObservableList<String>, String> header = new TableColumn<>("Squid");
        TableColumn<ObservableList<String>, String> col = new TableColumn<>("\n\n\nFractions");
        col.setComparator(new StringComparer());
        col.setPrefWidth(col.getPrefWidth() + 76);
        col.setStyle(colStyle);
        final int num = 0;
        col.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(num)));
        header.getColumns().add(col);
        header.setPrefWidth(header.getMaxWidth());
        boundCol.getColumns().add(header);
    }

    public void setAccepted() {
        boundCol.setItems(accepted);
        table.setItems(accepted);
        setUpWidths(accepted);
    }

    public void setRejected() {
        boundCol.setItems(rejected);
        table.setItems(rejected);
        setUpWidths(rejected);
    }

    public void setUpWidths(ObservableList<ObservableList<String>> data) {
        if (!data.isEmpty()) {
            int header = 0, counter = 0;
            for (int j = 1; j < data.get(0).size(); j++) {
                int max = 0;
                for (int i = 0; i < data.size(); i++) {
                    int length = data.get(i).get(j).length();
                    if (length > max) {
                        max = length;
                    }
                }
                if (table.getColumns().get(header).getColumns().size() <= counter) {
                    header++;
                    counter = 0;
                }
                Object col = table.getColumns().get(header).getColumns().get(counter);
                double prefWidth = max * characterSize;
                if (prefWidth > ((TableColumn) col).getPrefWidth()) {
                    ((TableColumn) col).setPrefWidth(prefWidth);
                }
                counter++;
            }
        }
    }

    public TableView<ObservableList<String>> getBoundCol() {
        return boundCol;
    }

    public void setBoundCol(TableView<ObservableList<String>> boundCol) {
        this.boundCol = boundCol;
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

    public String getColStyle() {
        return colStyle;
    }

    public void setColStyle(String colStyle) {
        this.colStyle = colStyle;
    }
}
