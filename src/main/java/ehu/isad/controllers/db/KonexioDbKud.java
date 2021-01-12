package ehu.isad.controllers.db;

import ehu.isad.model.Model;
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

    public ObservableList<Model> lortuGuztiak() throws SQLException {
        String query= "select * from captchas";
        DbKudSqlite dbkud= DbKudSqlite.getInstantzia();
        ResultSet rs= dbkud.execSQL(query);
        String filename, value;
        Integer id,date;
        ObservableList<Model> em= FXCollections.observableArrayList();
        Model m= new Model();
        while(rs.next()){
            id= rs.getInt("id");
            filename= rs.getString("filename");
            value= rs.getString("value");
            date= rs.getInt("date");
            m.setDate(date);
            m.setFilename(filename);
            m.setId(id);
            m.setValue(value);
            em.add(m);
        }
        return em;
    }
}
