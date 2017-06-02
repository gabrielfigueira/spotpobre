package br.com.gabrielfigueira.spotpobre;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AlertDialog;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTitulo;
    private EditText edtAutor;
    private EditText edtUrl;

    private Button btnSalvar;
    private Button btnCancelar;
    private Playlist playlist;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        edtTitulo = (EditText)findViewById(R.id.edtTitulo);
        edtAutor = (EditText)findViewById(R.id.edtAutor);
        edtUrl = (EditText)findViewById(R.id.edtUrl);

        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Intent it = getIntent();
        if (it != null){
            try{
                id = it.getIntExtra("id",0);
                playlist = (Playlist)new PlaylistDAO(this).pesquisarPorId(String.valueOf(id));
                if (playlist == null){
                    playlist = new Playlist();
                }
                edtTitulo.setText(playlist.getTitulo());
                edtAutor.setText(playlist.getAutor());
                edtUrl.setText(playlist.getUrl());

            }catch(Exception e){
                Log.e("ERRO", e.getMessage());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancelar){
            super.onBackPressed();
        }else if (v.getId() == R.id.btnSalvar){
            try {
                playlist.setTitulo(edtTitulo.getText().toString());
                playlist.setAutor(edtAutor.getText().toString());
                playlist.setUrl(edtUrl.getText().toString());

                if ( id == 0){
                    id = new PlaylistDAO(this).inserir(playlist);
                }else{
                    id = new PlaylistDAO(this).atualizar(playlist);
                }

            }catch(Exception e){
                Log.e("ERRO", e.getMessage());
            }


            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Playlist App");
            dlg.setMessage("Operação realizada com sucesso!");
            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlg.show();


        }
    }
}
