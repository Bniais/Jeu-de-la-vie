package src.Jeu.Cellules;

import src.Jeu.Visiteurs.Visiteur;

/**
 * Classe représentant un état Mort d'une cellule
 * Serialisable pour sauvegarder une grille de cellule comportant des etats dans FabriqueGrillCustom
 */
@SuppressWarnings("serial")
public class CelluleEtatMort implements CelluleEtat {
    
    /** Initialistion statique de l'instance unique : Singleton */
    private static CelluleEtatMort instance = new CelluleEtatMort();

    /** Constructeur mit en privé */
    private CelluleEtatMort(){
    }

    /**
     * Obtient l'instance unique de l'état mort
     * @return L'instance unique de l'état mort
     */
    public static CelluleEtatMort getInstance(){
        return instance;
    }

    /**
     * Fait "vivre" l'état en retournant l'instance d'état vivante
     * @return L'état vivant
     */
    @Override
    public CelluleEtat vit(){
        return CelluleEtatVivant.getInstance();
    }

    /**
     * Fait "mourir" l'état, il est déjà mort donc ne fait rien
     * @return L'état mort
     */
    @Override
    public CelluleEtat meurt(){
        return this;
    }

    /**
     * Permet de savoir si l'était est vivant ou mort, ici il est mort
     * @return Faux : l'état est mort
     */
    @Override
    public boolean estVivante(){
        return false;
    }

    /**
     * Accepte un visiteur provenant d'une cellule
     * @param v Le visiteur à accepter
     * @param c La cellule source
     */
    @Override
    public void accepte(Visiteur v, Cellule c){
        v.visiteCelluleMorte(c);
    }
}
