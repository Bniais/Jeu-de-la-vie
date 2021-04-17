package src.Jeu.Observation.UI.BoutonPlayPause;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;

/**
 * Bouton qui switch entre les état play et pause et propose des comportements différents selon son état
 */
@SuppressWarnings("serial")
public class BoutonPlayPause extends JButton{
    
    /** L'état du bouton */
    private BoutonEtat etatBouton = BoutonEtatPause.getInstance();

    /** 
     * Constructeur du bouton play/pause, en spécifiant son nom, le slider de l'UI, le timer de l'UI, et le bouton qui génère la prochaine génération
     * @param timer Le timer de l'UI afin de le mettre en pause ou le lancer lors de l'appui
     * @param slider Le slider de l'UI pour spécifier le temps à mettre au timer
     * @param buttonPlay Le bouton Play/Pause à qui appartient l'état
     * @param buttonNext Le bouton pour générer la prochaine génération  
     */
    public BoutonPlayPause(String name, JSlider slider, Timer timer, JButton boutonNextGen){
        super(name);
            
        /**
         * L'action liée au click du bouton, qui appelle la méthode de son état actuel
         */
        addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                etatBouton = etatBouton.press(timer, slider, BoutonPlayPause.this, boutonNextGen);
            }
        });
    }

    /**
     * Permet de savoir si le bouton est en pause on non, en demandant à son état
     * @return True si il est en pause, sinon Faux
     */
    public boolean estPause(){
        return etatBouton.estPause();
    }
}
