package sananismayilov.au.myapplicationmap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import sananismayilov.au.myapplicationmap.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var permission = 0
    private lateinit var fcpl: FusedLocationProviderClient
    private lateinit var locationtask: Task<Location>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fcpl = LocationServices.getFusedLocationProviderClient(this)
        binding.show.setOnClickListener {
            permission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

            if (permission == PackageManager.PERMISSION_GRANTED) {
                locationtask = fcpl.lastLocation
                getLoc()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            }
        }

    }

    private fun getLoc() {
        locationtask.addOnSuccessListener {
            if (it != null) {
                binding.showt1.text = it.latitude.toString()
                binding.showt2.text = it.longitude.toString()
            }else{
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                locationtask = fcpl.lastLocation
                getLoc()
                Toast.makeText(this, "Grant", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}