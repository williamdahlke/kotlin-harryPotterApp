import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dahlke.br.harrypotterapp.CharacterResponse
import dahlke.br.harrypotterapp.R

class StudentsHouseImageTextArrayAdapter(private val items: List<CharacterResponse>, private val context: Context) : RecyclerView.Adapter<StudentsHouseImageTextArrayAdapter.CharacterViewHolder>()
{
    inner class CharacterViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val characterImage = itemView.findViewById<ImageView>(R.id.item_image)
        val characterName = itemView.findViewById<TextView>(R.id.item_text)

        fun bind(character : CharacterResponse){
            if (character.image.isNotEmpty()){
                Picasso.get().load(character.image).resize(100,100).into(characterImage)
            } else{
                Picasso.get().load(R.drawable.no_image).resize(100,100).into(characterImage)
            }
            characterName.text = character.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_imagem_texto, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)
    }
}