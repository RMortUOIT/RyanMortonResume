Reaction Timer

This project was built as a subproject for my final year project at UOIT. The reaction timer is a 
physical device designed to test the user's reaction time. The test is simple: push the buttons
as fast as you can once they light up. The purpose of this device was to be apart of a larger 
system which would prevent people from driving impaired by giving them a speech test (questions
about basic arithmetic and personal facts), the reaction test and a breathalyzer, if the user
failed any of the tests they were deemed impaired and would not be allowed to start their vehicle.

Through a small field study conducted on actual test subjects while they were drinking, I was able
to prove that both the 1 button and 2 button prototypes did not do a sufficient job separating
impaired reaction times from one's sober baselines. This is why there is a clear evolution in the 
design of the device from 1 button to 4 buttons. The 4 button timer also has a simple HTTP server
attached to it since it was the prototype that was used in the final project prototype and needed
to send and receive messages from other devices.

The images posted are of the physical built prototypes.

The code examples provided are the source codes for each of the three devices. The Arduino Unos
used to control the device run a language that is a derivative of C++.