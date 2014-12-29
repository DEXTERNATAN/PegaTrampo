package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


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
