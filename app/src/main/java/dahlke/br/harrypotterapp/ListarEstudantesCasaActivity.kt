package dahlke.br.harrypotterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListarEstudantesCasaActivity : AppCompatActivity() {

    private lateinit var rgHouse : RadioGroup
    private lateinit var btnSearchStudents : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_estudantes_casa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rgHouse = findViewById(R.id.rgHouse)
        btnSearchStudents = findViewById(R.id.btnSearchStudents)

        btnSearchStudents.setOnClickListener {
            getSelectedHouseOption()
        }
    }

    private fun getSelectedHouseOption(){
        val selectedId = rgHouse.checkedRadioButtonId
        if (selectedId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedText = selectedRadioButton.text.toString()

            val intent = Intent(this, ListarEstudantesCasaDetalhesActivity::class.java)
            intent.putExtra("HOUSE", selectedText)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Nenhuma opção selecionada", Toast.LENGTH_SHORT).show()
        }

    }
}