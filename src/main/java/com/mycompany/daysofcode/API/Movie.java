/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.daysofcode.API;

/**
 *
 * @author arthur
 */
public class Movie {
     int id;
    String title;
    String overview;
    String release_date;
    String poster_path;   
    
     public Movie(){}
     
     public Movie(int id, String title, String overview, String release_date, String poster_path){
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }
         
        
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
        public String getOverview(){
        return overview;
    }
    
    public void setOverview(String overview){
        this.overview = overview;
    }
    
        public String getReleaseDate(){
        return release_date;
    }
    
    public void setReleaseDate(String release_date){
        this.release_date = release_date;
    }
    
        public String getPosterPath(){
        return poster_path;
    }
    
    public void setPosterPath(String poster_path){
        this.poster_path = poster_path;
    }
    
    
    
}
