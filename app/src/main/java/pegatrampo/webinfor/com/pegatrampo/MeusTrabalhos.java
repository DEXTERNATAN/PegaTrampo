package pegatrampo.webinfor.com.pegatrampo;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 98287028191 on 29/12/14.
 */
public class MeusTrabalhos extends ActionBarActivity{


    private static final String TAG = "PEGATRAMPOS";
    private ListViewItem ObVagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_vaga);

        // Recuperando o objeto vindos da outra activity
        ObVagas = (ListViewItem) getIntent().getSerializableExtra("Vaga");

        TextView titulo = (TextView) findViewById(R.id.textView5);
        titulo.setText(ObVagas.title);

        TextView Descricao = (TextView) findViewById(R.id.textView6);
        Descricao.setText(ObVagas.getDescription());

        // DownloadImageUtil possui um hashMap interno. Chave=URL
        /*ImageView ImgVaga = (ImageView) findViewById(R.id.icon);
        ImgVaga.setImageDrawable(ObVagas.icon);*/



    }

}