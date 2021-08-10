package com.laube.tech.comiccollection.comicdetail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.laube.tech.comiccollection.MainActivity
import com.laube.tech.comiccollection.R
import com.laube.tech.comiccollection.databinding.ComicDetailFragmentBinding
import com.laube.tech.comiccollection.models.ComicData
import com.laube.tech.comiccollection.models.response.MarvelResponse
import com.laube.tech.comiccollection.util.MarvelUtil


class ComicDetailFragment : Fragment() {
    private var viewBinding: ComicDetailFragmentBinding? = null
    private var comicId: String? = null
    private lateinit var viewModel: ComicDetailViewModel

    companion object {
        private const val COMIC_ID = "ComicId"
    }

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

        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                viewBinding?.loadingProgressBar?.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.comic.observe(viewLifecycleOwner, {
            loadComicData(it)
        })
        viewModel.loadIssue(comicId.toString())
    }

    private fun loadComicData(comicResult: MarvelResponse) {
        val comic = ComicData(comicResult)
        comic.let {
            // set the title on the app bar
            (activity as MainActivity).supportActionBar?.title = it.issueTitle
            var descriptionText = it.issueDescription ?: ""
            if (descriptionText.isBlank()) {
                descriptionText = resources.getString(R.string.no_description)
            }
            viewBinding?.issueDescriptionTextview?.text = descriptionText
            viewBinding?.issueDescriptionTextview?.movementMethod = ScrollingMovementMethod();
            val imageURL = MarvelUtil.getCoverLink(it.coverLink.toString(),"portrait_uncanny", "jpg" )
                //it.getCoverLink("portrait_uncanny", "jpg")
            if (!imageURL.isNullOrBlank()) {
                // load the cover image if we have one
                Glide.with(this).load(imageURL).into(viewBinding!!.issueCoverImageview)
            }
        }

    }

}