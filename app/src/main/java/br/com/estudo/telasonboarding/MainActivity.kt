package br.com.estudo.telasonboarding

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        banners = resources.getStringArray(R.array.banners)
        adapter = BannerAdapter(this, banners.size)
        view_pager.adapter = adapter


        // Evento de paginação
        view_pager.registerOnPageChangeCallback(callback)

        //Scrolling vertical
        //view_pager.orientation = ViewPager2.ORIENTATION_VERTICAL

        //TabLayout sincronizado com o View Pager
        TabLayoutMediator(tabLayout, view_pager) { tab, position ->
            tab.text = banners[position].split(".")[0]
        }.attach()

        //Animação
        view_pager.offscreenPageLimit = 3
        view_pager.clipToOutline = false
        view_pager.clipChildren = false

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer { page, position ->
            val ref = 1 - abs(position)
            page.scaleY = (0.85f + ref * 0.15f)
        }
        compositePageTransformer.addTransformer(MarginPageTransformer(40))

        view_pager.setPageTransformer(compositePageTransformer)

//        Suporte RTL
//        view_pager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
//        tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    private lateinit var adapter: BannerAdapter
    private lateinit var banners: Array<String>
    private val slider = Handler()

    private val runnable = Runnable {
        view_pager.currentItem = view_pager.currentItem + 1
    }

    private val callback
        get() = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if ((banners.size - 2) == position) {
                    banners += resources.getStringArray(R.array.banners)
                    adapter.itemsCount = banners.size
                    adapter.notifyDataSetChanged()
                }
                slider.removeCallbacks(runnable)
                slider.postDelayed(runnable, 1500)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        view_pager.unregisterOnPageChangeCallback(callback)
    }

    inner class BannerAdapter(activity: AppCompatActivity, var itemsCount: Int) :
        FragmentStateAdapter(activity) {
        override fun getItemCount() = itemsCount

        override fun createFragment(position: Int): Fragment {
            val filename = banners[position]
            return BannerFragment.getInstance(filename)
        }

    }
}
