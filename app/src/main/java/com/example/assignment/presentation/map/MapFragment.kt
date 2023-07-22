package com.example.assignment.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.assignment.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pub.devrel.easypermissions.AppSettingsDialog

class MapFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val loc = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(loc).title("Current Location")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(loc))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 7f))
        googleMap.addMarker(markerOptions)
    }
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var requestPermission: ActivityResultLauncher<String>
    private var mapFragment: SupportMapFragment? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        register()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back: ImageView = view.findViewById(R.id.map_btn_back)
        val btmNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        btmNav.visibility = View.GONE
        back.setOnClickListener {
            btmNav.selectedItemId = R.id.home
        }
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if (hasNoLocationPerm()) {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                Toast.makeText(
                    requireContext(), currentLocation.latitude.toString() + "" +
                            currentLocation.longitude, Toast.LENGTH_SHORT
                ).show()
                mapFragment?.getMapAsync(callback)
            }
        }
    }
    private fun register() {
        requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                fetchLocation()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Location Permission")
                        .setMessage("This permission is needed for using Map feature")
                        .setPositiveButton(
                            "Try Again"
                        ) { _, _ -> fetchLocation() }
                        .setNegativeButton(
                            "No Thanks"
                        ) { dialogInterface, _ -> dialogInterface.dismiss() }
                        .show()
                } else {
                    AppSettingsDialog.Builder(this).build().show()
                }
            }
        }
    }

    private fun hasNoLocationPerm(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) !=
                PackageManager.PERMISSION_GRANTED
    }

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return super.shouldShowRequestPermissionRationale(permission)
    }
}