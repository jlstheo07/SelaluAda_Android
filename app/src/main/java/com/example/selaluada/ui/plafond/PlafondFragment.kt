package com.example.selaluada.ui.plafond

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selaluada.R
import com.example.selaluada.data.model.PlafondLevel
import com.example.selaluada.ui.adapter.PlafondAdapter

class PlafondFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlafondAdapter

    private val plafondList = listOf(
        PlafondLevel("Topaz", "Rp 6.000.000", "12 Bulan", "5%"),
        PlafondLevel("Emerald", "Rp 15.000.000", "12 Bulan", "4%"),
        PlafondLevel("Sapphire", "Rp 30.000.000", "12 Bulan", "3%"),
        PlafondLevel("Ruby", "Rp 45.000.000", "24 Bulan", "2%"),
        PlafondLevel("Diamond", "Rp 60.000.000", "24 Bulan", "1%")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plafond, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPlafond)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PlafondAdapter(plafondList)
        recyclerView.adapter = adapter

        return view
    }

}
