package dahlke.br.harrypotterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnCharacterById : Button
    private lateinit var btnStaff : Button
    private lateinit var btnCharactersHouse : Button
    private lateinit var btnExit : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCharacterById = findViewById(R.id.btnCharacterById)
        btnStaff = findViewById(R.id.btnStaff)
        btnCharactersHouse = findViewById(R.id.btnCharactersHouse)
        btnExit = findViewById(R.id.btnExit)

        btnCharacterById.setOnClickListener {
            val intent = Intent(this, ListarPersonagemActivity::class.java)
            startActivity(intent)
        }

        btnStaff.setOnClickListener {
            val intent = Intent(this, ListarProfessorEscolaActivity::class.java)
            startActivity(intent)
        }

        btnCharactersHouse.setOnClickListener {
            val intent = Intent(this, ListarEstudantesCasaActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            finish()
        }
    }

    /*val retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    viaCepApi = retrofit.create(ViaCepApi::class.java)*/

}