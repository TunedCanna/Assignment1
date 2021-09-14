package MAD.assignment1.view

import MAD.assignment1.control.InstructorAdapter
import MAD.assignment1.control.StudentAdapter
import MAD.assignment1.control.TestData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class InstructorRecyclerFragment(): Fragment() {

    //Todo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Todo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentSelectorView: View = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        val recyclerView: RecyclerView = fragmentSelectorView.findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val instructorAdapter = InstructorAdapter(TestData.getTestInstructors()) //TODO make dynamic import
        recyclerView.adapter = instructorAdapter

        return fragmentSelectorView
    }
}