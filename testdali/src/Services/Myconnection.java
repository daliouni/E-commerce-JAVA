/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author abdou
 */
public class Myconnection {
      final static String url="jdbc:mysql://127.0.0.1:3306/pepinier";
	final static String name="root";
	final static String password="";
	static Connection con;
	static Myconnection ins;
	public static Connection getCon() {
		return con;
	}
	private Myconnection() {
		try {
			con=DriverManager.getConnection(url,name,password);
			System.out.println("connexion etablie");
		}
		catch (SQLException ex)
		{
			System.out.println("erreur: "+ex.getMessage());
		}
	}
	public static Myconnection getInstance()
	{
		if (ins==null)
		{
			ins=new Myconnection();
		}
		return ins;
	}

    
}