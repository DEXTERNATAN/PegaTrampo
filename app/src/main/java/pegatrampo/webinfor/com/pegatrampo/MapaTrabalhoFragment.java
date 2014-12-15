package pegatrampo.webinfor.com.pegatrampo;

/**
 * Created by 98287028191 on 10/12/14.
 */

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaTrabalhoFragment extends android.support.v4.app.Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Geocoder geocoder;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = -15.780148200000000000;
        double longitude = -47.929169800000010000;

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


        // create marker(marcador)
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Oportunidade de trabalho");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // adding marker
        googleMap.addMarker(marker);
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

        // Perform any camera updates here
        return v;
        //return inflater.inflate(R.layout.fragment_mapa_trabalho, container, false);

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
