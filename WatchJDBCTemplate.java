package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;


@Component
public class WatchJDBCTemplate {
	
	private JdbcTemplate jdbcTemplateObject;

    @Autowired
    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }
    
    public int insertOrologio(String marca ,String modello,  double prezzo, String tipologia,String url) {
        String query = "INSERT INTO orologi (marca, modello, tipologia, prezzo, url) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplateObject.update(query,marca, modello, tipologia, prezzo, url);
    }
    
    public int updatePrezzo(double prezzo, String modello) {
        String query = "UPDATE orologi SET prezzo = ? WHERE modello = ?";
        return jdbcTemplateObject.update(query, prezzo, modello);
    }
    
    public ArrayList<orologio> ritornaOrologi(){
    	ResultSet rs1 = null;
    	
    	
            String query = "SELECT * FROM orologi";
            return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<orologio>>() {
                @Override
                public ArrayList<orologio> extractData(ResultSet rs) throws SQLException {
                	ArrayList <orologio> listaWatch = new ArrayList<>();
                    while (rs.next()) {
                        orologio orologio = new orologio();
                       
                        orologio.setMarca(rs.getString("marca"));
                        orologio.setModello(rs.getString("modello"));
                        orologio.setPrezzo(Double.parseDouble(rs.getString("prezzo")));
                        orologio.setTipologia(rs.getString("tipologia") );
                        orologio.setUrlImage(rs.getString("url"));
                        
                        listaWatch.add(orologio);
                    }
                    return listaWatch;
                }
            });
        }
   

}
