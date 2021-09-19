package MAD.assignment1.view

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.InstructorAdapter
import MAD.assignment1.control.PracticalAdapter
import MAD.assignment1.control.TestData
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.model.InstructorList
import MAD.assignment1.model.PracticalList
import MAD.assignment1.model.User
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uni.worksheet3.R

class PracticalRecyclerFragment: Fragment(), SearchableFragment {

    companion object {
        const val USERNAME = "MAD.assignment1.PracticalRecyclerFragment.username"

        fun newInstance(username: String): PracticalRecyclerFragment {
            val frag = PracticalRecyclerFragment()
            val args = Bundle()
            args.putString(USERNAME, username)
            frag.arguments = args
            return frag
        }
    }

    lateinit var practicalList: PracticalList
    lateinit var practicalAdapter: PracticalAdapter
    var username: String? = ""
    var loggedInAuth: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = requireArguments().getString(USERNAME)
        loggedInAuth = User.getLoggedInUser(requireContext()).authLevel
        queryDatabase(username!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentSelectorView: View = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        val recyclerView: RecyclerView = fragmentSelectorView.findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        practicalAdapter = PracticalAdapter(practicalList, loggedInAuth, username == "", requireContext())
        recyclerView.adapter = practicalAdapter

        return fragmentSelectorView
    }

    override fun queryDatabase(queryText: String) {
        practicalList = PracticalList()
        practicalList.load(requireContext())
        if (queryText == "") {
            practicalList.loadOverviewPractical()
        } else {
            practicalList.loadStudentPracticals("studentUsername = '$queryText'")
        }


    }

    override fun updateListView(positionStart: Int, itemCount: Int) {
        practicalAdapter.practicalList = practicalList
        practicalAdapter.notifyDataSetChanged()
    }
}