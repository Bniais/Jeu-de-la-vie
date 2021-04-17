package src.Jeu.Observation;

/** 
 * Interface définissant les méthodes d'un objet observable 
 */
public interface Observable {
    /** 
     * Attache un nouvel observateur 
     */
    public void attacheObservateur(Observateur o);

    /**
     * Détache un observateur
     */
    public void detacheObservater(Observateur o);

    /**
     * Notifie tous les observateurs
     */
    public void notifieObservateurs();
}
