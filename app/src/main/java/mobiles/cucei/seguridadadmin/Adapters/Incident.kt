package mobiles.cucei.seguridadadmin.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_incident.view.*
import mobiles.cucei.seguridadadmin.DataClasses.Incidente
import mobiles.cucei.seguridadadmin.R
import java.util.*
import kotlin.collections.ArrayList

class Incident(private val myDataset: ArrayList<Incidente>, val onClickListener: (Incidente) -> Unit) :
        RecyclerView.Adapter<Incident.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        fun bind(incidente: Incidente, clickListener: (Incidente) -> Unit){
            //val date = Date(incidente.fecha)
            layout.recycler_incident_text_type.text = incidente.suceso
//            layout.recycler_incident_text_hour.text = date.hours.toString() + ":" + date.minutes.toString()
//            layout.recycler_incident_text_date.text = date.day.toString() + "/" + date.month.toString() + "/" + date.year.toString()
            layout.recycler_incident_text_date.text = incidente.fecha
            layout.recycler_incident_text_victim.text = incidente.nombre
            layout.recycler_incident_text_campus.text = incidente.lugar
            layout.setOnClickListener { clickListener(incidente) }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Incident.ViewHolder {
        // create a new view
        val view:View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_incident, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        (holder as ViewHolder).bind(myDataset[position],onClickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}