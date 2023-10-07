package com.ayush.trulyias.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.trulyias.R
import com.ayush.trulyias.adapters.Listadapter
import com.ayush.trulyias.models.List1
import com.ayush.trulyias.models.List2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Sciencebook : AppCompatActivity() {
    private lateinit var nameTv: TextView
    private lateinit var classTv: TextView
    private lateinit var rv: RecyclerView
    private lateinit var rvAdapter: Listadapter
    private lateinit var parentList: ArrayList<List1>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_science_book)
        val name = intent.getStringExtra("NAME_KEY")
        val userClass = intent.getStringExtra("CLASS_KEY")

        val window: Window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar)
        nameTv = findViewById(R.id.NameTv)
        classTv = findViewById(R.id.classTv)
        nameTv.text = "Hi, $name"
        classTv.text = "Class:$userClass"

        if (name == null || userClass == null) {
            val database = FirebaseDatabase.getInstance().reference
            val userRef = database.child("users")
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val name = userSnapshot.child("name").getValue(String::class.java)
                            val userClass = userSnapshot.child("class").getValue(String::class.java)
                            nameTv.text = "Hi, $name"
                            classTv.text = "Class:$userClass"
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
        parentList = ArrayList()


        rv = findViewById(R.id.rv)

        rv.layoutManager = LinearLayoutManager(this@Sciencebook)
        rv.setHasFixedSize(true)

        val childItems1 = ArrayList<List2>()
        childItems1.add(List2(R.drawable.aaa, "Lava Lamp", "Lava Lamp at home"))
        childItems1.add(List2(R.drawable.bbb, "Ocean Bottle Experiment", "Magnet Experiment at home"))
        childItems1.add(List2(R.drawable.ccc, "Wheel Model Experiment", "Wheel Model at home"))
        parentList.add(List1("Chapter 1: Food: Where Does it Come From", childItems1))
        rvAdapter = Listadapter(parentList) { clickedList2Item ->
            when (clickedList2Item) {
                childItems1[0] -> {
                    val intent = Intent(this, VideoViews::class.java)
                    intent.putExtra("VIDEO_RESOURCE_ID", R.raw.aa1)
                    intent.putExtra("PDF_URL","https://firebasestorage.googleapis.com/v0/b/assignment-54a5f.appspot.com/o/Dummy%20content%20For%20Lava%20Lamp.pdf?alt=media&token=829fc288-0928-4250-8c35-4ce1ac0051f0&_gl=1*1cx9puv*_ga*NTQ0MDc1NjY1LjE2OTY0MTk5MTA.*_ga_CW55HF8NVT*MTY5NjY4NDI5Mi4xNC4xLjE2OTY2ODQzOTkuNDcuMC4w")
                    startActivity(intent)
                }
                childItems1[1] -> {
                    val intent = Intent(this, VideoViews::class.java)
                    intent.putExtra("VIDEO_RESOURCE_ID", R.raw.bb1)
                    intent.putExtra("PDF_URL","https://firebasestorage.googleapis.com/v0/b/assignment-54a5f.appspot.com/o/Dummy%20content%20For%20Ocean%20Bottle.pdf?alt=media&token=e8923e8a-d586-4a23-b60a-f3116fda627d&_gl=1*15u6lv9*_ga*NTQ0MDc1NjY1LjE2OTY0MTk5MTA.*_ga_CW55HF8NVT*MTY5NjY4NDI5Mi4xNC4xLjE2OTY2ODQ0MjYuMjAuMC4w")
                    startActivity(intent)
                }
                childItems1[2] -> {
                    val intent = Intent(this, VideoViews::class.java)
                    intent.putExtra("VIDEO_RESOURCE_ID", R.raw.cc2)
                    intent.putExtra("PDF_URL","https://firebasestorage.googleapis.com/v0/b/assignment-54a5f.appspot.com/o/Dummy%20content%20For%20Wheel%20Model.pdf?alt=media&token=dac49356-c4cd-4c30-b338-552078309162&_gl=1*s3zslv*_ga*NTQ0MDc1NjY1LjE2OTY0MTk5MTA.*_ga_CW55HF8NVT*MTY5NjY4NjM0Ny4xNS4xLjE2OTY2ODYzNzAuMzcuMC4w")
                    startActivity(intent)
                }
            }
        }
        rv.adapter = rvAdapter
        prepareData()
    }

    private fun prepareData() {

        val childItems2 = ArrayList<List2>()
        childItems2.add(List2(R.mipmap.ic_launcher,"A-1","B-1"))
        childItems2.add(List2(R.mipmap.ic_launcher,"C-1","D-1"))
        childItems2.add(List2(R.mipmap.ic_launcher,"E-1","F-1"))
        parentList.add(List1( "Chapter 2: Components of Food",childItems2))


        val childItems3 = ArrayList<List2>()
        childItems3.add(List2(R.mipmap.ic_launcher,"A-2","B-2"))
        childItems3.add(List2(R.mipmap.ic_launcher,"C-2","D-2"))
        childItems3.add(List2(R.mipmap.ic_launcher,"E-2","F-2"))
        parentList.add(List1( "Chapter 3: Fibre To Fabric",childItems3))


        val childItems4 = ArrayList<List2>()
        childItems4.add(List2(R.mipmap.ic_launcher,"A-3","B-3"))
        childItems4.add(List2(R.mipmap.ic_launcher,"C-3","D-3"))
        childItems4.add(List2(R.mipmap.ic_launcher,"E-3","F-3"))
        parentList.add(List1("Chapter 4: Sorting Materials into Groups",childItems4))

        val childItems5 = ArrayList<List2>()
        childItems5.add(List2(R.mipmap.ic_launcher,"A-4","B-4"))
        childItems5.add(List2(R.mipmap.ic_launcher,"C-4","D-4"))
        childItems5.add(List2(R.mipmap.ic_launcher,"E-4","F-4"))
        parentList.add(List1( "Chapter 5: Separation Of Substances",childItems5))



        val childItems6 = ArrayList<List2>()
        childItems6.add(List2(R.mipmap.ic_launcher,"A-5","B-5"))
        childItems6.add(List2(R.mipmap.ic_launcher,"C-5","D-5"))
        childItems6.add(List2(R.mipmap.ic_launcher,"E-5","F-5"))
        parentList.add(List1( "Chapter 6: Changes Around Us",childItems6))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
