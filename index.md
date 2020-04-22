# iTipper Android Application

This application is a phone app on the Android OS for targeted at anybody that works for tips. The primary goal of iTipper is for users to be able to record tips on the fly, keep track of how 
much they are earning and to be able to help users budget better. For drivers, there is a built in google maps for quickly searching for a location.

# Demo Video
[[iTipper Tutorial](https://user-images.githubusercontent.com/47394267/80042154-45f37780-84cc-11ea-80ba-50d4269c5e41.png)](https://www.youtube.com/watch?time_continue=1&v=0fP-TIaoAwA&feature=emb_logo)
# How to use
![](https://user-images.githubusercontent.com/47394267/79916723-30f8e480-83f7-11ea-8947-4de35ac12200.jpg) <br/>
![](https://user-images.githubusercontent.com/47394267/79916767-42da8780-83f7-11ea-8bfe-19c5a7f57065.jpg) <br/>
![](https://user-images.githubusercontent.com/47394267/79916806-54239400-83f7-11ea-919b-8b8aee810c40.jpg) <br/>
![](https://user-images.githubusercontent.com/47394267/79917177-fe032080-83f7-11ea-918b-576411e4d3ba.jpg) >br/>
![](https://user-images.githubusercontent.com/47394267/79916818-5b4aa200-83f7-11ea-8f4b-78e794594d05.jpg) <br/>


# Technologies
In order to build this project you will need to install:
* [Android Studio](https://developer.android.com/studio/) <br/>

# Setting Up
To install Android Studio on Windows, proceed as follows:

1. If you downloaded an `.exe` file (recommended), double-click to launch it. <br/>

   If you downloaded a `.zip` file, unpack the ZIP, copy the **android-studio** folder into your **Program Files** folder, and then open the **android-studio > bin** folder and launch `studio64.exe` (for 64-bit machines) or `studio.exe` (for 32-bit machines).

2. Follow the setup wizard in Android Studio and install any SDK packages that it recommends.
That's it.

3. In the **Welcome to Android Studio** window, click **Start a new Android Studio Project**. <br/>
   If you have a project already opened, select **File > New > New Project**.

4. In the **Choose your project** window, select **Empty Activity** and click **Next**.

5. In the **Configure your project** window, complete the following:
* Enter "My First App" in the **Name** field.
* Enter "com.example.myfirstapp" in the **Package name** field.
* If you'd like to place the project in a different folder, change its **Save** location.
* Select **Java** from the **Language** drop-down menu.
* Select the checkbox next to **Use** **androidx.*** **artifacts**.
* Leave the other options as they are.

6. Click **Finish**.

# Running 
To run the app on your computer you have one of two choices: Either on a real device or an emulator.

### Run on a real device
Set up your device as follows:

1. Connect your device to your development machine with a USB cable. If you developed on Windows, you might need to [install the appropriate USB driver](https://developer.android.com/studio/run/oem-usb) for your device. <br/>

2. Perform the following steps to enable **USB debugging** in the **Developer options** window: <br/>

   **a**. Open the **Settings** app. <br/>
   
   **b**. If your device uses Android v8.0 or higher, select **System**. Otherwise, proceed to the next step. <br/>
   
   **c**. Scroll to the bottom and select **About phone**. <br/>
   
   **d**. Scroll to the bottom and tap Build number seven times. <br/>
   
   **e**. Return to the previous screen, scroll to the bottom, and tap **Developer options**. <br/>
   
   **f**. In the **Developer options** window, scroll down to find and enable **USB debugging**.
   
Run the app on your device as follows:

1. In Android Studio, select your app from the run/debug configurations drop-down menu in the toolbar.

2. In the toolbar, select the device that you want to run your app on from the target device drop-down menu.

3. Click **Run**.

    Android Studio installs your app on your connected device and starts it. You now see "Hello, World!" displayed in the app on your device.

### Run on an emulator

Run the app on an emulator as follows:

1. In Android Studio, [create an Android Virtual Device (AVD)](https://developer.android.com/studio/run/managing-avds#createavd) that the emulator can use to install and run your app.

2. In the toolbar, select your app from the run/debug configurations drop-down menu.

3. From the target device drop-down menu, select the AVD that you want to run your app on.

4. Click **Run**. <br/>

    Android Studio installs the app on the AVD and starts the emulator. You now see "Hello, World!" displayed in the app.

# Testing

Unit tests are integrated with JUnit. Instrumented tests are run and supported by Espresso.
The tests can either be run from inside of Android Studio or from the command line:

### In the Android Studio UI

Open Android Studio. The project can be cloned by clicking File > New > Project from Version Control... > Git
Enter the project URL ( https://github.com/SCCapstone/iTipper ) in the URL box, then click clone. 

Once the repository is cloned, unit tests can be run from the project directory view. 
Locate app > java > com (test). Right click, then select "Run 'Tests in 'com''".

For instrumented tests, either connect an Android device or set up an emulator (help with setting up an emulator can be found [here](https://developer.android.com/studio/run/emulator)). Locate app > java > com (androidTest), right click, "Run 'Tests in 'com''".

### From a command line interface

Clone the repository with:
git clone https://github.com/SCCapstone/iTipper

Then navigate into the top level folder with:
cd iTipper

If done correctly, an "ls" should show this readme as one of the files in the current directory.

Run unit tests with:
./gradlew test

To run instrumented tests, connect an Android device via USB. (Help with setting up a device for testing over USB can be found [here](https://developer.android.com/studio/run/device) .)
Then enter:
./gradlew connectedAndroidTest

## About the developers
iTipper is the product of a four member team of seniors at the University of South Carolina. Visit our githubs below and view some of our work

 * [@TylerPChambers (Tyler Chambers)](https://github.com/TylerPChambers)
 * [@AzianEquation (John Esco)](https://github.com/AzianEquation)
 * [@christine1312 (Christine Burris)](https://github.com/christine1312)
 * [@42kevinzheng (Kevin Zheng)](https://github.com/42kevinzheng)
