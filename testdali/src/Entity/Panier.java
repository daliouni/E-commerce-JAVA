/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author DALI
 */
public class Panier {
    
    int id;
    String nomp;
    int quantite;

    public int getId() {
        return id;
    }

    public String getNomp() {
        return nomp;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Panier(String nomp, int quantite) {
        this.nomp = nomp;
        this.quantite = quantite;
    }

    public Panier() {
    }

    @Override
    public String toString() {
        return "" + nomp + " quantite = " + quantite +"\n";
    }
    
    
    
}
