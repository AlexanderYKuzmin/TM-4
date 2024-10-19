package com.kuzmin.tm_4.feature.login.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.feature.login.R
import com.kuzmin.tm_4.feature.login.databinding.FragmentLoginBinding
import com.kuzmin.tm_4.feature.login.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment(), OnClickListener {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.btnOkReg.setOnClickListener(this)
        binding.btnCancelReg.setOnClickListener(this)*/

    }

    override fun onClick(v: View?) {
        TODO()
    }
}