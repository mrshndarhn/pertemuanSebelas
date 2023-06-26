package com.marshanda.pertemuansebelas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pertemuansebelas.adapter.HomeAdapter
import com.google.firebase.database.*
import com.marshanda.pertemuansebelas.R
import com.marshanda.pertemuansebelas.databinding.FragmentHomeBinding
import com.marshanda.pertemuansebelas.model.Mahasiswa

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var adapter : HomeAdapter
    lateinit var databaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_tambahFragment)
        }

        adapter = HomeAdapter(ArrayList())
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())

        databaseRef = FirebaseDatabase.getInstance().getReference("mahasiswa")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mahasiswaList = ArrayList<Mahasiswa>()
                for (snapshot in dataSnapshot.children){
                    val mahasiswa = snapshot.getValue(Mahasiswa::class.java)
                    mahasiswa?.let{
                        mahasiswaList.add(it)
                    }
                    adapter.setData(mahasiswaList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handler Error
            }

        })

        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_tambahFragment)
        }

        }
    }