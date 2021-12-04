package cat.smartcoding.mendez.freedating.ui.Login


import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import cat.smartcoding.mendez.freedating.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cat.smartcoding.mendez.freedating.databinding.FragmentLoginBinding
import androidx.navigation.fragment.findNavController


class Login : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance() = Login()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {

        auth = Firebase.auth
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        Log.i("GameFragment", "Called ViewModelProvider.get")

        // Get the viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        binding.btnAuthetification.setOnClickListener {

            var email = binding.editTextMailAuth.text.toString()
            var pass = binding.editTextPassAuth.text.toString()
            signIn( email.trim(), pass.trim())

//            val action = LoginDirections.actionLoginToNavGallery2()
//            action. = viewModel.email.value
//            action.score = viewModel.score.value ?: 0
//
//            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_login, container, false)
    }


    private fun signIn(email: String, password: String) {

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    clearText()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    clearText()
                }
            }

    }

    private  fun clearText(){

        binding.editTextMailAuth.editableText.clear()
        binding.editTextPassAuth.editableText.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel



    }




}

