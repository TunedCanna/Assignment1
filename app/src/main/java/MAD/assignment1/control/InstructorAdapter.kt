package MAD.assignment1.control

import MAD.assignment1.model.Instructor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class InstructorAdapter(
    var instructorList: ArrayList<Instructor>
) : RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryImageView: ImageView = itemView.findViewById(R.id.countryImageView)
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var marksTextView: TextView = itemView.findViewById(R.id.marksTextView)
        var editButton: ImageButton = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newView = layoutInflater.inflate(R.layout.rec_user_list_overview, parent, false)
        val instructorViewHolder = InstructorViewHolder(newView)
        return instructorViewHolder
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val instructor = instructorList[position]
        holder.countryImageView.setImageResource(CountryData.getFlag(instructor.country)!!)
        holder.nameTextView.text = instructor.name
        holder.marksTextView.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return instructorList.size
    }
}