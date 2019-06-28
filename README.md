# Joker
## Project Overview
An android sample app that illustrates use of multiple modules and flavors. This application consists of four modules: (1) a Java library to provide jokes, (2) a backend Google Cloud Endpoints (GCE) project that serves the jokes, (3) an Android Library that displays the jokes in an activity, (4) and an Android app that fetches the jokes from the server to pass them to the display.

### Installation
Clone this repository and import into **Android Studio**.
`https://github.com/CEThompson/udacity-joker.git`

### Configuration
This project requires Google [Cloud SDK](https://cloud.google.com/sdk/) and is set up to test on a physical device.

##### 1. Set your backend IP
Edit your local.properties file to include the following lines:
`testIp = "$your_ip_address"`
Set testIp equal to the IP address of the system running the GCE endpoint.

##### 2. Set the Google Cloud SDK location
Edit the build.gradle for the backend module. In the appengine tools block set
`cloudSdkHome = "$your_cloud_sdk_location" `
Set cloudSdkHome to the location of your cloud sdk install.

##### Emulator testing
If you wish to test on the emulator set the test IP as follows:
`testIp="10.0.2.2"`

Then remove the [run block](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/backend/build.gradle#L42-L45
) from the appengine in the backend build.gradle.


## Notable Samples

### Java Library provides jokes
An example library named [jokeprovider](https://github.com/CEThompson/udacity-joker/tree/master/jokeprovider) has been created to supply the jokes. 

### Google Cloud Endpoints backend serves jokes from library
The GCE module named [backend](https://github.com/CEThompson/udacity-joker/tree/master/backend) uses the jokeprovider library to return a joke when the server receives a request. 

### App module requests jokes from backend with async task
The [app](https://github.com/CEThompson/udacity-joker/tree/master/app) module contains the main activity and definitions for [free](https://github.com/CEThompson/udacity-joker/tree/master/app/src/free) and [paid](https://github.com/CEThompson/udacity-joker/tree/master/app/src/paid) flavors. 

When the user clicks the "tell joke" button, behavior differs in free and paid flavors, but in either case an [async task](https://github.com/CEThompson/udacity-joker/blob/master/app/src/main/java/com/udacity/gradle/builditbigger/EndpointsAsyncTask.java) eventually fires to retrieve the joke from the GCE endpoint.

##### Free and paid flavors
In the free flavor the button first triggers an interstitial ad. Closing the interstitial ad then begins the asyc task. Additionally, a banner ad is displayed on the main activity fragment.

In the paid flavor the button immediately begins the async task and the banner ad is removed.

##### Free and paid testing
Three samples tests have been set up:
1. A test for the free flavor [checks if clicking the joke button starts an ad](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/app/src/androidTestFree/java/com/udacity/gradle/builditbigger/MainActivityFreeTest.java#L40-L50).

2. A test for the paid flavor [checks if clicking the joke button retrieves a joke](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/app/src/androidTestPaid/java/com/udacity/gradle/builditbigger/MainActivityPaidTest.java#L24-L37).

3. Lastly a test for either flavor [verifies the async task itself](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/app/src/androidTest/java/com/udacity/gradle/builditbigger/EndpointsAsyncTaskTest.java#L21-L36).


### Android Library displays jokes
Once the joke is retrieved from the server it is [passed](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/app/src/free/java/com/udacity/gradle/builditbigger/MainActivity.java#L149-L158) to the display activity defined in the [display module](https://github.com/CEThompson/udacity-joker/blob/2c658659ea648f4eda178ad1afb75e7e0544e089/app/src/free/java/com/udacity/gradle/builditbigger/MainActivity.java#L17).



