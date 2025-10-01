/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.daysofcode.API;

import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author arthur
 */
public class TelaFilmesTabela extends javax.swing.JFrame  {
 
    private static final String API_KEY = "dfe5831316419e036ead45de1a29450e";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private JTable tabela;
    private MovieTableModel modelo;
    
    public TelaFilmesTabela(){
         
        
        modelo = new MovieTableModel(returnMovieResponse());
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        
        setTitle("CRUD Clientes da Cantina");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
    }
    
    public List<Movie> returnMovieResponse(){
        List<Movie> res = new ArrayList<Movie>();
        
        try {
            String endpoint = "/movie/popular";
            String urlString = BASE_URL + endpoint + "?api_key=" + API_KEY;
			
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
			
	    if(conn.getResponseCode() != 200) {
		throw new RuntimeException("HTTP error code: "+ conn.getResponseCode());
	    }
			
	    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	    StringBuilder response = new StringBuilder();
	    String line;
	    while((line = br.readLine()) != null) {
                response.append(line);
            }
			//System.out.println(response);
            Gson gson = new Gson();
            MovieResponse movieResponse = gson.fromJson(response.toString(), MovieResponse.class);

             List<Movie> filmes = movieResponse.results;
            //System.out.println("Página: " + movieResponse.page);
            //for (Movie m : movieResponse.results) {
             //   System.out.println(m.title + " (" + m.release_date + ")");
            //}
            res = filmes;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaFilmesTabela.class.getName());

    @SuppressWarnings("unchecked")
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> new TelaFilmesTabela().setVisible(true));
    }
}
