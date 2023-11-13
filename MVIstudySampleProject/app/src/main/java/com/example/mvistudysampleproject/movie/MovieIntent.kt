package com.example.mvistudysampleproject.movie

import com.example.mvistudysampleproject.base.MviIntent

sealed class MovieIntent : MviIntent {
    object InitialIntent : MovieIntent()
    object SwipeToRefresh : MovieIntent()
    data class FilterIntent(val type: FilterType) : MovieIntent()
}

sealed class FilterType(val typeName: String) {
    data class All(val name: String = "All") : FilterType(name)
    data class US(val name: String = "US") : FilterType(name)
    data class CA(val name: String = "CA") : FilterType(name)
    data class FR(val name: String = "FR") : FilterType(name)
    data class DE(val name: String = "DE") : FilterType(name)
}
