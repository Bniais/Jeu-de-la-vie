package src.Jeu.Observation.UI;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Classe proposant un TextField dont le nombre maximum de caractère est limité
 */
@SuppressWarnings("serial")
public class JTextFieldLimit extends JTextField {
    /** Le nombre de caractère maximum */
    private int limit;

    /**
     * Constructeur du TextField avec limite, en spécifiant la limite
     * @param limit La limite de caractère
     */
    public JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    /**
     * Crée le modèle à suivre pour le document du TextField, qui limitera les caractères
     * @return Le document créé
     */
    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    /**
     * Classe interne du limitateur de caractère
     */
    private class LimitDocument extends PlainDocument {
        /**
         * Redéfinition du comportement à l'insertion, en refusant les caractères lorsqu'on dépasse la limite
         */
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }

    }

}