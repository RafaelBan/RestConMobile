package com.example.restconmobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.restconmobile.R;
import com.example.restconmobile.ui.comanda.ComandaFragment;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExtraFragment extends Fragment {

    public static ArrayList<String> produseFinal = new ArrayList<>();
    public static String produse = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extra, container, false);
        ArrayList<String> produse = new ArrayList<>();
        for (String x : ComandaMenu.produseDataBase)
            if (x.split(",")[5].contains(ComandaMenu.prodType) && !x.split(",")[4].equals("0"))
                produse.add(x.split(",")[1] + ", pret: " + x.split(",")[2] + " ron");
        ListView listView = (ListView) view.findViewById(R.id.extraList);
        ArrayAdapter<String> listViewAdapter = null;
        listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, produse);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(String x : ComandaMenu.produseDataBase)
                    if(x.split(",")[1].equals(produse.get(i).split(",")[0]))
                        showCustomDialog(x);
            }
        });
        return view;
    }
// TODO: MAI JOS SA SE CITEASCA STRINGUL 'MASA' DIN COMANDAMENU, SA SE UPDATEZE COMANDA, NRPROD SI VALOAREA SI SA SE TRIMITA PE PUTDATA + TRANSFORMAT DIN INT IN STRING INAPOI
    void showCustomDialog(String x) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_produs);

        final EditText cantitateProdusView = dialog.findViewById(R.id.cantitateProdus);
        Button submitButton = dialog.findViewById(R.id.adauga);
        Button cancelButton = dialog.findViewById(R.id.cancel);

        submitButton.setOnClickListener((View v) -> {
            int cantitateProdusInt = Integer.parseInt(String.valueOf(cantitateProdusView.getText()));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                produse = produse.join(",", ComandaMenu.prod);
            }
            if(cantitateProdusInt >= 1 && cantitateProdusInt <= Integer.parseInt(x.split(",")[4])) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<cantitateProdusInt;i++) {
                            if(produse.split(",")[0].equals("gol"))
                                produse = x.split(",")[0];
                            else
                                produse = produse + ',' + Integer.parseInt(x.split(",")[0]);
                        }
                        ComandaMenu.nrProd = ComandaMenu.nrProd + cantitateProdusInt;
                        ComandaMenu.valoare = ComandaMenu.valoare + cantitateProdusInt*Integer.parseInt(x.split(",")[2]);
                        String[] field = new String[4];
                        field[0] = "numeM";
                        field[1] = "comanda";
                        field[2] = "nrProd";
                        field[3] = "valoare";
                        String[] data = new String[4];
                        data[0] = ComandaMenu.numeMasa;
                        data[1] = produse;
                        data[2] = String.valueOf(ComandaMenu.nrProd);
                        data[3] = String.valueOf(ComandaMenu.valoare);
                        PutData putData = new PutData("https://hosting2062588.online.pro/mobile/updateMasa.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Updatarea s-a efectuat cu succes!")) {
                                    field = new String[2];
                                    field[0] = "id";
                                    field[1] = "stoc";
                                    data = new String[2];
                                    data[0] = x.split(",")[0];
                                    data[1] = String.valueOf(Integer.parseInt(x.split(",")[4]) - cantitateProdusInt);
                                    putData = new PutData("https://hosting2062588.online.pro/mobile/updateStoc.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            result = putData.getResult();
                                            if (result.equals("Updatarea s-a efectuat cu succes!")) {
                                                ComandaMenu.navView.setSelectedItemId(R.id.navigation_home);
                                                final FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                                ft.replace(R.id.nav_host_fragment, new ComandaFragment(), "NewFragmentTag");
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            } else {
                                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                dialog.dismiss();
            }
            else {
                Toast.makeText(getContext(), "Cantitate imposibila!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener((View v) -> {dialog.dismiss();});
        dialog.show();
    }
}