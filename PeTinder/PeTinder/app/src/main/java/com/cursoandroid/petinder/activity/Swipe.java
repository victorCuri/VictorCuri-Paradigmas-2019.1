package com.cursoandroid.petinder.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cursoandroid.petinder.Matches.Matches;
import com.cursoandroid.petinder.R;
import com.cursoandroid.petinder.config.ConfiguracaoFirebase;
import com.cursoandroid.petinder.Cards.arrayAdapter;
import com.cursoandroid.petinder.Cards.cards;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class Swipe extends Activity {

    private cards cardsData[];
    private com.cursoandroid.petinder.Cards.arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    private String currentUId;

    private DatabaseReference usersDb, myUser, userMatch;

    Dialog mydialog;


    ListView listView;
    List<cards> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        mydialog = new Dialog(this);

        usersDb = FirebaseDatabase.getInstance().getReference().child("usuarios");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        checkUserAction();
        //adaptar com a classe configuracaoFirebase

        rowItems = new ArrayList<>();

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);

        final SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                cards obj = (cards) dataObject;
                String userId= obj.getUserId();
                usersDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);
                Toast.makeText(Swipe.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId= obj.getUserId();
                usersDb.child(userId).child("connections").child("yep").child(currentUId).setValue(true);
                isConectionMatch(userId);
                Toast.makeText(Swipe.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                Intent intent = new Intent(Swipe.this, Perfil_Usuario.class);
                Bundle b = new Bundle();
                b.putString("userId", userId);
                intent.putExtras(b);
                startActivity(intent);
                Toast.makeText(Swipe.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConectionMatch(final String userId) {

        DatabaseReference currentUserConnectionDn = usersDb.child(currentUId).child("connections").child("yep").child(userId);
        currentUserConnectionDn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(Swipe.this, "new connection", Toast.LENGTH_LONG).show();

                    String key = ConfiguracaoFirebase.getFireBase().child("Chat").push().getKey();
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(key);
                    usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);

                    myUser = ConfiguracaoFirebase.getFireBase().child("usuarios").child(currentUId);
                    userMatch = ConfiguracaoFirebase.getFireBase().child("usuarios").child(dataSnapshot.getKey());
                    openDialog();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String userAction;
    private String notUserAction;

    public void checkUserAction(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userdb = usersDb.child(user.getUid());
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    if (dataSnapshot.child("Action").getValue() != null) {
                        userAction = dataSnapshot.child("Action").getValue().toString();
                        switch (userAction) {
                            case "Adopt":
                                notUserAction = "Donate";
                                break;

                            case "Donate":
                                notUserAction = "Adopt";
                                break;
                        }

                        getOppositeAction();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void getOppositeAction(){
            usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("Action").getValue() != null) {

                    if (dataSnapshot.exists() && !dataSnapshot.child("connections").child("nope").hasChild(currentUId) && !dataSnapshot.child("connections").child("yep").hasChild(currentUId) && dataSnapshot.child("Action").getValue().toString().equals(notUserAction)) {

                        String profileImageUrl = "default";//m
                        if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        String age = "default";
                        if (!dataSnapshot.child("phone").getValue().equals("default")) {
                            age = dataSnapshot.child("phone").getValue().toString();
                        }


                        cards item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("profileImageUrl").getValue().toString(), dataSnapshot.child("phone").getValue().toString());

                        rowItems.add(item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void goToSettings(View view) {
        Intent intent = new Intent(Swipe.this, Settings.class);
        startActivity(intent);
        return;
    }

    public void goToMatches(View view) {
        Intent intent = new Intent(Swipe.this, Matches.class);
        startActivity(intent);
        return;
    }

    public void openDialog(){

        mydialog.setContentView(R.layout.popup);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();

    }

}

//modificados swipe, arrayAdapter e Settings