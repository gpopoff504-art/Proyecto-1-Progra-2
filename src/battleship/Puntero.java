/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

/**
 *
 * @author gpopo
 */
public class Puntero {
    //se supone que es como un pointer (coordenadas x y) y registra hits
    //los barcos se mueven de forma random
    public int x;
    public int y;
    private boolean hit;
    
    public Puntero(int x, int y){
        this.x = x;
        this.y = y;
        hit = false;
    }
    
    public boolean isHit() {
        return hit;
    }
    
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    @Override
    public boolean equals (Object objeto) {
        if(objeto instanceof Puntero) {
            Puntero otroPunto = (Puntero)objeto;
            return (x == otroPunto.x && y == otroPunto.y );
        }
        else
            return false;
    }
}
