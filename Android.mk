# Copyright (C) 2014 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_DEX_PREOPT:=false
LOCAL_PACKAGE_NAME := FactoryUtility
LOCAL_CERTIFICATE := platform
LOCAL_MODULE_TAGS := optional
LOCAL_PROGUARD_FLAG_FILES := proguard.cfg

#LOCAL_PROPRIETARY_MODULE := true
LOCAL_PRIVILEGED_MODULE := true
LOCAL_JAVA_LIBRARIES := droidlogic droidlogic-tv

LOCAL_STATIC_ANDROID_LIBRARIES := \
    android-support-v7-recyclerview \
    android-support-v7-preference \
    android-support-v7-appcompat \
    android-support-v14-preference \
    android-support-v17-preference-leanback \
    android-support-v17-leanback

LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/res

LOCAL_USE_AAPT2 := true

LOCAL_SRC_FILES := \
    $(call all-java-files-under, src) \
    $(call all-Iaidl-files-under, src)

include frameworks/base/packages/SettingsLib/common.mk

#ifndef PRODUCT_SHIPPING_API_LEVEL
LOCAL_PRIVATE_PLATFORM_APIS := true
#endif
include $(BUILD_PACKAGE)
