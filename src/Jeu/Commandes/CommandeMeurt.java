package src.Jeu.Commandes;

import src.Jeu.Cellules.Cellule;

/**
 * Classe représentant une commande qui fait mourir la cellule concernée
 */
public class CommandeMeurt extends Commande{
    /**
     * Constructeur de la commandeMeurt, qui appelle super pour initialiser la cellule 
     * @param c La cellule concernée
     */
    public CommandeMeurt(Cellule c){
        super(c);
    }

    /*
     * Execute la commande en faisant mourir la cellule
     */
    public void executer() {
        cellule.meurt();
    }
}
