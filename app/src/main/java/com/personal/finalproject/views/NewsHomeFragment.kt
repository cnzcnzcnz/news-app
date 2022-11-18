package com.personal.finalproject.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.personal.finalproject.R
import com.personal.finalproject.adapters.NewsListAdapter
import com.personal.finalproject.databinding.FragmentNewsHomeBinding
import com.personal.finalproject.models.LoadingStatus
import com.personal.finalproject.viewmodel.NewsHomeViewModel

class NewsHomeFragment : Fragment() {

    private var binding: FragmentNewsHomeBinding? = null
    private val viewModel: NewsHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsHomeBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = viewLifecycleOwner

        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frag: View = requireActivity().findViewById(R.id.main_fragment)
        val navController = Navigation.findNavController(frag)

        binding!!.rvNews.adapter = NewsListAdapter(NewsListAdapter.OnClickListener{ article, position ->
            if(article.content != null || article.description != null ){
                navController.navigate(RootFragmentDirections.actionRootFragmentToNewsDetailFragment().setNewsData(article))
            } else {
                Toast.makeText(requireContext(), "Maaf isi konten tidak ada", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            binding!!.apply {
                when (it) {
                    LoadingStatus.LOADING -> {
                        swipeRefresh.visibility = View.GONE
                        progressCircular.visibility = View.VISIBLE
                        statusMessage.visibility = View.GONE
                        ivRefresh.visibility = View.GONE
                    }
                    LoadingStatus.DONE -> {
                        swipeRefresh.visibility = View.VISIBLE
                        progressCircular.visibility = View.GONE
                        statusMessage.visibility = View.GONE
                        ivRefresh.visibility = View.GONE
                    }
                    else -> {
                        swipeRefresh.visibility = View.GONE
                        progressCircular.visibility = View.GONE
                        statusMessage.apply {
                            visibility = View.VISIBLE
                            text = requireContext().getString(R.string.problem_with_connection)
                        }
                        ivRefresh.visibility = View.VISIBLE
                    }
                }
            }

            binding!!.llRefreshAndError.setOnClickListener {
                viewModel.fetchData()
            }

            binding!!.swipeRefresh.setOnRefreshListener {
                viewModel.fetchData()
                binding!!.swipeRefresh.isRefreshing = false
            }

        }

        viewModel.newsList.observe(viewLifecycleOwner) {
            val adapter = binding!!.rvNews.adapter as NewsListAdapter
            adapter.submitList(it)
        }
    }

}