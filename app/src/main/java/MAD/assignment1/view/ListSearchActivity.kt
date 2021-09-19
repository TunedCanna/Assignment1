package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.CountryData
import MAD.assignment1.control.database.PracMarkerSchema.*
import MAD.assignment1.model.*

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uni.worksheet3.R

class ListSearchActivity : AppCompatActivity() {

    var loggedInUser = User(-1, "", "")
    //private var loggedInUser: User = Admin

    lateinit var spinner: Spinner
    lateinit var queryEditText: EditText
    lateinit var searchButton: ImageButton
    lateinit var addButton: Button
    lateinit var logoutButton: ImageButton

    //Popop box
    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var nameEditText3: EditText
    lateinit var emailEditText3 : EditText
    lateinit var usernameEditText3: EditText
    lateinit var pinEditText3: EditText
    lateinit var countrySpinner: Spinner
    lateinit var instructorEditText3: EditText
    lateinit var cancelButton3: Button
    lateinit var confirmButton3: Button

    //This is the fragment that contains the listed results
    lateinit var currentList: SearchableFragment

    private val adminList = arrayListOf("Instructors", "Practicals", "Students")
    private val instructorList = arrayListOf("Students")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_search)


        loggedInUser = User.getLoggedInUser(this)

        //Find view items
        spinner = findViewById(R.id.selectionSpinner)
        queryEditText = findViewById(R.id.queryEditText)
        searchButton = findViewById(R.id.searchButton)
        addButton = findViewById(R.id.addButton)
        logoutButton = findViewById(R.id.logoutButton)

        //Make search button purple
        searchButton.drawable.setTint(Color.parseColor("#FF6200EE"))

        //Set spinner list based on logged in user
        val spinnerAdapter: ArrayAdapter<String> = if(loggedInUser.authLevel == AuthData.ADMIN) {
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
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        //Search on click listener
        searchButton.setOnClickListener {
            currentList.queryDatabase(queryEditText.text.toString())
            currentList.updateListView(0, 100)
        }

        //This is to make pressing enter on keyboard while in search to "press search button"
        queryEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                currentList.queryDatabase(queryEditText.text.toString())
                currentList.updateListView(0, 100)
                return@OnKeyListener true
            }
            false
        })

        logoutButton.setOnClickListener {
            User.logoutUser(this)
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        addButton.setOnClickListener {
            createNewDialog()
        }

    }


    fun spinnerUpdate() {
        addButton.text = "Add ${spinner.selectedItem.toString().dropLast(1)}"

        when (spinner.selectedItem.toString()) {
            "Students" -> {
                queryEditText.hint = "Search ${spinner.selectedItem}"
                currentList = StudentRecyclerFragment()
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
            "Instructors" -> {
                queryEditText.hint = "Search ${spinner.selectedItem}"
                currentList = InstructorRecyclerFragment()
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
            "Practicals" -> {
                queryEditText.hint = "Search UNAVAILABLE"
                currentList = PracticalRecyclerFragment.newInstance("")
                supportFragmentManager.beginTransaction().replace(R.id.listResults, currentList as Fragment)
                    .commit()
            }
        }
    }

    fun createNewDialog() {
        dialogBuilder = AlertDialog.Builder(this)
        val contactPopupView = layoutInflater.inflate(R.layout.add_user_popup, null)
        nameEditText3 = contactPopupView.findViewById(R.id.nameEditText3)
        emailEditText3 = contactPopupView.findViewById(R.id.emailEditText3)
        usernameEditText3 = contactPopupView.findViewById(R.id.usernameEditText3)
        pinEditText3 = contactPopupView.findViewById(R.id.pinEditText3)
        countrySpinner = contactPopupView.findViewById(R.id.countrySpinner)
        instructorEditText3 = contactPopupView.findViewById(R.id.instructorEditText3)
        cancelButton3 = contactPopupView.findViewById(R.id.cancelButton3)
        confirmButton3 = contactPopupView.findViewById(R.id.confirmButton3)

        countrySpinner.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, CountryData.getCountrySet().toList())


        fun checkUsedUsername(username: String): Boolean {
            val instructorList = InstructorList()
            instructorList.load(this, "${InstructorTable.Cols.USERNAME} = '$username'")
            val studentList = StudentList()
            studentList.load(this, "${StudentTable.Cols.USERNAME} = '$username'")
            val admin = Admin()
            admin.load(this)
            return instructorList.size() > 0 || studentList.size() > 0 || username == admin.username
        }

        when (spinner.selectedItem.toString()) {
            "Students" -> {
                confirmButton3.setOnClickListener {

                    nameEditText3.setBackgroundColor(Color.WHITE)
                    emailEditText3.setBackgroundColor(Color.WHITE)
                    usernameEditText3.setBackgroundColor(Color.WHITE)
                    pinEditText3.setBackgroundColor(Color.WHITE)
                    instructorEditText3.setBackgroundColor(Color.WHITE)

                    var isValid = true

                    if (pinEditText3.length() != 4) {
                        isValid = false
                        pinEditText3.setBackgroundColor(Color.RED)
                    }
                    if (nameEditText3.length() == 0) {
                        isValid = false
                        nameEditText3.setBackgroundColor(Color.RED)
                    }
                    if (emailEditText3.length() == 0) {
                        isValid = false
                        emailEditText3.setBackgroundColor(Color.RED)
                    }
                    if (instructorEditText3.length() == 0) {
                        isValid = false
                        instructorEditText3.setBackgroundColor(Color.RED)
                    }
                    if (usernameEditText3.length() == 0) {
                        isValid = false
                        usernameEditText3.setBackgroundColor(Color.RED)
                    } else {
                        if (checkUsedUsername(usernameEditText3.text.toString())) { //If username already exists
                            isValid = false
                            usernameEditText3.setBackgroundColor(Color.RED)
                        }
                    }
                    if(isValid) {
                        //Add to database
                        val studentList = StudentList()
                        studentList.load(this, "${StudentTable.Cols.PIN} = '-1'")
                        studentList.add(Student(
                            nameEditText3.text.toString(),
                            emailEditText3.text.toString(),
                            usernameEditText3.text.toString(),
                            pinEditText3.text.toString(),
                            countrySpinner.selectedItem.toString(),
                            instructorEditText3.text.toString()))
                        spinnerUpdate()
                        dialog.dismiss()
                    }
                }
            }
            "Instructors" -> {
                instructorEditText3.visibility = View.GONE

                confirmButton3.setOnClickListener {

                    nameEditText3.setBackgroundColor(Color.WHITE)
                    emailEditText3.setBackgroundColor(Color.WHITE)
                    usernameEditText3.setBackgroundColor(Color.WHITE)
                    pinEditText3.setBackgroundColor(Color.WHITE)

                    var isValid = true

                    if (pinEditText3.length() != 4) {
                        isValid = false
                        pinEditText3.setBackgroundColor(Color.RED)
                    }
                    if (nameEditText3.length() == 0) {
                        isValid = false
                        nameEditText3.setBackgroundColor(Color.RED)
                    }
                    if (emailEditText3.length() == 0) {
                        isValid = false
                        emailEditText3.setBackgroundColor(Color.RED)
                    }
                    if (usernameEditText3.length() == 0) {
                        isValid = false
                        usernameEditText3.setBackgroundColor(Color.RED)
                    } else {
                        if (checkUsedUsername(usernameEditText3.text.toString())) { //If username already exists
                            isValid = false
                            usernameEditText3.setBackgroundColor(Color.RED)
                        }
                    }
                    if(isValid) {
                        //Add to database
                        val instructorList = InstructorList()
                        instructorList.load(this, "${InstructorTable.Cols.PIN} = '-1'")
                        instructorList.add(
                            Instructor(
                            nameEditText3.text.toString(),
                            emailEditText3.text.toString(),
                            usernameEditText3.text.toString(),
                            pinEditText3.text.toString(),
                            countrySpinner.selectedItem.toString())
                        )
                        spinnerUpdate()
                        dialog.dismiss()
                    }
                }

            }
            "Practicals" -> {
                nameEditText3.hint = "Title"
                emailEditText3.hint = "Description"
                pinEditText3.hint = "Marks"

                pinEditText3.inputType = InputType.TYPE_CLASS_NUMBER

                countrySpinner.visibility = View.GONE
                usernameEditText3.visibility = View.GONE
                instructorEditText3.visibility = View.GONE

                confirmButton3.setOnClickListener {

                    nameEditText3.setBackgroundColor(Color.WHITE)
                    emailEditText3.setBackgroundColor(Color.WHITE)
                    pinEditText3.setBackgroundColor(Color.WHITE)

                    var isValid = true

                    if (pinEditText3.length() == 0 || pinEditText3.text.toString().toDouble() < 1) {
                        isValid = false
                        pinEditText3.setBackgroundColor(Color.RED)
                    }

                    if (emailEditText3.length() == 0) {
                        isValid = false
                        emailEditText3.setBackgroundColor(Color.RED)
                    }
                    if (nameEditText3.length() == 0) {
                        isValid = false
                        nameEditText3.setBackgroundColor(Color.RED)
                    } else {
                        val practicalList = PracticalList()
                        practicalList.load(this)
                        practicalList.loadStudentPracticals("${PracticalTable.Cols.TITLE} = '${nameEditText3.text}'")
                        if (practicalList.size() > 0) { //If username already exists
                            isValid = false
                            nameEditText3.setBackgroundColor(Color.RED)
                        }
                    }

                    if (isValid) {
                        cancelButton3.text = "TRUE"
                    } else {
                        cancelButton3.text = "FALSE"
                    }

                    if(isValid) {
                        //Add to database
                        val practicalList = PracticalList()
                        practicalList.load(this)
                        val studentList = StudentList()
                        studentList.load(this, null)
                        practicalList.add(
                            Practical(
                            nameEditText3.text.toString(),
                            emailEditText3.text.toString(),
                            pinEditText3.text.toString().toDouble()))
                        spinnerUpdate()
                        dialog.dismiss()
                    }
                }
            }
        }

        cancelButton3.setOnClickListener {
            dialog.dismiss()
        }

        dialogBuilder.setView(contactPopupView)
        dialog = dialogBuilder.create()
        dialog.show()

    }
}