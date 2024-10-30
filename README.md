to make program broombroom run
-andrei

1. in VSCode, Ctrl+Shift+P, Java: Configure Classpath, add lib folder to Source Root
    this ensures that you will be using the JConnector properly upon 'javac'

2. run initializationQuery.sql to init the database

3. change your credentials (user and pass) in lines 9 10 11 of DatabaseConnection.java