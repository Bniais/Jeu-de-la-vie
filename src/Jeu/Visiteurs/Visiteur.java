package src.Jeu.Visiteurs;

import java.io.Serializable;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Cellules.Cellule;

/**
 * Classe abstraite définissant les méhodes que les visiteurs doivent redéfinir, permet de faire des modes de jeu
 */
@SuppressWarnings("serial")
public abstract class Visiteur implements Serializable{
    /** Le jeu lié au visiteur */
    protected transient JeuDeLaVie jeu;

    /**
     * Constructeur d'un visiteur, servant de base aux autres visiteur
     * @param jeu Le jeu lié
     */
    public Visiteur(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    /**
     * Méthode de visite d'une cellule vivante
     * @param c La cellule visitée
     */
    public abstract void visiteCelluleVivante(Cellule c);

    /**
     * Méthode de visite d'une cellule morte
     * @param c La cellule visitée
     */
    public abstract void visiteCelluleMorte(Cellule c);

    /**
     * Permet de spécifier le jeu au visiteur après avoir chargé la sauvegarde
     * @param jeu Le jeu à associer
     */
    public void setJeu(JeuDeLaVie jeu){
        this.jeu = jeu;
    }
}
