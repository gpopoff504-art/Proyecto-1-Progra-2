/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

/**
 *
 * @author gpopo
 */
public class Player {
    private String username;
    private String password;
    private int points;
    private String[] logs;
    
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        this.logs = new String[10];
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void addPoints(int p) {
        this.points = this.points + p;
    }
    
    public void addLog(String log) {
        for (int i = logs.length - 1; i > 0; i--) {
            logs[i] = logs[i - 1];
        }
        logs[0] = log;
    }
    
    public void showLogs() {
        System.out.println("Ultimos juegos:");
        for (String log : logs) {
            if (log != null) {
                System.out.println(log);
            }
        }
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String[] getLogs() {
        return logs;
    }
}
