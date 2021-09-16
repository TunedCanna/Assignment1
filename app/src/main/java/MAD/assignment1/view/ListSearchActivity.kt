package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.model.Admin
import MAD.assignment1.model.Instructor
import MAD.assignment1.model.User
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uni.worksheet3.R

class ListSearchActivity : AppCompatActivity() {

    //private var loggedInUser: User = Instructor("Anthony Gregleson", "anthonyg@school.com", "AnthonyG", 1234, "Australia")
    private var loggedInUser: User = Admin()

    lateinit var spinner: Spinner
    lateinit var queryEditText: EditText
    lateinit var searchButton: ImageButton
    lateinit var addButton: Button

    //This is the fragment that contains the listed results
    lateinit var currentList: SearchableFragment

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

        //Make search button purple
        searchButton.drawable.setTint(Color.parseColor("#FF6200EE"))

        //Set spinner list based on logged in user
        val spinnerAdapter: ArrayAdapter<String> = if(getLoggedInUser().getAuthLevel() == AuthData.ADMIN) {
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, adminList)
        } else {
            ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, instructorList)
        }
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

        //Search on click listener
        searchButton.setOnClickListener {
            currentList.queryDatabase(queryEditText.text.toString())
            currentList.updateListView(0, 10)
        }


    }

    fun getLoggedInUser(): User {
        return loggedInUser
    }

    fun spinnerUpdate() {
        queryEditText.hint = "Search ${spinner.selectedItem}"
        addButton.text = "Add ${spinner.selectedItem.toString().dropLast(1)}"

        when (spinner.selectedItem.toString()) {
            "Students" -> {
                currentList = StudentRecyclerFragment(this)
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
            "Instructors" -> {
                currentList = InstructorRecyclerFragment()
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
            "Practicals" -> {
                currentList = PracticalRecyclerFragment()
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
        }
    }
}