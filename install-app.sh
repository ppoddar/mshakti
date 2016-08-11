# ----------------------------------------------
# Builds Android app and installs it on a device
# ----------------------------------------------

./gradlew assembleDebug
if [ $? -eq 0 ]; then
  ~/android-sdks/platform-tools/adb -d install -rg app/build/outputs/apk/app-debug-unaligned.apk
else
  echo ***ERROR: compile failed
fi
