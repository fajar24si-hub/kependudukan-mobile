package com.example.fajar_time.Message.OnBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fajar_time.R

class OnBoardingFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "description"
        private const val ARG_ICON = "icon_res"

        fun newInstance(title: String, description: String, iconRes: Int): OnBoardingFragment {
            return OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESC, description)
                    putInt(ARG_ICON, iconRes)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE) ?: ""
        val description = arguments?.getString(ARG_DESC) ?: ""
        val iconRes = arguments?.getInt(ARG_ICON) ?: 0

        view.findViewById<TextView>(R.id.tvSlideTitle).text = title
        view.findViewById<TextView>(R.id.tvSlideDescription).text = description
        if (iconRes != 0) {
            view.findViewById<ImageView>(R.id.ivSlideIcon).setImageResource(iconRes)
        }
    }
}
