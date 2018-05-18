package mobiles.cucei.seguridadadmin.DataClasses

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Incidente (var latitud:Double = 0.0,
                      var longitud:Double = 0.0,
                      var suceso: String = "",
                      var nombre: String = "",
                      var fecha: String = "",
                      var lugar: String = "",
                      var id:String = "") : Serializable {
    var place = LatLng(latitud,longitud)
}