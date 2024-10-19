package com.kuzmin.tm_4.feature.login.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnPreDrawListener
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kuzmin.tm_4.common.R.*
import com.kuzmin.tm_4.common.R.id.btn_cancel_login
import com.kuzmin.tm_4.common.R.id.btn_ok_login
import com.kuzmin.tm_4.common.extension.toast
import com.kuzmin.tm_4.common.util.CommonConstants.AUTHORIZATION_CANCELLED
import com.kuzmin.tm_4.common.util.messages.LoginMessage
import com.kuzmin.tm_4.feature.api.api.activity.OnFragmentActionListener
import com.kuzmin.tm_4.feature.api.domain.model.FragmentAction
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.login.R
import com.kuzmin.tm_4.feature.login.databinding.FragmentLoginBinding
import com.kuzmin.tm_4.feature.login.domain.model.LoginRegResult.*
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), OnClickListener {

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private var onFragmentActionListener: OnFragmentActionListener? = null

    lateinit var usernameOnPreDrawListener: OnPreDrawListener
    lateinit var passwordOnPreDrawListener: OnPreDrawListener

    private var username: String? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    //private lateinit var savedStateHandle: SavedStateHandle

    private val navController by lazy { findNavController() }

    private lateinit var btnOkLogin: Button
    private lateinit var btnCancelLogin: Button

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
            usernameOnPreDrawListener = createOnPreDrawListener(tilUsername, etUsername, USERNAME)
                .also {
                    if (tilUsername.height > 0) {
                        tilUsername.viewTreeObserver.removeOnPreDrawListener(it)
                    }
                }
            tilUsername.viewTreeObserver.addOnPreDrawListener(usernameOnPreDrawListener)

            //Рассмотреть EditTextBackEventListener
            etUsername.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_BACK) {
                    binding.chbRem.visibility = View.VISIBLE
                    binding.tvRegister.visibility = View.VISIBLE
                }
                false
            }

            passwordOnPreDrawListener = createOnPreDrawListener(tilPassword, etPassword, PASSWORD)
                .also {
                    if (tilPassword.height > 0) {
                        tilPassword.viewTreeObserver.removeOnPreDrawListener(it)
                    }
                }
            tilPassword.viewTreeObserver.addOnPreDrawListener(passwordOnPreDrawListener)

            etUsername.setOnFocusChangeListener { _, hasFocus ->
                updateHintPosition(hasFocus, !etUsername.text.isNullOrEmpty(), USERNAME)
                binding.chbRem.visibility = View.GONE
                binding.tvRegister.visibility = View.GONE
            }

            etPassword.setOnFocusChangeListener { _, hasFocus ->
                updateHintPosition(hasFocus, !etPassword.text.isNullOrEmpty(), PASSWORD)
            }

            btnOkLogin =
                llBtnsLogin.findViewById<Button>(btn_ok_login).apply {
                    setOnClickListener(this@LoginFragment)
                }
            btnCancelLogin =
                llBtnsLogin.findViewById<Button>(btn_cancel_login).apply {
                    setOnClickListener(this@LoginFragment)
                }

            tvRegister.setOnClickListener(this@LoginFragment)
        }

        with(loginViewModel) {
            loginRegResult.observe(viewLifecycleOwner) {
                when(it) {
                    is LocalUserCheck -> {
                        if (it.authUser != null) fillFields(it.authUser)
                    }
                    is Success -> {
                        TODO()
                    }
                    is Failure -> {
                        if (it.authFbInfo.hasError) {
                            appContext.toast(it.authFbInfo.message!!)
                        } else {
                            throw RuntimeException("Authorization info must contain message")
                        }
                    }
                    else -> throw RuntimeException("Unexpected login statement.")
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionListener) {
            onFragmentActionListener = context
        } else {
            throw RuntimeException("Activity must implement OnFragmentActionListener")
        }
    }

    override fun onClick(v: View) {
        Log.d("MainActivity", "Login Fragment onClick")
        with(binding) {
            when(v) {
                btnOkLogin -> loginViewModel.signIn(
                    User(
                        etUsername.text.toString(),
                        etPassword.text.toString()
                    ),
                    chbRem.isChecked
                )
                btnCancelLogin -> {
                    appContext.toast(LoginMessage.AUTHORIZATION_CANCELED)
                    close(AUTHORIZATION_CANCELLED)
                }
                tvRegister -> {
                    launchRegisterFragment()
                }
                else -> {throw RuntimeException("Unknown case")}
            }
        }
    }

    private fun fillFields(authUser: AuthUser) {
        if (authUser.dataVisibility) {
            with(binding) {
                etUsername.setText(authUser.email)
                etPassword.setText(authUser.password)
            }
        }
    }

    private fun createOnPreDrawListener(til: TextInputLayout, et: TextInputEditText, name: String): OnPreDrawListener {
        return OnPreDrawListener {
            if (til.height > 0) {
                removeListener(name)
                updateHintPosition(
                    et.hasFocus(),
                    !et.text.isNullOrEmpty(),
                    name
                )
                return@OnPreDrawListener false
            }
            true
        }
    }

    private fun updateHintPosition(hasFocus: Boolean, hasText: Boolean, name: String) {
        if (hasFocus || hasText) {
            if (name == USERNAME) binding.tilUsername.hint = appContext.getString(R.string.username_hint_small)
            else binding.tilPassword.hint = appContext.getString(R.string.password_hint_small)

        } else {
            if (name == USERNAME) binding.tilUsername.hint = appContext.getString(R.string.username_hint)
            else binding.tilPassword.hint = appContext.getString(R.string.password_hint)
            binding.chbRem.visibility = View.VISIBLE
            binding.tvRegister.visibility = View.VISIBLE
        }
    }

    private fun removeListener(name: String) {
        when(name) {
            USERNAME -> {
                if (usernameOnPreDrawListener != null) {
                    binding.tilUsername.viewTreeObserver.removeOnPreDrawListener(usernameOnPreDrawListener)
                }
            }
            PASSWORD -> binding.tilUsername.viewTreeObserver.removeOnPreDrawListener(passwordOnPreDrawListener)
        }
    }

    private fun launchRegisterFragment() {
        navController.navigate(com.kuzmin.tm_4.common.R.id.action_login_nav_graph_to_register_nav_graph)
    }

    private fun close(action: String) {
        onFragmentActionListener?.onFragmentAction(
            FragmentAction.LoginAction(action)
        )
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }
}