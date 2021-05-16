package com.example.restconmobile.ui.comanda;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.restconmobile.ComandaMenu;
import com.example.restconmobile.MainActivity;
import com.example.restconmobile.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class ComandaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comanda, container, false);
        ListView listView = (ListView) view.findViewById(R.id.comandaList);
        String savedPut;
        String[] field = new String[1];
        field[0] = "cod";
        String[] data = new String[1];
        data[0] = ComandaMenu.cod;
        PutData putData = new PutData("https://hosting2062588.online.pro/mobile/codMasa.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                savedPut = putData.getResult();
                if(savedPut.charAt(0) == '0') {
                    String[] masa = savedPut.split("\\s+");
                    ComandaMenu.numeMasa = masa[1];
                    ComandaMenu.prod = masa[2].split(",");
                    ComandaMenu.nrProd = Integer.parseInt(masa[3]);
                    ComandaMenu.valoare = Integer.parseInt(masa[4]);
                }
                else {
                    Toast.makeText(getContext(), savedPut, Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (ComandaMenu.prod[0].equals("gol")) {
            listView.setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.text_comanda);
            textView.setVisibility(View.VISIBLE);
        }
        else {
            putData = new PutData("https://hosting2062588.online.pro/mobile/produse.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    savedPut = putData.getResult();
                    if (savedPut.charAt(0) == '1') {
                        String[] produseDataBase = savedPut.split("~");
                        ArrayList<String> produse = new ArrayList<>();
                        for (String p : ComandaMenu.prod) {
                            for (String x : produseDataBase)
                                if (x.charAt(0) == p.charAt(0))
                                    produse.add(x.split(",")[1] + ", pret: " + x.split(",")[2] + " ron");
                        }
                        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, produse);
                        listView.setAdapter(listViewAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getContext(), "clicked item: " + produse.get(i).toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), savedPut, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return view;
    }
}