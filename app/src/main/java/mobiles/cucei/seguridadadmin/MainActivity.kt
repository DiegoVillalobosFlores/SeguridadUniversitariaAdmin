package mobiles.cucei.seguridadadmin

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import mobiles.cucei.seguridadadmin.Adapters.Incident
import mobiles.cucei.seguridadadmin.DataClasses.Incidente

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var incidentes:ArrayList<Incidente>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var mMap: GoogleMap
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val url = getString(R.string.API_URL) + getString(R.string.API_GET_REPORTES)
        url.httpGet().responseObject { request: Request, response: Response, result: Result<ArrayList<Incidente>, FuelError> ->

            incidentes = result.get()
            Log.wtf("Incidentes",incidentes.toString())

            viewManager = LinearLayoutManager(this)
            viewAdapter = Incident(incidentes, {incident: Incidente -> onIncidentClicked(incident) })

            recyclerView = findViewById<RecyclerView>(R.id.main_recycler_incident).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter

            }

            for (incident:Incidente in incidentes){
                incident.place = LatLng(incident.latitud,incident.longitud)
            }

            val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.main_map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }


        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
        fusedLocationClient.lastLocation
                .addOnSuccessListener {location: Location? ->
                    val currentPlace = LatLng(location?.latitude!!,location.longitude)
                    Log.wtf("LOC",currentPlace.toString())
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPlace,16f))
                }
    }

    fun showIncidents(v: View){
        hideIncidents()
    }

    fun hideIncidents(){
        if(main_nested.visibility == View.GONE){
            main_nested.visibility = View.VISIBLE
        }else{
            main_nested.visibility = View.GONE
        }
    }

    fun onIncidentClicked(incidente: Incidente){
        hideIncidents()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(incidente.place,16f))
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        for(incidente:Incidente in incidentes){
            Log.d("Incidente",incidente.toString())
            mMap.addMarker(MarkerOptions().position(incidente.place).title(incidente.lugar))
        }


        setUpMap()
    }

}
