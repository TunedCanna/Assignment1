package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.TestData
import MAD.assignment1.control.database.PracMarkerSchema.*
import MAD.assignment1.model.Admin
import MAD.assignment1.model.InstructorList
import MAD.assignment1.model.StudentList
import MAD.assignment1.model.User
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import uni.worksheet3.R

class LoginActivity : AppCompatActivity() {

    //Declare
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var pin0: EditText
    lateinit var pin1: EditText
    lateinit var pin2: EditText
    lateinit var pin3: EditText
    lateinit var usernameInput: EditText
    lateinit var loginButton: Button
    lateinit var clearButton: Button
    lateinit var testDataButton: Button

    lateinit var snackBar: Snackbar

    //Used to verify pin similarity on initial login
    var pinVerify = ""

    lateinit var admin: Admin
    lateinit var loggedInUser: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Find view objects
        coordinatorLayout = findViewById(R.id.coordinatorLayoutLogin)
        pin0 = findViewById(R.id.pinInput0)
        pin1 = findViewById(R.id.pinInput1)
        pin2 = findViewById(R.id.pinInput2)
        pin3 = findViewById(R.id.pinInput3)
        usernameInput = findViewById(R.id.usernameInput)
        loginButton = findViewById(R.id.loginButton)
        clearButton = findViewById(R.id.clearButton)
        testDataButton = findViewById(R.id.testDataButton)

        //Set listeners for 4 pin fields to curse through
        pin0.addTextChangedListener {
            if (it!!.length == 1) {
                pin1.requestFocus()
            }
        }
        pin1.addTextChangedListener {
            if (it!!.length == 1) {
                pin2.requestFocus()
            }
        }
        pin2.addTextChangedListener {
            if (it!!.length == 1) {
                pin3.requestFocus()
            }
        }
        pin3.addTextChangedListener {
            if (it!!.length == 1) {
                pin3.clearFocus()
                closeKeyboard()
            }
        }

        clearButton.setOnClickListener {
            admin.clearAdminDB()
            TestData.wipeInstructorTable(this)
            TestData.wipeStudentTable(this)
        }
        testDataButton.setOnClickListener {
            TestData.writeTestInstructors(this)
            TestData.writeTestStudents(this)
        }


        //Check if a user is logged in
        loggedInUser = User.getLoggedInUser(this)
        if (loggedInUser.authLevel == -1) { //If no user is logged in
            //Check if the admin has been created
            admin = Admin()
            admin.load(this)
            if (admin.isCreated()) { //Begin login
                loginButton.setOnClickListener {
                    verifyLogin()
                }
            } else { //Begin initial registration

                showSnackbar("NO ADMIN ACCOUNT CREATED\nPlease register above")
                usernameInput.hint = "Enter admin username"
                loginButton.text = "register"
                loginButton.setOnClickListener {
                    verifyNewDetails()
                }
            }
        } else if (loggedInUser.authLevel == AuthData.ADMIN || loggedInUser.authLevel == AuthData.INSTRUCTOR) {
            openListSearchActivity()
        } else if (loggedInUser.authLevel == AuthData.STUDENT) {
            openStudentPageActivity()
        }

    }



    fun showSnackbar(output: String) {
        closeKeyboard()
        snackBar = Snackbar.make(coordinatorLayout, output, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("OK", View.OnClickListener {
            snackBar.dismiss()
        })
        snackBar.show()
    }

    fun closeKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(coordinatorLayout.windowToken, 0)
    }

    fun openListSearchActivity() {
        intent = Intent(this, ListSearchActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun openStudentPageActivity() {
        intent = Intent(this, StudentPageActivity::class.java)
        startActivity(intent)
        finish()
    }



    fun verifyLogin() {

        fun incorrect() {
            showSnackbar("Incorrect username and/or password!")
            pin0.text.clear()
            pin1.text.clear()
            pin2.text.clear()
            pin3.text.clear()
        }

        val usernameString = usernameInput.text.toString()
        if ((usernameString != "") && //If all fields are inputted
            (pin0.text.toString() != "") &&
            (pin1.text.toString() != "") &&
            (pin2.text.toString() != "") &&
            (pin3.text.toString() != ""))
        {
            val pinString = pin0.text.toString() + pin1.text.toString() + pin2.text.toString() + pin3.text.toString()

            val studentList = StudentList()
            studentList.load(this, "${StudentTable.Cols.USERNAME} = '$usernameString'")

            val instructorList = InstructorList()
            instructorList.load(this, "${InstructorTable.Cols.USERNAME} = '$usernameString'")

            if (usernameString == admin.username && pinString == admin.pin) { //Check if admin is logging in
                User.writeLoggedInUser(this, AuthData.ADMIN, "", usernameString)
                openListSearchActivity()
            } else if (studentList.size() > 0) {
                if (pinString == studentList[0].pin) {
                    User.writeLoggedInUser(this, AuthData.STUDENT, "", usernameString)
                    openStudentPageActivity()
                } else {
                    incorrect()
                }
            } else if (instructorList.size() > 0) {
                if (pinString == instructorList[0].pin) {
                    User.writeLoggedInUser(this, AuthData.INSTRUCTOR, usernameString, usernameString)
                    openListSearchActivity()
                } else {
                    incorrect()
                }
            } else {
                incorrect()
            }


        }
    }

    fun verifyNewDetails() {
        if ((usernameInput.text.toString() != "") && //If all fields are inputted
            (pin0.text.toString() != "") &&
            (pin1.text.toString() != "") &&
            (pin2.text.toString() != "") &&
            (pin3.text.toString() != ""))
        {
            if (pinVerify == "") { //If not currently verifying pin
                val username = usernameInput.text.toString()
                val instructorList = InstructorList()
                instructorList.load(this, "${InstructorTable.Cols.USERNAME} = '$username'")
                val studentList = StudentList()
                studentList.load(this, "${StudentTable.Cols.USERNAME} = '$username'")

                if (instructorList.size() > 0 || studentList.size() > 0) { //If username already exists
                    showSnackbar("Username already taken\nPlease try again")
                } else { //If username does not exist
                    usernameInput.isEnabled = false
                    pinVerify = pin0.text.toString() + pin1.text.toString() +
                            pin2.text.toString() + pin3.text.toString()
                    pin0.text.clear()
                    pin1.text.clear()
                    pin2.text.clear()
                    pin3.text.clear()
                    showSnackbar("Please re enter PIN to verify")
                }
            } else { //If currently verifying pin
                if (pinVerify == pin0.text.toString() + pin1.text.toString() + //If pin verify success
                    pin2.text.toString() + pin3.text.toString())
                {
                    //Update admintable
                    admin.addAdmin(usernameInput.text.toString(), pinVerify)
                    //Update loggedintable
                    User.writeLoggedInUser(this, AuthData.ADMIN,"", usernameInput.text.toString())

                    // change to ListSearchActivity
                    intent = Intent(this, ListSearchActivity::class.java)
                    startActivity(intent)
                    finish()

                } else { //if pin verify failed
                    pinVerify = ""
                    usernameInput.isEnabled = true
                    pin0.text.clear()
                    pin1.text.clear()
                    pin2.text.clear()
                    pin3.text.clear()
                    showSnackbar("Incorrect\nPlease try again")
                }
            }
        } else { //If all fields aren't inputted
            showSnackbar("Please enter login details")
        }
    }





}