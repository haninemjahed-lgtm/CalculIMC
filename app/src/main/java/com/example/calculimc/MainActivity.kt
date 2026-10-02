package com.example.calculimc

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPoids = findViewById<EditText>(R.id.editTextPoids)
        val editTextTaille = findViewById<EditText>(R.id.editTextTaille)
        val buttonCalculer = findViewById<Button>(R.id.buttonCalculer)
        val buttonEffacer = findViewById<Button>(R.id.buttonEffacer)
        val textViewImc = findViewById<TextView>(R.id.textViewImc)
        val textViewCategorie = findViewById<TextView>(R.id.textViewCategorie)

        buttonCalculer.setOnClickListener {
            val textePoids = editTextPoids.text.toString().trim()
            val texteTaille = editTextTaille.text.toString().trim()

            // Vérifier que les deux champs sont remplis
            if (textePoids.isEmpty() || texteTaille.isEmpty()) {
                Toast.makeText(this, getString(R.string.erreur_champs_vides), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir en nombres décimaux (la virgule est acceptée)
            val poids = textePoids.replace(',', '.').toDoubleOrNull()
            val taille = texteTaille.replace(',', '.').toDoubleOrNull()

            // Vérifier que les valeurs sont valides et strictement positives
            if (poids == null || taille == null || poids <= 0 || taille <= 0) {
                Toast.makeText(this, getString(R.string.erreur_valeurs_invalides), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calcul et affichage de l'IMC
            val imc = poids / (taille * taille)
            val imcAffiche = String.format(Locale.getDefault(), "%.2f", imc)
            textViewImc.text = getString(R.string.resultat_imc, imcAffiche)

            // Catégorie et couleur
            val (categorie, couleur) = if (imc < 18.5) {
                Pair(R.string.categorie_insuffisance, Color.rgb(239, 108, 0))
            } else if (imc < 25) {
                Pair(R.string.categorie_normale, Color.rgb(46, 125, 50))
            } else if (imc < 30) {
                Pair(R.string.categorie_surpoids, Color.rgb(239, 108, 0))
            } else if (imc < 35) {
                Pair(R.string.categorie_obesite_moderee, Color.rgb(211, 47, 47))
            } else if (imc < 40) {
                Pair(R.string.categorie_obesite_severe, Color.rgb(211, 47, 47))
            } else {
                Pair(R.string.categorie_obesite_morbide, Color.rgb(139, 0, 0))
            }

            textViewCategorie.text = getString(categorie)
            textViewCategorie.setTextColor(couleur)
            textViewImc.setTextColor(couleur)
        }

        buttonEffacer.setOnClickListener {
            editTextPoids.text.clear()
            editTextTaille.text.clear()
            textViewImc.text = ""
            textViewCategorie.text = ""
            editTextPoids.requestFocus()
        }
    }
}