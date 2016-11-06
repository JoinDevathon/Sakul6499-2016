# Devathon Project
Author: @Sakul6499

I decided to make a small but kinda powerful plugin.  
It will spawn a little cart (also known as Turtle).  
You can easily control this cart by using a self coded Script (within Java).  
 
## Commands
| Name | Function                                                                                                   |
| ---- | ---------------------------------------------------------------------------------------------------------- |
| /spawn \<name\> | Will spawn a cart at your current position with the given name                                    |
| /remove \<name\> | Will delete the cart with the name                                                               |
| \#\<name\> \<script\> | Will assoc the script and the cart and execute the Script. The script know will be executed!   |

## Scripts
To make your own Script, simply use the Core.jar as your API.  
You know need to create any class (with any name) and add the three important @annotations:  

| Name                           | Where                     | Function                                                                                                      |
| ------------------------------ | ------------------------- | ------------------------------------------------------------------------------------------------------------- |
| @script(name, author, version) | Directly above the Class  | This is the most important one. It will set a name for the script, as well as the author and current version. |
| @startup                       | Directly above the Method | Will point to the startup method                                                                              |
| @shutdown                      | Directly above the Method | Will point to the shutdown method                                                                             |

> See more in the 'Test' folder (Example: Test/src/main/java/de/sakul6499/test/Test.java)

All Scripts will need to be in the 'SERVER_ROOT/scripts' folder.

## Example work through:
1. Create a own Script (like the Example).
2. Build it and copy it into the 'SERVER_ROOT/scripts' folder.
3. Start the Spigot Server with the Core.jar plugin.
4. Join and execute:
    1. /spawn \<name\>          [eg. /spawn Cake]
    2. \#\<name\> \<script\>    [eg. #Cake test]
5. Watch it!


