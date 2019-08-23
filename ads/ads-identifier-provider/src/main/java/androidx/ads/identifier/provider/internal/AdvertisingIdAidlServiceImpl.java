/*
 * Copyright 2019 The Android Open Source Project
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

package androidx.ads.identifier.provider.internal;

import androidx.ads.identifier.provider.AdvertisingIdProvider;
import androidx.ads.identifier.provider.IAdvertisingIdService;

/**
 * The implementation of the IAdvertisingIdService.aidl which retrieves values from
 * {@link AdvertisingIdProvider} and replies to the client.
 */
class AdvertisingIdAidlServiceImpl extends IAdvertisingIdService.Stub {

    private final AdvertisingIdProvider mProvider;

    AdvertisingIdAidlServiceImpl(AdvertisingIdProvider advertisingIdProvider) {
        mProvider = advertisingIdProvider;
    }

    @Override
    public String getId() {
        return mProvider.getId();
    }

    @Override
    public boolean isLimitAdTrackingEnabled() {
        return mProvider.isLimitAdTrackingEnabled();
    }
}
