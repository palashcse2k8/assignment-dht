# assignment-dht
Distributed Hash Table

<--How to run this program-->

Import project using Eclipse IDE. 
File->Import->General->Existing project into workspace then select the project from the local location.

Steps:
1. Run DriverClass.java using Run As -> Java Application
This will create a server and wait for new clients
2. Input number of input you want run
Example 2,4,8...
3. Observe Till Finished! is shown in the console
4. Refresh project after execution completion and check output.txt for output
Output will be written in output.txt file

Sample output is given for 8 clients, parameter as 1024 block and 4194304 per block

Console.txt contains the console output for the sample execution, check that for your convenience.

[N.B]

To change T : Change in Line 19 of Server.java file 
T is fixed as, public static String T = "1011312001"; //input desire T

To change block number : Change in Line 15 of Server.java file 
public static int blockNumbers [] = new int [1024]; //input desire block number

To change per block range : Change in Line 84 of Client.java file 
int range = 4194304; //input desire block number


