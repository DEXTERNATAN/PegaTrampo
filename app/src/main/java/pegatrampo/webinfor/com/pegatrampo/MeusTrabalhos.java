package pegatrampo.webinfor.com.pegatrampo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 98287028191 on 29/12/14.
 */
public class MeusTrabalhos extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pesquisar_vaga, container, false);
    }
}
