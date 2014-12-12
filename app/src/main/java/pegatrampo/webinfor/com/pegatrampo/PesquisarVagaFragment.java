package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 09/12/14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PesquisarVagaFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pesquisar_vaga, container, false);
    }
}
