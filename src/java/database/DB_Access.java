/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Yvonne
 */
public class DB_Access 
{
    private LinkedList<String> filmList; 
    private DB_ConnectionPool connPool; 
   
    private static DB_Access theInstance = null; 
    
    
    public static DB_Access getInstance() throws ClassNotFoundException, Exception
    {
        if(theInstance == null)
        {
            theInstance = new DB_Access(); 
        }
        return theInstance; 
    }

    private DB_Access() throws ClassNotFoundException, Exception 
    {
        connPool = DB_ConnectionPool.getInstance();
        getIngredients(); 
    }
    
    public LinkedList getIngredients() throws Exception
    {
        Connection conn = connPool.getConnection(); 
        Statement stat = conn.createStatement(); 
        LinkedList<String> li_ingredients = new LinkedList<>(); 
        String sqlString = "";
        sqlString ="SELECT * " +
                    "FROM ingredient;"; 
    
    
        ResultSet rs = stat.executeQuery(sqlString);
        
        while (rs.next()) 
        {
            String name= rs.getString("name"); 
            
            if(!li_ingredients.contains(name))
            {
                li_ingredients.add(name); 
            }
        }
        
        connPool.releaseConnection(conn);
        
        return li_ingredients; 
    }
    
    public static void main(String[] args) throws ClassNotFoundException, Exception
    {
        DB_Access.getInstance();
    }
}
