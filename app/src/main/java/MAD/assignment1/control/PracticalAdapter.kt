package MAD.assignment1.control

import MAD.assignment1.model.PracticalList
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class PracticalAdapter(
    var practicalList: PracticalList,
    var loggedInAuth: Int,
    var isOverview: Boolean,
    var context: Context
) : RecyclerView.Adapter<PracticalAdapter.PracticalViewHolder>() {

    inner class PracticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        var descriptionEditText: EditText = itemView.findViewById(R.id.descriptionEditText)
        var availableMarksEditText: EditText = itemView.findViewById(R.id.marksTextView2)
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton2)
        var finalMarksEditText: EditText = itemView.findViewById(R.id.finalMarkEditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newView = layoutInflater.inflate(R.layout.rec_practical_list_overview, parent, false)
        val practicalViewHolder = PracticalViewHolder(newView)
        return practicalViewHolder
    }


    override fun onBindViewHolder(holder: PracticalViewHolder, position: Int) {
        val practical = practicalList[position]
        var firstTime = true

        holder.titleTextView.text = practical.title
        holder.descriptionEditText.setText(practical.description)
        holder.availableMarksEditText.setText("/${practical.availableMarks}")

        //Marking text field checker and database updater
        holder.finalMarksEditText.addTextChangedListener {
            fun isValidMark(finalMark: String, availableMark: Double): Int {
                if (finalMark == "") {
                    return -1
                }
                val doubleMark = finalMark.toDoubleOrNull()
                if (doubleMark == null) {
                    return 0
                } else if (doubleMark in 0.0..availableMark) {
                    return 1
                } else {
                    return 0
                }
            }
            if (!firstTime) {
                var validInt =
                    isValidMark(holder.finalMarksEditText.text.toString(), practical.availableMarks)
                if (validInt == 1) {
                    practical.finalMarks = holder.finalMarksEditText.text.toString().toDouble()
                    holder.finalMarksEditText.setTextColor(Color.parseColor("#00b622"))
                    practicalList.editSingle(practical)
                } else if (validInt == 0) {
                    holder.finalMarksEditText.setTextColor(Color.parseColor("#c91404"))
                } else if (validInt == -1) {
                    practical.finalMarks = -1.0
                    practicalList.editSingle(practical)
                }
            }
            firstTime = false
        }

        holder.descriptionEditText.addTextChangedListener {

            practical.description = holder.descriptionEditText.text.toString()
            practicalList.editAll(practical)

            firstTime = false
        }

        holder.deleteButton.setOnClickListener {
            practicalList.remove(practical)
            notifyItemRemoved(position)

        }

        //Setting up correct interactivity for user

        holder.availableMarksEditText.apply {
            isEnabled = false
            isClickable = false
        }

        if (loggedInAuth != 3) {
            holder.deleteButton.visibility = View.GONE
            holder.descriptionEditText.apply {
                isEnabled = false
                isClickable = false
            }
            holder.availableMarksEditText.apply {
                isEnabled = false
                isClickable = false
            }
        }
        if (isOverview || loggedInAuth == 1) {
            holder.finalMarksEditText.apply {
                isEnabled = false
                isClickable = false
            }
        }
        if (!isOverview) {
            holder.deleteButton.visibility = View.GONE
            holder.descriptionEditText.apply {
                isEnabled = false
                isClickable = false
            }
        }
        holder.finalMarksEditText.setText(
            if (practical.isMarked()) {
                practical.finalMarks.toString()
            } else {
                ""
            }
        )
        if (holder.finalMarksEditText.text.toString() != "") {
            holder.deleteButton.visibility = View.GONE
            holder.descriptionEditText.apply {
                isEnabled = false
                isClickable = false
            }
        } else {
            if (isOverview) {
                holder.availableMarksEditText.apply {
                    isEnabled = true
                    isClickable = true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return practicalList.size()
    }
}