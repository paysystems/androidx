/*
 * Copyright 2021 The Android Open Source Project
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

package androidx.wear.tiles.builders;

import static androidx.annotation.Dimension.DP;
import static androidx.annotation.Dimension.SP;

import android.annotation.SuppressLint;

import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.wear.tiles.proto.DimensionProto;

/** Builders for dimensions for layout elements. */
public final class DimensionBuilders {
    private DimensionBuilders() {}

    private static final ExpandedDimensionProp EXPAND = ExpandedDimensionProp.builder().build();
    private static final WrappedDimensionProp WRAP = WrappedDimensionProp.builder().build();

    /** Shortcut for building a {@link DpProp} using a measurement in DP. */
    @NonNull
    public static DpProp dp(@Dimension(unit = DP) float valueDp) {
        return DpProp.builder().setValue(valueDp).build();
    }

    /** Shortcut for building a {@link SpProp} using a measurement in SP. */
    @NonNull
    public static SpProp sp(@Dimension(unit = SP) int valueSp) {
        return SpProp.builder().setValue(valueSp).build();
    }

    /** Shortcut for building an {@link DegreesProp} using a measurement in degrees. */
    @NonNull
    public static DegreesProp degrees(float valueDegrees) {
        return DegreesProp.builder().setValue(valueDegrees).build();
    }

    /**
     * Shortcut for building an {@link ExpandedDimensionProp} that will expand to the size of its
     * parent.
     */
    @NonNull
    public static ExpandedDimensionProp expand() {
        return EXPAND;
    }

    /**
     * Shortcut for building an {@link WrappedDimensionProp} that will shrink to the size of its
     * children.
     */
    @NonNull
    public static WrappedDimensionProp wrap() {
        return WRAP;
    }

    /** A type for linear dimensions, measured in dp. */
    public static final class DpProp
            implements LinearOrAngularDimension, ContainerDimension, ImageDimension {
        private final DimensionProto.DpProp mImpl;

        DpProp(DimensionProto.DpProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /** @hide */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.DpProp toProto() {
            return mImpl;
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.LinearOrAngularDimension toLinearOrAngularDimensionProto() {
            return DimensionProto.LinearOrAngularDimension.newBuilder()
                    .setLinearDimension(mImpl)
                    .build();
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ContainerDimension toContainerDimensionProto() {
            return DimensionProto.ContainerDimension.newBuilder().setLinearDimension(mImpl).build();
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ImageDimension toImageDimensionProto() {
            return DimensionProto.ImageDimension.newBuilder().setLinearDimension(mImpl).build();
        }

        /** Builder for {@link DpProp}. */
        public static final class Builder
                implements LinearOrAngularDimension.Builder,
                        ContainerDimension.Builder,
                        ImageDimension.Builder {
            private final DimensionProto.DpProp.Builder mImpl = DimensionProto.DpProp.newBuilder();

            Builder() {}

            /** Sets the value, in dp. */
            @SuppressLint("MissingGetterMatchingBuilder")
            @NonNull
            public Builder setValue(@Dimension(unit = DP) float value) {
                mImpl.setValue(value);
                return this;
            }

            @Override
            @NonNull
            public DpProp build() {
                return new DpProp(mImpl.build());
            }
        }
    }

    /** A type for font sizes, measured in sp. */
    public static final class SpProp {
        private final DimensionProto.SpProp mImpl;

        SpProp(DimensionProto.SpProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /**
         * Get the protocol buffer representation of this object.
         *
         * @hide
         */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.SpProp toProto() {
            return mImpl;
        }

        /** Builder for {@link SpProp} */
        public static final class Builder {
            private final DimensionProto.SpProp.Builder mImpl = DimensionProto.SpProp.newBuilder();

            Builder() {}

            /** Sets the value, in sp. */
            @SuppressLint("MissingGetterMatchingBuilder")
            @NonNull
            public Builder setValue(@Dimension(unit = SP) int value) {
                mImpl.setValue(value);
                return this;
            }

            /** Builds an instance from accumulated values. */
            @NonNull
            public SpProp build() {
                return new SpProp(mImpl.build());
            }
        }
    }

    /** A type for angular dimensions, measured in degrees. */
    public static final class DegreesProp implements LinearOrAngularDimension {
        private final DimensionProto.DegreesProp mImpl;

        DegreesProp(DimensionProto.DegreesProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /** @hide */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.DegreesProp toProto() {
            return mImpl;
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.LinearOrAngularDimension toLinearOrAngularDimensionProto() {
            return DimensionProto.LinearOrAngularDimension.newBuilder()
                    .setAngularDimension(mImpl)
                    .build();
        }

        /** Builder for {@link DegreesProp}. */
        public static final class Builder implements LinearOrAngularDimension.Builder {
            private final DimensionProto.DegreesProp.Builder mImpl =
                    DimensionProto.DegreesProp.newBuilder();

            Builder() {}

            /** Sets the value, in degrees. */
            @SuppressLint("MissingGetterMatchingBuilder")
            @NonNull
            public Builder setValue(float value) {
                mImpl.setValue(value);
                return this;
            }

            @Override
            @NonNull
            public DegreesProp build() {
                return new DegreesProp(mImpl.build());
            }
        }
    }

    /**
     * A type for a dimension that fills all the space it can (i.e. MATCH_PARENT in Android
     * parlance).
     */
    public static final class ExpandedDimensionProp implements ContainerDimension, ImageDimension {
        private final DimensionProto.ExpandedDimensionProp mImpl;

        ExpandedDimensionProp(DimensionProto.ExpandedDimensionProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /** @hide */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.ExpandedDimensionProp toProto() {
            return mImpl;
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ContainerDimension toContainerDimensionProto() {
            return DimensionProto.ContainerDimension.newBuilder()
                    .setExpandedDimension(mImpl)
                    .build();
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ImageDimension toImageDimensionProto() {
            return DimensionProto.ImageDimension.newBuilder().setExpandedDimension(mImpl).build();
        }

        /** Builder for {@link ExpandedDimensionProp}. */
        public static final class Builder
                implements ContainerDimension.Builder, ImageDimension.Builder {
            private final DimensionProto.ExpandedDimensionProp.Builder mImpl =
                    DimensionProto.ExpandedDimensionProp.newBuilder();

            Builder() {}

            @Override
            @NonNull
            public ExpandedDimensionProp build() {
                return new ExpandedDimensionProp(mImpl.build());
            }
        }
    }

    /**
     * A type for a dimension that sizes itself to the size of its children (i.e. WRAP_CONTENT in
     * Android parlance).
     */
    public static final class WrappedDimensionProp implements ContainerDimension {
        private final DimensionProto.WrappedDimensionProp mImpl;

        WrappedDimensionProp(DimensionProto.WrappedDimensionProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /** @hide */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.WrappedDimensionProp toProto() {
            return mImpl;
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ContainerDimension toContainerDimensionProto() {
            return DimensionProto.ContainerDimension.newBuilder()
                    .setWrappedDimension(mImpl)
                    .build();
        }

        /** Builder for {@link WrappedDimensionProp}. */
        public static final class Builder implements ContainerDimension.Builder {
            private final DimensionProto.WrappedDimensionProp.Builder mImpl =
                    DimensionProto.WrappedDimensionProp.newBuilder();

            Builder() {}

            @Override
            @NonNull
            public WrappedDimensionProp build() {
                return new WrappedDimensionProp(mImpl.build());
            }
        }
    }

    /**
     * A type for a dimension that scales itself proportionally to another dimension such that the
     * aspect ratio defined by the given width and height values is preserved.
     *
     * <p>Note that the width and height are unitless; only their ratio is relevant. This allows for
     * specifying an element's size using common ratios (e.g. width=4, height=3), or to allow an
     * element to be resized proportionally based on the size of an underlying asset (e.g. an
     * 800x600 image being added to a smaller container and resized accordingly).
     */
    public static final class ProportionalDimensionProp implements ImageDimension {
        private final DimensionProto.ProportionalDimensionProp mImpl;

        ProportionalDimensionProp(DimensionProto.ProportionalDimensionProp impl) {
            this.mImpl = impl;
        }

        /** Returns a new {@link Builder}. */
        @NonNull
        public static Builder builder() {
            return new Builder();
        }

        /** @hide */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.ProportionalDimensionProp toProto() {
            return mImpl;
        }

        /** @hide */
        @Override
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        public DimensionProto.ImageDimension toImageDimensionProto() {
            return DimensionProto.ImageDimension.newBuilder()
                    .setProportionalDimension(mImpl)
                    .build();
        }

        /** Builder for {@link ProportionalDimensionProp}. */
        public static final class Builder implements ImageDimension.Builder {
            private final DimensionProto.ProportionalDimensionProp.Builder mImpl =
                    DimensionProto.ProportionalDimensionProp.newBuilder();

            Builder() {}

            /** Sets the width to be used when calculating the aspect ratio to preserve. */
            @SuppressLint("MissingGetterMatchingBuilder")
            @NonNull
            public Builder setAspectRatioWidth(@IntRange(from = 0) int aspectRatioWidth) {
                mImpl.setAspectRatioWidth(aspectRatioWidth);
                return this;
            }

            /** Sets the height to be used when calculating the aspect ratio ratio to preserve. */
            @SuppressLint("MissingGetterMatchingBuilder")
            @NonNull
            public Builder setAspectRatioHeight(@IntRange(from = 0) int aspectRatioHeight) {
                mImpl.setAspectRatioHeight(aspectRatioHeight);
                return this;
            }

            @Override
            @NonNull
            public ProportionalDimensionProp build() {
                return new ProportionalDimensionProp(mImpl.build());
            }
        }
    }

    /** Interface defining a dimension that can be linear or angular. */
    public interface LinearOrAngularDimension {
        /**
         * Get the protocol buffer representation of this object.
         *
         * @hide
         */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.LinearOrAngularDimension toLinearOrAngularDimensionProto();

        /** Builder to create {@link LinearOrAngularDimension} objects. */
        @SuppressLint("StaticFinalBuilder")
        interface Builder {

            /** Builds an instance with values accumulated in this Builder. */
            @NonNull
            LinearOrAngularDimension build();
        }
    }

    /** Interface defining a dimension that can be applied to a container. */
    public interface ContainerDimension {
        /**
         * Get the protocol buffer representation of this object.
         *
         * @hide
         */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.ContainerDimension toContainerDimensionProto();

        /** Builder to create {@link ContainerDimension} objects. */
        @SuppressLint("StaticFinalBuilder")
        interface Builder {

            /** Builds an instance with values accumulated in this Builder. */
            @NonNull
            ContainerDimension build();
        }
    }

    /** Interface defining a dimension that can be applied to an image. */
    public interface ImageDimension {
        /**
         * Get the protocol buffer representation of this object.
         *
         * @hide
         */
        @RestrictTo(Scope.LIBRARY)
        @NonNull
        DimensionProto.ImageDimension toImageDimensionProto();

        /** Builder to create {@link ImageDimension} objects. */
        @SuppressLint("StaticFinalBuilder")
        interface Builder {

            /** Builds an instance with values accumulated in this Builder. */
            @NonNull
            ImageDimension build();
        }
    }
}