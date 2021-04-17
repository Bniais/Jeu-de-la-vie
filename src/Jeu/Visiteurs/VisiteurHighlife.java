package src.Jeu.Visiteurs;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Cellules.Cellule;
import src.Jeu.Commandes.CommandeMeurt;
import src.Jeu.Commandes.CommandeVit;

/**
 * Visiteur définissant les règles HighLife
 */
@SuppressWarnings("serial")
public class VisiteurHighlife extends Visiteur{

    /**
     * Constructeur du visiteur HighLife, qui appelle le constructeur standard
     * @param jeu Le jeu à associer
     */
    public VisiteurHighlife(JeuDeLaVie jeu){
        super(jeu);
    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de mort avec les conditions de HighLife
     * @param cellule La cellule visitée
     */
    public void visiteCelluleVivante(Cellule cellule) {
        long nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins < 2 || nbVoisins > 3) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Définition de la visite d'une cellule vivante, qui ajoute une commande de vie avec les conditions de HighLife
     * @param cellule La cellule visitée
     */
    public void visiteCelluleMorte(Cellule cellule) {
        long nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if ( nbVoisins == 3 || nbVoisins== 6) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
    
}
