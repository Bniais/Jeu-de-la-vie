package src.Jeu.Observation.UI;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import src.Jeu.JeuDeLaVie;

/**
 * Classe représentant le panel où sera affiché le jeu de la vie
 */
@SuppressWarnings("serial")
public class PanelJeu extends JPanel {

    /** Les valeur entière de zoom minimale et maximale */
    private static final int MIN_ZOOM = 0, MAX_ZOOM = 100;

    /** La valeur entière initiale du zoom */
    private static final int ZOOM_INIT = 37;

    /** L'offset par défaut du jeu, qui permet de séparé l'affichage des bords du panel */
    private static final int BASE_OFFSET = 4;

    /** La vitesse de déplacement lors de la navigation avec sqdz */
    private static final int MOVE_SPEED = 8;

    /** Variable qui indique si le jeu se fait modifier par l'éditeur de grille */
    private boolean editing;

    /** Le jeu de la vie lié */
    private JeuDeLaVie jeu;

    /** La valeur entière actuelle du zoom */
    private int zoomInt;

    /** Son correspondant en double, qui représente le ratio à ajouter à la taille des éléments à dessiner */
    private double zoom;

    /** L'offset dû aux déplacements de caméra de l'utilisateur */
    private double userOffserX, userOffserY;

    
    /**
     * Constructeur du Panel de jeu, en spécifiant le jeu de la vie lié
     * @param jeu Le jeu de la vie lié
     */
    public PanelJeu(JeuDeLaVie jeu) {
        this.jeu = jeu;
        setLayout(new FlowLayout());
        resetNavigation();
        editing = false;
    }

    /**
     * Accesseur en lecture de l'edition
     * @param edit La variable qui indique si le jeu est en mode éditeur
     */
    public void setEdit(boolean edit){
        editing = edit;
    }

    /** 
     * Remet le zoom et les offset à 0 pour center le jeu
     */
    public void resetNavigation(){
        zoomInt = ZOOM_INIT;
        zoom = 0.1 * Math.exp(0.062146 * zoomInt);
        userOffserX = 0;
        userOffserY = 0;
    }

    /**
     * Méhode qui réagit aux touches sqdz pour faire déplacer l'offset et donc la caméra
     * @param moveX Le déplacement en x à faire
     * @param moveY Le déplacement en y à faire
     */
    public void moveOffset(int moveX, int moveY){
        userOffserX -= moveX * MOVE_SPEED * 1/zoom;
        userOffserY -= moveY * MOVE_SPEED * 1/zoom;
    }

    /**
     * Zoom ou dézoome la caméra
     * @param zoomAmmount La valeur positive ou négative qui indique à quel point on doit zoomer ou dézoomer
     */
    public void zoom(int zoomAmmount){
        zoomInt += zoomAmmount;
        
        if (zoomInt < MIN_ZOOM)
            zoomInt = MIN_ZOOM;
        else if (zoomInt > MAX_ZOOM)
            zoomInt = MAX_ZOOM;

        /*
            Formule :

            F0 = A * Exp(B * x0)
            F1 = A * Exp(B * x1)

            Exp(B * (x1 -x0) = F1 / F0
            B * (x1 -x0) = ln(F1 / F0)

            B = ln(F1 / F0) / (x1 - x0)
            A = F0 * Exp(-B * x0)
        
            x0=0, x1=100 
            zoom0 = 0.2,  zoom1=100
            B = ln(500) / 100 = 0.062146
            A = 0.2 * Exp(0) = 0.2 
            zoom(i) = 0.2 * Exp(0.062146 * i)
         */

        zoom = 0.1 * Math.exp(0.062146 * zoomInt);
    }

    /**
     * Modifie la grille de jeu en réaction à un click lors du mode editing
     * @param mousePoint La position de la souris relative au panel
     * @return Vrai si l'utilisateur à cliqué sur une case pendant le mode edit, sinon Faux
     */
    public boolean editGrid(Point mousePoint){
        if (editing) {
            int xMax = jeu.getXMax();
            int yMax = jeu.getYMax();
            int width = getSize().width - BASE_OFFSET * 2;
            int height = getSize().height - BASE_OFFSET * 2;
            double size = zoom * Math.min((width / xMax), (height / yMax));
            double offsetX = userOffserX * zoom + BASE_OFFSET + (width - size * xMax) / 2;
            double offsetY = userOffserY * zoom + BASE_OFFSET + (height - size * yMax) / 2;

            int cellX=-1, cellY=-1;
            for (int y = 0; y <= yMax; y++) {
                if( mousePoint.y < (int) (y * size + offsetY) ){
                    cellY = y - 1;
                    break;
                }
            }
            for (int x = 0; x <= xMax; x++) {
                if( mousePoint.x < (int) (x * size + offsetX) ){
                    cellX = x - 1;
                    break;
                }
            }

            if(cellX != -1 && cellY != -1){
                jeu.modifieCellule(cellX, cellY);
                return true;
            }
            
        }
        return false;
    }

    /**
     * Définit comment pendre le panel afin d'afficher la grille de jeu de la vie, le contour et la grille d'editing
     * @param g Le composant graphique pour peindre
     */
    @Override
    public void paintComponent(Graphics g) {   
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        //Calculer variables pour simplifier les calculs de coordonnées
        int xMax = jeu.getXMax();
        int yMax = jeu.getYMax();
        int width = getSize().width - BASE_OFFSET*2;
        int height = getSize().height - BASE_OFFSET * 2;

        double size = zoom * Math.min((width / xMax), (height / yMax));
        double offsetX = userOffserX*zoom + BASE_OFFSET + (width - size * xMax) / 2;
        double offsetY = userOffserY*zoom + BASE_OFFSET + (height - size * yMax) / 2;
        
        //Ligne de séparateur du haut
        g.drawLine(0, 0 ,getSize().width, 0 );

        //Rectangle qui délimite la grille de jeu
        g.drawRoundRect((int)offsetX- BASE_OFFSET/2, (int)offsetY - BASE_OFFSET/2, (int)(size * xMax)+ BASE_OFFSET, (int)(size * yMax)+ BASE_OFFSET, 10, 10);

        //Quadrillage du mode editing
        if(editing){
            for (int x = 1; x < xMax; x++){
                g.drawLine((int)(x * size + offsetX), (int)offsetY - BASE_OFFSET/2, (int)(x * size + offsetX), (int)offsetY - BASE_OFFSET/2 + (int)(size * yMax)+ BASE_OFFSET);
            }
            for (int y = 1; y < yMax; y++) {
                g.drawLine((int)offsetX- BASE_OFFSET/2, (int)(y * size + offsetY), (int)offsetX- BASE_OFFSET/2 + (int)(size * xMax)+ BASE_OFFSET, (int)(y * size + offsetY));
            }
        }        
        
        //Cellules du jeu
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                if (jeu.getGrilleXY(x, y).estVivante()){
                    g.fillOval((int)(x * size + offsetX) , (int)(y * size + offsetY), (int)size, (int)size );
                }
            }
        } 
    }
}