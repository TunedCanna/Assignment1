package MAD.assignment1.control

import MAD.assignment1.model.Student
import MAD.assignment1.model.StudentList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class StudentAdapter(
    var studentList: StudentList
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryImageView: ImageView = itemView.findViewById(R.id.countryImageView)
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var marksTextView: TextView = itemView.findViewById(R.id.marksTextView)
        var editButton: ImageButton = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newView = layoutInflater.inflate(R.layout.rec_user_list_overview, parent, false)
        val studentViewHolder = StudentViewHolder(newView)
        return studentViewHolder
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.countryImageView.setImageResource(CountryData.getFlag(student.country)!!)
        holder.nameTextView.text = student.name
        holder.marksTextView.text = "70%" //TODO make dynamic
    }

    override fun getItemCount(): Int {
        return studentList.size()
    }
}