

***Libraried Used***
- java.util.*
- java.time.*
- java.sql.*


# HR and Inventory Management System

## Overview

This system manages HR and Inventory functions through a command-line interface. The application initializes by displaying the HR main menu, which interacts with the database to manage branches and employees. The `CLIInterface` handles various inventory operations.

## Main Menu

Upon running the application, the HR main menu will be displayed. If no branch is found, you will be prompted to create one.

### Example Input

When prompted to create a new branch, follow the example below:

- **System Prompt**: `Branch not found. Do you want to create a new branch? (yes/no)`
- **User Input**: `yes`
- **System Prompt**: `Enter the branch name:`
- **User Input**: `Main Branch`
- **System Prompt**: `Enter the branch address:`
- **User Input**: `123 Main St`

The system will then create the branch and add a default admin employee.

## Accessing the HR System

1. Start the Application:
    - Run the application to access the main menu.
2. Enter Your User ID:
    - When prompted, enter your user ID.
    - If you are an admin, enter `0`.
    - To exit the system, enter `-1`.

### For HR Managers (Default HR Manager User ID: 0)

#### Main HR Menu

Once logged in as an HR Manager, you will see the following options:

1. Employee Functions
2. Schedule and Shift Functions
3. Logout

#### Employee Functions

To manage employees, select `1` from the main HR menu. You will see the following options:

1. **Add Employee**
    - Follow the prompts to add a new employee.
    - Confirm the employee details to complete the addition.
2. **Remove Employee**
    - Select the employee to remove from the system.
3. **View Available Employees**
    - Display the list of all available employees.
4. **Update Employee Information**
    - Select an employee and update their position or other details.
5. **Return to Main HR Menu**
    - Go back to the main HR menu.

#### Schedule and Shift Functions

To manage schedules and shifts, select `2` from the main HR menu. You will see the following options:

1. **Create Shift**
    - Follow the prompts to create a new shift.
    - Confirm the shift details to complete the creation.
2. **Update Shift Requirements**
    - Modify the requirements for existing shifts.
3. **Print Weekly Schedule**
    - Display the weekly schedule for the branch.
4. **Return to Main HR Menu**
    - Go back to the main HR menu.

#### Logging Out

To log out and return to the main menu, select `3` from the main HR menu.

### For Non-Manager Employees

#### Non-Manager Menu

Once logged in as a non-manager employee, you will see the following options:

1. **Add Shift Limitation**
    - Add limitations to your shift availability.
2. **Remove Shift Limitation**
    - Remove existing limitations on your shift availability.
3. **View Shift Limitations**
    - Display your current shift limitations.
4. **Logout**
    - Log out and return to the main menu.

#### Adding/Removing/View Shift Limitations

- To add a shift limitation, select `1` and follow the prompts.
- To remove a shift limitation, select `2` and follow the prompts.
- To view your current shift limitations, select `3`.

#### Logging Out

To log out and return to the main menu, select `4` from the non-manager menu.

## Inventory Management

Only storage employees can access the inventory management system. To access the inventory management system, enter the storage employee ID.
Navigate through the inventory management options by selecting the appropriate menu item. Options include:

1. **Add/Remove Items**: Manage the addition or removal of inventory items.
2. **Update Inventory**: Update details of inventory items.
3. **Generate Reports**: Create various reports related to inventory status.
4. **Low Supply Report**: Generate a report of items with low supply.
5. **Generate CSV Reports**: Create CSV reports for low supply delta and reorder needs.

### Example Input

When interacting with the inventory management system, follow the example below for adding an item:

- **System Prompt**: `Enter your choice:`
- **User Input**: `1` (for Add/Remove Items)
- **System Prompt**: `Enter item ID:`
- **User Input**: `1001`
- **System Prompt**: `Enter item name:`
- **User Input**: `Milk`
- **System Prompt**: `Enter quantity:`
- **User Input**: `50`
- **System Prompt**: `Enter price:`
- **User Input**: `1.99`

The system will then add the item to the inventory.

## Exiting

To exit the application, select the exit option from the main menu. For example:

- **System Prompt**: `Enter your choice:`
- **User Input**: `7`

The system will display `Exiting...` and terminate the program.

