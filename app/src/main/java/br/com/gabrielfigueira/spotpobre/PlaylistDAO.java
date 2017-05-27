package br.com.gabrielfigueira.spotpobre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by figueira on 25/05/17.
 */

public class PlaylistDAO  extends SQLiteOpenHelper {

    private final String sql_create =
            "create table playlist("+
                    "id integer primary_key auto increment,"+
                    "titulo text not null, " +
                    "autor text not null, " +
                    "url string"+
            ");";

    private SQLiteDatabase db;

    public PlaylistDAO(Context context){
        super(context,"playlist.db",null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE playlist;");
        db.execSQL(sql_create);
    }

    public long inserir(String titulo,String autor, String url){
        //Definir permissão de escrita
        this.db = getWritableDatabase();

        //Especificar parâmetros da inserção
        ContentValues cv = new ContentValues();
        cv.put("titulo", titulo);
        cv.put("autor", autor);
        cv.put("url", url);

        long id = this.db.insert("playlist", null, cv);
        return id;
    }

    public long atualizar(ContentValues playlist){
        this.db = getWritableDatabase();
        String where = "id = ?";
        String whereArgs[] = new String[]{playlist.getAsString("id")};
        return this.db.update("playlist", playlist, where, whereArgs);
    }

    public long remover(String id){
        this.db = getWritableDatabase();
        String where = "id = ?";
        String whereArgs[] = new String[]{id};
        return this.db.delete("playlist", where, whereArgs);
    }

    public List<Object> pesquisarPorNome(String titulo){

        String sql = null;
        String where[] = null;
        if ( titulo.equals("")){
            sql = "SELECT * FROM playlist ORDER BY titulo";
        }else{
            sql = "SELECT * FROM playlist WHERE UPPER(titulo) like ? ORDER BY titulo";
            where = new String[]{"%" + titulo.toUpperCase() + "%"};
        }
        //Definir permissão de leitura
        this.db = getReadableDatabase();

        //Realizar a consulta
        Cursor c = this.db.rawQuery(sql, where);

        List<Object> lista = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                Playlist playlist = new Playlist();
                playlist.setId(c.getInt(c.getColumnIndex("id")));
                playlist.setTitulo(c.getString(c.getColumnIndex("titulo")));
                playlist.setAutor(c.getString(c.getColumnIndex("autor")));
                playlist.setUrl(c.getString(c.getColumnIndex("url")));
                System.out.println("ID = "+ c.getColumnIndex("id"));
                System.out.println("playlist = "+ playlist.toString());
                lista.add(playlist);
            }while(c.moveToNext());
            return lista;
        }else{
            return null;
        }
    }
    public Object pesquisarPorId(String id){

        String sql = "SELECT * FROM playlist WHERE ID=?";
        String where[] = new String[]{id};

        //Definir permissão de leitura
        this.db = getReadableDatabase();

        //Realizar a consulta
        Cursor c = this.db.rawQuery(sql, where);

        if (c.moveToFirst()){
            Playlist playlist = new Playlist();
            playlist.setId(c.getInt(c.getColumnIndex("id")));
            playlist.setTitulo(c.getString(c.getColumnIndex("titulo")));
            playlist.setAutor(c.getString(c.getColumnIndex("autor")));
            playlist.setUrl(c.getString(c.getColumnIndex("url")));
            return playlist;
        }else{
            return null;
        }
    }

}
