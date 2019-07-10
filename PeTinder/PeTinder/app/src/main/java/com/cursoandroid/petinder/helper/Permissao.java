package com.cursoandroid.petinder.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if(Build.VERSION.SDK_INT >= 23){

            List<String> listaPermissoes = new ArrayList<String>();

            //Percorre as permissoes passadas, verificando uma a uma se ja tem a permissao liberada
            for(String permissao : permissoes){
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) listaPermissoes.add(permissao);
            }

            //caso a lista esteja vazia não é necessario solicitar permissao
            if(listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //solicita permissoes
            ActivityCompat.requestPermissions(activity, novasPermissoes,  requestCode);
        }

        return true;
    }

}

