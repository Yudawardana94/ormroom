package com.example.ormroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ormroom.databinding.ActivityAddBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var studentDatabase: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDatabase = StudentDatabase.getInstance(this)

        binding.btnSave.setOnClickListener{
            val objectStudent = Student(null, binding.etNamaStudent.text.toString(), binding.etEmailStudent.text.toString())

            GlobalScope.launch {
                val result = studentDatabase?.studentDao()?.insertStudent(objectStudent)
                runOnUiThread{
                    if(result != 0L) {
                        Toast.makeText(this@AddActivity, "success menambahkan student ${objectStudent.nama}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddActivity, "gagal menambahkan student ${objectStudent.nama}", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }
        }
    }
}