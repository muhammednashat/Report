package com.mnashat_dev.saveme.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.btEnter.setOnClickListener {
            if (isEditTextEmpty()) {
                return@setOnClickListener
            } else {
                if (isValidCredentials()) {
                  findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListOfDataFragment())
                }else Toast.makeText(requireContext(),"تحقق من صحة البيانات المدخلة ",Toast.LENGTH_SHORT).show()
            }
        }

//        binding.btEnter.setOnClickListener {
//                              findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListOfDataFragment())
//
//        }



        return binding.root
    }

    private fun isEditTextEmpty(): Boolean {

        var fieldsEmpty = false
        if (TextUtils.isEmpty(binding.edPassword.text)) {
            binding.edPassword.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true
        }
        if (TextUtils.isEmpty(binding.edUserName.text)) {
            binding.edUserName.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true
        }

        return fieldsEmpty
    }

    private fun isValidCredentials(): Boolean {

       return binding.edUserName.text.toString() == "admin" &&
            binding.edPassword.text.toString() == "1111"

    }

}