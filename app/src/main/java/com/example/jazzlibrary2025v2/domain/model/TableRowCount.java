package com.example.jazzlibrary2025v2.domain.model;

import java.util.Objects;

public class TableRowCount {

    private int tableRowCountId;  // Assuming there's a primary key
    private int tableId;
    private int tableName;
    private int count;  // Assuming there's a count column

    // Constructors
    public TableRowCount(int tableRowCountId, int tableId, int tableName, int count) {
        this.tableRowCountId = tableRowCountId;
        this.tableId = tableId;
        this.tableName = tableName;
        this.count = count;
    }

    public TableRowCount() {}

    // Getters and Setters
    public int getTableRowCountId() {
        return tableRowCountId;
    }

    public void setTableRowCountId(int tableRowCountId) {
        this.tableRowCountId = tableRowCountId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableName() {
        return tableName;
    }

    public void setTableName(int tableName) {
        this.tableName = tableName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TableRowCount{" +
                "tableRowCountId=" + tableRowCountId +
                ", tableId=" + tableId +
                ", tableName=" + tableName +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableRowCount that = (TableRowCount) o;
        return tableRowCountId == that.tableRowCountId && tableId == that.tableId && tableName == that.tableName && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableRowCountId, tableId, tableName, count);
    }
}