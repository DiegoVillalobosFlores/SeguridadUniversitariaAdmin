package mobiles.cucei.seguridadadmin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import mobiles.cucei.seguridadadmin.DataClasses.Incidente

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        val mapita = supportFragmentManager.findFragmentById(R.id.mapita) as SupportMapFragment
        val mapito = supportFragmentManager.findFragmentById(R.id.mapito) as SupportMapFragment

        val incidente = Incidente(20.65533543427295,-103.32559246656251,"Agresión","Diego","06:43:12 26/04/18","CUCEI",mapFragment)
        incidente.mapFragment.getMapAsync(incidente)

        val incidente2 = Incidente(20.740783427481432,-103.38087558746338,"Violación","Polo","06:43:12 26/04/18","CUCEA",mapita)
        incidente2.mapFragment.getMapAsync(incidente2)

        val incidente3 = Incidente(20.693457935368478,-103.35012674331665,"Drogadicción","Jesse","06:43:12 26/04/18","CUCSH",mapito)
        incidente3.mapFragment.getMapAsync(incidente3)
    }

}
