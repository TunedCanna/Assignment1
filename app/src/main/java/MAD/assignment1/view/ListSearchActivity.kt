package MAD.assignment1.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import uni.worksheet3.R

class ListSearchActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    lateinit var queryEditText: EditText
    lateinit var searchButton: ImageButton
    lateinit var addButton: Button

    lateinit var studentRecyclerFragment: StudentRecyclerFragment

    private val adminList = arrayListOf("Instructors", "Practicals", "Students")
    private val instructorList = arrayListOf("Practicals", "Students")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_search)

        //Find view items
        spinner = findViewById(R.id.selectionSpinner)
        queryEditText = findViewById(R.id.queryEditText)
        searchButton = findViewById(R.id.searchButton)
        addButton = findViewById(R.id.addButton)

        studentRecyclerFragment = supportFragmentManager.findFragmentById(R.id.listResults) as StudentRecyclerFragment?
        if (studentRecyclerFragment == null) {
            studentRecyclerFragment = StudentRecyclerFragment()
            supportFragmentManager.beginTransaction().add(R.id.listResults, studentRecyclerFragment)
                .commit()
        }

        //Make search button purple
        searchButton.drawable.setTint(Color.parseColor("#FF6200EE"))

        //TODO: This needs to be selected by the logged in user
        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, adminList)
        spinner.adapter = spinnerAdapter

        //Spinner on select listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerUpdate()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



    }

    private fun spinnerUpdate() {
        queryEditText.hint = "Search ${spinner.selectedItem}"
        addButton.text = "Add ${spinner.selectedItem.toString().dropLast(1)}"

        when (spinner.selectedItem.toString()) {
            "Students" -> {

            }
        }
    }
}