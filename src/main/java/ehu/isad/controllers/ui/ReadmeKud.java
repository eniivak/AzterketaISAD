package ehu.isad.controllers.ui;

import ehu.isad.App;
import ehu.isad.controllers.db.KonexioDbKud;
import ehu.isad.model.Readme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import org.apache.commons.codec.binary.Hex;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
        datuakBistaratu();

    }

    public void datuakBistaratu(){
        idTaula.setItems(lista);

        idMd5.setCellValueFactory(new PropertyValueFactory<>("md5"));
        idUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
        idVers.setCellFactory(TextFieldTableCell.forTableColumn());
        idVers.setCellValueFactory(new PropertyValueFactory<>("bertsioa"));

        if(lista.get(0).getBertsioa()==null){

            idInfo.setText("Ez zegoen Datubasean");
            idVers.setCellFactory(TextFieldTableCell.forTableColumn());
            idTaula.setEditable(true);
            idVers.setOnEditCommit(data -> {
                System.out.println( data.getNewValue());
                System.out.println( data.getOldValue());

                Readme p = data.getRowValue();
                p.setBertsioa(data.getNewValue());
            });
            datuBaseantxertatu();
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
public void datuBaseantxertatu(){
       KonexioDbKud kn = KonexioDbKud.getInstantzia();
       kn.sartu(idVers.getText(),idMd5.getText());

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

        URL url1 = new URL(url);
        InputStream is = url1.openStream();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String digest = getDigest(is, md, 2048);
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
