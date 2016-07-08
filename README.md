# crud1
Java Simple CRUD based on servlets and plain JDBC

Application database (PostgreSQL 9.4) represented hierarchical tree structure, like:<br>
   → Root category<br>
   → → Product<br>
   → → Category<br>
   → ... → Product<br>
   → ... → Category,<br>
- stored in two tables: 'product' and 'category', because poduct items has additional fields (price & quantity).

Every category could include subcategories or products or both of them.
You can navigate through this tree structure, add, delete or edit elements.

Application based on MVC model.
Used design patterns:
- Controller - for controller package,
- Singleton - for model, DAO packages,
- Decorator - for view package
- Template - for view package
- Action - for commands from controller to model
- DTO - for transfer data and messages from DAO to view through controller

Main implemented features in this project are:
- NAVIGATION through database with CRUD-functions
- DATABASE MANAGEMENT - refill database to initial values

Also implemented:
- General actions logger
