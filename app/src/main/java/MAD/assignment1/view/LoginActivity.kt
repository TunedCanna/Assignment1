package MAD.assignment1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import uni.worksheet3.R

class LoginActivity : AppCompatActivity() {

    companion object {

    }

    lateinit var constraintLayout: ConstraintLayout
    lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        constraintLayout = findViewById(R.id.constraintLayoutLogin)
        showSnackbar()

    }

    fun showSnackbar() {
        snackBar = Snackbar.make(constraintLayout, "NO ADMIN ACCOUNT CREATED\nPlease register above", Snackbar.LENGTH_INDEFINITE)
        snackBar.show()
    }
}