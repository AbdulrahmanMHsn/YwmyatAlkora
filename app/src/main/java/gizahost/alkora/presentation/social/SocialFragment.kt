package gizahost.alkora.presentation.social

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentSocialBinding
import gizahost.alkora.pojo.SocialModel
import gizahost.alkora.utils.NetworkConnection

class SocialFragment : Fragment() {

    private lateinit var binding: FragmentSocialBinding
    private lateinit var adapter: SocialListAdapter
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var list = mutableListOf<SocialModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_social, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Social")
        databaseReference.keepSynced(true)


        initRecyclerView()


        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner, Observer {
            if (it) {
                getdata()
            } else {
                getdata()
                val snack =
                    Snackbar.make(requireView(), "لايوجد الاتصال بالانترنت", Snackbar.LENGTH_LONG)
                snack.show()
            }
        })

        binding.socialToolbar.imgBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

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
        binding.socialRecVw.setHasFixedSize(true)
        binding.socialRecVw.layoutManager = LinearLayoutManager(requireContext())
        adapter = SocialListAdapter({ selectedItem: SocialModel ->
            listItemClicked(selectedItem)
        })
        binding.socialRecVw.adapter = adapter
    }

    private fun listItemClicked(selectedItem: SocialModel) {

    }

    private fun getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()

                for (item in snapshot.children) {
                    list.add(SocialModel(item.key.toString(), item.value.toString()))
                }

                adapter.setList(list)

            }

            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(requireContext(), "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })

    }

}