package com.example.satellitethreadpool;

import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Philippe
 * Ensemble de éthodes facilitant l'accès aux API de la NASA
 * Créer une clase utilitaire permet une organisation plus structurée du code.
 * Site NASA https://api.nasa.gov/
 * site https://api.nasa.gov/EPIC/api/natural/date/2019-05-30?api_key=DEMO_KEY
 * S'inscrire sur le site pour obtenir une clé d'accès, gratuit.
 */
public class UtilsConnexionNASA implements IHelperConnexionNasa {

    public static ArrayList<FicheImageNasa> getFichesFromJson(String jsonData) {
        ArrayList<FicheImageNasa> listeDesFiches = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.getJSONObject(i);
            FicheImageNasa fiche = new FicheImageNasa();
            fiche.setCommentaireLong(jo.getString("caption"));
            fiche.setNomImage(jo.getString("image"));
            fiche.setId(jo.getString("identifier"));
            fiche.setDate(UtilsConnexionNASA.getDateFromIdentifier(jo.getString("identifier")));
            listeDesFiches.add(fiche);
        }


        return listeDesFiches;
    }

    /**
     * Connexion à un site et récupération d'un fichier Json
     *
     * @return
     */

    public static String getJsonFromNasaAPI(String cle) throws IOException {
        // ajout de la clé à l'URL


//construction de la requete
        String urlRequest = urlRequestAPINasa + "api_key=" + cle;
        // Création de l'objet de connexion à l'URL
        URL urlConnexion = new URL(urlRequest);

        // Ouverture de la connexion HTTPS
        HttpsURLConnection connection = (HttpsURLConnection) urlConnexion.openConnection();
        connection.setRequestMethod("GET");

        // Lecture du flux de données en entrée
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String ligne;
        StringBuilder dataJson = new StringBuilder();

        while ((ligne = reader.readLine()) != null) {
            dataJson.append(ligne);
        }

        // Fermeture du flux Https
        reader.close();
        connection.disconnect();

        // Affichage de la réponse
        System.out.println("Donnees reçues :\n" + dataJson.toString());

        return dataJson.toString();
    }

    /**
     * The date is extracted from the identifier. This is needed because the url of imagesDB
     * is using the /year/month/day path.
     *
     * @param listeFiches
     * @param urlDB
     * @return
     */
    public static Map<String, Image> getSyncMapOfImages(ArrayList<FicheImageNasa> listeFiches, String urlDB) {
        Map<String, Image> dictionnaireImages = new HashMap<>();
        String urlImage = null;
        String year = null;
        String month = null;
        String day = null;
        String[] date = null;
        for (FicheImageNasa f : listeFiches) {
            //get date from identifier
            date = UtilsConnexionNASA.getDateFromIdentifier(f.getId());
            year = date[0];
            month = date[1];
            day = date[2];

            //construit le path d'accès à l'image
            urlImage = urlDB + "/" + year + "/" + month + "/" + day + "/jpg/" + f.getNomImage() + ".jpg";
            //lecture de l'mage par un stream , uniquement en javaFx
            Image image = new Image(urlImage);
            dictionnaireImages.put(f.getNomImage(), image);
        }
        return dictionnaireImages;
    }

    /**
     * Lecture asynchrone des images.
     * Utilisation d'un pool de threads.
     * Le pool de threads a un temps de rémonse indéterminé
     *
     * @param listeFiches
     * @param urlDB
     * @return
     */
    public static ArrayList<FicheImageNasa> getASyncMapOfImages(ArrayList<FicheImageNasa> listeFiches, String urlDB) {
        //initialisation de l'executor
        Executor mon_executor = Executors.newFixedThreadPool(20);
        //tableau de future permettant de décider de la fin du chargement
        ArrayList<CompletableFuture> liste_futures = new ArrayList<>();
//
        ArrayList<FicheImageNasa> listeFichesMAJ = new ArrayList<>();
        // String urlImage = null;
        String year = null;
        String month = null;
        String day = null;
        String[] date = null;
        for (FicheImageNasa f : listeFiches) {
            //get date from identifier
            date = UtilsConnexionNASA.getDateFromIdentifier(f.getId());
            year = date[0];
            month = date[1];
            day = date[2];

            //construit le path d'accès à l'image
            String urlImage = urlDB + "/" + year + "/" + month + "/" + day + "/jpg/" + f.getNomImage() + ".jpg";
            //lecture des images de façon asynchrone
            CompletableFuture<Void> image_future = CompletableFuture.runAsync(new Runnable() {

                @Override
                public void run() {
                    Image image = new Image(urlImage);
                    //Image image_use = null;
                    f.setImage(image);//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.out.println("message depuis tache de fond indice " + image.toString());
                    //listeFichesMAJ.add(f);// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }, mon_executor);
            liste_futures.add(image_future);

        }
        //verifie que toutes le images sont bien acquises
        boolean finished=false;
        while ( finished =false){

            finished=true;
            for (CompletableFuture c : liste_futures) {

                if (c.isDone() ==  false) {
                    finished=false;

                }

            }
        }

        return listeFiches;
    }

    /**
     *
     */


    /**
     * retourne un tableau contenant year/Month/day
     *
     * @param identifier
     * @return
     */
    public static String[] getDateFromIdentifier(String identifier) {

        String[] date = new String[3];
        date[0] = identifier.substring(0, 4);//year
        date[1] = identifier.substring(4, 6);//month
        date[2] = identifier.substring(6, 8);//day
        return date;
    }

    /**
     * construction de la liste des fiches
     * ici o rajoute les images aux objets FicheImagesNasa avant de les incorporer à la liste/
     */
    public static ArrayList<FicheImageNasa> createListeFichesImagesNasa(Map<String, Image> dicoImages, ArrayList<FicheImageNasa> listeFiches) {
        ArrayList<FicheImageNasa> listeMiseAJour = new ArrayList<>();
        for (FicheImageNasa f : listeFiches) {
            f.setImage(dicoImages.get(f.getNomImage()));
            listeMiseAJour.add(f);
        }
        return listeMiseAJour;
    }
}
