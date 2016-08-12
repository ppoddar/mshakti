#!/bin/bash

# ---------------------------------
# Aligns a apk

~/android-sdks/build-tools/24.0.0/zipalign -f -v 4 app/build/outputs/apk/app-release-unaligned.apk app-relase-aligned.apk
