package pegatrampo.webinfor.com.pegatrampo;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 98287028191 on 16/12/14.
 */

// Link de referencia:
//  --> http://www.perfectapk.com/sqliteopenhelper-example.html
public class ListViewDemoFragment extends android.support.v4.app.ListFragment {

    private static final String TAG = "PEGATRAMPO";
    private ListView listView;
    //private List<ListViewItem> mItems;
    private Resources resources;
    ListViewDemoAdapter adpt;

    // Declarando e instanciando o o Arraylist que vai ser usando
    List<ListviewContactItem> ListObVagas = new ArrayList<ListviewContactItem>();

    // Declarando e instanciando o objeto que vai ser repassado entre as activitys
    ListviewContactItem Contato;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        //mItems = new ArrayList<ListViewItem>();
        resources = getResources();

        // Exec async load task
        (new AsyncListViewLoader()).execute("http://mrestituicao.com.br/select.php");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);


        ListViewItem s = (ListViewItem) l.getAdapter().getItem(position);
        //ListViewItem s = adpt.getItem(position); - funcionou

        Toast.makeText(getActivity(), "Teste" +  "Valor: " + s.title , Toast.LENGTH_SHORT).show();

        Intent TelaDetalheVaga = new Intent(getActivity(), MeusTrabalhos.class);
        TelaDetalheVaga.putExtra("Vaga", s);
        //TelaDetalheVaga.putExtra("Descricao", item.getPhone());
        startActivity(TelaDetalheVaga);



    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<ListViewItem>> {

        private final ProgressDialog dialog = new ProgressDialog(ListViewDemoFragment.this.getActivity());

        // Metodo onde vamos atualizar a view
        @Override
        protected void onPostExecute(List<ListViewItem> result) {
            super.onPostExecute(result);

            dialog.dismiss();

            // Iniciando a variavel do adapter customizado
            adpt = new ListViewDemoAdapter(getActivity(), result);

            // initialize and set the list adapter
            setListAdapter(adpt);

            //adpt.setItemList(result);
            //adpt.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Baixando trampos...");
            dialog.show();
        }


        @Override
        protected List<ListViewItem> doInBackground(String... params) {
            List<ListViewItem> result = new ArrayList<ListViewItem>();

            try {
                URL u = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = conn.getInputStream();
// Read the stream
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ( is.read(b) != -1)
                    baos.write(b);
                String JSONResp = new String(baos.toByteArray());
                JSONArray arr = new JSONArray(JSONResp);
                for (int i=0; i < arr.length(); i++) {
                    result.add(convertContact(arr.getJSONObject(i)));
                    Log.i(TAG,"Imprimindo o array: " + String.valueOf(arr.getJSONObject(i)));
                }
                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }

    private ListViewItem convertContact(JSONObject obj) {
        String name = null;
        String description = null;
        try {

            Integer IdContato = obj.getInt("id_vaga");
            name = obj.getString("Desc_vaga");
            description = obj.getString("sumario_vaga");

            // Instanciando o objeto para preencher com dados
            Contato = new ListviewContactItem();
            Contato.setId(IdContato);
            Contato.setName(name);
            Contato.setPhone(description);

            // Adicionando ao listViewCotactItem os dados
            ListObVagas.add(Contato);

            Log.i(TAG, "Valores impressos: " + Contato.getName() + " - " + name );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ListViewItem(resources.getDrawable(R.drawable.ic_job_img), name, description);
    }
}