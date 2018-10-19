
Smart Home(ElectronicController)
===================================

A demo that can control electronics from mobile device

**1.Architecture**
Use MVC to develop the architecture. Xml files cares for UI, bean files for model, activities and fragments act as controller.
Create new class extends Appliaction to set basic configuration.
Create BaseActivity and BaseFragment to abstract some common methods.

**2.Functionality**
Create three fragments named by three rooms.
Use RecyclerView to list all the fixtures and their current state.
Just read BedroomFragment, Beacause the three rooms are similiar.
Show temperature using https://www.metaweather.com/api/location/2165352/.
The AC will change the status with the temperature automatically.
Using handler to start a request every 5 seconds so that the data will stay fresh.

**3.Persistence**
Persist data when user click the button Exit on the top of the page.
It also works when user click the back key twice to exit the app.
The last known state will shown when app start again.

**4.Performance**
Use retrofit to improve the interact with server.

**History**
10/19/2018
Add a music player in the kitchen
