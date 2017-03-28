# Base code 2017

## NOTE: ALL OF THIS CODE IS COMPLETELY UNTESTED
  
### Used by team 4504's programmers a base for every year's code.

Currently a WIP, but when finished the Drive Train should be mostly premade for most years (unless it uses an odd system). All you would have to do is create a DriveTrain class and extend whichever drive train you're using and set a default command, inputting the talons. As well, the SmartDashboard will be populated and premade objects with useful functions are made for things you use every year.

To use RPMs & drive using a closed-feedback loop using encoders, all one would need to do is calculate the PIDF values for each motor as described in the CANTalon software reference manual, set the max RPM (aka the slowest motor's highest RPM), run usingEncoders(true), and input -1.0 to 1.0 values as normal. All necessary calculations & unit conversions are done in the background.
