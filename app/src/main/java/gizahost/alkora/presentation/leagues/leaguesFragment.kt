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
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_leagues, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaguesViewModel::class.java)
        initRecyclerView()
        onBackPressed()

        binding.leagueToolbar.imgBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val leaguesList = mutableListOf<League>()

        leaguesList.add(League(538,R.drawable.egyptian_premier_league,"الدورى المصرى"))
        leaguesList.add(League(4496,R.drawable.africa_cup_of_nation_official_logo,"كأس الأمم الأفريقية"))
        leaguesList.add(League(4328,R.drawable.premier_league_logo,"الدورى الأنجليزى"))
        leaguesList.add(League(4335,R.drawable.logo_laliga,"الدورى الاسبانى"))
        leaguesList.add(League(4504,R.drawable.ligue1,"الدورى الفرنسى"))
        leaguesList.add(League(4332,R.drawable.serie,"الدورى الأيطالى"))
        leaguesList.add(League(4331,R.drawable.bundesliga_logo,"الدورى الألمانى"))
        leaguesList.add(League(4480,R.drawable.champions_league,"دورى أبطال أوروبا"))
        leaguesList.add(League(4502,R.drawable.uefa_europa_league_logo,"الدورى الأوروبى"))
        leaguesList.add(League(4502,R.drawable.egypt_cup,"كأس مصر"))
        leaguesList.add(League(4720,R.drawable.caf_champions_league,"دورى ابطال افريقيا"))



        adapter.setList(leaguesList)


        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.leagues.observe(viewLifecycleOwner, Observer {


                    for(item in it!!.response){
                        Log.i("My TAG", "onViewCreated: "+item.league)
                        list.add(item.league)
                    }

//                    adapter.setList(list)



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
        adapter = LeagueListAdapter { selectedItem: League ->
            listItemClicked(selectedItem)
        }
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