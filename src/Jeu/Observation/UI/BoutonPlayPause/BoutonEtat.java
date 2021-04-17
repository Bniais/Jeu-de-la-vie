package src.Jeu.Observation.UI.BoutonPlayPause;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 * Interface présentant les méthodes à redéfinir pour un état de bouton
 */
public interface BoutonEtat {
    /** 
     * Méthode appelée lorsque le bouton est appuyé*
     * @param timer Le timer de l'UI afin de le mettre en pause ou le lancer lors de l'appui
     * @param slider Le slider de l'UI pour spécifier le temps à mettre au timer
     * @param buttonPlay Le bouton Play/Pause à qui appartient l'état
     * @param buttonNext Le bouton pour générer la prochaine génération  
     */
    public BoutonEtat press(Timer timer, JSlider slider, JButton buttonPlay, JButton buttonNext);

    /** 
     * Permet de savoir si l'état est en pause ou non, pour ne pas faire d'instance of
     * @return Vrai si l'état est pause, sinon faux
     */
    public boolean estPause();
}
