# Indri4Python

This project provides a way to access Indri API in python. To run the codes, follow the steps below:

I) Install Py4j. 

II) Make sure /src/tju/session/utils/IndriAPI.java can be run properly. 

    Normally, this could be achieved by 
    
    i) importing /lib/Indri.jar and /lib/py4jx.xx.x.jar into the java project, and  
    
    ii) adding /lib/indri_jni.dll to system path.
    
III) Run /src/tju/session/utils/IndriAPIEntryPoint.java. Set the variable indexDir with the directory path of your index. 

IV) Run test_py4j.py.  The variable "indri" stores all the information of the index. You can try using other functions in IndriAPI.java to 

visit this variable and build your own application on top of it.
    
