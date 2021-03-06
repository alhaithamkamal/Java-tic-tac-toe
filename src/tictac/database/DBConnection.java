/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 *
 * @author al-haitham
 */
public class DBConnection {
    private Connection conn;

    public Connection connect(){
        File file = new File ("TTTDB");
        if(file.exists()){
            try {
                String url = "jdbc:sqlite:TTTDB";
                conn = DriverManager.getConnection(url);            
            }
            catch (SQLException e) {
                System.out.println("Faild to connect to database\n"+e.getMessage());
            }
        }
        else {
            conn = createDB();
        }
        return conn;
    }
    public void disconnect(Connection connection){
        try {
            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("Faild to close connection with database\n"+ex.getMessage());
        }
    }
    public Connection createDB(){
        try {
            String url = "jdbc:sqlite:TTTDB";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String userTable = "CREATE TABLE \"users\" (\n" +
                    "	\"id\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "	\"username\"	TEXT NOT NULL UNIQUE,\n" +
                    "	\"password\"	TEXT NOT NULL,\n" +
                    "	\"fname\"	TEXT,\n" +
                    "	\"lname\"	TEXT,\n" +
                    "	\"score\"	INTEGER DEFAULT 0\n" +
                    ");";
            String playerTable = "CREATE TABLE \"players\" (\n" +
                    "	\"id\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "	\"fname\"	TEXT,\n" +
                    "	\"lname\"	TEXT,\n" +
                    "	\"ip_address\"	TEXT UNIQUE\n" +
                    ");";
            String gameTable = "CREATE TABLE \"games\" (\n" +
                    "	\"id\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "	\"game_type\"	TEXT NOT NULL CHECK(game_type IN ('solo','dual')),\n" +
                    "	\"player_id\"	INTEGER,\n" +
                    "	\"result\"	TEXT NOT NULL CHECK(result IN ('victory','loss','draw')),\n" +
                    "	\"sympol\"	TEXT NOT NULL CHECK(sympol IN ('x','o')),\n" +
                    "	\"user_id\"	INTEGER,\n" +
                    "	\"level\"	TEXT,\n" +
                    "	\"timestamp\"	TEXT DEFAULT CURRENT_TIMESTAMP,\n" +
                    "	FOREIGN KEY(\"player_id\") REFERENCES \"players\"(\"id\")\n" +
                    ");";
            String stepTable = "CREATE TABLE \"steps\" (\n" +
                    "	\"id\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "	\"x\"	INTEGER NOT NULL,\n" +
                    "	\"y\"	INTEGER NOT NULL,\n" +
                    "	\"game_id\"	INTEGER NOT NULL,\n" +
                    "	\"turn\"	TEXT NOT NULL CHECK(turn IN ('mine','oponent')),\n" +
                    "	FOREIGN KEY(\"game_id\") REFERENCES \"games\"(\"id\")\n" +
                    ");";
            stmt.execute(userTable);
            stmt.execute(playerTable);
            stmt.execute(gameTable);
            stmt.execute(stepTable);
            
            String query = "insert into players('fname') values('Computer')";
            stmt.execute(query);
            query = "insert into players('fname') values('Opponent')";
            stmt.execute(query);
        }
        catch (SQLException ex) {
            System.out.println("Faild to close create database\n"+ex.getMessage());
        }
        return conn;
    }
    public void dropDB(){
        try {
            String url = "jdbc:sqlite:TTTDB";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String dropUsers = "DELETE FROM users;";
            String dropPlayers = "DELETE FROM players;";
            String dropGames = "DELETE FROM games;";
            String dropSteps = "DELETE FROM steps;";
            String dropSeq = "DELETE FROM sqlite_sequence;";

            stmt.execute(dropUsers);
            stmt.execute(dropPlayers);
            stmt.execute(dropGames);
            stmt.execute(dropSteps);
            stmt.execute(dropSeq);
            conn.close();
        }
        catch(SQLException ex){
            System.out.println("Faild to drop create database\n"+ex.getMessage());
        }
    }
}
