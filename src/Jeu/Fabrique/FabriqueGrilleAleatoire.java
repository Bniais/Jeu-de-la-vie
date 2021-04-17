package src.Jeu.Fabrique;

import src.Jeu.Cellules.Cellule;
import src.Jeu.Cellules.CelluleEtatMort;
import src.Jeu.Cellules.CelluleEtatVivant;

/**
 * Fabrique qui permet de fabriquer des grilles avec des dispositions aléatoires d'états, en spécifiant la densité et la taille de la grille en largeur et hauteur
 */
@SuppressWarnings("serial")
public class FabriqueGrilleAleatoire implements FabriqueGrille{
    
    /** La largeur et hauteur de la grille à fabriquer */
	private int xMin, yMin;

    /** La densité de la grille à fabriquer */
    private double densite;
    
    /**
     * Constructeur de la fabrique, en spécifiant la taille et la densité des grilles que la fabrique doit produire
     * @param xMin La largeur de la grille
     * @param yMin La hauteur de la grille
     * @param densite La densité de la grille
     */
    public FabriqueGrilleAleatoire(int xMin, int yMin, double densite){
        this.xMin = xMin;
        this.yMin = yMin;
        this.densite = densite;
    }

    /**
     * Fabrique une grille à la dimension stockée dans la fabrique, en mettant un état aléatoire dépendant de la densité pour chaque Cellule
     * @return La grille de cellule
     */
    @Override
    public Cellule[][] fabriqueGrille() {
        Cellule[][] grille = new Cellule[xMin][yMin];
        for (int x = 0; x < xMin; x++) {
            for (int y = 0; y < yMin; y++) {
                grille[x][y] = new Cellule(x, y, Math.random() <= densite ? CelluleEtatVivant.getInstance() : CelluleEtatMort.getInstance());
            }
        }

        return grille;
    }
}
