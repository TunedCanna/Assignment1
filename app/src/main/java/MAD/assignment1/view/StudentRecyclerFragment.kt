package MAD.assignment1.view

import MAD.assignment1.control.StudentAdapter
import MAD.assignment1.model.StudentList
import MAD.assignment1.control.database.PracMarkerSchema.StudentTable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

//TODO: Fix crash on rotate, needs an empty constructor or sumthin
class StudentRecyclerFragment(
    val parentActivity: ListSearchActivity
): Fragment(), SearchableFragment {

    lateinit var studentList: StudentList
    lateinit var studentAdapter: StudentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryDatabase("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentSelectorView: View = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        var recyclerView: RecyclerView = fragmentSelectorView.findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        studentAdapter = StudentAdapter(studentList)
        recyclerView.adapter = studentAdapter

        queryDatabase("")

        return fragmentSelectorView
    }

    //If admin is logged in return "1=1"
    //Else, return "instructorUsername = '<loggedInInstructorUsername>'
    //This is so that the admin will return all students regardless of username
    private fun getUsernameWhere(): String {
        return if (parentActivity.loggedInUser.studentListUsername == "") {
            "1=1"
        } else {
            "${StudentTable.Cols.INSTRUCTORUSERNAME} = '${parentActivity.loggedInUser.studentListUsername}'"
        }
    }

    //This will load a new studentList from search criteria AND username
    //Input "" if you want to have no search criteria and return all students that the logged in user has auth for
    //Input "<name search query>" if you want to search for all student names that the logged in user has auth for
    override fun queryDatabase(queryText: String) {
        val toQueryText = if (queryText == "") {
            ""
        } else {
            " AND ${StudentTable.Cols.NAME} LIKE '%$queryText%'"
        }
        studentList = StudentList()
        studentList.load(requireActivity(), getUsernameWhere() + toQueryText)
    }

    override fun updateListView(positionStart: Int, itemCount: Int) {
        studentAdapter.studentList = studentList
        studentAdapter.notifyDataSetChanged()
    }
}