package com.laube.tech.comiccollection.comicdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.laube.tech.comiccollection.databinding.ComicDetailFragmentBinding
import com.laube.tech.comiccollection.models.ComicData


class ComicDetailFragment : Fragment() {
    private var viewBinding : ComicDetailFragmentBinding? = null
    private var comicId: String? = null
    private lateinit var viewModel: ComicDetailViewModel
    private var comicResult : ComicData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComicDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ComicDetailFragmentBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = ComicDetailFragmentArgs.fromBundle(it)
            comicId = args.comicId
        }

        viewModel.loading.observe(viewLifecycleOwner, {isLoading ->
            isLoading?.let{
                viewBinding?.loadingProgressBar?.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
        viewModel.comic.observe(viewLifecycleOwner,{
            it.let{
                comicResult = ComicData(it)
                comicResult.let{
                    viewBinding?.issueDetailsTextview?.text = it?.issueTitle
                    val imageURL = it?.getCoverLink("portrait_uncanny", "jpg")
                    if(!imageURL.isNullOrBlank()) {
                        // load the cover image if we have one
                        Glide.with(this).load(imageURL).into(viewBinding!!.issueCoverImageview)
                    }
                }
            }
        })
        viewModel.loadIssue(comicId.toString())
    }
    companion object {
        private const val COMIC_ID = "ComicId"
        @JvmStatic
        fun newInstance(comicId: String) =
            ComicDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(COMIC_ID, comicId)
                }
            }
    }
}