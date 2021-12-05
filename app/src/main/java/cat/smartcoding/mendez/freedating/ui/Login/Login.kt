package cat.smartcoding.mendez.freedating.ui.Login


import android.content.Intent
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import cat.smartcoding.mendez.freedating.ui.gallery.GalleryFragment


class Login : Fragment() {
//    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
//    private lateinit var email : String
//    private lateinit var pass  : String

    companion object {
        fun newInstance() = Login()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {

        (activity as AppCompatActivity).supportActionBar?.hide()

        //auth = Firebase.auth
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

            viewModel.setEmail(binding.editTextMailAuth.text.toString().trim())
 //           email = viewModel.email.value.toString()

            viewModel.setPassword(binding.editTextPassAuth.text.toString().trim())
//            pass = binding.editTextPassAuth.text.toString()

//            signIn( email.trim(), pass.trim())
            signIn( viewModel.email.value.toString(), viewModel.password.value.toString())
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_login, container, false)
    }


    private fun signIn(email: String, password: String) {
       // val auth : FirebaseAuth? = viewModel.auth.value


        if (viewModel.auth.value != null){
            // [START sign_in_with_email]
            try {
                viewModel.auth.value!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            Toast.makeText(activity, "Authentication success.", Toast.LENGTH_SHORT).show()
                            val user = viewModel.auth.value!!.currentUser

                            clearText()

                            val action = LoginDirections.actionLoginToNavGallery(email)
                            NavHostFragment.findNavController(this).navigate(action)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            clearText()
                        }
                    }
            }catch (e: IllegalArgumentException)  {
                Toast.makeText(activity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
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

