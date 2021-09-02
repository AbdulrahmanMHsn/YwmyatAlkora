package gizahost.alkora.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentHomeBinding
import gizahost.alkora.pojo.HomeModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        initRecyclerView()

        val list: List<HomeModel> = mutableListOf(
            HomeModel(
                1, getString(R.string.date),
                R.drawable.ic_date2
            )
            , HomeModel(
                2, getString(R.string.latest_news),
                R.drawable.ic_sport_news
            )
            , HomeModel(
                3, getString(R.string.social),
                R.drawable.ic_social_media
            )
            , HomeModel(
                4, getString(R.string.tournaments),
                R.drawable.ic_tournament
            )
            , HomeModel(
                6, getString(R.string.about),
                R.drawable.ic_info
            )
            , HomeModel(
                5, getString(R.string.registration),
                R.drawable.ic_edit
            )
        )

        adapter.setList(list)

    }

    private fun initRecyclerView() {
        binding.homeContainer.setHasFixedSize(true)
        binding.homeContainer.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter =
            HomeGridAdapter({ selectedItem: HomeModel ->
                listItemClicked(selectedItem)
            })
        binding.homeContainer.adapter = adapter
    }

    private fun listItemClicked(selectedItem: HomeModel) {
        when (selectedItem.id) {
            1 -> {
                Toast.makeText(requireContext(), selectedItem.body, Toast.LENGTH_SHORT).show()
            }
            2 -> {
                requireView().findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
            }
            3 -> {
                requireView().findNavController()
                    .navigate(R.id.action_homeFragment_to_socialFragment)
            }
            4 -> {
                requireView().findNavController()
                    .navigate(R.id.action_homeFragment_to_leaguesFragment)
            }
            5 -> {
                requireView().findNavController()
                    .navigate(R.id.action_homeFragment_to_registerFragment)
            }
            6 -> {
                requireView().findNavController()
                    .navigate(R.id.action_homeFragment_to_aboutFragment)
            }
        }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}