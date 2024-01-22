package com.kuzmin.tm_4.feature.login.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kuzmin.tm_4.feature.login.databinding.FragmentLoginBinding
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import dagger.hilt.android.AndroidEntryPoint

const val AUTH_USER = "auth_user"

@AndroidEntryPoint
class LoginFragment : Fragment(), OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    //val args: LoginFragmentArgs by navArgs()

    private val loginViewModel: LoginViewModel by viewModels()

    /*@Inject
    lateinit var application: Application*/

    /*@Inject
    lateinit var viewModelFactory: ViewModelFactory*/

    /*private val loginFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginFragmentViewModel::class.java]
    }*/

    /*private val loginComponent by lazy {
        (requireActivity().application as TmApp).component.getLoginSubComponent()
    }*/

    //private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[IS_AUTH_USER_DATA_CHANGED] = false*/


        //loginComponent.inject(this)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        with(binding) {
            btnLogin.setOnClickListener(this@LoginFragment)
            btnLoginCancel.setOnClickListener(this@LoginFragment)
        }

        //loginViewModel.observeAuth(viewLifecycleOwner, ::close) //TODO
        loginViewModel.checkWork()

        /*with(loginFragmentViewModel) {
            //readAuthUserFromProperties()
            *//*authUserData.observe(viewLifecycleOwner) {
                if (AuthValidation.isTokenValid(it.token, it.dateToken)) loginSucceed()
                else showAuthUser(it)
            }*//*
            isAuthUserDataChanged.observe(viewLifecycleOwner) {
                arguments = bundleOf(IS_AUTH_USER_DATA_CHANGED to it)
                //todo
            }
        }*/

        //showAuthUser(args.username)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginListener) {
            loginListener = context
        } else {
            throw RuntimeException("Activity must implement OnDeviceItemClickListener")
        }
    }*/

    override fun onClick(v: View) {
        with(binding) {
            when(v) {
                btnLogin -> {}//sendLoginData()
                btnLoginCancel -> close(false, null)
                else -> {throw RuntimeException("Unknown case")}
            }
        }
    }

    /*private fun setTextChangeListeners() {
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (AuthValidation.isNameConsistent(s.toString())) {
                    //TODO set galochka
                } else binding.etUsername.error = INVALID_USERNAME
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (AuthValidation.isPasswordConsistent(s.toString())) {
                    //TODO set galochka
                } else binding.etPassword.error = INVALID_PASSWORD
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }*/

    private fun showAuthUser(username: String) {
        Log.d("Login", "auth is not succeed")
        with(binding) {
            etUsername.setText(username)

            etPassword.setText("h98dGDJx")
        }
    }

    /*private fun sendLoginData() {
        with(binding) {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            with(loginViewModel) {
                if (!AuthValidation.isNameConsistent(username)) etUsername.error = getString(R.string.invalid_username)
                else if (!AuthValidation.isPasswordConsistent(password)) etPassword.error = getString(R.string.invalid_password)
                else getUserWithToken(username, password)
            }
        }
    }*/

    /*private fun cancelLogin() {
        requireActivity().toast(getString(R.string.login_cancel))
        popBackStack()
    }

    private fun loginSucceed() {
        requireActivity().toast(getString(R.string.login_succeed))
        loginFragmentViewModel.handleLoginSucceed()
        popBackStack()
    }*/

    private fun close(isSucceed: Boolean, authUser: AuthUser?) {
        /*if (isSucceed) {
            requireActivity().toast(getString(R.string.login_succeed))
            requireActivity().supportFragmentManager
                .setFragmentResult(LOGIN_INFO,bundleOf(AUTH_USER to authUser))
        } else {
            requireActivity().toast(getString(R.string.login_cancel))
        }
        popBackStack()*/
    }


    private fun popBackStack() {
        //requireActivity().findNavController(R.id.nav_host_fragment_activity_main).popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}