package MAD.assignment1.control

import MAD.assignment1.model.Practical
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class PracticalAdapter(
    var practicalList: ArrayList<Practical>
) : RecyclerView.Adapter<PracticalAdapter.PracticalViewHolder>() {

    inner class PracticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        var descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        var marksTextView2: TextView = itemView.findViewById(R.id.marksTextView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newView = layoutInflater.inflate(R.layout.rec_practical_list_overview, parent, false)
        val practicalViewHolder = PracticalViewHolder(newView)
        return practicalViewHolder
    }

    override fun onBindViewHolder(holder: PracticalViewHolder, position: Int) {
        val practical = practicalList[position]

        holder.titleTextView.text = practical.title
        holder.descriptionTextView.text = practical.description
        holder.marksTextView2.text = if (practical.finalMarks == -1.0) {
            "NYM"
        } else {
            "${practical.finalMarks}/${practical.availableMarks}"
        }
    }

    override fun getItemCount(): Int {
        return practicalList.size
    }
}