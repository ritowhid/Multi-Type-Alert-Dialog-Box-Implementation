package com.program.alertdialogbox

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.program.alertdialogbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Type 1: Exit Confirmation Dialog
        binding.btn1.setOnClickListener {
            val builder1 = AlertDialog.Builder(this)
            builder1.setTitle("Confirm Exit")
            builder1.setMessage("Do you want to close the app?")
            builder1.setIcon(R.drawable.exit_to_app)
            builder1.setPositiveButton("Yes") { _, _ ->
                finish()
            }
            builder1.setNegativeButton("No", null)
            builder1.show()
        }

        // Type 2: Simple Single Choice Dialog
        binding.btn2.setOnClickListener {
            val options = arrayOf("C", "C++", "Java", "Kotlin")
            val builder2 = AlertDialog.Builder(this)
            builder2.setTitle("Which is your favourite programming language?")
            builder2.setSingleChoiceItems(options, -1) { _, which ->
                Toast.makeText(this, "You clicked on ${options[which]}", Toast.LENGTH_SHORT).show()
            }
            builder2.setPositiveButton("Submit") { dialog, _ -> dialog.dismiss() }
            builder2.setNegativeButton("Decline", null)
            builder2.show()
        }

        // Type 3: Multi-Choice Dialog
        binding.btn3.setOnClickListener {
            val options = arrayOf("C", "C++", "Java", "Kotlin")
            val selectedItems = BooleanArray(options.size)

            AlertDialog.Builder(this)
                .setTitle("Which is your favorite programming language?")
                .setMultiChoiceItems(options, selectedItems) { _, which, isChecked ->
                    selectedItems[which] = isChecked
                }
                .setPositiveButton("Submit") { _, _ ->
                    val selected = options.filterIndexed { i, _ -> selectedItems[i] }
                    Toast.makeText(this, "Selected: ${selected.joinToString()}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Decline") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // Type 4: Toggleable Single Choice (Custom)
        binding.btn4.setOnClickListener {
            val options = arrayOf("C", "C++", "Java", "Kotlin")
            var selectedIndex = -1

            val builder = AlertDialog.Builder(this)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, options)

            builder.setTitle("Choose your favorite (can unselect)")
            builder.setSingleChoiceItems(adapter, selectedIndex) { dialog, which ->
                val alertDialog = dialog as AlertDialog
                if (selectedIndex == which) {
                    selectedIndex = -1
                    alertDialog.listView.setItemChecked(which, false)
                } else {
                    selectedIndex = which
                }
            }
            builder.setPositiveButton("Submit") { dialog, _ ->
                if (selectedIndex != -1) {
                    Toast.makeText(this, "You selected: ${options[selectedIndex]}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No option selected", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }
}
