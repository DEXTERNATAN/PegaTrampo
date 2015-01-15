package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends Activity{

    private static final String TAG = "PegaTrampos - AsyncTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //setContentView(R.layout.listview_item);


        // NOVA IMPLEMENTAÇÃO USANDO ASSYNC TASK DE CONEXAO COM SERVIDOR WEB PHP E LISTAGEM DE UM XML
        //new task().execute();

         // IMPLEMENTAÇÃO ANTERIOR DE LOGIN
        Button btnGooglePlus = (Button) findViewById(R.id.button3);

        btnGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent telaInicioApp = new Intent(PrincipalActivity.this,MainActivity.class);
                PrincipalActivity.this.startActivity(telaInicioApp);
                PrincipalActivity.this.finish();

            }
        });

    }




}
