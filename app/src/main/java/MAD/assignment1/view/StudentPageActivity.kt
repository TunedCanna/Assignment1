package MAD.assignment1.view

import MAD.assignment1.model.User
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import uni.worksheet3.R

class StudentPageActivity : AppCompatActivity() {

    lateinit var practicalFragment: PracticalRecyclerFragment
    lateinit var logoutButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)

        practicalFragment = PracticalRecyclerFragment()
        supportFragmentManager.beginTransaction().replace(R.id.practicalResults, practicalFragment)
            .commit()

        logoutButton = findViewById(R.id.logoutButton2)


        logoutButton.setOnClickListener {
            User.logoutUser(this)
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}