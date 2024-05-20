package com.example.assignment.ui

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.UserApplication
import com.example.assignment.UserViewModel
import com.example.assignment.UserViewModelFactory
import com.example.assignment.databinding.FragmentUsersBinding

class UsersFragment :Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : UserAdapter
    private val userViewModel :UserViewModel by activityViewModels{  UserViewModelFactory((requireActivity().application as UserApplication).repository)}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          _binding = FragmentUsersBinding.inflate(inflater,container, false )
          val recyclerView = binding.recyclerView

          userViewModel.users.observe(viewLifecycleOwner){
              Log.d("UsersFragment", "User list size: ${it?.size}")
              adapter.submitList(it)
          }
        adapter = UserAdapter{ index ->
            val directions = UsersFragmentDirections.actionUsersFragmentToBottomSheetFragment(index)
            findNavController().navigate(directions)
        }
        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{
            val directions = UsersFragmentDirections.actionUsersFragmentToCreateUserFragment()
            findNavController().navigate(directions)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.sort_options, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                      R.id.action_sort_by_age -> { userViewModel.sortByAge(); true}
                      R.id.action_sort_by_place -> {userViewModel.sortByPlace(); true}
                       R.id.action_sort_by_name ->    {userViewModel.sortByName();true}
                       else ->{ false}
                }
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)


        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            private val backgroundIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_24)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.currentList[position]
                userViewModel.deleteUser(item)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                backgroundIcon?.let{
                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - it.intrinsicHeight)/2
                    val iconTop = itemView.top +(itemView.height - it.intrinsicHeight)/2
                    val iconBottom = iconTop + it.intrinsicHeight
                    if(dX > 0 ){ //Swiping to right
                        val iconLeft = itemView.left + iconMargin
                        val iconRight = iconLeft + it.intrinsicWidth
                        it.setBounds(iconLeft,iconTop,iconRight, iconBottom)
                    } else if( dX < 0 ){ // Swiping to Left
                        val iconRight = itemView.right - iconMargin
                        val iconLeft = iconRight- it.intrinsicWidth
                    } else{
                        it.setBounds(0,0,0,0)
                    }
                    it.draw(c)
                }

            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}