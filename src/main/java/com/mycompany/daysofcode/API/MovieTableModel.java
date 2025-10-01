/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.daysofcode.API;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arthur
 */
public class MovieTableModel extends AbstractTableModel {

     private final String[] colunas = {"Título", "Descrição", "Data de lançamento"};
    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies= movies;
    }
    
    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie m = movies.get(rowIndex);
        switch (columnIndex) {
            case 0: return m.getTitle();
            case 1: return m.getOverview();
            case 2: return m.getReleaseDate();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }
    
}
