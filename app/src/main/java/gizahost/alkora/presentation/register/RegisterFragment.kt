package gizahost.alkora.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gizahost.alkora.R
import gizahost.alkora.databinding.FragmentRegisterBinding
import gizahost.alkora.utils.Utils


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("User")
        databaseReference.keepSynced(true)


        binding.registerToolbar.imgBack.setOnClickListener {
            view.findNavController().popBackStack()
        }


        binding.btnSend.setOnClickListener {

            if (binding.etName.length() == 0) {
                binding.etName.error = "الاسم مطلوب"
                return@setOnClickListener
            }

            if (binding.etPhone.length() == 0) {
                binding.etPhone.error = "الرقم مطلوب"
                return@setOnClickListener
            }


            if (Utils.isNetworkAvailable(requireContext())) {
                addToFirebase()
            } else {
                val snack =
                    Snackbar.make(requireView(), "لايوجد الاتصال بالانترنت", Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
    }


    fun addToFirebase() {

        val map = HashMap<String, String>()

        map.put("name", binding.etName.text.toString())
        map.put("email", binding.etEmail.text.toString())
        map.put("phone", binding.etPhone.text.toString())
        map.put("comment", binding.etComment.text.toString())

        val key: String = databaseReference.push().getKey().toString()
        databaseReference.child(key).setValue(map)
        Toast.makeText(requireContext(), "تم الارسال", Toast.LENGTH_SHORT).show()
        requireView().findNavController().popBackStack()
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