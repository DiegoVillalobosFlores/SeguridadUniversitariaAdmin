package mobiles.cucei.seguridadadmin.DataClasses

import android.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable

class Incidente (lat:Double,
                 lon:Double,
                 private var tipo: String,
                 private var victima: String,
                 private var fecha: String,
                 private var lugar: String,
                 public var mapFragment: SupportMapFragment) : OnMapReadyCallback,Serializable {

    private lateinit var mMap: GoogleMap
    private var place: LatLng = LatLng(lat,lon)

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.addMarker(MarkerOptions().position(place).title(lugar))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,16f))
    }
}