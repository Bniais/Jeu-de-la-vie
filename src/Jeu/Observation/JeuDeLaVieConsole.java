package src.Jeu.Observation;

import src.Jeu.JeuDeLaVie;

/**
 * Observateur permettant de voir le jeu dans le terminal
 */
public class JeuDeLaVieConsole implements Observateur{

    /** Le jeu de la vie lié */
    private JeuDeLaVie jeu;

    /** 
     * Constructeur de l'observateur console, en spécifiant le jeu
     * @param jeu Le jeu de la vie lié
     */
    public JeuDeLaVieConsole(JeuDeLaVie jeu) {
        this.jeu = jeu;
        actualise();
    }

    /**
     * Réagit au message du jeu en réaffichant le jeu dans le terminal
     */
    @Override
    public void actualise() {
        System.out.println("\n\nGénération " + jeu.getGen() + " : \n");

        int nbVivant = 0;
        for (int y = 0; y < jeu.getYMax(); y++) {
            for (int x = 0; x < jeu.getXMax(); x++) {
               if(jeu.getGrilleXY(x, y).estVivante())
                    nbVivant++;
            }
        }
        System.out.println("Nombre vivantes : " + nbVivant + "\n");
        
        //Affiche la grille (pas demandé)
        for (int y = 0; y < jeu.getYMax(); y++) {
            for (int x = 0; x < jeu.getXMax(); x++) {
                System.out.print(" " + jeu.getGrilleXY(x, y) + " ");
            }
            System.out.println("");
        }
    }
}
