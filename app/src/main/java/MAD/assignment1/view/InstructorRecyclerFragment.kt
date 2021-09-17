package MAD.assignment1.view

import MAD.assignment1.control.InstructorAdapter
import MAD.assignment1.control.StudentAdapter
import MAD.assignment1.control.TestData
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.InstructorTable
import MAD.assignment1.model.InstructorList
import MAD.assignment1.model.PracticalList
import MAD.assignment1.model.StudentList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

//TODO: Fix crash on rotate, needs an empty constructor or sumthin
class InstructorRecyclerFragment(
    val parentActivity: ListSearchActivity
): Fragment(), SearchableFragment {

    lateinit var instructorList: InstructorList
    lateinit var instructorAdapter: InstructorAdapter

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
        val recyclerView: RecyclerView = fragmentSelectorView.findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        instructorAdapter = InstructorAdapter(instructorList) //TODO make dynamic import
        recyclerView.adapter = instructorAdapter

        return fragmentSelectorView
    }

    override fun queryDatabase(queryText: String) {
        instructorList = InstructorList()
        instructorList.load(requireActivity(), "${InstructorTable.Cols.NAME} LIKE '%$queryText%'")
    }

    override fun updateListView(positionStart: Int, itemCount: Int) {
        instructorAdapter.instructorList = instructorList
        instructorAdapter.notifyDataSetChanged()
    }
}