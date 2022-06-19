# Book&Drink - Android App
<img src="https://user-images.githubusercontent.com/63399959/170885645-fa3191ff-690e-42e8-b0ab-f939aacf7b86.png" align="left"
width="250" hspace="10" vspace="10">

**Book&Drink** is an mobile app for ordering drinks and books from Our beloved cafe "<b>Kawon once upon a time</b>" with a few clicks.
You need a Book&Drink account first, which you are going to use in this app.
you can signup from signup page that will apper directly opening the app and waiting a couple of seconds.

<p align="left">
  
## Details
  <p align="right">
  <img src="https://user-images.githubusercontent.com/63399959/174493973-3d1097f1-3fe2-4143-bdb7-8be48a92b51b.png" align="left" width="500" hspace="10" vspace="10">

In this project i used Firebase Firestore for database and storing data as documents,dont worry your data is safe
  this is an example for the document in Firestore:
and used Firestorage for saving images for items that will be displayed in the application.
    
<h1>Installation:</h1>


### Android Studio (Recommended)


* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of your project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

### Gradle (command line)

* Build the APK: `./gradlew build`


## Running the Sample App

Connect an Android device to your development machine.

### Android Studio

* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'

### Gradle

* Install the debug APK on your device `./gradlew installDebug`
* Start the APK: `<path to Android SDK>/platform-tools/adb -d shell am start io.keen.client.android.example/io.keen.client.android.example.MyActivity`


## Using the Sample App

Each time you press the "Send Event!" button the sample app queues an event to be sent to the Keen API with an increasing
counter. Note that the events will not actually be sent until the activity's `onPause` method is called, so you will need
to exit the app or otherwise cause `onPause` to be called to cause events to be sent. (Rotating your device to cause an
orientation change is one trick, but you can also just exit the app and re-open it.)

You can press the "Query!" button to issue a count query on the same collection with a timeframe of `this_24_hours`. The
result will be shown in a toast.

You should also be able to see the events show up in queries issued via the API or the web UI for your project.
