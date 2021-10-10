# Parliament-Project
This project implements the Android MVVM and Single-Activity architecture, uses a SQLite Database (Room) for data storage, Retrofit and Moshi for fetching and parsing JSON data into data class objects. Work Manager is used for regular updates for the SQLite Database. 

This project is an Android application, which fetches Member of Parliament data from https://avoindata.eduskunta.fi/ and stores the data into a SQLite database. The user is able to search the database by giving an input to a SearchView. In addition, the found MP's can be filtered by party and age. Specific data of the MP can be viewed by clicking the MP's name. The user can also create reviews for the chosen MP.

# MainFragment
The MainFragment displays an overview of the usage of the application. Basic information of the last viewed Member of Parliament is displayed, as well the last time the SQLite database was updated, and the current settings.

