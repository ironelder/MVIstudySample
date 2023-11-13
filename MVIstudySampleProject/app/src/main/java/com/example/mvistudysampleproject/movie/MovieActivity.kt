package com.example.mvistudysampleproject.movie

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.mvistudysampleproject.R
import com.example.mvistudysampleproject.base.BaseActivity
import com.example.mvistudysampleproject.databinding.ActivityMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

@FlowPreview
@AndroidEntryPoint
class MovieActivity : BaseActivity<ActivityMovieBinding, MovieIntent, MovieState, MovieViewModel>(R.layout.activity_movie) {

    override val vm: MovieViewModel by viewModels()

    private val initialIntent = flowOf(MovieIntent.InitialIntent)
    private val refreshIntent = flowOf(MovieIntent.SwipeToRefresh)
    private val changeFilterIntent = flowOf(MovieIntent.FilterIntent(FilterType.All()))

    override fun viewIntents(): Flow<MovieIntent> = merge(
        initialIntent,
        callbackFlow<Unit> {
            (binding as ActivityMovieBinding).swipeRefreshLayout.setOnRefreshListener {
                trySend(Unit)
            }
            awaitClose { (binding as ActivityMovieBinding).swipeRefreshLayout.setOnRefreshListener(null) }
        }.map { MovieIntent.SwipeToRefresh },
        changeFilterIntent,
    )

    override fun render(state: MovieState) {
        Log.d("ironelder", "render ${state.initial}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        movieBinding = ActivityMovieBinding.inflate(layoutInflater)
//        setContentView(movieBinding.root)
    }
}
