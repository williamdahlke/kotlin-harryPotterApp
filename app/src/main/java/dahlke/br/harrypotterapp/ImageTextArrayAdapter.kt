package dahlke.br.harrypotterapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageTextArrayAdapter(private val items: List<StaffResponse>, private val context: Context) : RecyclerView.Adapter<ImageTextArrayAdapter.StaffViewHolder>()
{
        inner class StaffViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
            val staffImage = itemView.findViewById<ImageView>(R.id.item_image)
            val staffName = itemView.findViewById<TextView>(R.id.item_text)

            fun bind(staff : StaffResponse){
                if (staff.image.isNotEmpty()){
                    Picasso.get().load(staff.image).resize(100,100).into(staffImage)
                } else{
                    Picasso.get().load(R.drawable.no_image).resize(100,100).into(staffImage)
                }
                staffName.text = staff.name
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.list_item_imagem_texto, parent, false)
            return StaffViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
            val staff = items[position]
            holder.bind(staff)
        }
}