package src.Jeu.Cellules;

import java.io.Serializable;

import src.Jeu.Visiteurs.Visiteur;

/**
 * Interface comportant les méthodes qu'un état de cellule doit définir
 */
public interface CelluleEtat extends Serializable{

    /**
     * Rend l'état de la cellule vivant
     * @return L'état vivant de la cellule
     */
    public CelluleEtat vit();

    /**
     * Rend l'état de la cellule mort
     * @return L'état mort de la cellule
     */
    public CelluleEtat meurt();

    /**
     * Permet de savoir si l'état de la cellule est vivant ou mort (au lieu d'utilise instance of)
     * @return Vrai si l'état est vivant, sinon faux
     */
    public boolean estVivante();

    /**
     * Accepte un visiteur
     * @param v Le visiteur à accepter
     * @param c La cellule d'origine
     */
    public void accepte(Visiteur v, Cellule c);
}
