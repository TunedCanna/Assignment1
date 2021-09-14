package MAD.assignment1.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uni.worksheet3.R

class StudentPageActivity : AppCompatActivity() {

    lateinit var practicalFragment: PracticalRecyclerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_page)

        practicalFragment = PracticalRecyclerFragment()
        supportFragmentManager.beginTransaction().replace(R.id.practicalResults, practicalFragment)
            .commit()
    }
}