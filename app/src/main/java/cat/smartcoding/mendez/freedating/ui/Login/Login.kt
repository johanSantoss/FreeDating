package cat.smartcoding.mendez.freedating.ui.Login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cat.smartcoding.mendez.freedating.R
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var tvTexto: TextView
    private lateinit var btAutentifica: Button
    private lateinit var btAlta: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btRtDatabase: Button
    private lateinit var btStorage: Button

    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}