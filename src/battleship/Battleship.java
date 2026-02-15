/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;
import java.util.ArrayList;

public class Battleship {
    
    private ArrayList<Player> jugadores;
    private Player currentUser; 
    private Player jugador1;
    private Player jugador2;
    private int dificultad;
    private int modo;
    private Tablero tablero1;
    private Tablero tablero2;
    
    public Battleship() {
        jugadores = new ArrayList<>();
        dificultad = 2;
        modo = 1;
        currentUser = null;
    }
    
    public boolean crearJugador(String username, String password) {
        for (Player p : jugadores) {
            if (p.getUsername().equals(username)) {
                return false;
            }
        }   
        Player nuevoJugador = new Player(username, password);
        jugadores.add(nuevoJugador);
        return true;
    }
    
    public boolean login(String username, String password) {
        for (Player p : jugadores) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                currentUser = p;  
                return true;      
            }
        }
        return false; 
    }
    
    public Player buscarJugador(String username) {
        for (Player p : jugadores) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }
    
    public void eliminarCuenta() {
        jugadores.remove(currentUser);
        currentUser = null;
    }
       
    public void cambiarDificultad(int nuevaDificultad) {
        this.dificultad = nuevaDificultad;
    }
    
    public void cambiarModo(int nuevoModo) {
        this.modo = nuevoModo;
    }
    
    public int obtenerCantidadBarcos() {
        switch (dificultad) {
            case 1: return 5;
            case 2: return 4;
            case 3: return 2;
            case 4: return 1;
            default: return 4;
        }
    }  
    
    public void iniciarPartida(String usernameJugador2) {
        jugador1 = currentUser;
        jugador2 = buscarJugador(usernameJugador2);
        
        tablero1 = new Tablero();
        tablero2 = new Tablero();
    }
    
    public String obtenerNombreDificultad() {
       return (dificultad == 1) ? "EASY" :
               (dificultad == 2) ? "NORMAL" :
               (dificultad == 3) ? "EXPERT" :
               (dificultad == 4) ? "GENIUS" :
               "NORMAL";      
    }
    
    public String obtenerNombreModo() {
        return (modo == 1) ? "TUTORIAL" : "ARCADE";
    }
    
    public void asignarTablero1(Tablero tablero) {
        this.tablero1 = tablero;
    }
    
    public void asignarTablero2(Tablero tablero) {
        this.tablero2 = tablero;
    }
    
    public String bombardearTablero1(int fila, int col) {
        return tablero1.bombardear(fila, col);
    }
    
    public String bombardearTablero2(int fila, int col) {
        return tablero2.bombardear(fila, col);
    }
    
    public boolean jugador1Gano() {
        return tablero2.todosHundidos();
    }
    
    public boolean jugador2Gano() {
        return tablero1.todosHundidos();
    }
    
    public void registrarVictoria(Player ganador, Player perdedor) {
        ganador.addPoints(3);
        String log = ganador.getUsername() + " hundio todos los barcos de " + 
                     perdedor.getUsername() + " en modo " + obtenerNombreDificultad();
        ganador.addLog(log);
        perdedor.addLog(log);
    }
    
    public ArrayList<Player> obtenerRanking() {
        for (int i = 0; i < jugadores.size() - 1; i++) {
            for (int j = 0; j < jugadores.size() - 1 - i; j++) {
                if (jugadores.get(j).getPoints() < jugadores.get(j + 1).getPoints()) {
                    Player temp = jugadores.get(j);
                    jugadores.set(j, jugadores.get(j + 1));
                    jugadores.set(j + 1, temp);
                }
            }
        }
        return jugadores;
    }
    
    public Tablero getTablero1() {
        return tablero1;
    }
    
    public Tablero getTablero2() {
        return tablero2;
    }
    
    public Player getJugador1() {
        return jugador1;
    }
    
    public Player getJugador2() {
        return jugador2;
    }
    
    public Player getCurrentUser() {
        return currentUser;
    }
    
    public ArrayList<Player> getJugadores() {
        return jugadores;
    }
    
    public int getDificultad() {
        return dificultad;
    }
    
    public int getModo() {
        return modo;
    }
}