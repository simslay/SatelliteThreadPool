package com.example.satellitethreadpool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.Label;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private ListView lvAffichageFiches;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String maCle ="MvhdCR0W3R9j8tYzPeYY3IKEYJNycXzgqgXbwYG1";
        final String urlDBImages = "https://epic.gsfc.nasa.gov";
        String dataJson=null;
        try {
            dataJson = UtilsConnexionNASA.getJsonFromNasaAPI(maCle);
        } catch (IOException e) {
            System.out.println("Erreur de connexion" + e.getMessage());
        }
        ArrayList<FicheImageNasa> listeFiches;
        listeFiches = UtilsConnexionNASA.getFichesFromJson(dataJson);
        System.out.println(listeFiches.size());
        for (FicheImageNasa f: listeFiches){
            System.out.println(f.getCommentaireLong() + " ' " + f.getNomImage());
        }
/*
Fonctionnement en mode synchrone
création du dictionnaire contenant les images avec leur nom comme clé
 */
        HashMap<String, Image> dictionnaireImages = (HashMap<String, Image>) UtilsConnexionNASA.getSyncMapOfImages(listeFiches,UtilsConnexionNASA.urlDBImages);
        for(String s: dictionnaireImages.keySet()){
            System.out.println(s);
        }
/**
 *     affichage de la liste des fiches
 *     L'affichage des fiches necessite de modifier les cellules du liste view pour permettre
 *     d'afficher une image et du texte.
 *     Pour cela on redéfinit la méthode cellFactory du listView.
 *
 */
        lvAffichageFiches.setCellFactory(new Callback<ListView<FicheImageNasa>, ListCell<FicheImageNasa>>() {
            @Override
            public ListCell<FicheImageNasa> call(ListView<FicheImageNasa> param) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return new ListCellFactory();
            }
        });
        /*
         * fonctionnement en mode synchrone
         */

        //lvAffichageFiches.setItems( FXCollections.observableList(UtilsConnexionNASA.createListeFichesImagesNasa(dictionnaireImages, listeFiches)));

    /*
    Fonctionnement en mode asynchrone
     */
        lvAffichageFiches.setItems(FXCollections.observableList(UtilsConnexionNASA.getASyncMapOfImages(listeFiches,UtilsConnexionNASA.urlDBImages)));
    }
}



