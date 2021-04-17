package src.Jeu.Cellules;

import java.io.Serializable;
import java.util.stream.Stream;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Visiteurs.Visiteur;


/**
 * Représente une cellule d'une grille, qui contient un état de cellule. 
 * Cette classe est sérialisable afin de sauvegarder les FabriqueGrilleCustom.
 * @author Hugo Lecomte
 */
@SuppressWarnings("serial")
public class Cellule implements Serializable{
    /** Les positions en x et y */
    private int x, y;
    
    /** L'état de la cellule */
    private CelluleEtat etat;

    /**
     * Constructeur d'une Cellule, en spécifiant sa position et son état
     * @param x La position en x
     * @param y La position en y
     * @param etat L'état de la cellule
     */
    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    /**
     * Définit comment cloner une cellule. Utile pour le FabriqueGrilleCustom
     */
    @Override
    public Object clone() {
        return new Cellule(x, y, etat);
    }

    /**
     * Compte le nombre de voisins vivants dans les 8 directions autour de la cellule
     * @param jeu Le jeu de la vie pour acceder aux cases adjacentes
     * @return Le nombre de voisins vivants autour de la cellule
     */
    public int nombreVoisinesVivantes(JeuDeLaVie jeu){ 
        return (int)Stream.of(jeu.getGrilleXY(x+1, y),    jeu.getGrilleXY(x, y+1),   jeu.getGrilleXY(x-1, y),   jeu.getGrilleXY(x, y-1), 
                              jeu.getGrilleXY(x+1, y+1),  jeu.getGrilleXY(x-1, y+1), jeu.getGrilleXY(x-1, y-1), jeu.getGrilleXY(x+1, y-1))
        .filter(c-> c != null)
        .filter(Cellule::estVivante)
        .count();

        /* sinon : 
        nbVoisins = 0;
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if( i||j ){
                    Cellule c = jeu.getGrilleXY(x+1, y)
                    if(c != null && c.estVivante()){
                        nbVoisins++;
                    }  
                }
            }  
        }
        return nbVoisins;
        */
    }

    /**
     * Rend la cellule vivante
     */
    public void vit(){
        etat = etat.vit();
    }

    /**
     * Rend la cellule morte
     */
    public void meurt(){
        etat = etat.meurt();
    }

    /**
     * Permet d'acceder à l'état de vie d'une cellule
     * @return Vrai si la cellule est vivante, sinon faux
     */
    public boolean estVivante(){
        return etat.estVivante();
    }

    /**
     * Redéfinition de toString afin d'afficher une cellule (et donc la grille) dans le terminal
     */
    @Override
    public String toString(){
        return estVivante() ? "#" : " ";
    }
        
    /**
     * Accepte un visiteur
     * @param v Le visiteur à accepter
     */
    public void accepte(Visiteur v){
        etat.accepte(v, this);
    }
}
