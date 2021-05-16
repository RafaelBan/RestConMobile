package com.example.restconmobile.ui.bautura;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.restconmobile.ComandaMenu;
import com.example.restconmobile.ExtraFragment;
import com.example.restconmobile.MainActivity;
import com.example.restconmobile.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BauturaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bautura, container, false);
        ArrayList<String> produse = new ArrayList<>();
        for (String x : ComandaMenu.produseDataBase)
            if (x.split(",")[3].contains("bautura") && !x.split(",")[4].equals("0"))
                produse.add(x.split(",")[5]);
        ListView listView = (ListView) view.findViewById(R.id.bauturaList);
        ArrayAdapter<String> listViewAdapter = null;
        ComandaMenu.getProduse();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ComandaMenu.produseFinal = (ArrayList<String>) produse.stream().distinct().collect(Collectors.toList());
        }
        listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ComandaMenu.produseFinal);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ComandaMenu.prodType = ComandaMenu.produseFinal.get(i).toString();
                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ExtraFragment(), "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}