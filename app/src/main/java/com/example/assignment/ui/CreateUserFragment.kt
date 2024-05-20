package com.example.assignment.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.UserApplication
import com.example.assignment.UserViewModel
import com.example.assignment.UserViewModelFactory
import com.example.assignment.databinding.FragmentUserCreateBinding
import com.example.assignment.Data.model.LoginDetail
import com.example.assignment.Data.model.User
import java.util.regex.Pattern

class CreateUserFragment: Fragment() {
    private var _binding : FragmentUserCreateBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel:UserViewModel by activityViewModels { UserViewModelFactory((requireActivity().application as UserApplication).repository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserCreateBinding.inflate(inflater, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userId.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val userId = s.toString()
                if (userId.length == 10) {
                    viewModel.isUserIdAvailable(userId)
                } else {
                    binding.userId.error = "User ID must be exactly 10 characters"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        viewModel.isUserIdAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                binding.userId.error = null
                binding.addUser.isEnabled = true
            } else {
                binding.userId.error = "User ID is not available"
                binding.addUser.isEnabled = false
            }
        }

        binding.addUser.setOnClickListener {
            if (validateForm()) {

                viewModel.add(
                    User(userId =binding.userId.editText!!.text.toString(),
                        name = binding.name.editText!!.text.toString(),
                        age = (binding.dateOfBirth.editText!!.text).toString().toInt(),
                        place = binding.place.editText!!.text.toString() ,),
                    LoginDetail(userId = binding.userId.editText!!.text.toString(),
                        password = binding.password.editText!!.text.toString() )
                )
            }
            val directions = CreateUserFragmentDirections.actionCreateUserFragmentToUsersFragment("")
            findNavController().navigate(directions)
        }

        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        binding.password.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateForm(): Boolean {
        val isUserIdValid = validateUserId()
        val isPasswordValid = validatePassword()
        return isUserIdValid && isPasswordValid
    }

    private fun validateUserId(): Boolean {
        val userId = binding.userId.editText?.text.toString()
        return if (userId.length == 10) {
            binding.userId.error = null
            true
        } else {
            binding.userId.error = "User ID must be exactly 10 characters"
            false
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding.password.editText?.text.toString()
        val passwordPattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{7,15}$"
        )
        return if (passwordPattern.matcher(password).matches()) {
            binding.password.error = null
            true
        } else {
            binding.password.error = "Password must be 7-15 characters with one uppercase alphabet, one special character, and one numeric digit"
            false
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}