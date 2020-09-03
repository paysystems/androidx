/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.wear.watchface

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.support.wearable.complications.ComplicationData
import android.support.wearable.complications.ComplicationText
import android.support.wearable.watchface.IWatchFaceService
import android.support.wearable.watchface.WatchFaceStyle
import android.support.wearable.watchface.accessibility.ContentDescriptionLabel
import android.view.SurfaceHolder
import androidx.test.core.app.ApplicationProvider
import androidx.wear.watchface.style.UserStyleCategory
import androidx.wear.watchface.style.UserStyleManager
import org.junit.runners.model.FrameworkMethod
import org.robolectric.RobolectricTestRunner
import org.robolectric.internal.bytecode.InstrumentationConfiguration

class TestWatchFaceService(
    @WatchFaceType private val watchFaceType: Int,
    private val complicationSet: ComplicationSet,
    private val renderer: TestRenderer,
    private val userStyleManager: UserStyleManager,
    private val watchState: WatchState,
    private val handler: Handler,
    private val interactiveFrameRateMs: Long
) : WatchFaceService() {
    var complicationSingleTapped: Int? = null
    var complicationDoubleTapped: Int? = null
    var complicationSelected: Int? = null
    var mockSystemTimeMillis = 0L
    var lastUserStyle: Map<UserStyleCategory, UserStyleCategory.Option>? = null

    init {
        userStyleManager.addUserStyleListener(
            object : UserStyleManager.UserStyleListener {
                override fun onUserStyleChanged(
                    userStyle: Map<UserStyleCategory, UserStyleCategory.Option>
                ) {
                    lastUserStyle = userStyle
                }
            }
        )

        complicationSet.addTapListener(
            object : ComplicationSet.TapListener {
                override fun onComplicationSingleTapped(complicationId: Int) {
                    complicationSingleTapped = complicationId
                }

                override fun onComplicationDoubleTapped(complicationId: Int) {
                    complicationDoubleTapped = complicationId
                }
            })
    }

    fun reset() {
        clearTappedState()
        complicationSelected = null
        renderer.lastOnDrawCalendar = null
        mockSystemTimeMillis = 0L
    }

    fun clearTappedState() {
        complicationSingleTapped = null
        complicationDoubleTapped = null
    }

    init {
        attachBaseContext(ApplicationProvider.getApplicationContext())
    }

    lateinit var watchFace: WatchFace

    override fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchFaceHost: WatchFaceHost,
        watchState: WatchState
    ): WatchFace {
        watchFace = WatchFace.Builder(
            watchFaceType,
            interactiveFrameRateMs,
            userStyleManager,
            complicationSet,
            renderer,
            watchFaceHost,
            watchState
        ).setSystemTimeProvider(object : WatchFace.SystemTimeProvider {
            override fun getSystemTimeMillis(): Long {
                return mockSystemTimeMillis
            }
        }).build()
        return watchFace
    }

    override fun getHandler() = handler

    override fun getSystemState() = watchState
}

/**
 * IWatchFaceService.Stub implementation that redirects all method calls to a mock so they can be
 * verified. (Using a Spy on the actual stub doesn't work).
 */
class WatchFaceServiceStub(private val iWatchFaceService: IWatchFaceService) :
    IWatchFaceService.Stub() {
    override fun setStyle(style: WatchFaceStyle) {
        iWatchFaceService.setStyle(style)
    }

    override fun registerUserStyleSchema(styleSchema: MutableList<Bundle>?) {
        iWatchFaceService.registerUserStyleSchema(styleSchema)
    }

    override fun setActiveComplications(ids: IntArray, updateAll: Boolean) {
        iWatchFaceService.setActiveComplications(ids, updateAll)
    }

    override fun setComplicationDetails(id: Int, bounds: Rect?, @ComplicationBoundsType type: Int) {
        iWatchFaceService.setComplicationDetails(id, bounds, type)
    }

    override fun setDefaultComplicationProvider(
        watchFaceComplicationId: Int,
        provider: ComponentName,
        type: Int
    ) {
        iWatchFaceService.setDefaultComplicationProvider(
            watchFaceComplicationId, provider, type
        )
    }

    override fun setDefaultSystemComplicationProvider(
        watchFaceComplicationId: Int,
        systemProvider: Int,
        type: Int
    ) {
        iWatchFaceService.setDefaultSystemComplicationProvider(
            watchFaceComplicationId, systemProvider, type
        )
    }

    override fun setContentDescriptionLabels(labels: Array<ContentDescriptionLabel>) {
        iWatchFaceService.setContentDescriptionLabels(labels)
    }

    override fun reserved1() {
        iWatchFaceService.reserved1()
    }

    override fun setDefaultComplicationProviderWithFallbacks(
        watchFaceComplicationId: Int,
        providers: List<ComponentName>,
        fallbackSystemProvider: Int,
        type: Int
    ) {
        iWatchFaceService.setDefaultComplicationProviderWithFallbacks(
            watchFaceComplicationId, providers, fallbackSystemProvider, type
        )
    }

    override fun getStoredUserStyle(): Bundle? {
        return iWatchFaceService.getStoredUserStyle()
    }

    override fun setCurrentUserStyle(style: Bundle?) {
        iWatchFaceService.setCurrentUserStyle(style)
    }

    override fun getApiVersion() = iWatchFaceService.apiVersion

    override fun setComplicationSupportedTypes(id: Int, types: IntArray?) {
        iWatchFaceService.setComplicationSupportedTypes(id, types)
    }

    override fun registerWatchFaceType(watchFaceType: Int) {
        iWatchFaceService.registerWatchFaceType(watchFaceType)
    }

    override fun registerIWatchFaceCommand(iWatchFaceCommandBundle: Bundle) {
        iWatchFaceService.registerIWatchFaceCommand(iWatchFaceCommandBundle)
    }
}

class TestRenderer(
    surfaceHolder: SurfaceHolder,
    userStyleManager: UserStyleManager,
    watchState: WatchState
) :
    CanvasRenderer(surfaceHolder, userStyleManager, watchState, CanvasType.HARDWARE) {
    var lastOnDrawCalendar: Calendar? = null
    var lastDrawMode = DrawMode.INTERACTIVE

    override fun onDraw(
        canvas: Canvas,
        bounds: Rect,
        calendar: Calendar
    ) {
        lastOnDrawCalendar = calendar
        lastDrawMode = drawMode
    }
}

fun createComplicationData(): ComplicationData {
    return ComplicationData
        .Builder(ComplicationData.TYPE_SHORT_TEXT)
        .setShortText(ComplicationText.plainText("Test Text"))
        .setTapAction(
            PendingIntent.getActivity(
                ApplicationProvider.getApplicationContext(), 0,
                Intent("Fake intent"), 0
            )
        ).build()
}

/**
 * We need to prevent roboloetric from instrumenting our classes or things break...
 */
class WatchFaceTestRunner(testClass: Class<*>) : RobolectricTestRunner(testClass) {
    override fun createClassLoaderConfig(method: FrameworkMethod): InstrumentationConfiguration =
        InstrumentationConfiguration.Builder(super.createClassLoaderConfig(method))
            .doNotInstrumentPackage("android.support.wearable.watchface")
            .doNotInstrumentPackage("androidx.wear.complications")
            .doNotInstrumentPackage("androidx.wear.watchface")
            .doNotInstrumentPackage("androidx.wear.watchface.ui")
            .doNotInstrumentPackage("androidx.wear.watchfacestyle")
            .build()
}
