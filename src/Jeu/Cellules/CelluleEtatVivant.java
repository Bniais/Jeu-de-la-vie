package src.Jeu.Cellules;

import src.Jeu.Visiteurs.Visiteur;

/**
 * Classe représentant un état Vivant d'une cellule
 * Serialisable pour sauvegarder une grille de cellule comportant des etats dans FabriqueGrillCustom
 */
@SuppressWarnings("serial")
public class CelluleEtatVivant implements CelluleEtat{
    
    /** Initialistion statique de l'instance unique : Singleton */
    private static CelluleEtatVivant instance = new CelluleEtatVivant();

    /** Constructeur mit en privé */
    private CelluleEtatVivant(){
    }

    /**
     * Obtient l'instance unique de l'état mort
     * @return L'instance unique de l'état mort
     */
    public static CelluleEtatVivant getInstance() {
        return instance;
    }

    /**
     * Fait "vivre" l'état, il est déjà vivant donc ne fait rien
     * @return L'état vivant
     */
    @Override
    public CelluleEtat vit() {
        return this;
    }

    /**
     * Fait "mourir" l'état en retournant l'instance d'état mort
     * @return L'état mort
     */
    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    /**
     * Permet de savoir si l'était est vivant ou mort, ici il est vivant
     * @return Vrai : l'état est vivant
     */
    @Override
    public boolean estVivante() {
        return true;
    }

    /**
     * Accepte un visiteur provenant d'une cellule
     * 
     * @param v Le visiteur à accepter
     * @param c La cellule source
     */
    @Override
    public void accepte(Visiteur v, Cellule c) {
        v.visiteCelluleVivante(c);
    }
}
