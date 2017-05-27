package br.com.gabrielfigueira.spotpobre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by figueira on 25/05/17.
 */

public class AdapterListView extends BaseAdapter {
    private List<Object> lista;
    private LayoutInflater layout;

    public AdapterListView(Context contexto, List<Object> lista){
        this.layout = LayoutInflater.from(contexto);
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return  (lista!=null)?lista.size():0;
    }

    @Override
    public Object getItem(int position) {
        return (lista!=null)?lista.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return ((Playlist)lista.get(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Playlist playlist = (Playlist)lista.get(position);
        convertView = layout.inflate(R.layout.item_list, null);

        //Associar os atributos do objeto aos elementos da lista
        TextView tv1 = (TextView)convertView.findViewById(R.id.txtTitulo);
        tv1.setText(String.format("%s (%d)", playlist.getTitulo(), playlist.getId()));

        TextView tv2 = (TextView)convertView.findViewById(R.id.txtAutor);
        tv2.setText(playlist.getAutor());

        TextView tv3 = (TextView)convertView.findViewById(R.id.txtUrl);
        tv2.setText(playlist.getUrl());
        return convertView;
    }
}
