package com.arjunchecklist


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), CheckListAdapter.OnItemClickListener {
    override fun onEditItemclicked(checklist: CheckList) {
        val todoFragment = ToDoFragment.newInstance(checklist.title,checklist.date,checklist.time, checklist.id
        )
        val manager =activity!!.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, todoFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true
    }
    override fun onDeleteItemclicked(checklist: CheckList) {

        Toast.makeText(activity, "Title ${checklist.title} ", Toast.LENGTH_LONG)
            .show()

        wordViewModel.deleteATitle(checklist.id);
    }

    private lateinit var wordViewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view);
        val adapter = CheckListAdapter(context, this);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        wordViewModel = ViewModelProvider(this).get(ViewModel::class.java)
        wordViewModel.allWords.observe(viewLifecycleOwner, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        return view;
    }


}
