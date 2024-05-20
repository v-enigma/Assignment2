package com.example.assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.LoginViewModel
import com.example.assignment.LoginViewModelFactory
import com.example.assignment.Data.model.Result
import com.example.assignment.UserApplication

import com.example.assignment.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val viewModel : LoginViewModel by viewModels{ LoginViewModelFactory((requireActivity().application as UserApplication).loginRepository)}

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply{
            button.isEnabled = false

        }
        viewModel.authenticationResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                Result.SUCCESS -> {
                    val directions =
                        LoginFragmentDirections.actionLoginFragmentToUsersFragment(binding.UserId.toString())
                    findNavController().navigate(directions)
                }

                Result.FAILURE -> {
                    Toast.makeText(this.context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }

            }
        }


            // Initially, disable the login button


            // Add TextChangedListener to the User ID field
            binding.UserId.editText?.addTextChangedListener {
                updateLoginButtonState()
            }

            // Add TextChangedListener to the Password field
            binding.password.editText?.addTextChangedListener {
                updateLoginButtonState()
            }


        binding.button.setOnClickListener {
            viewModel.authenticate(binding.UserId.editText!!.text.toString().trim() , binding.password.editText!!.text.toString().trim())
        }

        return binding.root

    }

    private fun updateLoginButtonState() {
        val userIdText = binding.UserId.editText?.text.toString().trim()
        val passwordText = binding.password.editText?.text.toString().trim()

        // Enable the login button if both fields are not empty; otherwise, disable it
        binding.button.isEnabled = userIdText.isNotEmpty() && passwordText.isNotEmpty()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}