package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaTrabalhoFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        ArrayList<ListviewContactItem> listContact = GetlistContact();
        ListView lv = (ListView)getActivity().findViewById(R.id.lv_contact);
        lv.setAdapter(new ListviewContactAdapter(getActivity(), listContact));

        return rootView;

        //return inflater.inflate(R.layout.fragment_lista_trabalhos, container, false);
    }


    private ArrayList<ListviewContactItem> GetlistContact(){
        ArrayList<ListviewContactItem> contactlist = new ArrayList<ListviewContactItem>();

        ListviewContactItem contact = new ListviewContactItem();

        contact.SetName("Topher");
        contact.SetPhone("01213113568");
        contactlist.add(contact);

        contact = new ListviewContactItem();
        contact.SetName("Jean");
        contact.SetPhone("01213869102");
        contactlist.add(contact);

        contact = new ListviewContactItem();
        contact.SetName("Andrew");
        contact.SetPhone("01213123985");
        contactlist.add(contact);

        return contactlist;
    }
}