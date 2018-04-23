# Indri4Python

This project provides a way to access Indri API in python. To run the codes, follow the steps below:

I) Install Py4j. \\
II) Make sure /src/tju/session/utils/IndriAPI.java can be run properly. \\
    Normally, this could be achieved by \\
    i) importing Indri.jar and py4jx.xx.x.jar in /lib, and  \\
    ii) adding /lib/indri_jni.dll to system path.\\
III) Run /src/tju/session/utils/IndriAPIEntryPoint.java. Set the variable indexDir with the directory path of your index. \\
IV) Run test_py4j.py.  The variable "indri" stores all the information of the index. Use the functions in IndriAPI.java to access it.\\
    
