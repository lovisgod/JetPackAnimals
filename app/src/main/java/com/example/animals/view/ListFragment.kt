package com.example.animals.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.viewmodel.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private lateinit var viewmodel : ListViewModel
    private val listAdaptar = AnimalListAdaptar(arrayListOf()) // creates an empty list and pass it into the adaptar
    private lateinit var animalList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingError: TextView
    private lateinit var refreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewmodel.animals.observe(this,  animalListObserver )
        viewmodel.loading.observe(this, loadingObserver)
        viewmodel.loadError.observe(this, loadingErrorLiveDataObserver)

        viewmodel.refresh()

         animalList = view.findViewById<RecyclerView>(R.id.animalList)
         progressBar = view.findViewById<ProgressBar>(R.id.loadingView)
         loadingError = view.findViewById<TextView>(R.id.listError)
         refreshLayout  = view.findViewById(R.id.refresh_layout)

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdaptar
        }

        refreshLayout.apply {
            this.setOnRefreshListener {
                animalList.visibility = View.GONE
                loadingError.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                viewmodel.refresh()
                this.isRefreshing = false
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ListFragment()
    }

    private val animalListObserver  = Observer<List<Animal>> {
        it?.let {
            animalList.visibility = View.VISIBLE
            listAdaptar.updateAnimalList(it)
        }
    }

    private val loadingObserver = Observer<Boolean> {
        progressBar.visibility = if (it) View.VISIBLE else View.GONE
        if (it) {
            loadingError.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val loadingErrorLiveDataObserver = Observer<Boolean> {
        loadingError.visibility = if(it) View.VISIBLE else View.GONE
    }
}