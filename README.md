# Parliament-Project
This project implements the Android MVVM and Single-Activity architecture, uses a SQLite Database (Room) for data storage, Retrofit and Moshi for fetching and parsing JSON data into data class objects. Work Manager is used for regular updates for the SQLite Database. 

This project is an Android application, which fetches Member of Parliament data from https://avoindata.eduskunta.fi/ and stores the data into a SQLite database. The user is able to search the database by giving an input to a SearchView. In addition, the found MP's can be filtered by party and age. Specific data of the MP can be viewed by clicking the MP's name. The user can also create reviews for the chosen MP.

# Fragments
The MainActivity contains a FragmentContainerView which is navigated according to the navigation component. The Fragments

```
Navigation to another Fragment.


val action = MemberFragmentDirections.actionMemberFragmentToMemberReviewFragment(memberViewModel.member)
findNavController().navigate(action)
```

<p>
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/main_demo.jpg" height="550" width="265">  
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/member_list_demo.jpg" height="550" width="265">  
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/member_demo.jpg" height="550" width="265">  
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/settings_demo.jpg" height="550" width="265">  
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/review_recycler_demo.jpg" height="550" width="265">  
  <img src="https://github.com/AOskari/Parliament-Project/blob/master/images/review_demo.jpg" height="550" width="265">  
</p>


# ViewModel
ViewModels are used for storing data displayed in the Views. As ViewModels are lifecycle-aware, data displayed in the Views survive configuration changes.

```
Instantiation of a ViewModel in a Fragment class.

memberListViewModelFactory = MemberListViewModelFactory((activity?.application as MPApplication).memberRepository,
            (activity?.application as MPApplication).settingsRepository)
memberListViewModel = ViewModelProvider(this, memberListViewModelFactory).get(MemberListViewModel::class.java)
```

# SQLite Database
The database is implemented using Room. DAOs (Data Access Object) are used for accessing and modifying data in the database tables.

```
Creation of the database.

@Volatile
private var INSTANCE: MemberDatabase? = null

// Returns an instance of the MemberDatabase if it is not null.
// Otherwise, create a new instance and return it.
fun getDatabase(context: Context, scope: CoroutineScope): MemberDatabase {
    return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            MemberDatabase::class.java,
            "member_database"
        )
            .addCallback(MemberDatabaseCallback(scope))
            .build()
        INSTANCE = instance
        instance
    }
}
```
asd
     
