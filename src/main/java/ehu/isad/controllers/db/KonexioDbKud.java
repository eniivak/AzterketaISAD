package ehu.isad.controllers.db;

import ehu.isad.model.Readme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KonexioDbKud {

    private static KonexioDbKud instantzia;

    private KonexioDbKud(){}

    public static KonexioDbKud getInstantzia(){
        if(instantzia==null){
            instantzia= new KonexioDbKud();
        }
        return instantzia;
    }

    public ObservableList<Readme> lortuGuztiak() throws SQLException {
        String query= "select * from captchas";
        DbKudSqlite dbkud= DbKudSqlite.getInstantzia();
        ResultSet rs= dbkud.execSQL(query);
        String filename, value;
        Integer id,date;
        ObservableList<Readme> em= FXCollections.observableArrayList();
        Readme m= new Readme();
        while(rs.next()){
            id= rs.getInt("id");
            filename= rs.getString("filename");
            value= rs.getString("value");
            date= rs.getInt("date");

            em.add(m);
        }
        return em;
    }
    public ObservableList<Readme> badago(String md5,String url) throws SQLException {
        String query= "select * from checksums where md5='"+md5+"'";
        DbKudSqlite dbkud= DbKudSqlite.getInstantzia();
        ResultSet rs= dbkud.execSQL(query);
        ObservableList<Readme> ema= FXCollections.observableArrayList();
        Readme r= new Readme();
        if(rs.next()){
            r.setBertsioa(rs.getString("version"));
            r.setId(rs.getInt("id"));
            r.setPath(rs.getString("path"));

        }
        r.setUrl(url);
        r.setMd5(md5);
        ema.add(r);
        return ema;
    }
}
