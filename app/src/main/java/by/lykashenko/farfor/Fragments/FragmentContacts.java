package by.lykashenko.farfor.Fragments;

import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import by.lykashenko.farfor.R;

/**
 * Created by Дмитрий on 09.09.16.
 */
public class FragmentContacts extends android.support.v4.app.Fragment {
    private View view;
    private GoogleMap map;
    private MapView mapView;
    private SupportMapFragment mapFragment;
    private TextView contact, adress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.contacts, container, false);
        contact = (TextView) view.findViewById(R.id.textView2);
        adress = (TextView) view.findViewById(R.id.textView4);

        contact.setText(getResources().getString(R.string.contacts));
        adress.setText(getResources().getString(R.string.adress));

        Linkify.addLinks(contact, Linkify.ALL);
        Linkify.addLinks(adress, Linkify.ALL);

        MapsInitializer.initialize(getActivity());
        try {
            mapView = (MapView) view.findViewById(R.id.mapFragment2);

            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    map.getUiSettings().setZoomControlsEnabled(true);
                    LatLng location = new LatLng(15.00000, 27.00000);
                    LatLng location2 = new LatLng(15.05000, 27.05000);
                    map.addMarker(new MarkerOptions().position(location).title(getResources().getString(R.string.you_map)));
                    map.addMarker(new MarkerOptions().position(location2).title(getResources().getString(R.string.restourant)));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                }

                public void onCreate(Bundle savedInstanceState) {
                    FragmentContacts.super.onCreate(savedInstanceState);
                    mapView.onCreate(savedInstanceState);
                }
            });
        } catch (InflateException e) {
            Log.d("Contact", "Ошибка mapView = " + e);
        }

        return view;
    }

}
