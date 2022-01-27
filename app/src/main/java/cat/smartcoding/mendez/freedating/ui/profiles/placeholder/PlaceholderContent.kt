package cat.smartcoding.mendez.freedating.ui.profiles.placeholder

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import cat.smartcoding.mendez.freedating.MainActivity
import cat.smartcoding.mendez.freedating.ui.profiles.ProfilesRecyclerViewAdapter
import cat.smartcoding.mendez.freedating.ui.user.UserFragment
import cat.smartcoding.mendez.freedating.ui.user.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 25

    var database = FirebaseDatabase.getInstance("https://freedating-9dbd7-default-rtdb.europe-west1.firebasedatabase.app/")


    var name = ""
    var edad = ""
    var imgProgile = ""
    var error = ""


    init {
        // Add some sample items.

        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }
    class DatosProfile {
        val nom: String = ""
        val edat: String = ""
        val sexe: String = ""
        val ciutat: String = ""
        val email: String = ""
        val imgProfile = ""
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun cargarDatosUsuario(){
        val auth: FirebaseAuth = Firebase.auth
        val myRef = database.getReference("${auth.currentUser?.uid}/userDates")
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value: DatosProfile? = snapshot.getValue<DatosProfile>()
                name = value?.nom?:""
                edad = value?.edat?:""
                imgProgile = value?.imgProfile?:""
                Log.d("Datos Usuario1: ", name)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun createPlaceholderItem(position: Int): PlaceholderItem {
       Log.d("Datos Usuario2: ", name)
//       Log.d("Datos Usuario: ", edad)
//       cargarDatosUsuario()
        return PlaceholderItem(position.toString(), name, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}