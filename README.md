# (WIP) okmocker
Mock OkHttp responses at Interceptor level

# Usage

## Case 1 - Run Espresso tests with mock responses
### Step 1 - Add writer interceptor to save real responses
```kotlin
if (BuildConfig.DEBUG) {
    okHttpClientBuilder.addInterceptor(OkMockerWriteInterceptor())
}
```
Run the app. Responses from the server will be saved to the external storage. Don't forget to grant `WRITE_EXTERNAL_STORAGE` permission. (If your app doesn't need `WRITE_EXTERNAL_STORAGE` permission [you can add this permission to a specific build variant](https://medium.com/android-bits/implement-build-variant-specific-permissions-a330540871b0) that you run the tests)

### Step 2 - Pull responses from device storage to assets folder
Pull saved responses to assets under `androidTest` folder.
```bash
adb pull /sdcard/okmocker app/src/androidTest/assets
```

### Step 3 - Add reader interceptor to tests then run the test
```kotlin
okHttpClientBuilder
.addInterceptor(OkMockerReadInterceptor(AssetsReader(InstrumentationRegistry.getContext().assets)))
```

## Case 2 - Use mock responses to speed up your development
Step 1 is the same as above
 
### Step 2 
Pull responses to the build variant that will use mocking.
```bash
adb pull /sdcard/okmock app/src/[BUILD_VARIANT]/assets/
````
### Step 3
Use application context
```kotlin
okHttpClientBuilder
.addInterceptor(OkMockerReadInterceptor(AssetsReader(applicationContext.assets)))
```

## Case 3 - Use your custom reader/writers
```kotlin
class MyReader : OkMockerReader {

    override fun read(chain: Interceptor.Chain): ResponseBody {
        val path = chain.request().url().toString().toFileName()
        val content = when (path) {
            "https://PATH" -> "[{\"name\":\"path\"}]"
            else -> "[{\"name\":\"else\"}]"

        }
        return ResponseBody.create(MediaType.parse("application/json"), content)
    }

    override fun canRead(request: Request) = true // Always return true

}
```

Pass the reader to the interceptor

```kotlin
.addInterceptor(OkMockerReadInterceptor(MyReader()))
```

# License
[Apache License 2.0](https://github.com/faruktoptas/okmocker/blob/master/LICENSE)
