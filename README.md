# README #

This README would normally document whatever steps are necessary to get your application up and running.

### How do I get set up? ###

After the launch you will see a recycler view with the fruits displayed in Cardviews. The push and refresh will let you load new data. 
Clicking on a cardview fruit will open a new view that will display the details of the fruits.

### Architecture guidelines ###

I've used the Model/View/Controller architecture and create layers. 
The purpose was to have a very basic activity and to move the logic to the controller.
I've used the repository pattern. The repository being the "unique source of truth" it is the entry point for getting the data, 
will it be remotely or locally.
