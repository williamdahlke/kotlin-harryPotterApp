package dahlke.br.harrypotterapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListarPersonagemActivity : AppCompatActivity() {

    private lateinit var btnSearch : Button
    private lateinit var editTextId : EditText
    private lateinit var tvCharacterName : TextView
    private lateinit var tvCharacterHouse : TextView
    private lateinit var imgViewCharacter : ImageView

    private lateinit var harryPotterAPI : HarryPotterAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_personagem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        harryPotterAPI = retrofit.create(HarryPotterAPI::class.java)

        btnSearch = findViewById(R.id.btnSearch)
        tvCharacterName = findViewById(R.id.tvCharacterName)
        tvCharacterHouse = findViewById(R.id.tvCharacterHouse)
        imgViewCharacter = findViewById(R.id.imgViewCharacter)
        editTextId = findViewById(R.id.editTextId)

        btnSearch.setOnClickListener{

            clearFields()

            if (editTextId.text.isNotEmpty()){
                searchCharacterByIdFromAPI(editTextId.text.toString())
            } else{
              Toast.makeText(this, getString(R.string.errorMessageGetCharacterById), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        tvCharacterName.text = ""
        tvCharacterHouse.text = ""
        imgViewCharacter.setImageDrawable(null)
    }

    private fun searchCharacterByIdFromAPI(id : String) {
        lifecycleScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    harryPotterAPI.getCharacterById(id)
                }
                if (response.isEmpty()){
                    Toast.makeText(this@ListarPersonagemActivity, R.string.errorMessageGetCharacterById, Toast.LENGTH_SHORT).show()
                } else{
                    val characterResponse = response.first()
                    tvCharacterName.text = buildString {
                        append(getString(R.string.lblCharacterName))
                        append(" ")
                        append(characterResponse.name)
                    }
                    tvCharacterHouse.text = buildString {
                        append(getString(R.string.lblHouseName))
                        append(" ")
                        append(characterResponse.house)
                    }
                    Picasso.get().load(characterResponse.image).resize(800,800).into(imgViewCharacter)
                }
            } catch (ex: Exception){
                Log.e(this@ListarPersonagemActivity.toString(), "Error", ex)
            }
        }
    }

}