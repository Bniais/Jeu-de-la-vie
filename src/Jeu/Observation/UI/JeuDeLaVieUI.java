package src.Jeu.Observation.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Jeu.JeuDeLaVie;
import src.Jeu.Fabrique.FabriqueGrille;
import src.Jeu.Fabrique.FabriqueGrilleAleatoire;
import src.Jeu.Fabrique.FabriqueGrilleCustom;
import src.Jeu.Observation.Observateur;
import src.Jeu.Observation.UI.BoutonPlayPause.BoutonPlayPause;
import src.Jeu.Visiteurs.Visiteur;
import src.Jeu.Visiteurs.VisiteurClassique;
import src.Jeu.Visiteurs.VisiteurCustom;
import src.Jeu.Visiteurs.VisiteurDayAndNight;
import src.Jeu.Visiteurs.VisiteurHighlife;
import src.Jeu.Visiteurs.VisiteurLife34;


/**
 * Interface graphique qui permet de controler et créer des configurations et règles de jeu de la vie
 */
@SuppressWarnings("serial")
public class JeuDeLaVieUI extends JFrame implements Observateur {

    /** Définition des direction pour plus de clarté */
    private static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;

    /** Nombre de configurations de base */
    private static final int NB_DEFAULT_CONFIG = 4;

    /** Nombre de modes de jeu de base */
    private static final int NB_DEFAULT_GAME_MODES = 4;

    /** Tableau stockant les touches qsdz actuellement appuyées pour se déplacer en diagonale */
    private boolean keyPressed[];

    /** Le jeu de la vie lié */
    private JeuDeLaVie jeu;
    
    
    /* Divers widgets graphiques */
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private PanelJeu panelJeu;

    private JButton btnSave, btnDeleteConfig, btnDeleteGameMode;
    private JTextField xField, yField;
    private JSlider sliderDensite;
    private JTextField nameField;
    private JCheckBox[] checkBoxsBorn;
    private JCheckBox[] checkBoxsDie;

    private List<GameMode> gameModes;
    private JComboBox<GameMode> comboGameMode;
    private GameMode lastGameMode;

    private List<Config> configs;
    private JComboBox<Config> comboConfig;
    private Config lastConfig;

    private JSlider slider;
    private Timer timer;

    private BoutonPlayPause boutonPlay;


    /**
     * Constructeur de l'interface graphique, en spécifiant le jeu de la vie
     * @param jeu Le jeu de la vie
     */
    public JeuDeLaVieUI(JeuDeLaVie jeu) {
        //Configuration de la fenêtre
        super("Le jeu de la vie");
        setSize(750, 750);
        setMinimumSize(new Dimension(750, 750));
        setResizable(true);

        //Centrer la fenetre
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Initialisation des attributs
        keyPressed = new boolean[]{false, false, false, false};
        checkBoxsBorn = new JCheckBox[9];
        checkBoxsDie = new JCheckBox[9];
        this.jeu = jeu;
        slider = new JSlider();
        panelJeu = new PanelJeu(jeu);
        menuBar = new JMenuBar();


        //Création de l'interface
        createMenuBar(menuBar);
        setJMenuBar(menuBar);
        mainPanel = new JPanel(); 
        buildGamePanel(mainPanel);

        add(mainPanel);
        setVisible(true);


        //Listeners

        addWindowListener(new WindowListener() { 
            @Override
            public void windowOpened(WindowEvent e) {   
            }

            // Sauvegarder quand la fenetre se quitte
            @Override
            public void windowClosing(WindowEvent e) {
                File fichierMode = new File("Sauvegardes/gameMode.save");

                ObjectOutputStream oos;

                try {
                    oos = new ObjectOutputStream(new FileOutputStream(fichierMode));
                    oos.writeObject(gameModes);
                } catch (IOException e1) {
                    System.out.println("catch save mode");
                    e1.printStackTrace();
                }

                File fichierConfig = new File("Sauvegardes/config.save");
                try {
                    oos = new ObjectOutputStream(new FileOutputStream(fichierConfig));
                    oos.writeObject(configs);
                } catch (IOException e1) {
                    System.out.println("catch save config");
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) { 
            }

            @Override
            public void windowDeactivated(WindowEvent e) {  
            }
        });

        addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            //Avertir le panel de jeu lors des clicks
            @Override
            public void mousePressed(MouseEvent e) {          
                if(panelJeu.editGrid(SwingUtilities.convertPoint(JeuDeLaVieUI.this, e.getX(), e.getY(), panelJeu)))
                    actualise();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
   
            }

            @Override
            public void mouseEntered(MouseEvent e) {
 
            }

            @Override
            public void mouseExited(MouseEvent e) {
 
            }
            
        });

        //Zoomer lors des mouvements de molette
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                panelJeu.zoom(- e.getWheelRotation());
                actualise();
            }
        });

        //Se déplacer lors d'appuie sur qsdz
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_Q)
                    keyPressed[LEFT] = (e.getID() == KeyEvent.KEY_PRESSED);

                if(e.getKeyCode() == KeyEvent.VK_D)
                    keyPressed[RIGHT] = (e.getID() == KeyEvent.KEY_PRESSED );

                if(e.getKeyCode() == KeyEvent.VK_Z)
                    keyPressed[UP] = (e.getID() == KeyEvent.KEY_PRESSED );
                
                if(e.getKeyCode() == KeyEvent.VK_S)
                    keyPressed[DOWN] = (e.getID() == KeyEvent.KEY_PRESSED );
                

                if(keyPressed[LEFT])
                    panelJeu.moveOffset(-1, 0);

                if(keyPressed[RIGHT])
                    panelJeu.moveOffset(1, 0);

                if(keyPressed[UP])
                    panelJeu.moveOffset(0, -1);

                if(keyPressed[DOWN])
                    panelJeu.moveOffset(0, 1);

                panelJeu.repaint();
                return false;
            }
        });
    }

    /**
     * Crée la barre de menu principal
     * @param menuBar La barre de menu à modifier
     */
    private void createMenuBar(JMenuBar menuBar) {
        //Timer play/pause
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jeu.calculerGenerationSuivante();
            }
        };

        timer = new javax.swing.Timer(5000, taskPerformer);
        timer.stop();


        JButton boutonNextGen = new JButton("Next gen");
        JButton btnRestart = new JButton("Replay");
        
        boutonPlay = new BoutonPlayPause("Play", slider, timer, boutonNextGen);
        
        if (btnDeleteConfig == null)
            btnDeleteConfig = new JButton("Del config");

        if (btnDeleteGameMode == null)
            btnDeleteGameMode = new JButton("Del gameMode");
        

        //Listeners
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!boutonPlay.estPause()) {
                    timer.setInitialDelay(1000 - 10 * slider.getValue());
                    timer.setDelay(1000 - 10 * slider.getValue());
                }
            }

        });

        boutonNextGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.calculerGenerationSuivante();
            }
        });

        btnDeleteConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteConfig();
            }
        });

        btnDeleteGameMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGameMode();
            }
        });

        btnRestart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!boutonPlay.estPause())
                    boutonPlay.doClick();

                jeu.initialiseGrille();
                actualise();
            }
        });

        //Add elements
        menuBar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        menuBar.removeAll();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.6;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 5, 0, 5);
        menuBar.add(createConfigCombo(), c);

        c.gridx = 2;
        menuBar.add(createGameModeCombo(), c);

        c.gridx = 4;
        c.weightx = 0.2;
        c.gridwidth = 1;
        menuBar.add(btnRestart, c);

        c.gridx = 5;
        menuBar.add(boutonPlay, c);

        c.gridx = 6;
        menuBar.add(boutonNextGen, c);

        c.gridx = 7;
        menuBar.add(btnDeleteConfig, c);

        c.gridx = 8;
        menuBar.add(btnDeleteGameMode, c);

        revalidate();
        repaint();
    }

    /**
     * Construit le panel de jeu
     * @param panel Le panel à modifier
     */
    private void buildGamePanel(JPanel panel) {
        panelJeu.setEdit(false);
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        panel.add(buildSlider(), BorderLayout.NORTH);
        panel.add(panelJeu, BorderLayout.CENTER);
        panelJeu.resetNavigation();

        revalidate();
        repaint();
    }

    /**
     * Construit le slider de vitesse d'execution
     * @return Le panel comprenant le slider
     */
    private JPanel buildSlider() {
        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new GridBagLayout());
        panelSlider.setBorder(BorderFactory.createEmptyBorder(5, 0, 3, 0));
        GridBagConstraints d = new GridBagConstraints();

        d.fill = GridBagConstraints.HORIZONTAL;
        d.gridx = 0;
        d.weightx = 1;
        panelSlider.add(new JLabel(""), d);

        d.gridx = 1;
        d.weightx = 0;
        panelSlider.add(new JLabel("Speed : "), d);

        d.gridx = 2;
        d.weightx = 2;
        panelSlider.add(slider, d);

        d.gridx = 3;
        d.weightx = 1;
        panelSlider.add(new JLabel(""), d);

        return panelSlider;
    }

    /**
     * Construit le comboBox de selection de mode de jeu
     * @return Le  comboBox de selection de mode de jeu
     */
    private JComboBox<GameMode> createGameModeCombo(){
        if(gameModes == null){ //Une seule fois

            
            File fichier = new File("Sauvegardes/gameMode.save");
            ObjectInputStream ois;

            try {// Charger la sauvegarde
                ois = new ObjectInputStream(new FileInputStream(fichier));
                gameModes = (ArrayList<GameMode>) ois.readObject();
                gameModes.forEach(mode -> mode.setJeu(jeu));
            } catch (Exception e) {// Faire combo par défaut
                System.out.println("Cant load : ");
                e.printStackTrace();

                gameModes = new ArrayList<GameMode>(
                        Arrays.asList(new GameMode("Classic", new VisiteurClassique(jeu)),
                                new GameMode("Highlife", new VisiteurHighlife(jeu)),
                                new GameMode("Day&Night", new VisiteurDayAndNight(jeu)),
                                new GameMode("Life 3-4", new VisiteurLife34(jeu)), new GameMode("Create", null)));
            }

            comboGameMode = new JComboBox<GameMode>(gameModes.toArray(new GameMode[0]));
            comboGameMode.setFocusable(false);
            btnDeleteGameMode.setEnabled(false);
            lastGameMode = (GameMode)comboGameMode.getSelectedItem();
            addListenerGameMode(comboGameMode);
        }
    
        
        return comboGameMode;
    }

    /**
     * Ajoute un mode de jeu au comboBox
     * @param mode Le mode de jeu à ajouter
     */
    private void addGameMode(GameMode mode){
        comboGameMode.insertItemAt(mode, gameModes.size() - 1);   
        comboGameMode.setSelectedIndex(gameModes.size() - 1);
        lastGameMode = (GameMode)comboGameMode.getSelectedItem();

        gameModes.add(gameModes.size() - 1, mode);
    }

    /**
     * Supprime le mode de jeu actuel de la combobox de modes de jeu
     */
    private void deleteGameMode() {
        if (comboGameMode.getSelectedIndex() >= NB_DEFAULT_GAME_MODES) {
            Object gameMode = comboGameMode.getSelectedItem();
            comboGameMode.removeItem(gameMode);
            comboGameMode.setSelectedIndex(0);
            lastGameMode = (GameMode) comboGameMode.getSelectedItem();

            gameModes.remove(gameMode);
        }
    }

    /**
     * Ajouter le listener au comboBox de modes de jeu
     * @param combo Le comboBox à qui ajoute un listener
     */
    private void addListenerGameMode(JComboBox<GameMode> combo){
        combo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Visiteur v = ((GameMode)combo.getSelectedItem()).getVisiteur();
                
                if(v == null){//créer new visitor
                    if(!boutonPlay.estPause()){
                        boutonPlay.doClick();
                    }
                 
                    buildGameModeMenuBar(menuBar);     
                    buildGameModePanel(mainPanel);
                }
                else{
                    jeu.setVisiteur(v);
                    if (combo.getSelectedIndex() < NB_DEFAULT_GAME_MODES) {
                        btnDeleteGameMode.setEnabled(false);
                    } else {
                        btnDeleteGameMode.setEnabled(true);
                    }
                }
            }
        });
    }


    /**
     * Crée la comboBox de configuration de jeu
     * @return La comboBox de configuration de jeu
     */
    private JComboBox<Config> createConfigCombo() {
        if(configs == null){
            File fichier = new File("Sauvegardes/config.save");

            ObjectInputStream ois;
            try { //Charger la sauvegarder
                ois = new ObjectInputStream(new FileInputStream(fichier));
                configs = (ArrayList<Config>) ois.readObject();
            } catch (Exception e) {//Sinon faire un comboBox par défaut

                configs = new ArrayList<Config>(
                        Arrays.asList(new Config("10x10", new FabriqueGrilleAleatoire(10, 10, 0.5)),
                                new Config("50x50", new FabriqueGrilleAleatoire(50, 50, 0.5)),
                                new Config("Dense", new FabriqueGrilleAleatoire(25, 25, 0.85)),
                                new Config("Glider", new FabriqueGrilleCustom(FabriqueGrilleCustom.GRILLE_PLANEUR)),
                                new Config("Create", null)));     
            }
            comboConfig = new JComboBox<Config>(configs.toArray(new Config[0]));
            comboConfig.setFocusable(false);
            btnDeleteConfig.setEnabled(false);
            lastConfig = (Config)comboConfig.getSelectedItem();
            addListenerConfig(comboConfig);
        }
        
        return comboConfig;
    }

    /**
     * Ajoute le listener au comboBox de configuration
     * @param combo Le comboBox à qui ajouter le listener
     */
    private void addListenerConfig(JComboBox<Config> combo) {
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FabriqueGrille f = ((Config) combo.getSelectedItem()).getFabrique();

                if (f == null) {// créer new visitor
                    if (!boutonPlay.estPause()) {
                        boutonPlay.doClick();
                    }

                    buildConfigMenuBar(menuBar);
                    buildAleatoirePanel(mainPanel);
                } 
                else {
                    lastConfig = (Config)combo.getSelectedItem();
                    jeu.setFabrique(f);
                    jeu.initialiseGrille();
                    actualise();

                    if (!boutonPlay.estPause()) {
                        boutonPlay.doClick();
                    }

                    if(combo.getSelectedIndex() < NB_DEFAULT_CONFIG){
                        btnDeleteConfig.setEnabled(false);
                    }
                    else{
                        btnDeleteConfig.setEnabled(true);
                    }
                }
            }
        });
    }

    /**
     * Ajoute une configuration à la comboBox de configurations
     * @param config La configuration de jeu à ajouter
     */
    private void addConfig(Config config) {
        comboConfig.insertItemAt(config, configs.size() - 1);
        comboConfig.setSelectedIndex(configs.size() - 1);
        lastConfig = (Config) comboConfig.getSelectedItem();

        configs.add(configs.size() - 1, config);
    }

    /**
     * Supprime la configuration actuelle de la comboBox de configurations
     */
    private void deleteConfig() {
        if(comboConfig.getSelectedIndex() >= NB_DEFAULT_CONFIG){
            Object config = (Object) comboConfig.getSelectedItem();

            comboConfig.removeItem(config);
            comboConfig.setSelectedIndex(0);
            lastConfig = (Config) comboConfig.getSelectedItem();

            configs.remove(config);
        }      
    }

    /**
     * Construit la barre de menu de l'écran de création de configuration
     * @param menu La barre de mennu à modifier
     */
    private void buildConfigMenuBar(JMenuBar menu){
        menu.removeAll();
        menu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        
        
        String[] modes = {
                    "Random",
                    "Builder"};
        
        JComboBox<String> combo = new JComboBox<String>(modes); 

        btnSave = new JButton("   Save   ");
        btnSave.setEnabled(false);

        nameField = new JTextFieldLimit(10);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 5, 0, 5);
        c.weightx = 0.5;
        menu.add(combo, c);

        c.gridx = 1;
        c.weightx = 0.2;
        menu.add(new JLabel(""), c);

        c.gridx = 2;
        c.weightx = 0;
        menu.add(new JLabel("Name :"), c);

        c.gridx = 3;
        c.weightx = 1.0;
        menu.add(nameField, c);

        c.gridx = 4;
        c.weightx = 0.2;
        menu.add(new JLabel(""), c);

        c.gridx = 5;
        c.weightx = 0.5;
        menu.add(btnSave, c);
        


        //Listeners
        btnSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(combo.getSelectedIndex() == 0){
                    addConfig(new Config(nameField.getText(),
                            new FabriqueGrilleAleatoire(Integer.parseInt(xField.getText()),
                                    Integer.parseInt(yField.getText()), sliderDensite.getValue() / 100.)));

                    createMenuBar(menuBar);
                    buildGamePanel(mainPanel);
                }
                else{
                    addConfig(new Config(nameField.getText(),
                            new FabriqueGrilleCustom(jeu.getGrille())));

                    createMenuBar(menuBar);
                    buildGamePanel(mainPanel);
                }       
            }
        });

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(combo.getSelectedItem().toString().equals(modes[0])){
                    buildAleatoirePanel(mainPanel);
                }
                else{
                    buildBuilderPanel(mainPanel);
                }
            }

        });

        revalidate();
        repaint();
    }

    /**
     * Construit le Panel de l'écran de création de configurations aléatoires
     * @param panel Le panel à modifier
     */
    private void buildAleatoirePanel(JPanel panel){

        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        
        
        //LIGNE 1
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;

        c.insets = new Insets(25, 0, 5, 0);
        panel.add(new JLabel("Width :"), c);

        c.gridx = 1;
        xField = new JTextField();
        panel.add(xField ,c);

        

        //LIGNE 2
        c.insets = new Insets(5, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Height :  "), c);

        c.gridx = 1;
        yField = new JTextField();
        panel.add(yField ,c);
        
        // LIGNE 3
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Density :  "), c);

        
        
        sliderDensite = new JSlider();
        sliderDensite.setPaintTicks(true); 
        sliderDensite.setPaintLabels(true);
        sliderDensite.setMajorTickSpacing(25);
        sliderDensite.setMinorTickSpacing(5);
        sliderDensite.createStandardLabels(25, 0);

        c.gridx = 1;
        panel.add(sliderDensite ,c);

        //LIGNE 4 (anchor)

        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 100.0;
        panel.add(new JLabel(""), c);


        JPanel pRetour = new JPanel();
        pRetour.setLayout(new FlowLayout(FlowLayout.CENTER));
        

        //LIGNE 5
        c.gridx = 0;
        c.gridy = 4; 
        c.weighty = 0.1;
        c.gridwidth = 2;
        panel.add(pRetour, c);

        JButton retourButton = new JButton("      Back      ");
        pRetour.add(retourButton);
        

        //Listeners
        class FieldListener implements DocumentListener{
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    int x = Integer.parseInt(xField.getText(), 10);
                    int y = Integer.parseInt(yField.getText(), 10);

                    if(x<=0 || y<=0){
                        throw new Exception();  
                    }
                        

                    if (nameField.getText().length() > 0) {
                        for (int i = 0; i < comboConfig.getItemCount(); i++) {
                            if (comboConfig.getItemAt(i).toString().equals(nameField.getText())) {
                                btnSave.setEnabled(false);
                                return;
                            }
                        }
                        btnSave.setEnabled(true);
                    }
                    else{
                        btnSave.setEnabled(false);
                    }
                    
                } catch (Exception ex) {
                    
                    btnSave.setEnabled(false);
                }
            }
        }

        xField.getDocument().addDocumentListener(new FieldListener());
        yField.getDocument().addDocumentListener(new FieldListener());

        retourButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createMenuBar(menuBar);
                buildGamePanel(mainPanel);
                comboConfig.setSelectedItem(lastConfig);            
            }

        });

        nameField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (nameField.getText().length() > 0) {
                    for (int i = 0; i < comboConfig.getItemCount(); i++) {
                        if (comboConfig.getItemAt(i).toString().equals(nameField.getText())) {
                            btnSave.setEnabled(false);
                            return;
                        }
                    }
                    if(comboConfig.getSelectedIndex()==0){
                        try {
                            int x = Integer.parseInt(xField.getText(), 10);
                            int y = Integer.parseInt(yField.getText(), 10);

                            if (x <= 0 || y <= 0) {
                                throw new Exception();
                            }
                            btnSave.setEnabled(true);
                        } catch (Exception ex) {
                            btnSave.setEnabled(false);
                        }
                    }
                    else{
                        btnSave.setEnabled(true);
                    }
                        
                } 
                else {
                    btnSave.setEnabled(false);
                }
            }

        });
        

        revalidate();
        repaint();
    }


    /**
     * Construit le panel de l'éditeur de grille
     * @param panel Le panel à modifier
     */
    private void buildBuilderPanel(JPanel panel){
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        
        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        

       
        JLabel labelWidth = new JLabel(" Width : 10");
          
        JSlider sliderWidth = new JSlider();
        sliderWidth.setValue(9);


        JLabel labelHeight = new JLabel("Height : 10");
    
        JSlider sliderHeight = new JSlider();
        sliderHeight.setValue(9);


        //LIGNE 1
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        slidersPanel.add(new JLabel(""), c);
  
        c.gridx = 1;
        c.weightx = 0;
        slidersPanel.add(labelWidth, c);

        c.gridx = 2;
        c.weightx = 2;
        slidersPanel.add(sliderWidth, c);

        c.gridx = 3;
        c.weightx = 3;
        slidersPanel.add(new JLabel(""), c);

        //LIGNE 2
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 3;
        slidersPanel.add(new JLabel(""), c);

        c.gridx = 1;
        c.weightx = 0;
        slidersPanel.add(labelHeight, c);

        c.gridx = 2;
        c.weightx = 2;
        slidersPanel.add(sliderHeight, c);

        c.gridx = 3;
        c.weightx = 3;
        slidersPanel.add(new JLabel(""), c);


        JPanel pRetour = new JPanel();
        pRetour.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton retourButton = new JButton("      Back      ");
        pRetour.add(retourButton);
        pRetour.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
    


        panel.add(slidersPanel, BorderLayout.NORTH);

        panelJeu.setEdit(true);
        panel.add(panelJeu, BorderLayout.CENTER);
        panel.add(pRetour, BorderLayout.SOUTH);


        sliderWidth.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                int width = sliderWidth.getValue() + 1;
                if(width > 100)
                    width = 100;

                int height = sliderHeight.getValue() + 1;
                if(height > 100)
                    height = 100;

                labelWidth.setText("Width : " + String.format("%3d", width));
                jeu.setFabrique( new FabriqueGrilleAleatoire(width, height, 0));
                jeu.initialiseGrille();
                actualise();
            }
        });

        sliderHeight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int width = sliderWidth.getValue() + 1;
                if(width > 100)
                    width = 100;

                int height = sliderHeight.getValue() + 1;
                if(height > 100)
                    height = 100;

                labelHeight.setText("Height : " + String.format("%3d", height));
                jeu.setFabrique( new FabriqueGrilleAleatoire(width, height, 0));
                jeu.initialiseGrille();
                actualise();
            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMenuBar(menuBar);
                buildGamePanel(mainPanel);
                comboConfig.setSelectedItem(lastConfig);
            }

        });

        jeu.setFabrique(new FabriqueGrilleAleatoire(10, 10, 0));
        jeu.initialiseGrille();
        
        actualise();
        revalidate();
        repaint();
    }

    /**
     * Construit la barre de menu de la création de mode de jeu
     * @param menu La barre de menu à modifier
     */
    private void buildGameModeMenuBar(JMenuBar menu){
        menu.removeAll();
        menu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        btnSave = new JButton("   Save   ");
        btnSave.setEnabled(false);

        nameField = new JTextFieldLimit(10);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 5, 0, 5);
        c.weightx = 0.5;
        menu.add(new JLabel(""), c);

        c.gridx = 1;
        c.weightx = 0.2;
        menu.add(new JLabel(""), c);

        c.gridx = 2;
        c.weightx = 0;
        menu.add(new JLabel("Name :"), c);

        c.gridx = 3;
        c.weightx = 1.0;
        menu.add(nameField, c);

        c.gridx = 4;
        c.weightx = 0.2;
        menu.add(new JLabel(""), c);

        c.gridx = 5;
        c.weightx = 0.5;
        menu.add(btnSave, c);

        // Listeners
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean[] conditionBorn = new boolean[9];
                boolean[] conditionDie = new boolean[9];
                for(int i=0; i <= 8; i++){
                    conditionBorn[i] = checkBoxsBorn[i].isSelected();
                    conditionDie[i] = checkBoxsDie[i].isSelected();
                }

                addGameMode( new GameMode(nameField.getText(), new VisiteurCustom(jeu, conditionBorn
                                                                                     , conditionDie)));
                createMenuBar(menuBar);
                buildGamePanel(mainPanel);
            }
        });

        nameField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e); 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
                if(nameField.getText().length() > 0){
                    for( int i = 0; i<comboGameMode.getItemCount(); i++){
                        if(comboGameMode.getItemAt(i).toString().equals(nameField.getText())){
                            btnSave.setEnabled(false);
                            return;
                        } 
                    }
                    btnSave.setEnabled(true);
                }
                else{
                    btnSave.setEnabled(false);
                }            
            }

        });

        revalidate();
        repaint();
    }

    /**
     * Construit le panel de création de mode de jeu
     * @param panel Le panel à modifier
     */
    private void buildGameModePanel(JPanel panel){
        panel.removeAll();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        // LIGNE 1
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(35, 5, 10, 5);
        c.gridx = 0;
        c.gridy = 0;

        panel.add(new JLabel("Number of neighbors to born : "), c);
        c.insets = new Insets(32, 5, 10, 5);
        for(int i=0; i<=8; i++){
            c.gridx = i+1;
            checkBoxsBorn[i]=new JCheckBox(Integer.toString(i));
            panel.add(checkBoxsBorn[i], c);
        }
        // LIGNE 2
        c.gridy = 1;
        c.gridx = 0;
        c.insets = new Insets(20, 5, 10, 5);
        panel.add(new JLabel("Number of neighbors to die : "), c);
        c.insets = new Insets(17, 5, 10, 5);
        for (int i = 0; i <= 8; i++) {
            c.gridx = i+1;
            checkBoxsDie[i]=new JCheckBox(Integer.toString(i));
            panel.add(checkBoxsDie[i], c);
        }

        // LIGNE 3 (anchor)

        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 100.0;
        panel.add(new JLabel(""), c);

        JPanel pRetour = new JPanel();
        pRetour.setLayout(new FlowLayout(FlowLayout.CENTER));

        // LIGNE 4
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 0;
        c.gridwidth = 10;
        panel.add(pRetour, c);

        JButton retourButton = new JButton("      Back      ");
        pRetour.add(retourButton);
        

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMenuBar(menuBar);
                buildGamePanel(mainPanel);
                comboGameMode.setSelectedItem(lastGameMode);
            }
        });

        revalidate();
        repaint();
    }


    @Override
    /** Réaction au messages de l'observable, en redéssinant l'écran de jeu */
    public void actualise(){
        panelJeu.repaint();
    }
}

/**
 * Classe interne représentant un mode de jeu, sauvegardable
 */
@SuppressWarnings("serial")
class GameMode implements Serializable {
    /** Le nom du mode de jeu */
    private String name;

    /** Le visiteur associé */
    private Visiteur visiteur;

    /**
     * Constructeur d'un mode de jeu, en spécifiant le nom et le visiteur
     * 
     * @param name     Le nom du mode de jeu
     * @param visiteur Le visiteur du mode de jeu
     */
    public GameMode(String name, Visiteur visiteur) {
        this.name = name;
        this.visiteur = visiteur;
    }

    /**
     * Accesseur du visiteur
     * 
     * @return Le visiteur lié au mode de jeu
     */
    public Visiteur getVisiteur() {
        return visiteur;
    }

    /**
     * Associe un jeu au mode de jeu lors du chargement du mode de jeu sauvegardé
     */
    public void setJeu(JeuDeLaVie jeu) {
        if (visiteur != null)
            visiteur.setJeu(jeu);
    }

    /**
     * Définit comment afficher le mode de jeu : avec son nom
     * 
     * @return Le nom du Mode de jeu
     */
    @Override
    public String toString() {
        return name;
    }
}

/**
 * Classe interne représentant une configuration de jeu, sauvegardable
 */
@SuppressWarnings("serial")
class Config implements Serializable {
    /** Le nom de la configuration */
    private String name;

    /** La fabrique liée */
    private FabriqueGrille fabrique;

    /**
     * Constructeur d'une configuration, en spécifiant le nom et la fabrique de
     * grille
     * 
     * @param name     Le nom de la configuration
     * @param fabrique La fabrique de grille
     */
    public Config(String name, FabriqueGrille fabrique) {
        this.name = name;
        this.fabrique = fabrique;
        System.out.println(name + fabrique);
    }

    /**
     * Accesseur en lecture de la fabrique associée
     * 
     * @return La fabrique associée à la configuration
     */
    public FabriqueGrille getFabrique() {
        return fabrique;
    }

    /**
     * Définit comment afficher la configuration : avec son nom
     * 
     * @return Le nom de la configuration
     */
    @Override
    public String toString() {
        return name;
    }
}