package alexandra.rodriguez.seguimientocuidadomascotas.inicio

import alexandra.rodriguez.seguimientocuidadomascotas.BotonesMenu
import alexandra.rodriguez.seguimientocuidadomascotas.Mascota
import alexandra.rodriguez.seguimientocuidadomascotas.MenuActivity
import alexandra.rodriguez.seguimientocuidadomascotas.R
import alexandra.rodriguez.seguimientocuidadomascotas.adapters.BotonesAdaptador
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide

class MascotasperfilActivity : AppCompatActivity() {
    var botonesMenuPerfilM=ArrayList<BotonesMenu>()
    var adapter: BotonesAdaptador? =null
    lateinit var mascota: Mascota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotasperfil)

        val bundle = intent.extras

        if(bundle != null){

            val imageM: de.hdodenhof.circleimageview.CircleImageView = findViewById(R.id.my_image_view)
            val nombreM: TextView = findViewById(R.id.nombreM)
            val edadM: TextView = findViewById(R.id.edadM)

            if(edadM.equals("New Pet")){
                finish()
            }

            nombreM.setText(bundle.getString("nombre").toString())
            edadM.setText(bundle.getString("edad").toString())
            var imagenS: String = bundle.getString("uri").toString()
            val imagenUri = Uri.parse(imagenS)
            mascota = Mascota(bundle.getString("nombre").toString(), bundle.getInt("image"), imagenUri, bundle.getString("edad").toString() )
            if (mascota.imageUri.toString() != "") {
                Glide.with(this)
                    .load(mascota.imageUri)
                    .into(imageM)
            } else {
                imageM.setImageResource(mascota.image)
            }

        }
        cargarBotones()
        adapter = BotonesAdaptador(this, botonesMenuPerfilM)

        var gridPelis: GridView = findViewById(R.id.mascotasBotones)

        gridPelis.adapter = adapter

        val btn_menu: ImageView = findViewById(R.id.menu) as ImageView

        btn_menu.setOnClickListener {
            var intento = Intent(this, MenuActivity::class.java)
            intento.putExtra("nombre",  mascota.nombre)
            intento.putExtra("image",  mascota.image)
            intento.putExtra("edad", mascota.edad)
            intento.putExtra("uri", mascota.imageUri.toString())
            this.startActivity(intento)
            finish()
        }
    }

    fun cargarBotones(){
        botonesMenuPerfilM.add(BotonesMenu("Signos vitales", R.drawable.frecuenciacardiaca, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Comportamiento", R.drawable.comportamiento, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Historial Clinico", R.drawable.historial, mascota))
        botonesMenuPerfilM.add(BotonesMenu("Información General", R.drawable.informacion, mascota))
    }

}