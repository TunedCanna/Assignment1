package MAD.assignment1.view

//This allows for StudentRecyclerFragment, PracticalRecyclerFragment and InstructorRecyclerFragment
//to all have their search called from ListSearchActivity
interface SearchableFragment {
    fun queryDatabase(queryText: String)
    fun updateListView(positionStart: Int, itemCount: Int)
}