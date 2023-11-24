package com.example.satellitethreadpool;

import javafx.scene.image.Image;

/**
 * @author Philippe
 * Classe représentant les objets lus depuis un flux Json de la Nasa
 */
public class FicheImageNasa {
    private String commentaireLong;
    private String commentaireCourt;
    private String[] coordonnees;
    private String  nomImage;
    private String urlDBImages;
    private Image image =null;
    private String[] tailleImage;
    private String id;

    private String[] date;

    public FicheImageNasa() {
    }

    public String getCommentaireLong() {
        return commentaireLong;
    }

    public void setCommentaireLong(String commentaireLong) {
        this.commentaireLong = commentaireLong;
    }

    public String getCommentaireCourt() {
        return commentaireCourt;
    }

    public void setCommentaireCourt(String commentaireCourt) {
        this.commentaireCourt = commentaireCourt;
    }

    public String[] getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(String[] coordonnees) {
        this.coordonnees = coordonnees;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public String getUrlDBImages() {
        return urlDBImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrlDBImages(String urlDBImages) {
        this.urlDBImages = urlDBImages;
    }

    public String[] getTailleImage() {
        return tailleImage;
    }

    public void setTailleImage(String[] tailleImage) {
        this.tailleImage = tailleImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }
}
