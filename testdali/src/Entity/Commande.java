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
public class Commande {
    private int id;
    private float montant;
    private int etat;
    private String etats;

    public String getEtats() {
        return etats;
    }

    public void setEtats(String etats) {
        this.etats = etats;
    }

    public Commande(int id, float montant, int etat) {
        this.id = id;
        this.montant = montant;
        this.etat = etat;
    }

    public Commande(float montant, int etat) {
        this.montant = montant;
        this.etat = etat;
    }

    public Commande() {
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    
}
