package src.Jeu.Fabrique;

import src.Jeu.Cellules.Cellule;
import src.Jeu.Cellules.CelluleEtatMort;
import src.Jeu.Cellules.CelluleEtatVivant;

/**
 * Fabrique qui construit des copies de la grille spécifiée à l'initialisation
 */
@SuppressWarnings("serial")
public class FabriqueGrilleCustom implements FabriqueGrille{
    
    /** Une grille comportant un planeur, constuite statiquement */
	public static Cellule[][] GRILLE_PLANEUR;
    static{
        GRILLE_PLANEUR = new Cellule[36][36];
        for (int i = 0; i < GRILLE_PLANEUR.length; i++) {
            for (int j = 0; j < GRILLE_PLANEUR[0].length; j++) {
                GRILLE_PLANEUR[i][j] = new Cellule(i, j, CelluleEtatMort.getInstance());
            }
        }

        GRILLE_PLANEUR[0][0] = new Cellule(0, 0, CelluleEtatVivant.getInstance());
        GRILLE_PLANEUR[1][1] = new Cellule(1, 1, CelluleEtatVivant.getInstance());
        GRILLE_PLANEUR[2][1] = new Cellule(2, 1, CelluleEtatVivant.getInstance());
        GRILLE_PLANEUR[0][2] = new Cellule(0, 2, CelluleEtatVivant.getInstance());
        GRILLE_PLANEUR[1][2] = new Cellule(1, 2, CelluleEtatVivant.getInstance());
    }
    
    /** La grille que la fabrique doit recopier */
    private Cellule[][] grille;
    
    /**
     * Constructeur d'une fabrique de grille custom, en spécifiant la grille à recopier
     * @param grille La grille à recopier
     */
    public FabriqueGrilleCustom(Cellule[][] grille){
        this.grille = grille;
    }

    /**
     * Fabrique une grille en recopiant la grille de modèle spécifiée à l'initialisation
     * @return La grille fabriquée
     */
    @Override
    public Cellule[][] fabriqueGrille(){
        final Cellule[][] result = new Cellule[grille.length][grille[0].length];
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                result[i][j] = (Cellule)((grille[i][j]).clone());
            }
        }
        return result;
    }
}
