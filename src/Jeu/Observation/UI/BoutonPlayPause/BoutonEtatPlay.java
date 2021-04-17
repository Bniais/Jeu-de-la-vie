package src.Jeu.Observation.UI.BoutonPlayPause;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 * Classe représentant l'état play d'un bouton
 */
public class BoutonEtatPlay implements BoutonEtat {
    /** Initialisation de l'instance unique de l'état play */
    private static BoutonEtatPlay instance = new BoutonEtatPlay();

    /**
     * Obtient l'instance unique de l'état play
     * @return L'instance unique de l'état play
     */
    public static BoutonEtatPlay getInstance() {
        return instance;
    }

    /** Constructeur privé */
    private BoutonEtatPlay(){
        super();
    }

    /**
     * Définition du comportement lors du click sur le boiton lorsqu'il est sur play, en changeant le nom du bouton et dégrisant le bouton nextGen et stoppant le timer
     * @param timer Le timer de l'UI afin de le stopper
     * @param slider Le slider de l'UI pour spécifier le temps à mettre au timer (inutile ici)
     * @param buttonPlay Le bouton Play/Pause à qui appartient l'état
     * @param buttonNext Le bouton pour générer la prochaine génération  
     * @return Le nouvel état que le bouton doit prendre : pause
     */
    @Override
    public BoutonEtat press(Timer timer, JSlider slider, JButton buttonPlay, JButton buttonNext) {
        timer.stop();
        buttonPlay.setText("Play");
        buttonNext.setEnabled(true);
        return BoutonEtatPause.getInstance();
    }

    @Override
    public boolean estPause() {
        return false;
    }
}