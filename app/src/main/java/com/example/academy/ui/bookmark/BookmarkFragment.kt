package com.example.academy.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy.R
import com.example.academy.data.CourseEntity
import kotlinx.android.synthetic.main.fragment_bookmark.*

class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[BookmarkViewModel::class.java]
            val courses = viewModel.getBookmarks()

            val adapter = BookmarkAdapter(this)
            adapter.setCourses(courses)

            with(rv_bookmark) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder.from(activity as AppCompatActivity).apply {
                setType(mimeType)
                setChooserTitle("Bagikan aplikasi ini sekarang.")
                setText(resources.getString(R.string.share_text, course.title))
                startChooser()
            }
        }
    }

}