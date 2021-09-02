package gizahost.alkora.presentation.news

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentNewsBinding
import gizahost.alkora.pojo.Articles
import gizahost.alkora.utils.Dialogs
import gizahost.alkora.utils.NetworkConnection


class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var mProgress: Dialog
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)


        mProgress = Dialogs.createProgressBarDialog(context, "")


        initRecyclerView()


        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                getData()
            } else {
                val snack =
                    Snackbar.make(requireView(), "لايوجد الاتصال بالانترنت", Snackbar.LENGTH_LONG)
                snack.show()
                mProgress.cancel()
            }
        })



        binding.swipeToRefresh.setOnRefreshListener {
            networkConnection.observe(viewLifecycleOwner, Observer {
                if (it) {
                    getData()
                } else {
                    val snack =
                        Snackbar.make(
                            requireView(),
                            "لايوجد الاتصال بالانترنت",
                            Snackbar.LENGTH_LONG
                        )
                    snack.show()
                    mProgress.cancel()
                }
            })
            binding.swipeToRefresh.isRefreshing = false
        }


        binding.newsToolbar.imgBack.setOnClickListener {
            it.findNavController().popBackStack()
        }


    }


    private fun getData() {
        mProgress.show()
        viewModel.news.observe(viewLifecycleOwner, Observer {
            adapter.setList(it!!.articles)
            mProgress.cancel()
        })
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireView().findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }


    private fun initRecyclerView() {
        binding.newsContainer.setHasFixedSize(true)
        binding.newsContainer.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            NewsAdapter({ selectedItem: Articles ->
                listItemClicked(selectedItem)
            })
        binding.newsContainer.adapter = adapter
    }


    private fun listItemClicked(selectedItem: Articles) {
        val bundle = Bundle()
        bundle.putString("url", selectedItem.url)
        requireView().findNavController()
            .navigate(R.id.action_newsFragment_to_detailsFragment, bundle)
    }

}