package com.jorlina.syncapp.Fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.jorlina.syncapp.AyudaActivity
import com.jorlina.syncapp.MainLogin.MainActivity
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
        val ivInfo = view.findViewById<ImageView>(R.id.ivInfo)



        arrowBackIv.setOnClickListener {
            requireActivity().finish()
        }

        userIconIv.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("sync_prefs", 0)
            val userId = prefs.getString("userId", null)
            if (userId != null) {
                val intent = Intent(requireContext(), Perfil::class.java)
                intent.putExtra("RecueprarIdUser", userId)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Usuario no disponible", Toast.LENGTH_SHORT).show()
            }
        }

        ivInfo.setOnClickListener {
            val intent = Intent(requireContext(), AyudaActivity::class.java)
            startActivity(intent)
        }

    }
}