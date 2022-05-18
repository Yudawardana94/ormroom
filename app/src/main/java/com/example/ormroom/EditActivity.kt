package com.example.ormroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ormroom.databinding.ActivityEditBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private var studentDatabase: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDatabase = StudentDatabase.getInstance(this)
        val objStudent = intent.getParcelableExtra<Student>("student")
        binding.run {
            etNamaStudent.setText(objStudent?.nama)
            etEmailStudent.setText(objStudent?.email)

            btnSave.setOnClickListener{
                objStudent?.nama = etNamaStudent.text.toString()
                objStudent?.email = etEmailStudent.text.toString()

                GlobalScope.async {
                    val result = objStudent?.let { it1 ->
                        studentDatabase?.studentDao()?.updateStudent(
                            it1
                        )
                    }

                    runOnUiThread {
                        if(result != 0) {
                            Toast.makeText(this@EditActivity, "berhasil mengedit data student ${objStudent?.nama}", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@EditActivity, "gagal mengedit data student ${objStudent.nama}", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                }
            }
        }
    }
}