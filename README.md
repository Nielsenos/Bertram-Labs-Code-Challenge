Hello! Welcome to the Fair Pay System

This system will help users to decide who must pay the total for all employees drinks for that day!
There is a simple CLI client included, under the cli folder. This is a simple linux bash script that was developed with Cygwin.
Please place this file where you would like and run it through the command ./cli.
There is a 'help' command that can be used to display a more thourough usage guide of the system.

In order to run this system locally Tomcat 10.1 and Java21 will need to be downloaded. Tomcat should by default run on port 8080, please make sure that this is the case.
Once Tomcat is installed please download the pay-serivce.war file from the target package. In the file explorer navigate to where Tomcat was downloaded and then 
go to Tomcat 10.1/webapps and paste the war file there. Make sure that Tomcat is running and a new folder containing the contents of the war package should pop up with the 
same "pay-service" name. There are several images below to go along with this process.


**Image Guide:**

![p1](https://github.com/Nielsenos/bertramlabscodechallenge/assets/142841842/1c35df4e-bc70-433a-95e2-3a0aa1ee0ef9)
![p2](https://github.com/Nielsenos/bertramlabscodechallenge/assets/142841842/11723934-afc3-4b8b-a054-32da60b4184f)
![p3](https://github.com/Nielsenos/bertramlabscodechallenge/assets/142841842/daf94087-4d21-49d6-9570-209183610588)
![p4](https://github.com/Nielsenos/bertramlabscodechallenge/assets/142841842/4e100c7f-cb4c-4818-b924-40cf0358d812)
![p5](https://github.com/Nielsenos/bertramlabscodechallenge/assets/142841842/109c354f-2f5c-45ce-8ac7-6825c5b0a3f7)

Once the war file is in place and Tomcat is running, the system can be used! In order to use the system easily, run the client and begin tracking your fair payments! 

**Usage for Client**
The client supports several commands:

1) 'help': Displays usage information.
1) 'q': Exits the program.
1) 'changeConfig': Allows loading a different configuration of employees and products. Users can choose between 'e' (empty configuration), 'p' (default products with an empty employee list), and 'ep' (both employees and products with default values). For example, to set an empty configuration, you would enter 'e'.
1) 'getConfig': Retrieves the current configuration of employees and products.
1) 'whoPays': Returns a JSON with the employee responsible for paying for all drinks.
1) 'changeFavoriteDrink': Changes an employee's favorite drink and returns a JSON with the old and new favorite drink information and associated employee details. Users must provide an employee ID and a product ID.
1) 'addProduct': Adds a new product to the list and returns a JSON with the product's details and the updated product list. Users need to enter a product ID, name, and drink price.
1) 'removeProduct': Removes a specified product and returns a JSON with details of the removed product and the updated product list. The product to be removed should not be a favorite of any employee.
1) 'addEmployee': Adds a new employee to the list and returns a JSON with the employee's details and the updated employee list. Users must enter an employee ID, name, and favorite drink ID.
1) 'removePlayer': Removes a specified employee and returns a JSON with details of the removed employee and the updated employee list. Users must enter an employee ID.
1) 'getProductInformation': Provides a JSON with all products and their details.
1) 'getEmployeeInformation': Offers a JSON with all employees and their details.

**Configuration Files**

There are several JSON files in the resources package, these files are different default configurations of employees and products that can be loaded.
These configurations include an empty product and employee list configuration, allowing users to custom enter all employees and products. Another configuration
includes default examples for both employees and products, and a final configuration that contains a default product list and empty employee list. 
When the system is run for the first time it will by default be loaded with the configuration that contains example employees and products. Any changes made to the current
configuration being ran will be saved automatically and that configuration will be loaded the next time the service is ran through Tomcat. For example, if the system was started for 
the first time and loaded with the default employee and products, the user could then add/remove products or employees to that current active configuration and it would be saved for
the next times use! However, if a new configuraiton is loaded with changeConfig, then this will REPLACE the active configuration. An example would be loading up the program for the first 
time and the changing the confiuguration to an empty configuration containing no employees or products, this empty configuration will then be the active configuration. Always be sure that 
you are okay with losing information when changing the configuration.  

The active configuration will automaticlaly be written to a file named 'activeconfiguration.json' which will be saved by default to 'C:\FairPaySystemConfigs'. There is a file containing the file path to write to that can be found in the resourcese package in the file 'paysystemproperties.json'. The first time run of the system will create this folder and whenever a command is enacted, the updated version of the active configuration will get written to the activeconfiguration.json file (or altered file name).

**Alternative Way to Use Commands**

If you would like to avoid using the client, then the commands can be achieved similarly through URL queries. 
- Example Queries:
  - http://localhost:8080/pay-service/getConfig
  - http://localhost:8080/pay-service/changeConfig?fileType=ep
  - http://localhost:8080/pay-service/whoPays
  - http://localhost:8080/pay-service/addProduct?productId=45&name=Oreo%20Shake&drinkPrice=8.75
  - http://localhost:8080/pay-service/removeProduct?productId=4
  - http://localhost:8080/pay-service/addEmployee?employeeId=55&name=Beatrice%20Nielsen&favoriteDrinkId=3


**Assumptions Made**

Several assumptions were made about how the system was likely to be used:

- Users would likely want to add or remove different products
- Users would likely want to be able to add or remove employees (Maybe an employee finds a new job or decides they don't want to get drinks after lunch anymore)
- The employee who is selected to pay is based off the total amount that every employee has paid, whoever has paid the least will pay for the drinks that day. If there is a tie in who has  the least then an employee will randomly be selected from those that have  the least.
- Assumed that users may have disagreement over who is meant to pay, so the ability to get all the employee information was added which includes the payment totals of the different employees.
- Assumed that users could want to change what their favorite drink is


Thank you for your time and I hope that this system meets your needs!
