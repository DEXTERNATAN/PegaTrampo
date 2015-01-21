package pegatrampo.webinfor.com.pegatrampo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

/**
 * Created by 98287028191 on 29/12/14.
 */
public class MeusTrabalhos extends ActionBarActivity {


    private static final String TAG = "PEGATRAMPOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_vaga);


        ListviewContactItem ObjVaga = (ListviewContactItem) getIntent().getSerializableExtra("Vaga");

        Log.i(TAG,"Valor no nome da vaga" + ObjVaga.getName());


    }

    // Conectar no banco de dados e buscar os dados da vaga de acordo com o id que vem da outra tela.
    /*public class BuscaVagas extends AsyncTask<String, String, Void> {

        private static final String TAG = "PEGATRAMPOS";
        private ProgressDialog progressDialog = new ProgressDialog(MeusTrabalhos.this);
        private InputStream is = null ;
        private String result = "";

        protected void onPreExecute() {

            progressDialog.setMessage("Buscando dados. Aguarde ...");
            progressDialog.show();

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    BuscaVagas.this.cancel(true);
                }
            });

        }


        @Override
        protected Void doInBackground(String... params) {

            String url_select = "http://mrestituicao.com.br/select.php";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url_select);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                //Lê o conteúdo
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
                Log.e("log_tag", "Error converting result "+e.toString());
            }

            return null;

        }

        protected void onPostExecute(Void v) {

            // Tratando dados do tipo JSON
            try {
                JSONArray Jarray = new JSONArray(result);
                for(int i=0;i<Jarray.length();i++)
                {
                    JSONObject Jasonobject = null;
                    Jasonobject = Jarray.getJSONObject(i);

                    //1 - Pegar dados do banco e mostrar na tela
                    String endereco = Jasonobject.getString("Endereco");
                    String DescVaga = Jasonobject.getString("Desc_vaga");
                    String sumario = Jasonobject.getString("sumario_vaga");

                    // Chamar metodo que recebe o endereco e localiza a latitude e longitude setando o marcador de acordo
                    // com o novo endereço que veio do banco de dados
                    mapCurrentAddress(endereco);
                    Marker markerTrampos = maps.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

                    // Mudando o titulo do marcador de acordo com a vaga
                    markerTrampos.setTitle(DescVaga);

                    // Adicionando descrição ao marcador do mapa
                    markerTrampos.setSnippet(sumario);

                    // Alterando padrão do marcador para um customizado de acordo com o pegatrampos
                    markerTrampos.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.office_worker_04));

                    // Fazer com que a janela de titulo e descrição fiquem ativas por padrão
                    markerTrampos.showInfoWindow();

                    onMarkerClick(markerTrampos);


                }
                this.progressDialog.dismiss();

            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
            }

        }




    }*/


}