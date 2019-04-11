package com.mcba.comandaclient.model;

import com.adesigns.csv.annotation.CsvColumn;

import java.util.Date;


public class CSVData {

    @CsvColumn(title = "ID comanda")
    private int idColumn;

    @CsvColumn(title = "Date Column", dateFormat = "dd/MM/yy")
    // dateFormat is optional.  See CsvColumn for default format.
    private Date dateColumn;

    @CsvColumn(title = "Cliente")
    private String clienteColumn;

    @CsvColumn(title = "Producto")
    private String productColumn;

    @CsvColumn(title = "Total")
    private String totalColumn;

    public CSVData(int idColumn, Date dateColumn, String clienteColumn, String productColumn, String totalColumn) {
        this.idColumn = idColumn;
        this.dateColumn = dateColumn;
        this.clienteColumn = clienteColumn;
        this.productColumn = productColumn;
        this.totalColumn = totalColumn;
    }


    public CSVData() {

    }

    public CSVData(String clienteColumn, Date dateColumn, String totalColumn) {
        this.dateColumn = dateColumn;
        this.totalColumn = totalColumn;
        this.clienteColumn = clienteColumn;



    }

    public int getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(int idColumn) {
        this.idColumn = idColumn;
    }

    public String getClienteColumn() {
        return clienteColumn;
    }

    public void setClienteColumn(String clienteColumn) {
        this.clienteColumn = clienteColumn;
    }

    public String getProductColumn() {
        return productColumn;
    }

    public void setProductColumn(String productColumn) {
        this.productColumn = productColumn;
    }

    public String getTotalColumn() {
        return totalColumn;
    }

    public void setTotalColumn(String totalColumn) {
        this.totalColumn = totalColumn;
    }

    public Date getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(Date dateColumn) {
        this.dateColumn = dateColumn;
    }
}
