package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ListFragment;

import java.util.ArrayList;

// LInks de referencia:
// http://stackoverflow.com/questions/22512833/create-listview-in-fragment-android
// http://www.vogella.com/tutorials/AndroidListView/article.html

public class ListaTrabalhoFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_trabalhos, container, false);

        final ArrayList<ListviewContactItem> listContact = GetlistContact();
        ListView lv = (ListView)rootView.findViewById(R.id.listview);
        lv.setAdapter(new ListviewContactAdapter(getActivity(), listContact));

        final EditText TxvSearch = (EditText) rootView.findViewById(R.id.txvsearch);

        TxvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ListviewContactAdapter adapter = new ListviewContactAdapter(getActivity(), listContact);
                Log.i("Valor", "Cheguei aqui");
                //adapter.getFilter().filter(s);
                Log.i("Valor", String.valueOf(s));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
        //return inflater.inflate(R.layout.fragment_lista_trabalhos, container, false);
    }


    private ArrayList<ListviewContactItem> GetlistContact(){
        ArrayList<ListviewContactItem> contactlist = new ArrayList<ListviewContactItem>();

        ListviewContactItem contact = new ListviewContactItem();

        contact.setName("Analista de Sistemas");
        contact.setPhone("01213113568");
        contactlist.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Analista de Redes");
        contact.setPhone("01213869102");
        contactlist.add(contact);

        contact = new ListviewContactItem();
        contact.setName("Analista de Testes");
        contact.setPhone("01213123985");
        contactlist.add(contact);

        return contactlist;
    }
}
