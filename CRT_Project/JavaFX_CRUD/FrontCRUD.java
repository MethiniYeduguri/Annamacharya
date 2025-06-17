package JavaFX;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TableColumn;
import javafx.scene.control .TableView;

public class FrontCRUD extends Application {

    private final String DB_URL = "jdbc:mysql://localhost:3306/cse";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "3030";

    private TableView<Map<String, Object>> tableView;
    private TextArea errorBox;
    private VBox inputArea;
    private final ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
    private String lastSelectedTable = "";

    @Override
    public void start(Stage stage) {
        tableView = new TableView<>();
        tableView.setEditable(true);

        errorBox = new TextArea();
        errorBox.setEditable(false);
        errorBox.setStyle("-fx-text-fill: red");

        Button createBtn = new Button("Create Table");
        Button insertBtn = new Button("Insert Rows");
        Button selectBtn = new Button("Select Data");
        Button deleteBtn = new Button("Delete");
        Button dropBtn = new Button("Drop Table");

        HBox btnBox = new HBox(10, createBtn, insertBtn, selectBtn, deleteBtn, dropBtn);
        btnBox.setPadding(new Insets(10));

        inputArea = new VBox(10);
        inputArea.setPadding(new Insets(10));

        VBox layout = new VBox(10, btnBox, inputArea, tableView, errorBox);
        layout.setPadding(new Insets(10));

        createBtn.setOnAction(e -> showCreateTable());
        insertBtn.setOnAction(e -> showInsertRow());
        selectBtn.setOnAction(e -> selectData());
        deleteBtn.setOnAction(e -> deleteSelected());
        dropBtn.setOnAction(e -> showDropTable());

        stage.setScene(new Scene(layout, 900, 600));
        stage.setTitle("JavaFX MySQL CRUD GUI");
        stage.show();
    }

    private void showCreateTable() {
        inputArea.getChildren().clear();

        TextField tableName = new TextField();
        tableName.setPromptText("Enter table name");

        TextArea columns = new TextArea();
        columns.setPromptText("Enter columns (e.g., ID INT, NAME VARCHAR(50))");

        Button create = new Button("Create");
        create.setOnAction(e -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {

                String name = tableName.getText().trim().toUpperCase();
                String cols = columns.getText().trim().toUpperCase();
                String sql = "CREATE TABLE " + name + " (" + cols + ")";
                stmt.execute(sql);
                errorBox.setText("Table created successfully.");
            } catch (SQLException ex) {
                errorBox.setText("Error: " + ex.getMessage());
            }
        });

        inputArea.getChildren().addAll(new Label("Create Table:"), tableName, columns, create);
    }

    private void showInsertRow() {
        inputArea.getChildren().clear();

        TextField tableName = new TextField();
        tableName.setPromptText("Table name");

        TextArea columns = new TextArea();
        columns.setPromptText("Column names (e.g., ID, NAME)");

        TextArea values = new TextArea();
        values.setPromptText("Rows: (1, 'John'), (2, 'Jane')");

        Button insert = new Button("Insert");
        insert.setOnAction(e -> {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement()) {

            String table = tableName.getText().trim().toUpperCase();
            String cols = columns.getText().trim().toUpperCase();
            String vals = values.getText().trim().toUpperCase();
            String sql = "INSERT INTO " + table + " (" + cols + ") VALUES " + vals;
            stmt.executeUpdate(sql);
            errorBox.setText("Data inserted successfully.");
            lastSelectedTable = table; // update last selected table

            // Auto-refresh data
            if (!lastSelectedTable.isEmpty()) {
                TextField selectField = new TextField("SELECT * FROM " + lastSelectedTable);
                selectDataFromQuery(selectField.getText()); // helper function
            }

        } catch (SQLException ex) {
            errorBox.setText("Error: " + ex.getMessage());
        }
    });


        inputArea.getChildren().addAll(new Label("Insert Rows:"), tableName, columns, values, insert);
    }

    private void selectData() {
        inputArea.getChildren().clear();

        TextField selectField = new TextField();
        selectField.setPromptText("Enter SELECT query");
        Button runSelect = new Button("Run");

        runSelect.setOnAction(ev -> {
            String query = selectField.getText().trim();
            Pattern pattern = Pattern.compile("select\\s+\\*\\s+from\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);
            if (matcher.find()) {
                lastSelectedTable = matcher.group(1);
            }

            if (!query.toLowerCase().startsWith("select")) {
                errorBox.setText("Please enter a valid SELECT query.");
                return;
            }

            data.clear();
            tableView.getColumns().clear();

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                TableColumn<Map<String, Object>, Boolean> selectCol = new TableColumn<>("Select");
                selectCol.setCellValueFactory(cellData -> {
                    Map<String, Object> row = cellData.getValue();
                    if (!row.containsKey("_selected")) {
                        row.put("_selected", new SimpleBooleanProperty(false));
                    }
                    return (SimpleBooleanProperty) row.get("_selected");
                });
                selectCol.setCellFactory(tc -> {
                    CheckBoxTableCell<Map<String, Object>, Boolean> cell = new CheckBoxTableCell<>();
                    cell.setSelectedStateCallback(index -> {
                        Map<String, Object> item = tableView.getItems().get(index);
                        return (SimpleBooleanProperty) item.get("_selected");
                    });
                    return cell;
                });
                selectCol.setEditable(true);
                tableView.getColumns().add(selectCol);

                for (int i = 1; i <= columnCount; i++) {
                    String colName = meta.getColumnName(i);
                    TableColumn<Map<String, Object>, String> col = new TableColumn<>(colName);
                    col.setCellValueFactory(cellData -> {
                        Object val = cellData.getValue().get(colName);
                        return new SimpleStringProperty(val != null ? val.toString() : "");
                    });
                    tableView.getColumns().add(col);
                }

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    row.put("_selected", new SimpleBooleanProperty(false));
                    data.add(row);
                }

                tableView.setItems(data);
                errorBox.setText("Data loaded.");
            } catch (SQLException e) {
                errorBox.setText("Error: " + e.getMessage());
            }
        });

        inputArea.getChildren().addAll(new Label("SELECT Query:"), selectField, runSelect);
    }

    private void deleteSelected() {
        inputArea.getChildren().clear();

        TextField deleteQuery = new TextField();
        deleteQuery.setPromptText("Optional: Enter DELETE query manually");

        Button execute = new Button("Execute");

        execute.setOnAction(e -> {
            String sql = deleteQuery.getText().trim();

            if (sql.toLowerCase().startsWith("delete")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     Statement stmt = conn.createStatement()) {
                    int rows = stmt.executeUpdate(sql);
                    errorBox.setText("Deleted rows: " + rows);
                } catch (SQLException ex) {
                    errorBox.setText("Error: " + ex.getMessage());
                    if (!lastSelectedTable.isEmpty()) {
                        TextField selectField = new TextField("SELECT * FROM " + lastSelectedTable);
                        selectDataFromQuery(selectField.getText());
                    }
                }
            } else {
                List<Map<String, Object>> selectedRows = new ArrayList<>();
                for (Map<String, Object> row : data) {
                    if (row.containsKey("_selected") && ((SimpleBooleanProperty) row.get("_selected")).get()) {
                        selectedRows.add(row);
                    }
                }

                if (selectedRows.isEmpty()) {
                    errorBox.setText("No rows selected or query provided.");
                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     Statement stmt = conn.createStatement()) {

                    int count = 0;
                    for (Map<String, Object> row : selectedRows) {
                        StringBuilder condition = new StringBuilder();
                        for (Map.Entry<String, Object> entry : row.entrySet()) {
                            String key = entry.getKey();
                            if (key.equals("_selected")) continue;
                            Object val = entry.getValue();

                            if (condition.length() > 0) condition.append(" AND ");

                            if (val instanceof Number || val instanceof Boolean) {
                                condition.append(key).append("=").append(val);
                            } else {
                                condition.append(key).append("='").append(val).append("'");
                            }
                        }

                        String deleteSQL = "DELETE FROM " + lastSelectedTable + " WHERE " + condition;
                        count += stmt.executeUpdate(deleteSQL);
                    }

                    errorBox.setText("Deleted rows: " + count);
                    selectData(); // Refresh after delete

                } catch (SQLException ex) {
                    errorBox.setText("Error: " + ex.getMessage());
                }
            }
        });

        inputArea.getChildren().addAll(new Label("Delete via Query or Checkbox:"), deleteQuery, execute);
    }

    private void showDropTable() {
        inputArea.getChildren().clear();

        TextField tableNameField = new TextField();
        tableNameField.setPromptText("Enter table name to drop");

        Button drop = new Button("Drop");
        drop.setOnAction(e -> {
            String table = tableNameField.getText().trim();
            if (table.isEmpty()) {
                errorBox.setText("Table name is required.");
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {
                String sql = "DROP TABLE " + table;
                stmt.executeUpdate(sql);
                errorBox.setText("Table '" + table + "' dropped successfully.");
            } catch (SQLException ex) {
                errorBox.setText("Error: " + ex.getMessage());
            }
        });

        inputArea.getChildren().addAll(new Label("Drop Table:"), tableNameField, drop);
    }

    private void selectDataFromQuery(String query) {
    if (!query.toLowerCase().startsWith("select")) {
        errorBox.setText("Please enter a valid SELECT query.");
        return;
    }

    data.clear();
    tableView.getColumns().clear();

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        TableColumn<Map<String, Object>, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setCellValueFactory(cellData -> {
            Map<String, Object> row = cellData.getValue();
            if (!row.containsKey("_selected")) {
                row.put("_selected", new SimpleBooleanProperty(false));
            }
            return (SimpleBooleanProperty) row.get("_selected");
        });
        selectCol.setCellFactory(tc -> {
            CheckBoxTableCell<Map<String, Object>, Boolean> cell = new CheckBoxTableCell<>();
            cell.setSelectedStateCallback(index -> {
                Map<String, Object> item = tableView.getItems().get(index);
                return (SimpleBooleanProperty) item.get("_selected");
            });
            return cell;
        });
        selectCol.setEditable(true);
        tableView.getColumns().add(selectCol);

        for (int i = 1; i <= columnCount; i++) {
            String colName = meta.getColumnName(i);
            TableColumn<Map<String, Object>, String> col = new TableColumn<>(colName);
            col.setCellValueFactory(cellData -> {
                Object val = cellData.getValue().get(colName);
                return new SimpleStringProperty(val != null ? val.toString() : "");
            });
            tableView.getColumns().add(col);
        }

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(meta.getColumnName(i), rs.getObject(i));
            }
            row.put("_selected", new SimpleBooleanProperty(false));
            data.add(row);
        }

        tableView.setItems(data);
        errorBox.setText("Data loaded.");
    } catch (SQLException e) {
        errorBox.setText("Error: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}