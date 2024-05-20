package com.example.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.assignment.R
import com.example.assignment.UserApplication
import com.example.assignment.UserViewModel
import com.example.assignment.UserViewModelFactory
import com.example.assignment.Data.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class BottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel :UserViewModel by activityViewModels<UserViewModel> { UserViewModelFactory((requireActivity().application as UserApplication).repository) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: BottomSheetFragmentArgs by navArgs()
        val user = viewModel.users.value!!.get(args.index)
        val name :TextInputLayout = view.findViewById(R.id.name)
        name.editText?.setText(user.name)
        val place: TextInputLayout = view.findViewById(R.id.place)
        place.editText?.setText(user.place)
        val age :TextInputLayout  = view.findViewById(R.id.dateOfBirth)
        age.editText?.setText(user.age.toString())

        val button :Button = view.findViewById(R.id.edit)
        button.setOnClickListener {
            val textName  = name.editText?.text ?: " "
            val textAge = age.editText?.text ?: "0"
            val textPlace = place.editText?.text ?: " "

            viewModel.update(User(user.id, user.userId,textName.toString(), textAge.toString().toInt(), textPlace.toString()))
            dismiss()
        }
    }
}