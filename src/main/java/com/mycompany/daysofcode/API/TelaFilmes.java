/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.daysofcode.API;

import com.google.gson.Gson;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author arthur
 */
public class TelaFilmes extends javax.swing.JFrame  {
 
    private static final String API_KEY = "dfe5831316419e036ead45de1a29450e";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
    
    private JTable tabela;
    private MovieTableModel modelo;
    private JLabel imagem;
    private JLabel paginaLabel;
    private List<Movie> filmes;
    
    private int currentPage = 1;
    private int totalPages = 1;
    
    public TelaFilmes(){
         
        filmes = returnMovieResponse(currentPage);
        modelo = new MovieTableModel(filmes);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(900, 300));
        
        
        imagem = new JLabel("", JLabel.CENTER);
        imagem.setPreferredSize(new Dimension(300,300));
        imagem.setBorder(BorderFactory.createTitledBorder("Poster do Filme"));
        
        
        paginaLabel = new JLabel("Página: " + currentPage, JLabel.CENTER);
        
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                insertImage();
            }
        });
        
        
        JButton anteriorBtn = new JButton("Anterior");
        JButton proximoBtn = new JButton("Próximo");
        
        anteriorBtn.addActionListener(e -> {
            if(currentPage > 1){
                currentPage--;
                updateTable();
            }
        });
        
        proximoBtn.addActionListener(e -> {
                currentPage++;
                updateTable();
        });
        
        JPanel navPanel = new JPanel();
        navPanel.add(anteriorBtn);
        navPanel.add(paginaLabel);
        navPanel.add(proximoBtn);

        
        setLayout(new BorderLayout());
        add(imagem, BorderLayout.NORTH); // imagem fica acima da tabela
        add(scroll, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);
        
        
        setTitle("Filmes Populares");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    private List<Movie> returnMovieResponse(int page){
        try {
            String endpoint = "/movie/popular";
            String urlString = BASE_URL + endpoint + "?api_key=" + API_KEY  + "&page=" + page + "&language=pt-BR";
			
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
            
            totalPages = movieResponse.total_pages;
            
             return movieResponse.results;
        }catch(Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    private void updateTable(){
        filmes = returnMovieResponse(currentPage);
        modelo.setMovies(filmes);
        modelo.fireTableDataChanged();
        paginaLabel.setText("Página: " + currentPage);
        imagem.setIcon(null);
        imagem.setText("");
    }

    
private void insertImage() {
    int row = tabela.getSelectedRow();
    if (row >= 0) {
        String posterPath = filmes.get(row).poster_path;
        if (posterPath != null && !posterPath.isEmpty()) {
            try {
                String fullUrl = IMG_BASE_URL + posterPath;
                // baixa a imagem da URL
                Image img = ImageIO.read(new URL(fullUrl));
                if (img != null) {
                    // redimensiona a imagem para caber melhor no JLabel
                    Image scaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    imagem.setIcon(new ImageIcon(scaled));
                    imagem.setText(""); // garante que não sobre texto
                } else {
                    imagem.setIcon(null);
                    imagem.setText("Erro ao carregar imagem");
                }
            } catch (Exception e) {
                e.printStackTrace();
                imagem.setIcon(null);
                imagem.setText("Erro ao carregar imagem");
            }
        } else {
            imagem.setIcon(null);
            imagem.setText("Sem imagem disponível");
        }
    }
}
    
    
    
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaFilmes.class.getName());

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
        SwingUtilities.invokeLater(() -> new TelaFilmes().setVisible(true));
    }
}
