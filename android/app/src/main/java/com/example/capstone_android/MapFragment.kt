package com.example.capstone_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.naver.maps.map.MapView
import kotlinx.android.synthetic.main.fragment_map.view.*

class MapFragment : Fragment() {
    var mapview: MapView?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view= LayoutInflater.from(activity).inflate(R.layout.fragment_map,container,false)
        mapview=view.map_fragment
        mapview?.onCreate(savedInstanceState)
        return view
    }

    override fun onStart() {
        super.onStart()
        mapview?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapview?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapview?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapview?.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapview?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapview?.onLowMemory()
    }
}