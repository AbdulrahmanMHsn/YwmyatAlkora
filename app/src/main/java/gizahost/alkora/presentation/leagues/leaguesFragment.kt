package gizahost.alkora.presentation.leagues

import android.os.Bundle
import android.util.Log
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
import gizahost.alkora.databinding.FragmentLeaguesBinding
import gizahost.alkora.pojo.league.League
import gizahost.alkora.utils.NetworkConnection


class leaguesFragment : Fragment() {

    private lateinit var adapter: LeagueListAdapter
    private lateinit var binding:FragmentLeaguesBinding
    private lateinit var viewModel: LeaguesViewModel
    private var list = mutableListOf<League>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_leagues, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaguesViewModel::class.java)
        initRecyclerView()
        onBackPressed()

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.leagues.observe(viewLifecycleOwner, Observer {


                    for(item in it!!.response){
                        Log.i("My TAG", "onViewCreated: "+item.league.name)
                        list.add(item.league)
                    }

                    adapter.setList(list)



                })
            } else {
                val snack =
                    Snackbar.make(requireView(), "لايوجد الاتصال بالانترنت", Snackbar.LENGTH_LONG)
                snack.show()
            }
        })

    }


    private fun initRecyclerView() {
        binding.leagueRcVw.setHasFixedSize(true)
        binding.leagueRcVw.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeagueListAdapter({ selectedItem: League ->
            listItemClicked(selectedItem)
        })
        binding.leagueRcVw.adapter = adapter
    }

    private fun listItemClicked(selectedItem: League) {

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
}