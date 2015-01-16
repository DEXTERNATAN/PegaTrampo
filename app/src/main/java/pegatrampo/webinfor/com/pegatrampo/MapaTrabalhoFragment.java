package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapaTrabalhoFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "PEGATRAMPOS";


    MapView mMapView;
    private GoogleMap googleMap;
    private Geocoder geocoder;

    // Traçar rotas
    private Polyline polyline;
    private List<LatLng> list;
    private long distance;

    View rootView;
    Address address;

    double latitude;
    double longitude;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_mapa_trabalho, container,false);
        mMapView = (MapView) v.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        //

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Instanciando o google maps
        googleMap = mMapView.getMap();

        // Exec async load task para buscar os endereços e setar os marcadores no mapa
        (new VagaService(googleMap)).execute("http://mrestituicao.com.br/select.php");

        // Criando um marcador
        /*MarkerOptions marker = new MarkerOptions().position(
                new LatLng(-15.780148200000000000, -47.929169800000010000)).title("Analista de sistemas");*/


        // Mudando o icone do marcador
        /*marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.fromResource(R.drawable.ic_job_map));*/

        // Adicionando o marcador no mapa
        //googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-15.780148200000000000, -47.929169800000010000)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(17),200,null);

        // Setando o tipo de mapa
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Marcando a posição do usuário no mapa
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setMyLocationEnabled(true);


        // Metodo para startar a activiti levando a rota do google - Leva o cara para traçar a rota na API do google
        // Fonte: http://brasildroid.com.br/desenvolvimento-de-aplicacoes-jogos/11062-chamar-intent-google-navigation.html
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:ll=-15.780148200000000000,-47.929169800000010000&mode=w")));

        // Método para traçar a rota no mapa usando pollyLines
        //getRoute(new LatLng(-15.780148200000000000, -47.929169800000010000), new LatLng(latitude, longitude));

        return v;


    }

    // Tracando rota entre dois pontos no mapa
    private void getRoute(final LatLng origin, final LatLng destination) {
        new Thread() {
            public void run() {

                String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
                        + origin.latitude + "," + origin.longitude + "&destination="
                        + destination.latitude + "," + destination.longitude + "&sensor=false&alternatives=true";

                Log.i("ROTA","VALOR DA STRING" + url);

                HttpResponse response;
                HttpGet request;
                AndroidHttpClient client = AndroidHttpClient.newInstance("route");

                request = new HttpGet(url);

                try {
                    response = client.execute(request);
                    final String answer = EntityUtils.toString(response.getEntity());

                    // Chama o metodo de contexto da activity
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Log.i("Script", answer);
                                list = buildJSONRoute(answer);
                                drawRoute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }



    // PARSER JSON
    public List<LatLng> buildJSONRoute(String json) throws JSONException{

        JSONObject result = new JSONObject(json);
        JSONArray routes = result.getJSONArray("routes");

        distance = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");

        JSONArray steps = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        List<LatLng> lines = new ArrayList<LatLng>();

        for(int i=0; i < steps.length(); i++) {
            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));


            String polyline = steps.getJSONObject(i).getJSONObject("polyline").getString("points");

            for(LatLng p : decodePolyline(polyline)) {
                lines.add(p);
            }

            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng"));
        }

        return(lines);
    }


    // DECODE POLYLINE
    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            Log.i("Script", "POL: LAT: "+p.latitude+" | LNG: "+p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }


    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();

            for(int i = 0, tam = list.size(); i < tam; i++){
                po.add(list.get(i));
            }

            po.color(Color.BLACK).width(4);
            polyline = googleMap.addPolyline(po);
        }
        else{
            polyline.setPoints(list);
        }
    }

    /*
    * Método: Responsavel por buscar o endereço informado e atualizar o mapa com as novas latitudes e longitudes..
    * Links de referencia:
    *   http://dl.e-book-free.com/2013/07/android_cookbook.pdf
    *   http://stackoverflow.com/questions/26010548/the-constructor-geocodermap-tracker-locale-is-undefined
    *
    */
    protected void mapCurrentAddress(String endereco) {
        String addressString = endereco; //"Qr 317 Conjunto C - Santa Maria, Brasília - DF";
        //Geocoder g = new Geocoder(this);
        Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = gc.getFromLocationName(addressString, 1);
            String add = "";
            if (addresses.size() > 0) {

                address = addresses.get(0);
                for (int i=0; i<address.getMaxAddressLineIndex();i++) {
                    add += address.getAddressLine(i) + "\n";

                }
                latitude = address.getLatitude();
                longitude = address.getLongitude();
                Toast.makeText(getActivity(), add, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Valor da latitude " + latitude + " Valor da Longitude: " + longitude);

            } else {
                Toast.makeText(getActivity(),"We failed to locate this address.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    // Classe asynctask que busca dados do banco de dados e cria novos marcadores
    public class VagaService extends AsyncTask<String, String, Void> {

        private static final String TAG = "PEGATRAMPOS";
        private ProgressDialog progressDialog = new ProgressDialog(MapaTrabalhoFragment.this.getActivity());
        private InputStream is = null ;
        private String result = "";
        private Context c;
        GoogleMap maps;


        public VagaService(GoogleMap mapIn){
           maps = mapIn;
        }



        protected void onPreExecute() {

            progressDialog.setMessage("Buscando dados. Aguarde ...");
            progressDialog.show();

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface arg0) {
                    VagaService.this.cancel(true);
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


    }


    public boolean onMarkerClick(Marker marker) {
        // TODO Auto-generated method stub
        // googleMap.clear();
        Toast.makeText(getActivity(), "USER MARKER",Toast.LENGTH_LONG).show();
        Log.i(TAG, "Passei por aqui quando cliquei no marcador");

        return true;
    }

    //Manipular eventos de toque (onTouchEvent)
    //Fonte: http://www.androidsnippets.com/handle-touch-events-ontouchevent
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                // finger touches the screen - Dedo toca a tela.
                Log.i(TAG,"Dedo toca a tela");
                break;

            case MotionEvent.ACTION_MOVE:
                // finger moves on the screen -  Dedo se move na tela.
                Log.i(TAG,"Dedo se move na tela");
                break;

            case MotionEvent.ACTION_UP:
                // finger leaves the screen -  Dedo deixa a tela.
                Log.i(TAG,"Dedo deixa a tela");
                break;
        }

        // tell the system that we handled the event and no further processing is required
        //Informar ao sistema que lidamos com o evento e nenhum processamento adicional é necessária
        return true;
    }


}
