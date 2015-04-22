/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 *
 * @author Yvonne
 */
public class DB_ConnectionPool implements DB_Config
{
    private LinkedList<Connection> connections = new LinkedList<>(); 
    private static final int MAX_CONN = 50; 
    private int num_conn = 0; 

    private static DB_ConnectionPool theInstance = null; 
    
    public static DB_ConnectionPool getInstance() throws ClassNotFoundException
    {
        if(theInstance == null)
        {
            theInstance = new DB_ConnectionPool(); 
        }
        return theInstance; 
    }
    
    private DB_ConnectionPool() throws ClassNotFoundException 
    {
        Class.forName(DB_DRIVER); 
    }
    
    public synchronized Connection getConnection() throws Exception
    {
        if(connections.isEmpty())
        {
            if(num_conn == MAX_CONN)
            {
                throw new Exception("Maximum number of connections reached. Please try again later");
            }
            Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSSWD); 
            num_conn++; 
            return conn; 
        }
        else
        {
            return connections.poll();
        }
    }
    
    public synchronized void releaseConnection(Connection conn)
    {
        connections.offer(conn); 
    }
    
    public static void main(String[] args) {
        DB_ConnectionPool dbc; 
        
        try 
        {
            dbc = DB_ConnectionPool.getInstance(); 

            for (int i = 0; i <=100; i++) 
            {
                 Connection con = dbc.getConnection();
                 System.out.println("Connection "+i+" created");
                 dbc.releaseConnection(con);
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
    }
}
