package src.Jeu.Commandes;

import src.Jeu.Cellules.Cellule;

/**
 * Classe représentant une commande qui fait vivre la cellule concernée
 */
public class CommandeVit extends Commande{
    /**
     * Constructeur de la commandeMeurt, qui appelle super pour initialiser la cellule 
     * @param c La cellule concernée
     */
    public CommandeVit(Cellule c){
        super(c);
    }

    /*
     * Execute la commande en faisant vivre  la cellule
     */
    public void executer() {
        cellule.vit();
    }
}
