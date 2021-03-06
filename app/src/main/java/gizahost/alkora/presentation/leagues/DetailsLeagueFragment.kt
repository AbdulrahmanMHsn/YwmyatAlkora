package gizahost.alkora.presentation.leagues

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentDetailsLeagueBinding
import gizahost.alkora.databinding.FragmentLeaguesBinding
import gizahost.alkora.pojo.details_league.Details
import gizahost.alkora.pojo.league.League
import gizahost.alkora.utils.Dialogs
import gizahost.alkora.utils.NetworkConnection


class DetailsLeagueFragment : Fragment() {

    private lateinit var adapter: TableLeagueListAdapter
    private lateinit var binding: FragmentDetailsLeagueBinding
    private lateinit var viewModel: LeaguesViewModel
    private var list = mutableListOf<Details>()
    private var idLeague:String? = null
    private lateinit var mProgress: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments?.let {
            idLeague = it.getInt("IdLeague").toString()
        }
        Log.i("DDDDDD", "onCreate: $idLeague")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details_league, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaguesViewModel::class.java)
        mProgress = Dialogs.createProgressBarDialog(context, "")


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
        mProgress.show()
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.table(idLeague.toString()).observe(viewLifecycleOwner, Observer {


                    for (item in it!!.table) {
//                        englishArabicTranslator.translate(item.strTeam)
//                            .addOnSuccessListener { translatedText ->
//                                list.add(Details(item.idStanding,item.idLeague,item.intDraw,item.intGoalsAgainst,item.intGoalsFor,item.intLoss,item.intPlayed,item.intPoints,item.intRank,item.intWin,translatedText,item.strTeamBadge))
//                                adapter.setList(list)
//                            }
//                            .addOnFailureListener { exception ->
//                                Log.i("TAGTAGTAG", "onViewCreated1: $exception")
//
//                            }
                      list.add(item)
                        Log.i("MyTAG", "onViewCreated3: ")
                    }
                    Log.i("MyTAG", "onViewCreated3: ${list.size}")

                    adapter.setList(list)
                    mProgress.dismiss()
                })
            } else {
                val snack =
                    Snackbar.make(requireView(), "???????????? ?????????????? ??????????????????", Snackbar.LENGTH_LONG)
                snack.show()
                mProgress.dismiss()
            }
        })
    }


    private fun initRecyclerView() {
        binding.leagueRcVw.setHasFixedSize(true)
        binding.leagueRcVw.layoutManager = LinearLayoutManager(requireContext())
        adapter = TableLeagueListAdapter()
        binding.leagueRcVw.adapter = adapter
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