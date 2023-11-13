package com.example.mvistudysampleproject.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.mvistudysampleproject.collectIn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<VB : ViewDataBinding, I : MviIntent, S : MviState, VM : MviBaseViewModel<I, S>>(@LayoutRes protected val contentLayoutId: Int) :
    AppCompatActivity(),
    MviBaseView<I, S> {
    protected abstract val vm: VM
    protected lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        vm.viewState.collectIn(this) { render(it) }

        viewIntents()
            .onEach { vm.processIntent(it) }
            .launchIn(lifecycleScope)
    }
}
