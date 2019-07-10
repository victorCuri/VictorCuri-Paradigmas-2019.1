package com.cursoandroid.petinder.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cursoandroid.petinder.R;
import com.cursoandroid.petinder.config.ConfiguracaoFirebase;
import com.cursoandroid.petinder.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class Perfil_Usuario extends AppCompatActivity {


    private TextView nome, description, idade;
    private Button back;
    private ImageView foto;

    private String userId, name, about, profileImageUrl, age;

    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil__usuario);

        userId = getIntent().getExtras().getString("userId");

        nome = (TextView) findViewById(R.id.nomePerfil);
        back = (Button) findViewById(R.id.backId);
        foto = (ImageView) findViewById(R.id.imagemPerfilId);
        description = (TextView) findViewById(R.id.descricao);
        idade = (TextView) findViewById(R.id.agePerfilId);

        dbReference =  ConfiguracaoFirebase.getFireBase().child("usuarios").child(userId);

        getUserInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });


    }

    private void getUserInfo() {

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        name = map.get("name").toString();
                        nome.setText(name);
                    }
                    if (map.get("description") != null) {
                        about = map.get("description").toString();
                        description.setText(about);
                    }
                    if (map.get("phone") != null) {
                        age = map.get("phone").toString();
                        idade.setText(age);
                    }
                    Glide.clear(foto);//m
                    if(map.get("profileImageUrl")!=null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch(profileImageUrl){
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(foto);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(foto);
                                break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
