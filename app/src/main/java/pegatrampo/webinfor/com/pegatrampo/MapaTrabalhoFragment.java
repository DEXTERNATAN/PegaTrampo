package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapaTrabalhoFragment extends android.support.v4.app.Fragment {

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

        googleMap = mMapView.getMap();
        // latitude and longitude
        latitude = -15.780148200000000000;
        longitude = -47.929169800000010000;

        // Vou passar o endereço e o metodo vai retornar a latitude e longitude do endereço para eu setar o marcador no mapa
        // Link de referencia:
        // http://pt.stackoverflow.com/questions/31547/como-retornar-latitude-e-longitude-no-android-google-maps
        // http://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation

        // Parametro de busca do google para resolver esta questão "android mapview geocoder example"

         // Muito bom este link - http://www.botecodigital.info/javascript/introducao-a-api-de-mapas-do-google-maps/

        //geocoder = new Geocoder(this);
        /*List<Address> enderecos = geocoder.getFromLocationName("Av. Sampaio Vidal, Centro, Marília, SP", 1);
        if (enderecos.size() > 0) {
            Log.v("tag", "coordenadas " + enderecos.get(0).getLatitude() + ", " + enderecos.get(0).getLongitude());
        }
        */

        // Atualiza o marcador de acordo com o endereço informado.
        mapCurrentAddress();

        // create marker(marcador)
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(-15.780148200000000000, -47.929169800000010000)).title("Analista de sistemas"+distance);

        MarkerOptions marker1 = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Analista de requisitos"+distance);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        marker1.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));




        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-15.780148200000000000, -47.929169800000010000)).zoom(12).build();

        googleMap.addMarker(marker1);
        CameraPosition cameraPosition1 = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
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
        getRoute(new LatLng(-15.780148200000000000, -47.929169800000010000), new LatLng(latitude, longitude));

        // Perform any camera updates here
        return v;
        //return inflater.inflate(R.layout.fragment_mapa_trabalho, container, false);

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
    protected void mapCurrentAddress() {
        String addressString = "Qr 317 Conjunto C - Santa Maria, Brasília - DF";
        //Geocoder g = new Geocoder(this);
        Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses;
        try {
            addresses = gc.getFromLocationName(addressString, 1);
            //String add = "";
            if (addresses.size() > 0) {

                address = addresses.get(0);
                /*for (int i=0; i<address.getMaxAddressLineIndex();i++) {
                    add += address.getAddressLine(i) + "\n";

                }*/
                latitude = address.getLatitude();
                longitude = address.getLongitude();
                //Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getBaseContext(),"We failed to locate this address.", Toast.LENGTH_SHORT).show();
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


}
