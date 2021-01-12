package ehu.isad.controllers.ui;

import ehu.isad.App;
import ehu.isad.controllers.db.KonexioDbKud;
import ehu.isad.model.Readme;
import ehu.isad.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.codec.binary.Hex;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ReadmeKud {
    private App mainApp;
    private ObservableList<Readme> lista= FXCollections.observableArrayList();
    public void setMainApp(App main){
        this.mainApp=main;
    }
    @FXML
    private Button idBotoia;

    @FXML
    private TableView<Readme> idTaula;

    @FXML
    private TableColumn<Readme, String> idUrl;

    @FXML
    private TableColumn<Readme, String> idMd5;

    @FXML
    private TableColumn<Readme, String> idVers;

    @FXML
    private TextField idBilatuText;

    @FXML
    private Label idInfo;

    @FXML
    void onClick(ActionEvent event) throws SQLException, NoSuchAlgorithmException, IOException {
        KonexioDbKud kn= KonexioDbKud.getInstantzia();
       //lista= kn.lortuGuztiak();
       //klik egitean textfieldId|ko textua hartu eta md5-a atera. Datu basean dagoen begiratu eta baldin badago bere infoa pantailaratu.
        String md5= idBilatuText.getText()+"/README";
        String hash=hashKalkulatu(md5);//MD5 ATERA
        ObservableList<Readme> listap= FXCollections.observableArrayList();
        listap = kn.badago(hash,idBilatuText.getText());
        setLista(listap);
        idTaula.setItems(lista);

            idMd5.setCellValueFactory(new PropertyValueFactory<>("md5"));
            idUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
            idVers.setCellValueFactory(new PropertyValueFactory<>("bertsioa"));

            if(lista.get(0).getBertsioa()==null){
                idInfo.setText("Ez zegoen Datubasean");
                idVers.setEditable(true);

            }
            else{
                idInfo.setText("Datubasean zegoen");
            }



    }
    public void setLista(ObservableList<Readme> plista) {
        lista = FXCollections.observableArrayList();
        if(plista!=null) {
            lista.addAll(plista);
        }
    }

   @FXML
    void initialize() {
        idTaula.setItems(lista);
        idMd5.setVisible(true);
        idVers.setVisible(true);
        idUrl.setVisible(true);
        //menuaKargatu();
        //kargatu();

    }

    public String hashKalkulatu(String url) throws IOException, NoSuchAlgorithmException {

        URL url1 = new URL("http://152.92.198.164/phpMyAdmin");
        InputStream is = url1.openStream();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String digest = getDigest(is, md, 2048);

        System.out.println("MD5 Digest:" + digest);
        return digest;

    }

    public static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
            throws NoSuchAlgorithmException, IOException {

        md.reset();
        byte[] bytes = new byte[byteArraySize];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            md.update(bytes, 0, numBytes);
        }
        byte[] digest = md.digest();
        String result = new String(Hex.encodeHex(digest));
        return result;
    }
}
