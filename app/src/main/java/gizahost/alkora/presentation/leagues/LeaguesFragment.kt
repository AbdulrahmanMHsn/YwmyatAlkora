package gizahost.alkora.presentation.leagues

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentLeaguesBinding
import gizahost.alkora.pojo.league.League
import gizahost.alkora.utils.NetworkConnection


class LeaguesFragment : Fragment() {

    private lateinit var adapter: LeagueListAdapter
    private lateinit var binding: FragmentLeaguesBinding
    private lateinit var viewModel: LeaguesViewModel
    private var list = mutableListOf<League>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leagues, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaguesViewModel::class.java)
        initRecyclerView()
        onBackPressed()

        binding.leagueToolbar.imgBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()

        val englishArabicTranslator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder().build()

        englishArabicTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Log.i("TAGTAGTAG", "onViewCreated: $it")

            }
            .addOnFailureListener { exception ->
                Log.i("TAGTAGTAG", "onViewCreated: $exception")

            }


        val leaguesList = mutableListOf<League>()

        leaguesList.add(League(4829, R.drawable.egyptian_premier_league, "الدورى المصرى"))
        leaguesList.add(League(4496, R.drawable.africa_cup_of_nation_official_logo, "كأس الأمم الأفريقية"))
        leaguesList.add(League(4328, R.drawable.premier_league_logo, "الدورى الأنجليزى"))
        leaguesList.add(League(4335, R.drawable.logo_laliga, "الدورى الاسبانى"))
        leaguesList.add(League(4504, R.drawable.ligue1, "الدورى الفرنسى"))
        leaguesList.add(League(4332, R.drawable.serie, "الدورى الأيطالى"))
        leaguesList.add(League(4331, R.drawable.bundesliga_logo, "الدورى الألمانى"))
        leaguesList.add(League(4480, R.drawable.champions_league, "دورى أبطال أوروبا"))
        leaguesList.add(League(4502, R.drawable.uefa_europa_league_logo, "الدورى الأوروبى"))
//        leaguesList.add(League(4502,R.drawable.egypt_cup,"كأس مصر"))
        leaguesList.add(League(4720, R.drawable.caf_champions_league, "دورى ابطال افريقيا"))



        adapter.setList(leaguesList)


        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.leagues.observe(viewLifecycleOwner, Observer {


                    for (item in it!!.response) {
                        englishArabicTranslator.translate(item.league.name)
                            .addOnSuccessListener { translatedText ->
//                                Log.i("TAGTAGTAG", "onViewCreated0: $translatedText")
//                                list.add(League(item.league.id,item.league.logo,translatedText))
//                                Log.i("TAGTAGTAG", "onViewCreated0: $translatedText")
//                                adapter.setList(list)
                            }
                            .addOnFailureListener { exception ->
                                Log.i("TAGTAGTAG", "onViewCreated1: $exception")

                            }
                        Log.i("MyTAG", "onViewCreated3: ")
                    }
                    Log.i("MyTAG", "onViewCreated3: ${list.size}")


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
        val bundle = Bundle()
        bundle.putInt("IdLeague", selectedItem.id)
        findNavController().navigate(R.id.detailsLeagueFragment,bundle)
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