package src.Jeu.Fabrique;

import java.io.Serializable;

import src.Jeu.Cellules.Cellule;

/**
 * Interface fonctionelle pour une fabrique abstraite permetant de produire des grilles de cellules
 */
public interface  FabriqueGrille extends Serializable{
    /**
     * Fabrique et retourne une grille de cellules
     * @return La grille de cellules fabriquée
     */
    public Cellule[][] fabriqueGrille();
}
