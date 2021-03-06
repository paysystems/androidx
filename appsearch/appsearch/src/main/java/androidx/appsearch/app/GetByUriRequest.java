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

package androidx.appsearch.app;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Encapsulates a request to retrieve documents by namespace and URI.
 *
 * @see AppSearchSession#getByUri
 */
public final class GetByUriRequest {
    private final String mNamespace;
    private final Set<String> mUris;

    GetByUriRequest(@NonNull String namespace, @NonNull Set<String> uris) {
        mNamespace = namespace;
        mUris = uris;
    }

    /** Returns the namespace to get documents from. */
    @NonNull
    public String getNamespace() {
        return mNamespace;
    }

    /** Returns the URIs to get from the namespace. */
    @NonNull
    public Set<String> getUris() {
        return Collections.unmodifiableSet(mUris);
    }

    /** Builder for {@link GetByUriRequest} objects. */
    public static final class Builder {
        private String mNamespace = GenericDocument.DEFAULT_NAMESPACE;
        private final Set<String> mUris = new ArraySet<>();
        private boolean mBuilt = false;

        /**
         * Sets which namespace these documents will be retrieved from.
         *
         * <p>If this is not set, it defaults to {@link GenericDocument#DEFAULT_NAMESPACE}.
         */
        @NonNull
        public Builder setNamespace(@NonNull String namespace) {
            Preconditions.checkState(!mBuilt, "Builder has already been used");
            Preconditions.checkNotNull(namespace);
            mNamespace = namespace;
            return this;
        }

        /** Adds one or more URIs to the request. */
        @NonNull
        public Builder addUri(@NonNull String... uris) {
            Preconditions.checkNotNull(uris);
            return addUri(Arrays.asList(uris));
        }

        /** Adds one or more URIs to the request. */
        @NonNull
        public Builder addUri(@NonNull Collection<String> uris) {
            Preconditions.checkState(!mBuilt, "Builder has already been used");
            Preconditions.checkNotNull(uris);
            mUris.addAll(uris);
            return this;
        }

        /** Builds a new {@link GetByUriRequest}. */
        @NonNull
        public GetByUriRequest build() {
            Preconditions.checkState(!mBuilt, "Builder has already been used");
            mBuilt = true;
            return new GetByUriRequest(mNamespace, mUris);
        }
    }
}
