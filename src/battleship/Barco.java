/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;
import java.util.ArrayList;

public class Barco extends Embarcacion {
    
    private ArrayList<Puntero> coordenadas;
    
    public Barco(String code) {
        super(code, obtenerHpPorCodigo(code)); 
        coordenadas = new ArrayList<>();
    }
    
    private static int obtenerHpPorCodigo(String code) {
        switch (code) {
            case "PA": return 5;
            case "AZ": return 4;
            case "SM": return 3;
            case "DT": return 2;
            default: return 1;
        }
    }
    
    @Override
    public String getTipo() {
        switch (code) {
            case "PA": return "Portaaviones";
            case "AZ": return "Acorazado";
            case "SM": return "Submarino";
            case "DT": return "Destructor";
            default: return "Desconocido";
        }
    }
    
    public void addCoordenada(Puntero coord) {
        coordenadas.add(coord);
    }
    
    public boolean checkHit(Puntero hit) {
        for (Puntero coordenada : coordenadas) {
            if (coordenada.equals(hit)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Puntero> getCoordenadas() {
        return coordenadas;
    }
}
