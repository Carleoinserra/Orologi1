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


}
