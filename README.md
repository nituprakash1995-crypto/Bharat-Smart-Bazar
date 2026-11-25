# Bharat Smart Bazar - Android demo

This is a starter Android Studio project using Kotlin and Jetpack Compose.

## Build APK online with GitHub Actions
1. Upload the **contents** of this folder into the root of your GitHub repository (not the enclosing folder itself).
2. Keep `.github/workflows/build-apk.yml` in that exact path. Enable viewing hidden files when uploading, or create the workflow file separately in GitHub.
3. Open the Actions tab and run **Build Bharat Smart Bazar APK**.
4. Download the `Bharat-Smart-Bazar-APK` artifact from a successful run. The APK is `app-debug.apk` inside the downloaded ZIP.

## Demo limitations
- Includes sample grocery items only, not a live catalogue of 1000+ products.
- Cart works locally in the app, but checkout does not submit an order.
- No real UPI/payment gateway, backend, admin panel, inventory, or delivery tracking is connected.
- Add your verified shop logo and real product catalogue before release.
