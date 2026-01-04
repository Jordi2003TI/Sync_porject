package com.jorlina.syncapp.Fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jorlina.syncapp.Perfil
import com.jorlina.syncapp.R

class AppBarFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.app_bar_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrowBackIv = view.findViewById<ImageView>(R.id.arrowBackIv)
        val userIconIv = view.findViewById<ImageView>(R.id.userIconIv)

        arrowBackIv.setOnClickListener {
            requireActivity().finish()
        }

        userIconIv.setOnClickListener {
            val intent = Intent(requireContext(), Perfil::class.java)
            startActivity(intent)
        }

    }
}