package com.example.mvistudysampleproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mvistudysampleproject.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var useCase: MovieUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                useCase.getNowPlayingMovieList(1).collectIn(this@MainActivity) {
                    Log.d("ironelder", "test = $it")
                }
            }.onFailure {
                Log.d("ironelder", "test Error = $it")
            }
        }
        findViewById<Button>(R.id.test_button).setOnClickListener {
            startActivity(Intent(this, MovieActivity::class.java))
        }
    }
}
