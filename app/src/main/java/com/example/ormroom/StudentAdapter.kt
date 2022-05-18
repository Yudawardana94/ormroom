package com.example.ormroom

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ormroom.databinding.StudentItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(val studentList: List<Student>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(StudentItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    inner class StudentViewHolder(private val binding: StudentItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) = binding.run {
            tvID.text = studentList[position].id.toString()
            tvNama.text = studentList[position].nama
            tvEmail.text = studentList[position].email

            ivEdit.setOnClickListener {
                val intent = Intent(it.context, EditActivity::class.java)
                intent.putExtra("student", studentList[position])

                it.context.startActivity(intent)
            }

            ivDelete.setOnClickListener {
                alertDialog(position, it.context)
            }
        }

        fun alertDialog(position: Int, context: Context) {
            AlertDialog.Builder(context)
                .setPositiveButton("ya") { p0,p1 ->
                    val instance = StudentDatabase.getInstance(context)
                    GlobalScope.async {
                        val result = instance?.studentDao()?.deleteStudent(studentList[position])
                        (context as MainActivity).runOnUiThread{
                            if(result != 0) {
                                Toast.makeText(context, "student data ${studentList[position].nama} berhasil dihapus", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(context, "student data ${studentList[position].nama} gagal dihapus", Toast.LENGTH_SHORT).show()
                            }
                        }
                        context.fetchData()
                    }
                }
                .setNegativeButton("tidak") {p0,p1 ->
                    p0.dismiss()
                }
                .setMessage("Apakah anda yakin ingin menghapus data ${studentList[position].nama}?")
                .setTitle("Konfirmasi hapus")
                .create()
                .show()
        }
    }
}