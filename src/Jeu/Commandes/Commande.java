package src.Jeu.Commandes;

import src.Jeu.Cellules.Cellule;

/**
 * Classe abstraite qui définit les bases pour les commandes de vie et de mort sur des cellules
 */
public abstract class Commande{
    /** La cellule dont la commande concernera */
    protected Cellule cellule;

    /**
     * Constructeur d'une commande, en spécifiant la cellule dont traitera la commande.
     * Sert à appeler super dans tous les types de commandes
     * @param c La cellule concernée
     */
    public Commande(Cellule c){
        cellule = c;
    }

    /**
     * Execute la commande
     */
    public abstract void executer();
}
