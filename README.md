# Mobile-App-Project
Makeup based app as part of my Mobile App Development 1 module



## Introduction
As part of my Mobile App Development 1 module, it is required to create an app that has CRUD (Create, Retrieve, Update, Delete) functionality for recorded information. The app I chose to create is a makeup product related app, which will store images and descriptions about makeup products. 
The users will be able to upload data (create), edit the data (update), view the data they have saved already (retrieve) and delete any data they wish. I will be using the Realtime Database in Firebase to store the data and I will use Firebase to retrieve the data back to the app. 
When data is deleted it will also remove from Firebase and when a product is updated, a new product will not be added, the original product will be updated.
Motivation for creating my app
The motivation behind my choice of app, was based around a module I did when I was on Erasmus last semester. As part of my Human Computer Interaction module I created a user interface for a makeup app, and this sparked the idea that I would do a makeup related application for this module. 
I am creating an app where users can see makeup products and the stores where they can buy that product. The users can also upload products and add in descriptions about the products and where to buy them.
Another reason I wanted to do a makeup app was the fact I am interested in makeup outside of college and it is a hobby of mine. I have my own Instagram makeup page where I upload looks and video tutorials. 
I enjoy being creative with makeup and creating different looks and viewing other makeup artists Instagram pages and buying products off makeup websites.








## Architectural concerns
Originally, I was basing my app around a user interface I had designed last semester which looked like this:
 
I soon realized that it looked more like a website than a mobile application, so I needed to re consider my choice of layout.
I liked the layout that was used in the practical’s for the module, so I decided to stick with that layout and change some icons and activities.
For my app I have my home activity (which will be the first screen shown when the app is run). On my home activity I will have the logo for my makeup page, a video which contains other social media links and a get started button which will lead into the product list activity.
There is a burger menu on the top right-hand corner which is a drop-down menu and will give the user options to go back to the Home activity from the product list activity if they wish to do so.
The product list activity is where the products will be shown, and they are displayed by a recyclerview. The products all have a unique id like in an array zero is the first element which is shown. In order to get my product stores to show up on the map, I had to get the adapter position of the product because all the products were showing up, but only the first product was being shown and all the other ones were set on the equator on top of each other, as the first position is always shown. 
The product activity is where the products can be added, edited and saved to firebase, the product list activity will read in the data which is stored in firebase and retrieve it and send it back into a variable called productsList. The productsList is then sent to the recyclerview which is then presented to the screen when the user is finished creating or editing their products.
For each activity I have a recycler view which controls how the data displays to the screen.
Context of my app with respect to other similar apps
At the moment I am unaware of any apps similar to my app in the app store, but I would say I based my idea around how the app Instagram works. Instagram allows users to upload images and videos and provide captions, locations and share their images to different social media platforms. I wanted my app to have a similar layout in the fact users can upload images and descriptions. I see a lot of makeup artists posting on Instagram and I wanted to create an app where all makeup artists, lovers and enthusiasts can share and express their ideas when it comes to the makeup world.
Roadmap for future development
In the future I would love to develop my own online makeup store where I could sell makeup products and the users could use a mobile app to purchase the products. The users would also be able to leave reviews of the products and submit photos and videos of them using the products.
Link to repository on GitHub

[Mobile App Project](https://github.com/michaelahealy9/Mobile-App-Project)





## References
(https://www.youtube.com/watch?v=1Iha4zGLyU0)
https://codinginflow.com/tutorials/android/videoview
https://stackoverflow.com/questions/45267041/not-enough-information-to-infer-parameter-t-with-kotlin-and-android
https://firebase.google.com/docs/database/android/start
https://firebase.google.com/docs/database/android/read-and-write
https://stackoverflow.com/questions/36223373/firebase-updatechildren-vs-setvalue
https://medium.com/@ansujain/kotlin-how-to-create-static-members-for-class-543d0f126f7c
https://medium.com/@peterekeneeze/passing-data-between-activities-2d0ef122f19d
https://medium.com/@ansujain/kotlin-how-to-create-static-members-for-class-543d0f126f7c
https://stackoverflow.com/questions/42436012/how-to-put-the-arraylist-into-bundle
https://demonuts.com/android-change-tab-icon-text-color/
https://icons8.com/icons
https://icons8.com/icon/set/map/nolan
https://developer.android.com/studio/write/vector-asset-studio.html
https://dev.to/_s_farias/how-to-create-adaptive-icons-for-android-using-android-studio-459h
