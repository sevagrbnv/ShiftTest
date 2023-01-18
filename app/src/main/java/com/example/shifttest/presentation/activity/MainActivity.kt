package com.example.shifttest.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransitionImpl
import com.example.shifttest.R
import com.example.shifttest.presentation.detailFragment.DetailFragment
import com.example.shifttest.utils.ConnectionLiveData
import com.example.shifttest.presentation.mainFragment.MainFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainFragment.OpenSecondFragmentListener {

    private var container: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container)
        if (savedInstanceState == null) {
            setMainFragmentContainer()
        }

    }

    private fun setMainFragmentContainer() {
        val fragment = MainFragment()
        container?.let {
            supportFragmentManager.beginTransaction()
                .add(it.id, fragment)
                .commit()
        }
    }

    override fun openSecondFragment(itemId: Int) {
        val fragment = DetailFragment.newInstance(itemId)
        container?.let {
            supportFragmentManager.beginTransaction()
                .replace(it.id, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit()
        }
    }
}