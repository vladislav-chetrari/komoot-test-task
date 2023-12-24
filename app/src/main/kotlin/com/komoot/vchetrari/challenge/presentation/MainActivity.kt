package com.komoot.vchetrari.challenge.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.komoot.vchetrari.challenge.R
import com.komoot.vchetrari.challenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val listAdapter by lazy { PhotoListAdapter() }
    private var isLocationTrackingEnabled = false

    private val notificationPermissionRequest = registerForActivityResult(RequestPermission()) { _ -> }

    private val locationPermissionsRequest = registerForActivityResult(RequestMultiplePermissions()) { result ->
        if (result.values.all { it }) {
            startService(Intent(this, LocationTrackingBackgroundService::class.java))
        } else {
            showLocationPermissionsRationaleDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setTitle(R.string.app_name)
        binding.list.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.padding_small)))
        binding.list.adapter = listAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collectLatest { viewState ->
                    isLocationTrackingEnabled = viewState.isLocationTrackingEnabled
                    invalidateOptionsMenu()
                    listAdapter.submitList(viewState.photos) {
                        binding.list.smoothScrollToPosition(0)
                    }
                }
            }
        }

        if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            notificationPermissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_item_stop).isVisible = isLocationTrackingEnabled
        menu.findItem(R.id.menu_item_start).isVisible = !isLocationTrackingEnabled
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_item_start -> {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            locationPermissionsRequest.launch(
                if (VERSION.SDK_INT < VERSION_CODES.UPSIDE_DOWN_CAKE) permissions
                else permissions.plus(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
            )
            true
        }
        R.id.menu_item_stop -> {
            stopService(Intent(this, LocationTrackingBackgroundService::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        binding.list.adapter = null
        super.onDestroy()
    }

    private fun showLocationPermissionsRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_location_rationale_title)
            .setMessage(R.string.dialog_location_rationale_message)
            .setNegativeButton(R.string.dialog_location_rationale_close_app) { _, _ -> finish() }
            .setPositiveButton(R.string.dialog_location_rationale_go_to_settings) { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
            }
            .show()
    }
}