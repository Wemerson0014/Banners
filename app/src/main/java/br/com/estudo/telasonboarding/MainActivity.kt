package br.com.estudo.telasonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val banners = resources.getStringArray(R.array.banners)
        val adapter = BannerAdapter(this, banners.size)
        view_pager.adapter = adapter
    }

    class BannerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = itemsCount

        override fun createFragment(position: Int) = BannerFragment.getInstance(position)

    }
}
