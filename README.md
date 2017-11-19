# Profile List

- Displays items from user profiles, including images
- MVP architecture
- At first load, GET items from remote datasource and store in cache and database. If cached items are available, use them. If app is restarted, load items from database.
- If desired, in memory and database cache can be disabled by uncommenting the line // mCacheIsDirty = true; in the ItemsRepository.getItems method. By doing that a new request to the server will be done every time the list loads, which can be useful for testing
- Universal Image Loader is set to cache images in memory and in SD Card
- The app works offline and the list is displayed
- Click on list item opens profile details in a simple layout (there's space for improvement). Different layouts for Portrait and Landscape.
- Dependencies: Used Retrofit 2 for the GET call, Universal Image Loader for images loading/caching. Mockito and JUnit for some simple unit tests, Guava for perconditions like checkNotNul