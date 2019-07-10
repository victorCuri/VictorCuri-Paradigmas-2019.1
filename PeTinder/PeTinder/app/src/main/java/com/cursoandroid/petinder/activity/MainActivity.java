package com.cursoandroid.petinder.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoandroid.petinder.R;
import com.cursoandroid.petinder.config.ConfiguracaoFirebase;
import com.cursoandroid.petinder.helper.Permissao;
import com.cursoandroid.petinder.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends Activity {

    private EditText email;
    private EditText senha;
    private Button login;
    private TextView cadastro;
    private Usuario usuario;
    private FirebaseAuth auth;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        email = (EditText) findViewById(R.id.textoEmailid);
        senha = (EditText) findViewById(R.id.textoSenhaid);
        login = (Button) findViewById(R.id.loginid);
        cadastro = (TextView) findViewById(R.id.addContaid);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //conferir se o login e a senha estao corretos
                //seguir para a proxima tela
                //conferir se ele esta com algum chat em aberto(previous match) -> tela chat/match

                usuario  = new Usuario();
                usuario.setEmail( email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();



            }
        });

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, CadastroUsuario.class));

            }
        });


                auth = ConfiguracaoFirebase.getFirebaseAuth();
                auth.signOut();

    }

    private void validarLogin() {

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    abrirTela();
                    Toast.makeText(MainActivity.this, "Sucesso ao fazer login", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Erro ao fazer login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void abrirTela(){
        Intent intent = new Intent(MainActivity.this, Swipe.class); //se p usuario tivaer um match ativo ir para a tela do chat
        startActivity(intent);
        finish();
    }















    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int resultado : grantResults){

            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaVAlidacaoPermissao();
            }
        }
    }

    private void alertaVAlidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app, é necessario aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

