package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.widget.LoginButton;

import java.util.Arrays;

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
