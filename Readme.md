# MyNews

**Version 1.0.0**

This app is created to get the latest news from the truthful sources. This is an assignment for TBC Android Bootcamp.

Application consists of two activities: 1 - Login/Signup, 2 - MainActivity
In login activity system authorises the user, in case not authorised, it helps you to log in or to create a new account. The initial authorisatiion process goes behind the splash screen.

After successful first step, the application directs us to the main activity where we can find the bottom navigation views, which lets us swipe through the main three fragments:
"News", "Bookmarks", "Categories"

The "News" fragment shows us the previews of the top news from various source providers like NYT, CNN...
On each article you can find a bookmark icon, which saves the chosen articles to the user's firebase database under the "bookmarks" list.
By clicking the article itsef, user will be redirected to the detailed fragment, where you can read the whole content of the article.

The "Bookmarks" fragment retrieves data from user's firebase database and shows the previews of the bookmarked news.
By clicking the bookmark symbol again, the article will be removed from the database and UI too.
By clicking the article itsef, user will be redirected to the detailed fragment, where you can read the whole content of the article.

The "Categories" fragment lets us chose the category to query the news.
The api that is used in this application accepts only the given categories: business, entertainment, general, health, science, sports, technology
By clicking the category, user gets redirected to the new fragment, where he can scroll through the articles by chosen category.
On each article you can find a bookmark icon, which saves the chosen articles to the user's firebase database under the "bookmarks" list.
By clicking the article itsef, user will be redirected to the detailed fragment, where you can read the whole content of the article.

The following libraries were used in this app: ViewModel, LiveData, Navigation, Retrofit, Coroutines, Glide

Each recycler adapter is connected to its fragment through individual ViewModel and LiveData.
Fragments are organized with the help of navigation.
Data from the news api is retrieved in ViewModel in Coroutines with Retrofit @GET request.
Images are downloaded and put in image views through Glide.

## Licence & copyright
(c) Elene Bokuchava, TBC Android Bootcamp
