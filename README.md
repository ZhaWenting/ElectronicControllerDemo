# Electronic Controller (Smart Home)

#### A demo that can control electronics from mobile device

## Features
### 1.Architecture 
Use MVC to develop the architecture. Xml files cares for UI, entity files for model, activities and fragments act as controller.
Create new class extends Appliaction to set basic configuration.
Create BaseActivity and BaseFragment to abstract some common methods.

### 2.App Functionality
Create three fragments named by three rooms.
Use RecyclerView to list all the fixtures and their current state.
Just read BedroomFragment, Beacause the three rooms are similiar.
Show temperature using https://www.metaweather.com/api/location/2165352/.
The AC will change the status with the temperature automatically.
Using handler to start a request every 5 seconds so that the data will stay fresh.


### 3.Data Persistence
Persist data when user click the button Exit on the top of the page.
It also works when user click the back key twice to exit the app.
The last known state will shown when app start again.

### 4.Performance 
Use retrofit to improve the interact with server.

### History
#### 20-10-2018
1. Use TimerTask and Timer instead of Handler and Runnable, that can avoid starting the network request frequently.
2. Import FlycoTabLayout to improve the structure of the activity.
3. Fix the bug: The Music player doesn't play music. It is caused by a wrong path.
4. Fix the crash bug which is caused by an unimplemented listener in the adapter.
5. Remove exit button and now we can doubleclick the back key to exit.

#### 21-10-2018
1. Stop requesting to server when app is move to the backend.
2. As rooms' info are obtained from server, room pages should generate dynamically ,So I abstarct three fragments(bedroom,kitchen,Living room) into one fragment(FixtureFrgment). Check the updates in MainActivity.
3. Persist all the local data using Sharedpreferences and gson, app show local data when restart.
4. Open Vedio when TV is on.
5. Add Menu and cooking timer, set the cooker on and check new features.


