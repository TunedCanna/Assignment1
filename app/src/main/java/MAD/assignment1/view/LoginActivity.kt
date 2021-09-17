package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.*
import MAD.assignment1.model.Admin
import MAD.assignment1.model.InstructorList
import MAD.assignment1.model.StudentList
import android.app.Activity
import android.content.ContentValues
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

    lateinit var snackBar: Snackbar

    //Used to verify pin similarity on initial login
    var pinVerify = ""


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

        //Check if the admin has been created
        val admin = Admin()
        admin.load(this)
        if (admin.isCreated()) { //Begin login
            loginButton.setOnClickListener {
                verifyLogin()
            }
        } else { //Begin initial registration

            showSnackbar("NO ADMIN ACCOUNT CREATED\nPlease register above")
            usernameInput.hint = "Enter admin username"
            loginButton.setOnClickListener {
                verifyNewDetails()
            }
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



    fun verifyLogin() {

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
                    showSnackbar("Please reenter PIN to verify")
                }
            } else { //If currently verifying pin
                if (pinVerify == pin0.text.toString() + pin1.text.toString() + //If pin verify success
                    pin2.text.toString() + pin3.text.toString())
                {
                    //Update admintable
                    val db = PracMarkerDbHelper(this).writableDatabase
                    var cv = ContentValues().apply {
                        put(AdminTable.Cols.USERNAME, loginButton.text.toString())
                        put(AdminTable.Cols.PIN, pinVerify)
                    }
                    db.insert(AdminTable.NAME, null, cv)

                    //Update loggedintable
                    cv = ContentValues().apply {
                        put(LoggedInTable.Cols.TYPE, AuthData.ADMIN)
                        put(LoggedInTable.Cols.USERNAME, loginButton.text.toString())
                    }
                    db.insert(LoggedInTable.NAME, null, cv)

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