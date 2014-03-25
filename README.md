RESTfulRobot
============

Introduction
--------------
This project is designed as a lab for implementing the Robot-problem as a RESTful webservice using Spring HATEOAS. 

Problem description
--------------
The Robot-problem describes a simple robot contained inside a room. The robot has three basic operations, it can turn either left or right and it can move forward. Obviously it cannot move through walls, thus the dimensions of the room restrict which operations are legal.

In this lab, we are also interested in problems such as creating the rooms and robots, as well as listing them and their attributes.

We wish to start from a root resource, from where links will take us to a list of all created robots or rooms. 
- /
- /robots
- /rooms

These list resources should contain two parts, a list of all robots/rooms and which operations are availble. The operations could be adding items or removing them. The list of robots/rooms may consists only of links to the resource, or they may have the attributes of the resource embedded in them.
- /robots/{id}
- /rooms/{id}
- /robots/add
- /rooms/add

The robot resource should include the attributes of the robot as well as which operations are available.
- /robots/{id}/turnleft
- /robots/{id}/turnright
- /robots/{id}/move

Instructions
--------------
As this lab is about REST, the boot and domain code is already completed. As an example of REST using Spring, the root resource is also implemented.

A number of tests have been created using RestAssured that may guide the developer. Implement the necessary REST controller and resource code and most of the functionality listed in the previous section should be completed. The suggested order is:
- RoomResourceTest
- RobotResourceTest
- RobotMovementResourceTest 

If you believe the lab is too short, delete those three test classes and create your own tests using the RootResourceTest class as an example.

Example code
--------------
As an example, there is a completed solution available on the example branch.
