package src.Jeu.Observation.UI.BoutonPlayPause;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 * Classe représentant l'état pause d'un bouton
 */
public class BoutonEtatPause implements BoutonEtat{
    /** Initialisation de l'instance unique de l'état pause */
    private static BoutonEtatPause instance = new BoutonEtatPause();

    /**
     * Obtient l'instance unique de l'état pause
     * @return L'instance unique de l'état pause
     */
    public static BoutonEtatPause getInstance() {
        return instance;
    }

    /** Constructeur privé */
    private BoutonEtatPause(){
        super();
    }

    /**
     * Définition du comportement lors du click sur le boiton lorsqu'il est en pause, en changeant le nom du bouton et grisant le bouton nextGen et démarrant le timer
     * @param timer Le timer de l'UI afin de le lancer
     * @param slider Le slider de l'UI pour spécifier le temps à mettre au timer
     * @param buttonPlay Le bouton Play/Pause à qui appartient l'état
     * @param buttonNext Le bouton pour générer la prochaine génération  
     * @return Le nouvel état que le bouton doit prendre : play
     */
    @Override
    public BoutonEtat press(Timer timer, JSlider slider, JButton buttonPlay, JButton buttonNext) {
        timer.setInitialDelay(1000 - 10 * slider.getValue());
        timer.setDelay(1000 - 10 * slider.getValue());
        timer.restart();
        
        buttonPlay.setText("Pause");
        buttonNext.setEnabled(false);
        return BoutonEtatPlay.getInstance();
    }

    /** 
     * Permet de savoir si l'état est en pause ou non, ici il l'est
     * @return Vrai car l'état est pause
     */
    @Override
    public boolean estPause() {
        return true;
    }



}