package ehu.isad.controllers.ui;

import ehu.isad.App;
import ehu.isad.controllers.db.KonexioDbKud;
import ehu.isad.model.Model;
import ehu.isad.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

public class ModelKud {
    private App mainApp;
    private ObservableList<Model> lista= FXCollections.observableArrayList();
    public void setMainApp(App main){
        this.mainApp=main;
    }
    @FXML
    private Button idbotoita;

    @FXML
    void onClick(ActionEvent event) throws SQLException {
        KonexioDbKud kn= KonexioDbKud.getInstantzia();
       lista= kn.lortuGuztiak();
    }


    private void irudiaSartu(String url,String pa) throws IOException {
        BufferedImage irudia = ImageIO.read(new URL(url));
        Properties properties = Utils.getProperties();
        String imagePath = properties.getProperty("pathToImage");
        Files.createDirectories(Paths.get(imagePath));//setup.propertien jarri dugun karpeta sortuko dugu
        File file = new File(imagePath+"/"+pa);
        ImageIO.write(irudia,"jpg",file);
    }
}
