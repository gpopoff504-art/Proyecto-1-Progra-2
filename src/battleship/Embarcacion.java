/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

public abstract class Embarcacion {
    
    protected String code;
    protected int hp;
    protected int hpMax;
    
    public Embarcacion(String code, int hpMax) {
        this.code = code;
        this.hpMax = hpMax;
        this.hp = hpMax;
    }
    
    // Método abstracto - cada subclase lo implementa
    public abstract String getTipo();
    
    // Métodos concretos heredados por Barco
    public void recibirDano() {
        if (hp > 0) hp--;
    }
    
    public boolean esHundido() {
        return hp <= 0;
    }
    
    public int getHp() { return hp; }
    public int getHpMax() { return hpMax; }
    public String getCode() { return code; }
}
