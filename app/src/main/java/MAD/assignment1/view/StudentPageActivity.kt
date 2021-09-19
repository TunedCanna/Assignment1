package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.CountryData
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.*
import MAD.assignment1.model.Practical
import MAD.assignment1.model.PracticalList
import MAD.assignment1.model.StudentList
import MAD.assignment1.model.User
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import uni.worksheet3.R
import kotlin.math.roundToInt

class StudentPageActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "MAD.assignment1.StudentPageActivity.username"

        fun getIntent(c: Context, username: String) : Intent {
            return Intent(c, StudentPageActivity::class.java).apply {
                putExtra(USERNAME, username)
            }
        }
    }

    var loggedInUser = User(-1, "", "")

    lateinit var practicalFragment: PracticalRecyclerFragment

    lateinit var logoutButton: ImageButton
    lateinit var nameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var usernameEditText: EditText
    lateinit var pinEditText: EditText
    lateinit var instructorEditText: EditText
    lateinit var countryImage: ImageView
    lateinit var overallMarks: TextView

    lateinit var student: StudentList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)

        var firstTime = true

        //Get logged in user
        loggedInUser = User.getLoggedInUser(this)

        //Find view items
        logoutButton = findViewById(R.id.logoutButton2)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        usernameEditText = findViewById(R.id.usernameEditText)
        pinEditText = findViewById(R.id.pinEditText)
        instructorEditText = findViewById(R.id.instructorEditText)
        countryImage = findViewById(R.id.countryImageView1)
        overallMarks = findViewById(R.id.marksTextView1)

        //Load the student whose page it is
        student = StudentList()
        student.load(this, "${StudentTable.Cols.USERNAME} = '${intent.getStringExtra(USERNAME)}'")


        //Fill out student fields
        nameEditText.setText(student[0].name)
        emailEditText.setText(student[0].email)
        usernameEditText.setText(student[0].username)
        pinEditText.setText(student[0].pin)
        instructorEditText.setText(student[0].instructorUsername)
        countryImage.setImageResource(CountryData.getFlag(student[0].country)!!)

        //If student is logged in, make non intractable
        if (loggedInUser.authLevel == AuthData.STUDENT) {
            nameEditText.apply {
                isEnabled = false
                isClickable = false
            }
            emailEditText.apply {
                isEnabled = false
                isClickable = false
            }
            usernameEditText.apply {
                isEnabled = false
                isClickable = false
            }
            pinEditText.apply {
                isEnabled = false
                isClickable = false
            }
            instructorEditText.apply {
                isEnabled = false
                isClickable = false
            }
        }

        usernameEditText.apply {
            isEnabled = false
            isClickable = false
        }

        updateOverallMark()


        nameEditText.addTextChangedListener {

            if (!firstTime) {
                student[0].name = nameEditText.text.toString()
                student.edit(student[0])
            }
            firstTime = false
        }
        emailEditText.addTextChangedListener {

            if (!firstTime) {
                student[0].email = emailEditText.text.toString()
                student.edit(student[0])
            }
            firstTime = false
        }

        pinEditText.addTextChangedListener {
            if (!firstTime) {
                var pinText = pinEditText.text.toString()
                if (pinText.isDigitsOnly() && pinText.length == 4) {
                    pinEditText.setBackgroundColor(Color.WHITE)
                    student[0].pin = pinText
                    student.edit(student[0])
                } else {
                    pinEditText.setBackgroundColor(Color.RED)
                }
            }
            firstTime = false
        }

        instructorEditText.addTextChangedListener {

            if (!firstTime) {
                student[0].instructorUsername = instructorEditText.text.toString()
                student.edit(student[0])
            }
            firstTime = false
        }

        logoutButton.setOnClickListener {
            User.logoutUser(this)
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        practicalFragment = PracticalRecyclerFragment.newInstance(student[0].username)
        supportFragmentManager.beginTransaction().replace(R.id.practicalResults, practicalFragment)
            .commit()

    }

    fun updateOverallMark() {
        val practicals = PracticalList()
        practicals.load(this)
        practicals.loadStudentPracticals("${PracticalTable.Cols.STUDENTUSERNAME} = '${student[0].username}' AND " +
                "${PracticalTable.Cols.FINALMARKS} != -1.0")
        val finalMarksList = practicals.practicals

        val totalMarks = finalMarksList.sumOf { it.finalMarks }
        val maxMarks = finalMarksList.sumOf { it.availableMarks }

        val marks = ((totalMarks/maxMarks) * 100)
        if (marks.isNaN()) {
            overallMarks.text = "--%"
        } else {
            overallMarks.text = "${marks.roundToInt()}%"
            if (marks.roundToInt() <= 50) {
                overallMarks.setTextColor(Color.RED)
            } else if (marks.roundToInt() in 51..80) {
                overallMarks.setTextColor(Color.parseColor("#ffcd00"))
            } else {
                overallMarks.setTextColor(Color.GREEN)
            }
        }




    }
}