package MAD.assignment1.view

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import uni.worksheet3.R

class ListSearchActivity : AppCompatActivity() {

    val spinner: Spinner = findViewById(R.id.selectionSpinner)
    val queryEditText: EditText = findViewById(R.id.queryEditText)
    val searchButton: ImageButton = findViewById(R.id.searchButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_search)

        var studentRecyclerFragment = supportFragmentManager.findFragmentById(R.id.listResults) as StudentRecyclerFragment?
        if (studentRecyclerFragment == null) {
            studentRecyclerFragment = StudentRecyclerFragment()
            supportFragmentManager.beginTransaction().add(R.id.listResults, studentRecyclerFragment).commit()
        }

        spinner
    }
}