Play template
=============

This is a template app to use as a boilerplate for your play app. It has simple role based authentication and user CRUD.

Add or remove features that you need/don't need.

Libraries and tools
-------------------

* [Play Framework 1.2.3](http://www.playframework.org/)
* [Play's Secure module](http://www.playframework.org/documentation/1.2.3/secure)
* [Postmark module](https://github.com/FrostDigital/play-postmark)
* [Twitter Bootstrap](http://twitter.github.com/bootstrap/)


Getting started
---------------

    git clone git@github.com:FrostDigital/play-template.git
    cd play-template
    play deps --sync
    play run

Now you should be able to open **http://localhost:9000** in a browser.

This app is intended to be a template for a new app. So it probably makes sense to remove all git meta data and create a new, separate git repository for the app:

    # In dir above "play-template"
    mv play-template my-new-app
    
    # Remove old git metadata
    rm -rf my-new-app/.git
	
	# Init new git repo
    git init
    git add .
    git commit -m 'first commit'
    
    # Configure to use any remote repo, ie at GitHub...


Setup your IDE
--------------

Do you use Eclipse or IntelliJ? Try [this guide](http://www.playframework.org/documentation/1.2.3/ide).

License
-------

We use play-template in-house at [FrostDigital](http://frostdigital.se), but it's open source. Feel free to use it however you'd like. It's under the [Apache 2 License](http://en.wikipedia.org/wiki/Apache_2_License).

### Library licenses

* Play Framework - [Apache 2 License](http://en.wikipedia.org/wiki/Apache_2_License)
* Twitter Bootstrap - [Apache 2 License](http://en.wikipedia.org/wiki/Apache_2_License)
