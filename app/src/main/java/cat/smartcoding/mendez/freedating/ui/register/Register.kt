package cat.smartcoding.mendez.freedating.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.R
import cat.smartcoding.mendez.freedating.databinding.FragmentRegisterBinding
import cat.smartcoding.mendez.freedating.ui.Login.LoginDirections

class Register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    companion object {
        fun newInstance() = Register()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )


        // Get the viewModel
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        binding.btnNewRegister.setOnClickListener {

            // hay que generar un control para que los campos no esten vacios antes de iniciar este proceso--------------------------------------------------------------------------------------------------
            subirDatos()
            createAccount(viewModel.email.value.toString(), viewModel.password.value.toString(), viewModel.password2.value.toString())

        }

        binding.btnClear.setOnClickListener {
            binding.editTextEmailRegister.text.clear()
            binding.editTextPassword.text.clear()
            binding.editTextPassword2.text.clear()
        }



        return  binding.root
//        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        (activity as MainActivity).disableMenus()

        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        (activity as MainActivity).enableMenus()
        supportActionBar?.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createAccount(email: String, password: String, password2: String) {

        if (password == password2){
            // [START create_user_with_email]
            (activity as MainActivity).getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = (activity as MainActivity).getAuth().currentUser
                        Toast.makeText(activity, "Register success!", Toast.LENGTH_SHORT).show()
                        subirDatos()
                        val action = RegisterDirections.actionRegisterToLogin()
                        NavHostFragment.findNavController(this).navigate(action)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Register failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            // [END create_user_with_email]
        } else {
            Toast.makeText(activity, "Passwords not equals .", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subirDatos(){
        // guardar datos en el viewModel--------------------------------------------------------------------------------------------------------------------------------------
        viewModel.setEmail(binding.editTextEmailRegister.text.toString().trim())
        viewModel.setPassword(binding.editTextPassword.text.toString().trim())
        viewModel.setPassword2(binding.editTextPassword2.text.toString().trim())

        // guardar datos en "RealTimeDataBase" ---------------------------------------------------------------------------------------------------------------------------------

    }
}