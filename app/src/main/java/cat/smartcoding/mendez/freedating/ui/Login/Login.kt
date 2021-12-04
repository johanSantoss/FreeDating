package cat.smartcoding.mendez.freedating.ui.Login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cat.smartcoding.mendez.freedating.R
import com.google.firebase.auth.FirebaseAuth
import cat.smartcoding.mendez.freedating.databinding.FragmentLoginBinding


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance() = Login()
        private const val TAG = "EmailPassword"
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {

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

        }




        return binding.root
        //return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel



    }




}