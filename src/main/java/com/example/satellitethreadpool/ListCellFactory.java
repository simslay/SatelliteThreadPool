package com.example.satellitethreadpool;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ListCellFactory extends ListCell<FicheImageNasa> {



    FXMLLoader mLoader;

    @FXML
    private Label l_titre;
    @FXML
    private TextArea ta_commentairesLongs;
    @FXML
    private ImageView iv_imagePlace;
    @FXML
    AnchorPane an_anchorPane;
    @Override
    protected void updateItem(FicheImageNasa item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLoader == null) {
                mLoader = new FXMLLoader(getClass().getResource("custom_view_listView.fxml"));
                mLoader.setController(this);
                try {
                    mLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //tf_nom.setText(item.getNom());
            // commentaires.setText("Commentaire");
            //commentaires.setText(item.getCommentaires());
            l_titre.setText("image EPIC du : " + item.getDate()[2] + "/"+item.getDate()[1]+"/"+item.getDate()[0]);
            ta_commentairesLongs.setText(item.getCommentaireLong());
            //titre.setText("Titre");
            //titre.setText(item.getTitre());
            iv_imagePlace.setImage(item.getImage());
            setText(null);
            setGraphic(an_anchorPane);
        }
    }
}
