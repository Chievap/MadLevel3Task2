package com.example.madlevel3task2

import android.app.PendingIntent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_portal.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private val portalAdapter: PortalAdapter = PortalAdapter(portals) { portal: Portal -> portalItemClicked(portal) }
    private var customTabHelper: CustomTabHelper = CustomTabHelper()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        ObserveAddPortal()
    }
    private fun initViews() {
        rvPortals.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        rvPortals.adapter = portalAdapter
    }

    private fun ObserveAddPortal() {
        setFragmentResultListener(REQ_PORTAl_KEY) {resultKey, bundle ->  bundle.getParcelable<Portal>(
            BUNDLE_PORTAL_KEY
        )?.let { portals.add(it)
        portalAdapter.notifyDataSetChanged()}}
    }

    private fun portalItemClicked(portal : Portal) {
        val builder = CustomTabsIntent.Builder()

        // add share button to overflow menu
        builder.addDefaultShareMenuItem()
        activity?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
            builder.setToolbarColor(
                it
            )
        }

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.data = Uri.parse(portal.url)

        val pendingIntent = PendingIntent.getActivity(activity,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        // add menu item to oveflow
        builder.addMenuItem("Sample item", pendingIntent)
        builder.setShowTitle(true)

        // animation for enter and exit of tab
        activity?.let { builder.setStartAnimations(it, android.R.anim.fade_in, android.R.anim.fade_out) }
        activity?.let { builder.setExitAnimations(it, android.R.anim.fade_in, android.R.anim.fade_out) }

        val customTabsIntent = builder.build()

        // check is chrome available
        val packageName = activity?.let { customTabHelper.getPackageNameToUse(it, portal.url) }

        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName)
            activity?.let { customTabsIntent.launchUrl(it, Uri.parse(portal.url)) }
        } else {
            Toast.makeText(activity,getString(R.string.install_chrome),Toast.LENGTH_SHORT).show()
        }


    }

}