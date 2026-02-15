/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
    
    private String[][] grid;  
    private ArrayList<Barco> barcos; 
    private final int Tamano = 8; 
    
    public Tablero() {
        grid = new String[Tamano][Tamano];
        barcos = new ArrayList<>();
        inicializarTablero();
    }
    
    private void inicializarTablero() {
        for (int i = 0; i < Tamano; i++) {
            for (int j = 0; j < Tamano; j++) {
                grid[i][j] = "~"; 
            }
        }
    }
    
    private boolean esCodigoBarco(String celda) {
        return celda.equals("PA") || celda.equals("AZ") || 
               celda.equals("SM") || celda.equals("DT");
    }
    
    public boolean agregarBarco(Barco barco, int filaInicial, int colInicial, boolean horizontal) {
        int tamanoBarco = obtenerTamanoBarco(barco.getCode());
        
        if (!validarPosicion(filaInicial, colInicial, tamanoBarco, horizontal)) {
            return false;
        }
        
        if (horizontal) {
            for (int j = 0; j < tamanoBarco; j++) {
                Puntero coord = new Puntero(filaInicial, colInicial + j);
                barco.addCoordenada(coord);
                grid[filaInicial][colInicial + j] = barco.getCode();
            }
        } else {
            for (int i = 0; i < tamanoBarco; i++) {
                Puntero coord = new Puntero(filaInicial + i, colInicial);
                barco.addCoordenada(coord);
                grid[filaInicial + i][colInicial] = barco.getCode();
            }
        }
        
        barcos.add(barco);
        return true;
    }
    
    private int obtenerTamanoBarco(String codigo) {
        switch (codigo) {
            case "PA": return 5;
            case "AZ": return 4;
            case "SM": return 3;
            case "DT": return 2;
            default: return 0;
        }
    }
    
    private boolean validarPosicion(int fila, int col, int tamano, boolean horizontal) {
        if (horizontal) {
            if (col + tamano > Tamano) return false;
        } else {
            if (fila + tamano > Tamano) return false; 
        }
        
        if (horizontal) {
            for (int j = 0; j < tamano; j++) {
                if (!grid[fila][col + j].equals("~")) {
                    return false; 
                }
            }
        } else {
            for (int i = 0; i < tamano; i++) {
                if (!grid[fila + i][col].equals("~")) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public String bombardear(int fila, int col) {
        String celdaActual = grid[fila][col];
        
        if (esCodigoBarco(celdaActual)) {
            for (Barco barco : barcos) {
                Puntero objetivo = new Puntero(fila, col);
                
                if (barco.checkHit(objetivo)) {
                    grid[fila][col] = "X";
                    barco.recibirDano();
                    
                    if (barco.esHundido()) {
                        return "HUNDIDO:" + barco.getCode();
                    } else {
                        return "HIT:" + barco.getCode();
                    }
                }
            }
        }
        
        if (celdaActual.equals("X") || celdaActual.equals("F")) {
            return "FALLO";
        }
        
        grid[fila][col] = "F";
        return "FALLO";
    }
    
    public void limpiarFallos() {
        for (int i = 0; i < Tamano; i++) {
            for (int j = 0; j < Tamano; j++) {
                if (grid[i][j].equals("F")) {
                    grid[i][j] = "~";
                }
            }
        }
    }
    
    public void regenerarTablero() {
        ArrayList<Puntero> todasLasX = new ArrayList<>();
        for (int i = 0; i < Tamano; i++) {
            for (int j = 0; j < Tamano; j++) {
                if (grid[i][j].equals("X")) {
                    todasLasX.add(new Puntero(i, j));
                }
            }
        }
        
        inicializarTablero();
        
        for (Puntero x : todasLasX) {
            grid[x.x][x.y] = "X";
        }
        
        Random rand = new Random();
        
        for (Barco barco : barcos) {
            if (!barco.esHundido()) {
                barco.getCoordenadas().clear();
                
                boolean colocado = false;
                int intentos = 0;
                
                while (!colocado && intentos < 100) {
                    int fila = rand.nextInt(Tamano);
                    int col = rand.nextInt(Tamano);
                    boolean horizontal = rand.nextBoolean();
                    int tamano = obtenerTamanoBarco(barco.getCode());
                    
                    if (validarPosicionRegenerar(fila, col, tamano, horizontal)) {
                        colocarBarcoRegenerar(barco, fila, col, horizontal);
                        colocado = true;
                    }
                    intentos++;
                }
            }
        }
    }
 
    private boolean validarPosicionRegenerar(int fila, int col, int tamano, boolean horizontal) {
        if (horizontal) {
            if (col + tamano > Tamano) return false;
            for (int j = 0; j < tamano; j++) {
                if (!grid[fila][col + j].equals("~")) {
                    return false; 
                }
            }
        } else {
            if (fila + tamano > Tamano) return false;
            for (int i = 0; i < tamano; i++) {
                if (!grid[fila + i][col].equals("~")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void colocarBarcoRegenerar(Barco barco, int fila, int col, boolean horizontal) {
        int tamano = obtenerTamanoBarco(barco.getCode());
        
        if (horizontal) {
            for (int j = 0; j < tamano; j++) {
                Puntero coord = new Puntero(fila, col + j);
                barco.addCoordenada(coord);
                grid[fila][col + j] = barco.getCode();
            }
        } else {
            for (int i = 0; i < tamano; i++) {
                Puntero coord = new Puntero(fila + i, col);
                barco.addCoordenada(coord);
                grid[fila + i][col] = barco.getCode();
            }
        }
    }
    
    public boolean todosHundidos() {
        for (Barco barco : barcos) {
            if (!barco.esHundido()) {
                return false;
            }
        }
        return true;
    }
    
    public int contarBarcosVivos() {
        int count = 0;
        for (Barco barco : barcos) {
            if (!barco.esHundido()) {
                count++;
            }
        }
        return count;
    }
    
    public String obtenerCelda(int fila, int col) {
        return grid[fila][col];
    }
    
    public String[][] obtenerGrid() {
        return grid;
    }
    
    public ArrayList<Barco> obtenerBarcos() {
        return barcos;
    }
    
    public int obtenerTamano() {
        return Tamano;
    }
}

 