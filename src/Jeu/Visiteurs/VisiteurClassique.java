package src.Jeu.Visiteurs;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Cellules.Cellule;
import src.Jeu.Commandes.CommandeMeurt;
import src.Jeu.Commandes.CommandeVit;

/**
 * Visiteur définissant les règles du jeu de la vie classique
 */
@SuppressWarnings("serial")
public class VisiteurClassique extends Visiteur{

    /**
     * Constructeur du visiteur classique, qui appelle le constructeur standard
     * @param jeu Le jeu à associer
     */
	public VisiteurClassique(JeuDeLaVie jeu){
        super(jeu);
    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de mort avec les conditions du jeu de la vie
     * @param cellule La cellule visitée
     */
    public void visiteCelluleVivante(Cellule cellule) {
        long nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if( nbVoisins < 2 || nbVoisins > 3){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }      
    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de vie avec les conditions du jeu de la vie
     * @param cellule La cellule visitée
     */
    public void visiteCelluleMorte(Cellule cellule) {
        if(cellule.nombreVoisinesVivantes(jeu) == 3){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }   
    }
}
