package MAD.assignment1.view

import MAD.assignment1.control.PracticalAdapter
import MAD.assignment1.control.TestData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class PracticalRecyclerFragment(): Fragment() {

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
        val practicalAdapter = PracticalAdapter(TestData.getTestPracticals()) //TODO make dynamic import
        recyclerView.adapter = practicalAdapter

        return fragmentSelectorView
    }
}