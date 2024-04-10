package com.kuzmin.tm_4.feature.login.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuzmin.tm_4.feature.login.R
import com.kuzmin.tm_4.feature.login.databinding.FragmentLoginBinding
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserResult.*
import com.kuzmin.tm_4.feature.login.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val AUTH_USER = "auth_user"

@AndroidEntryPoint
class LoginFragment : Fragment(), OnClickListener {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context
/*
    @Inject
    lateinit var navController: NavController*/



    private var loginListener: LoginListener? = null

    private var username: String? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    //private lateinit var savedStateHandle: SavedStateHandle

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //username = arguments?.getString(getString(R.string.username))
        //isAuthDataChanged = arguments?.getBoolean(getString(R.string.is_auth_data_changed)) ?: false

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[IS_AUTH_USER_DATA_CHANGED] = false*/

        with(binding) {
            btnLogin.setOnClickListener(this@LoginFragment)
            btnLoginCancel.setOnClickListener(this@LoginFragment)
        }

        loginViewModel.userdata.observe(viewLifecycleOwner) {
            showAuthUserForm(it)
        }

        with(loginViewModel) {
            authUserResult.observe(viewLifecycleOwner) {
                when(it) {
                    is Success<*> -> {
                        Toast.makeText(appContext, getString(R.string.authorization_success), Toast.LENGTH_SHORT).show()
                        close(true)
                        //TODO MESSENGER OR NOTIFICATION
                    }
                    is Error<*> -> {
                        Toast.makeText(
                            appContext,
                            getString(R.string.authorization_error) + " " + it.throwable.toString(),
                            Toast.LENGTH_SHORT).show()
                        Log.d("LoginFragment", "error: ${it.throwable.toString()}")
                        showAuthUserForm(it.user)
                    }
                    /*is Default<*> -> {
                        showAuthUserForm(it)
                    }*/
                    else -> throw RuntimeException("Unexpected statement.")
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginListener) {
            loginListener = context
        } else {
            throw RuntimeException("Activity must implement OnDeviceItemClickListener")
        }
    }

    override fun onClick(v: View) {
        Log.d("MainActivity", "Login Fragment onClick")
        with(binding) {
            when(v) {
                btnLogin -> authenticate()
                btnLoginCancel -> {
                    Toast.makeText(appContext, getString(R.string.authorization_canceled), Toast.LENGTH_SHORT).show()
                    loginViewModel.cancelAuthentication()
                    close(false)
                }
                else -> {throw RuntimeException("Unknown case")}
            }
        }
    }

    private fun showAuthUserForm(user: User) {
        Log.d("Login", "auth is not succeed")
        with(binding) {
            etUsername.setText(user.username)
            etPassword.setText(user.password) // "h98dGDJx"
        }
    }

    private fun authenticate() {
        Log.d("MainActivity", "Login Fragment authenticate()")
        with(binding) {
            //loginViewModel.authenticate(User(etUsername.text.toString(), etPassword.text.toString()))
            loginViewModel.authenticate(User(etUsername.text.toString(), "h98dGDJx"))
        }
    }

    private fun close(isOk: Boolean) {
        loginListener?.onAuthorizationCompleted(isOk)
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface LoginListener {
        fun onAuthorizationCompleted(isOk: Boolean)
    }
}