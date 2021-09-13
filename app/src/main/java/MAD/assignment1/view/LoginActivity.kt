package MAD.assignment1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import uni.worksheet3.R

class LoginActivity : AppCompatActivity() {

    companion object {

    }

    lateinit var constraintLayout: ConstraintLayout
    lateinit var pin0: EditText
    lateinit var pin1: EditText
    lateinit var pin2: EditText
    lateinit var pin3: EditText
    lateinit var loginButton: Button

    lateinit var snackBar: Snackbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        constraintLayout = findViewById(R.id.constraintLayoutLogin)
        pin0 = findViewById(R.id.pinInput0)
        pin1 = findViewById(R.id.pinInput1)
        pin2 = findViewById(R.id.pinInput2)
        pin3 = findViewById(R.id.pinInput3)
        loginButton = findViewById(R.id.loginButton)

        showSnackbar()

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
            }
        }

    }

    fun showSnackbar() {
        snackBar = Snackbar.make(constraintLayout, "NO ADMIN ACCOUNT CREATED\nPlease register above", Snackbar.LENGTH_INDEFINITE)
        snackBar.show()
    }
}