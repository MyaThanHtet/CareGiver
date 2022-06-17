package com.myatech.caregiver.nearbyplaces

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.myatech.caregiver.R


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var mMap: GoogleMap? = null
    private var client: GoogleApiClient? = null
    private var locationRequest: LocationRequest? = null
    private var lastlocation: Location? = null
    private var currentLocationMarker: Marker? = null
    val REQUEST_LOCATION_CODE = 99
    var PROXIMITY_RADIUS = 10000
    var latitude = 0.0
    var longitude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        findViewById<Button>(R.id.get_nearby_hospital).setOnClickListener {

            Toast.makeText(this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show()

           //  showNearByHospital()
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (client == null) {
                            bulidGoogleApiClient()
                        }
                        mMap!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE //changes the type of map
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            bulidGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }

    @Synchronized
    protected fun bulidGoogleApiClient() {
        client = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        client!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        lastlocation = location
        if (currentLocationMarker != null) {
            currentLocationMarker!!.remove()
        }
        Log.d("lat = ", "" + latitude)
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        currentLocationMarker = mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomBy(50f))
        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client!!, this)
        }
    }

    fun showNearByHospital() {
        val dataTransfer = arrayOfNulls<Any>(2)
        val getNearbyPlacesData = GetNearbyPlacesData()
        mMap!!.clear()
        val Hospital = "hospital"
        val url = getUrl(latitude, longitude, Hospital)
        dataTransfer[0] = mMap
        dataTransfer[1] = url
        getNearbyPlacesData.execute(dataTransfer)
        Toast.makeText(this@MapsActivity, "Showing Nearby Hospitals", Toast.LENGTH_SHORT)
            .show()
    }


    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + "AIzaSyDmIY8sHoKqhihC1RfCptYut5GyQ4yigM4")
        Log.d("MapsActivity", "url = $googlePlaceUrl")
        return googlePlaceUrl.toString()
    }

    override fun onConnected(@Nullable bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest!!.interval = 100
        locationRequest!!.fastestInterval = 1000
        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                client!!,
                locationRequest!!,
                this
            )
        }
    }

    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_CODE
                )
            }
            false
        } else {
            true
        }
    }

    override fun onConnectionSuspended(i: Int) {}
    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
}