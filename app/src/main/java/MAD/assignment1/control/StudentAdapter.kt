package MAD.assignment1.control

import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.model.PracticalList
import MAD.assignment1.model.Student
import MAD.assignment1.model.StudentList
import MAD.assignment1.view.StudentPageActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R
import kotlin.math.roundToInt

class StudentAdapter(
    var studentList: StudentList
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    lateinit var context: Context

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryImageView: ImageView = itemView.findViewById(R.id.countryImageView)
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var marksTextView: TextView = itemView.findViewById(R.id.marksTextView)
        var userOverviewConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.userOverviewConstraintLayout)
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        var editButton: ImageButton = itemView.findViewById(R.id.editButton)
        init {
            context = itemView.context
        }
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
        holder.editButton.visibility = View.GONE


        holder.userOverviewConstraintLayout.setOnClickListener {
            context.startActivity(StudentPageActivity.getIntent(context, student.username))
        }

        holder.deleteButton.setOnClickListener {
            studentList.remove(student)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size()
    }


}