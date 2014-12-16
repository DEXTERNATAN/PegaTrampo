package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaTrabalhoFragment extends android.support.v4.app.Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Geocoder geocoder;

    View rootView;
    Address address;

    double latitude;
    double longitude;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_mapa_trabalho, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);

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

        // Atualiza o marcador de acordo com o
        mapCurrentAddress();

        // create marker(marcador)
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Oportunidade de trabalho");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(17),200,null);

        // Setando o tipo de mapa
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Marcando a posição do usuário no mapa
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.setMyLocationEnabled(true);

        // Perform any camera updates here
        return v;
        //return inflater.inflate(R.layout.fragment_mapa_trabalho, container, false);

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
            String add = "";
            if (addresses.size() > 0) {

                address = addresses.get(0);
                for (int i=0; i<address.getMaxAddressLineIndex();i++) {
                    add += address.getAddressLine(i) + "\n";

                }
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
