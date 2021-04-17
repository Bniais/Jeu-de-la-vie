package src.Jeu;
import java.util.ArrayList;
import java.util.List;

import src.Jeu.Cellules.Cellule;
import src.Jeu.Commandes.Commande;
import src.Jeu.Fabrique.FabriqueGrille;
import src.Jeu.Fabrique.FabriqueGrilleAleatoire;
import src.Jeu.Observation.JeuDeLaVieConsole;
import src.Jeu.Observation.Observable;
import src.Jeu.Observation.Observateur;
import src.Jeu.Observation.UI.JeuDeLaVieUI;
import src.Jeu.Visiteurs.Visiteur;
import src.Jeu.Visiteurs.VisiteurClassique;

/**
 * Classe principale du jeu de la vie, qui gère les différents composants
 */
public class JeuDeLaVie implements Observable{

    /**
     * Méthode d'entrée du programme, qui crée le jeu et lui attache les observateurs
     * @param argv Non-utilisé
     */
    public static void main(String argv[]) {
        //Test version basique
        JeuDeLaVie jeu = new JeuDeLaVie(10, 10);

        jeu.attacheObservateur(new JeuDeLaVieUI(jeu));
        //jeu.attacheObservateur(new JeuDeLaVieConsole(jeu)); //Fait ralentir l'UI avec les affichages du terminal
    }

    /** La grille de jeu */
    private Cellule[][] grille;

    /** Les dimensions de la grille */
    private int xMax, yMax;

    /** La liste des observateurs du jeu */
    private List<Observateur> observateurs;

    /** La liste de commandes à executer */
    private List<Commande> commandes;

    /** Le visiteur du jeu */
    private Visiteur visiteur;

    /** Le nombre de génération */
    private int generation;

    /** La fabrique de la grille */
    private FabriqueGrille fabriqueGrille;

    /**
     * Constructeur du jeu de la vie, en spécifiant les dimenstions du jeu
     * @param xMin La largeur de la grille
     * @param yMin La hauteur de la grille
     */
    public JeuDeLaVie(int xMin, int yMin) {
        this.xMax = xMin;
        this.yMax = yMin;

        visiteur = new VisiteurClassique(this);
        fabriqueGrille = new FabriqueGrilleAleatoire(xMin, yMin, 0.5);
        grille = fabriqueGrille.fabriqueGrille();
        initialiseGrille();
        observateurs = new ArrayList<Observateur>();
        commandes = new ArrayList<Commande>();
    }

    /**
     * Initialise la grille selon la fabrique du jeu
     */
    public void initialiseGrille(){
        generation = 0;
        grille = fabriqueGrille.fabriqueGrille();
        xMax = grille.length;
        yMax = grille[0].length;   
    }

    /**
     * Modifie une cellule (lors de l'éditeur de grille)
     * @param x La coordonnée x de la cellule à modifier
     * @param y La coordonnée y de la cellule à modifier
     */
    public void modifieCellule(int x, int y){
        if(grille[x][y].estVivante()){
            grille[x][y].meurt();
        }
        else{
            grille[x][y].vit();
        }       
    }

    /**
     * Accesseur d'une cellule de la grille
     * @param x La coordonnée x de la cellule
     * @param y La coordonnée y de la cellule
     * @return La Cellule aux coordonnées demandée, ou null si elle n'existe pas
     */
    public Cellule getGrilleXY(int x, int y){
        if(x<xMax && y < yMax && x>= 0 && y>=0){
            return grille[x][y];
        }
        else{
            return null;
        }
    }

    /**
     * Attache un nouvel observateur au jeu
     * * @param o L'observateur à attacher
     */
    @Override
    public void attacheObservateur(Observateur o){
        observateurs.add(o);
    }

    /**
     * Détache un observateur du jeu
     * @param o L'observateur à détacher
     */
    @Override
    public void detacheObservater(Observateur o){
        observateurs.remove(o);
    }

    /**
     * Notifie tous les observateurs
     */
    @Override
    public void notifieObservateurs(){
        observateurs.forEach(Observateur::actualise);
    }

    /**
     * Ajoute une commande à effectuer plus tard
     * @param c La commande
     */
    public void ajouteCommande(Commande c){
        commandes.add(c);
    }

    /**
     * Execute toutes les commandes stockées et les supprime
     */
    public void executeCommandes(){
        commandes.forEach(Commande::executer);
        commandes.clear();
    }

    /**
     * Distribue le visiteur à toutes les cellules de la grille
     */
    public void distribueVisiteur(){
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                grille[x][y].accepte(visiteur);
            }
        }
    }

    /**
     * Calcule la génération suivante du jeu
     */
    public void calculerGenerationSuivante(){
        generation++;
        distribueVisiteur();
        executeCommandes();
        notifieObservateurs();
    }


    /**
     * Accesseur en lecture de la largeur
     * @return La largeur de la grille
     */
    public int getXMax(){
        return xMax;
    }

    /**
     * Accesseur en lecture de la hauteur
     * @return La hauteur de la grille
     */
    public int getYMax() {
        return yMax;
    }

    /**
     * Accesseur du nombre de génération actuelle
     * @return La hauteur de la grille
     */
    public int getGen(){
        return generation;
    }

    /** 
     * Accesseur en lecture de la grille, afin de la stocker dans une fabrique de grille personalisée
     * @return La grille de jeu
     */
    public Cellule[][] getGrille(){
        return grille;
    }

    /**
     * Met un nouveau visiteur au jeu
     * @param visiteur Le visiteur à attribuer
     */
    public void setVisiteur(Visiteur visiteur){
        this.visiteur = visiteur;
    }

    /**
     * Attribue une fabrique au jeu
     * @param f La fabrique à attribuer
     */
	public void setFabrique(FabriqueGrille f) {
        fabriqueGrille = f;
	}

}