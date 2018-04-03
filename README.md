# README #


After the launch you will see a recycler view with the fruits displayed in Cardviews. The push and refresh will let you load new data. 
After clicking on a cardview fruit a fragment view will display the details of the fruits.

### Architecture guidelines ###

I've used the Model/View/Presenter architecture and create layers.
The purpose was to have a very basic activity and to move the logic outside the view.

I've used the repository pattern. The repository being the "unique source of truth" it is the entry point for getting the data,
will it be remotely or locally.

The presenter is the one who communicate with the repo and ask for the data and then send them back to the view.
