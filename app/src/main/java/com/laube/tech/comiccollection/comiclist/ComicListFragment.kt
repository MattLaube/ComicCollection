package com.laube.tech.comiccollection.comiclist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.laube.tech.comiccollection.databinding.ComicListFragmentBinding
import com.laube.tech.comiccollection.models.response.Series


class ComicListFragment : Fragment() {

    companion object {
        fun newInstance() = ComicListFragment()
        val CHARACTER_ID = "1009351"
        val SERIES_ID = "24278"
        val TEST_ISSUE_ID = "77342"
    }
    private var viewBinding: ComicListFragmentBinding? = null
    private lateinit var viewModel: ComicListViewModel
    private lateinit var comicListAdapter : ComicListAdapter

    private val comicListListener = object : ComicListAdapter.ComicListListener {
        override fun onSelected(item: Series) {
            val id = item.getComicId()
            val action = ComicListFragmentDirections.actionMainFragmentToComicDetailFragment(id.toString())
            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComicListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = ComicListFragmentBinding.inflate(inflater, container, false)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comicListAdapter = ComicListAdapter(arrayListOf(),comicListListener)
        viewBinding?.comicsListRecyclerview?.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = comicListAdapter
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let{
                viewBinding?.loadingProgressBar?.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    viewBinding?.listErrorTextview?.visibility = View.GONE
                }
            }
        })

        viewModel.comics.observe(viewLifecycleOwner, Observer { comicsList ->
            comicsList?.let{
                comicListAdapter.updateList(it)
                viewBinding?.comicsListRecyclerview?.visibility = View.VISIBLE
            }
        })

        viewBinding?.refreshLayout?.setOnRefreshListener {
            viewBinding?.comicsListRecyclerview?.visibility = View.GONE
            viewBinding?.listErrorTextview?.visibility = View.GONE
            viewBinding?.loadingProgressBar?.visibility = View.VISIBLE

            viewModel.loadSeries(SERIES_ID)
            viewBinding?.refreshLayout?.isRefreshing = false
        }
        viewModel.loadSeries(SERIES_ID)

    }


}