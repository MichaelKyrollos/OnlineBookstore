# OnlineBookstore

This application allows users to browse a collection of books available in a bookstore. The [main interface](#main-interface) has two sub-systems: [User](#user) (option 1 & 2) and [Manager](#manager) (option 3). 

The system uses SQL queries to access book information stored in a pgAdmin Database. The connection of the front-end to the back-end was acheived using JDBC. 

*Note: All user inputs are case-sensitive*


## How to run project: 
**Applications that are necessary to run the application:** [IntelliJ](https://www.jetbrains.com/idea/) and [pgAdmin](https://www.pgadmin.org/download/)
1. Using pgAdmin, create a new database with the name ``OnlineBookstore``
2. Ensure that the database password is ``admin``, this password is different from the pgAdmin master password. Click [here](https://help.alteryx.com/20212/alteryx-analytics-hub/change-postgres-admin-password) to learn more. 
3. Ensure the database username is ``postgres``, this is the default name
2. Run the program (From Bookstore.java)     
          - This project uses SDK version 19
          <br>- Project was created using IntelliJ IDEA.
          <br>- Ensure to compile and build project before running Project *Build->Build Project*
          <br>- For compile (this may not be necessary): *Build->Compile [filename].java*. You may need to do this for each file if running doesn't initially work
## User
A user models a customer for the online bookstore, each customer must have an account to use [the system](#user-menu) by either [creating an account](#creating-an-account) or logging in using an [existing acccount](#existing-account). The login requires a valid username and password, the system will ensure that both are valid when a user attempts to login. 

**Once logged in, a user can:**

1. ``Search`` for a book by [name](#search-by-name), [author](#search-by-author), [publisher](#search-by-publisher), [ISBN](#search-by-isbn) or [genre](#search-by-genre) using the ``Search Menu``
      - The querying allows user to search with partial words. For example: a book named "The Ride" can be searched using an input "The". 
      - The system will let the user know if the book is [not found](#search-for-book-not-in-db)
2. [Add](#adding-to-cart) a book to the cart in a given quantity (books in cart will be saved until the user logs out). 
      - In the ``Cart Menu``, a user can: 
         - [View cart](#view-cart) 
         - Place an order of the items in the cart (see number 3) 
         - [Increase quantity](#increase-quantity) of an item in the cart 
         - [Decrease quantity](#decrease-quantity) of an item in the cart 
         - [Remove an item](#remove-item) from the cart                                  
3. [Request an order](#request-order) for all the items in the cart to be bought 
      - The books in the cart will be used to create an order which is stored in the database
      - Cart will be emptied
      - The user can add more items to the now empty cart if he/she desires. This will create a seperate order in the database when processed
4. [View all orders](#view-all-orders) currently associated with the user who is logged in 
5. Logout - this will bring the session back the main interface
                              
## Manager
A user models the bookstore owner. In this menu, no username or password are required to login into the ``Manager`` [interface](#manager-interface). 

**Once logged in, a manager can:**
1. [Add author](#add-author) the file system 
      - The manager must input every attribute of the author, namely, the *email*, *name*. 
2. [Add book](#add-book) to the file system
3. [Remove book](#remove-book) from the system
4. [Add publisher](#add-publisher) to the system
5. [See sales per genre](#sales-per-genre-report)
6. [See sales per author](#sales-per-author-report)
7. [See sales per publisher](#sales-per-publisher-report)
8. [See sales vs expenditures](#sales-vs-expenditures)
9. Logout - this will bring the session back the main interface

--------------------------------------------------------------------------------------------------------
*Screenshots*
## Main Interface
![image](https://user-images.githubusercontent.com/83596468/206866408-0debc0c7-7d92-4014-8a50-badcfd0bdf89.png)
<br> [Go to top](#onlinebookstore)

## User Interface

### User menu 
![image](https://user-images.githubusercontent.com/83596468/206866570-1c35bd94-7b18-4aa8-9501-928c72f5945f.png)
<br> [Back to documentation](#user)

#### Creating an account

![image](https://user-images.githubusercontent.com/83596468/206866497-a71916cd-4fd2-4f2b-8fd2-debce4206e43.png)
<br> [Back to documentation](#user)

#### Existing account

![image](https://user-images.githubusercontent.com/83596468/206866521-b0e0f90a-373b-45d2-b40e-7baf941ab8cc.png)
<br> [Back to documentation](#user)

*Note: If the wrong username or password is inputted, the user will be prompted again*


#### Search by name 
![image](https://user-images.githubusercontent.com/83596468/206866759-e6217d45-dff9-4180-a408-7ca82273ffb2.png)
<br> [Back to documentation](#user)

#### Search by author 
![image](https://user-images.githubusercontent.com/83596468/206866864-8db40d6e-af9d-43f7-a2e1-f3e826c7c910.png)
<br> [Back to documentation](#user)

#### Search by publisher 
![image](https://user-images.githubusercontent.com/83596468/206866884-56ccb616-316d-417d-8ee9-26edd1bdd3c4.png)
[Back to documentation](#user)

#### Search by ISBN 
![image](https://user-images.githubusercontent.com/83596468/206866895-e341385f-79d2-4997-b45d-a4b460287423.png)
<br> [Back to documentation](#user)

#### Search by genre 
![image](https://user-images.githubusercontent.com/83596468/206867008-72286860-b5a1-479d-9a7d-50401b59574f.png)
<br> [Back to documentation](#user)

#### Search for book not in DB
![image](https://user-images.githubusercontent.com/83596468/206867040-9982ae31-634f-4bc6-84af-d42c0b54e888.png)
<br> [Back to documentation](#user)

#### Adding to cart 
![image](https://user-images.githubusercontent.com/83596468/206867599-b136fd53-3713-48cb-a87b-39d8f8c7f202.png)
<br> [Back to documentation](#user)

#### View cart
![image](https://user-images.githubusercontent.com/83596468/206867507-952710e3-5fe7-4f8b-91db-44089f535913.png)
<br> [Back to documentation](#user)

#### Increase quantity
![image](https://user-images.githubusercontent.com/83596468/206868175-be40abd7-ff24-426e-9d60-5106c32f408b.png)
<br> [Back to documentation](#user)

#### Decrease quantity
![image](https://user-images.githubusercontent.com/83596468/206867669-3f1242c9-1c6d-450c-916f-47f013c695fc.png)
<br> After decrease
<br> ![image](https://user-images.githubusercontent.com/83596468/206867758-4b33199e-534f-4cec-8d8a-056221affb5c.png)
<br> [Back to documentation](#user)

#### Remove item
![image](https://user-images.githubusercontent.com/83596468/206867969-98277d26-f248-4ed8-a4c1-cb41f47a14d5.png)
After removal
<br> ![image](https://user-images.githubusercontent.com/83596468/206867983-61b6e8ab-b44e-44e7-84d2-95a4e66c174e.png)
<br> [Back to documentation](#user)


#### Request order
![image](https://user-images.githubusercontent.com/83596468/206868241-3e5aba84-3010-4547-94e0-e7f404217650.png)
<br> [Back to documentation](#user)

#### View all orders
![image](https://user-images.githubusercontent.com/83596468/206868625-c0909e1f-be2b-4b74-97d2-a480a2182c49.png)
<br> [Back to documentation](#user)

## Manager Interface

#### Manager menu
![image](https://user-images.githubusercontent.com/83596468/206868845-13be1fbc-890f-4f79-b142-4c3a88dc1bd6.png)
<br> [Back to documentation](#manager)


#### Add author 
![image](https://user-images.githubusercontent.com/118630294/206876889-a80a9107-f277-4442-88d6-27cfcd290170.png)
<br> [Back to documentation](#manager)

#### Add book 
![image](https://user-images.githubusercontent.com/83596468/206869386-be6191de-8fed-49b3-893b-9d43a3b155cb.png)
<br> ![image](https://user-images.githubusercontent.com/83596468/206869391-8085dd94-0cee-404f-b63b-01e66260aeca.png)
<br> [Back to documentation](#manager)

#### Remove book 
![image](https://user-images.githubusercontent.com/83596468/206869563-81126efe-5cd9-40cf-b717-2dbc975ad91a.png)
<br> [Back to documentation](#manager)

#### Add publisher
![image](https://user-images.githubusercontent.com/83596468/206870221-5dc58b4b-cfff-4e1e-a56c-8d0ae55683d4.png)
<br> [Back to documentation](#manager)

#### Sales per genre report
![image](https://user-images.githubusercontent.com/83596468/206870275-e1e6b36b-be0a-471e-a24c-4c28107ff7e8.png)
<br> [Back to documentation](#manager)

#### Sales per author report
![image](https://user-images.githubusercontent.com/83596468/206870290-f4b2972c-1d13-464f-9ae5-c768e83d3546.png)
<br>[Back to documentation](#manager)

#### Sales per publisher report
![image](https://user-images.githubusercontent.com/83596468/206870306-dda04e3e-3cf2-4265-9417-53ed765ac3de.png)
<br> [Back to documentation](#manager)

#### Sales vs Expenditures
![image](https://user-images.githubusercontent.com/83596468/206870327-c7e8ad8a-f82e-48d3-82da-9ebdde3d787e.png)
<br> [Back to documentation](#manager)

