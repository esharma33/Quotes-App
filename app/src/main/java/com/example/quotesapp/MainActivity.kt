package com.example.quotesapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesapp.daos.PostDao
import com.example.quotesapp.models.post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PostAdapter.IPostAdapter {
   // private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            val intent = Intent(this , CreatPost::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
       auth = Firebase.auth

    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollection
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<post>().setQuery(query , post::class.java).build()


        adapter = PostAdapter(recyclerViewOptions , this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
   postDao.updateLikes(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

       menuInflater.inflate(R.menu.logout_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> {
                auth.signOut()
                val logoutIntent = Intent(this , SignInActivity::class.java)
                Toast.makeText(this , "Logout Successfully", Toast.LENGTH_SHORT).show()
                startActivity(logoutIntent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}