package src.Jeu.Visiteurs;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Cellules.Cellule;
import src.Jeu.Commandes.CommandeMeurt;
import src.Jeu.Commandes.CommandeVit;

/**
 * Visiteur définissant des règles personalisées
 */
@SuppressWarnings("serial")
public class VisiteurCustom extends Visiteur {
    
	private boolean[] conditionMeurt;
    private boolean[] conditionVit;

    /**
     * Constructeur du visiteur personalisé, en spécifiant les conditions de vie et mort
     * @param jeu Le jeu à associer
     * @param conditionVit Les conditions de vie
     * @param conditionMeurt Les conditions de mort
     */
    public VisiteurCustom(JeuDeLaVie jeu, boolean[] conditionVit, boolean[] conditionMeurt) {
        super(jeu);
        this.conditionMeurt = conditionMeurt;
        this.conditionVit = conditionVit;

    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de mort avec les conditions personalisées
     * @param cellule La cellule visitée
     */
    public void visiteCelluleVivante(Cellule cellule) {
        if(conditionMeurt[cellule.nombreVoisinesVivantes(jeu)]){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de vie avec les conditions personalisées
     * @param cellule La cellule visitée
     */
    public void visiteCelluleMorte(Cellule cellule) {
        if (conditionVit[cellule.nombreVoisinesVivantes(jeu)]) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
