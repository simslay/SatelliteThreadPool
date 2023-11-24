package com.example.satellitethreadpool;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Philippe
 * Exemple d'utilisation d'une interface modélisant l'accès aux données Json du site Nasa.
 */
public interface IHelperConnexionNasa {
    final String urlRequestAPINasa = "https://api.nasa.gov/EPIC/api/natural/images?";
    final String urlDBImages = "https://epic.gsfc.nasa.gov/archive/natural";

    /**
     * Parser Json customisé pour le cas spécifique à traiter
     *
     * @param jsonData
     * @return
     */
    public static ArrayList<FicheImageNasa> getFichesFromJson(String jsonData) {
        return null;
    }

    /**
     * Obtenir le fichier Json depuis l'url passée en constante de l'interface
     * la cle personnelle passée en paramètre
     *
     * @param cle
     * @return
     */
    public static String getJsonFromNasaAPI(String cle) {
        return null;
    }

    /**
     * Evolution possible en créant un mapping propriété/cle Json
     * pour créer automatiquement le parser Json
     * ex : Classe / nomImage mappé sur Json/Caption
     */

    /**
     * création 'une Map qui contient des images à partir d'une liste de fiches  et
     * de l'URL de la dataBase d'images. La construction se fait de façon synchrone en parcourant
     * la liste des noms pour exécuter une requête synchrone pour chaque nom de la liste.
     *
     * @return
     */
    public static Map<String, Image> getSyncMapOfImages(ArrayList<FicheImageNasa> listeFiches, String urlDB) {
        return null;
    }

    /**
     * création 'une Map qui contient des images à partir d'une liste de fiches et
     * de l'URL de la dataBase d'images. La construction se fait de façon asynchrone à partir d'un pool de thread e
     * qui exécute les requêtes d'obtention des images de façon asynchrone dans des threads parallèles.
     *
     * @return
     */
    public static Map<String, Image> getASyncMapOfImages(ArrayList<FicheImageNasa> listeFiches, String urlDB) {
        return null;
    }

    public static String[] getDateFromIdentifier(String identifier) {
        return new String[0];
    }
}

