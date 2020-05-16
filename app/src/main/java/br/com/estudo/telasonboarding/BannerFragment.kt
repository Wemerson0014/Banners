package br.com.estudo.telasonboarding

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_screen.*

class BannerFragment : Fragment() {

    companion object {
        fun getInstance(filename: String): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putString("filename", filename)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val filename = requireArguments().getString("filename")!!
        val file = resources.assets.open(filename)
        val bitmap = BitmapFactory.decodeStream(file)
        file.close()
        img.setImageBitmap(bitmap)
    }
}