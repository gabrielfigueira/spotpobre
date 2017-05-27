package br.com.gabrielfigueira.spotpobre;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private Button btnCadastrar;
    private ListView lstPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lstPlaylist = (ListView)findViewById(R.id.lstPlaylist);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        lstPlaylist.setLongClickable(true);
        btnCadastrar.setOnClickListener(this);
        lstPlaylist.setOnItemClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        preencherListView();
    }

    private void preencherListView(){
        List<Object> lista = new PlaylistDAO(this).pesquisarPorNome("");
        AdapterListView adp = new AdapterListView(this, lista);
        lstPlaylist.setAdapter(adp);
        adp.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Playlist playlist = (Playlist)parent.getItemAtPosition(position);
        Intent it = new Intent(getApplicationContext(),FormActivity.class);
        it.putExtra("id", playlist.getId());
        startActivity(it);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Playlist livro = (Playlist)parent.getItemAtPosition(position);

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Playlist App");
        dlg.setMessage("Tem certeza que deseja remover o playlist " + livro.getTitulo() + "?");
        dlg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new PlaylistDAO(getApplicationContext()).remover(String.valueOf(livro.getId()));
                preencherListView();
            }
        });
        dlg.setNegativeButton("NÃO", null);
        dlg.show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCadastrar){
            try {
                Intent it = new Intent(
                        this,
                        FormActivity.class
                );
                //Abrir a Atividade
                startActivity(it);
            } catch (Exception ex){

            }
        }
    }
}
