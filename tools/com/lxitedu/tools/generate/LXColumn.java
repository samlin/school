package com.lxitedu.tools.generate;

public class LXColumn {
    private int isNullable;
    private String columnName;
    private String columnLable;
    private int columnDisplaySize;

    public int getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(int isNullable) {
        this.isNullable = isNullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnLable() {
        return columnLable;
    }

    public void setColumnLable(String columnLable) {
        this.columnLable = columnLable;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
