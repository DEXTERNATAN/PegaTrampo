package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class PrincipalActivity extends Activity{

    private static final String TAG = "PegaTrampos - AsyncTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_item);


        // NOVA IMPLEMENTAÇÃO USANDO ASSYNC TASK DE CONEXAO COM SERVIDOR WEB PHP E LISTAGEM DE UM XML
        new task().execute();

         // IMPLEMENTAÇÃO ANTERIOR DE LOGIN
        Button btnGooglePlus = (Button) findViewById(R.id.button3);
        /*
        btnGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent telaInicioApp = new Intent(PrincipalActivity.this,MainActivity.class);
                PrincipalActivity.this.startActivity(telaInicioApp);
                PrincipalActivity.this.finish();

            }
        });*/

    }


    // Trabalhando com PHP
    // Link de referencia:
    //      https://ugirusgiarto.wordpress.com/2011/10/27/json-php-mysql-with-asynctask-progressdialog/
    class task extends AsyncTask<String, String, Void>{

        TextView text_1, text_2;

        private ProgressDialog progressDialog = new ProgressDialog(PrincipalActivity.this);

        InputStream is = null ;
        String result = "";
        protected void onPreExecute() {
            progressDialog.setMessage("Download data - natan...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    task.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(String... params) {
            String url_select = "http://mrestituicao.com.br/select.php";

            Log.i(TAG, "URL: " + url_select);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //Lê ao conteúdo
                is =  httpEntity.getContent();

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection " + e.toString());
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                result=sb.toString();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error converting result "+e.toString());
            }


            return null;

        }

        protected void onPostExecute(Void v) {

            // Tratando dados do tipó JSON
            try {
                JSONArray Jarray = new JSONArray(result);
                for(int i=0;i<Jarray.length();i++)
                {
                    JSONObject Jasonobject = null;
                    text_1 = (TextView)findViewById(R.id.tvTitle);
                    text_2 = (TextView)findViewById(R.id.tvDescription);

                    Jasonobject = Jarray.getJSONObject(i);

                    //get an output on the screen
                    String id = Jasonobject.getString("rid");
                    String name = Jasonobject.getString("name");
                    String password = Jasonobject.getString("weight");
                    //text_1.append(id+"\t\t"+name+"\t\t"+password+"\t\t"+"\n");
                    text_1.append(name);
                    text_2.append(password);

                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

        }


    }


}
