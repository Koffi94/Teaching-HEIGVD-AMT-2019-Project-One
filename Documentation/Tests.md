# AMT Project One - Tests

## DAO Test

We use Arquillian to test our DAO. Currently the tests are limited to check if the CRUD implementation of each DAO is working as expected.

The Arquillian tests allowed us to discover and fixed a security breach in our code.
When testing the updateUser() method we realised that, contrary to createUser(), we didn't hashed the password before storing it. Therefore when updating a user password, the password was stored in plaintext in the database. Thi is no more the case thanks to the test on updateUser().

## Details on automated tests

## Performances tests

### JMeter

