# AMT Project One - Tests

To run the tests, run the `runTests.sh` script.

## Unit Testing

### DAO Tests

We use Arquillian to test our DAO. Currently the tests are limited to check if the CRUD implementation of each DAO is working as expected.

The Arquillian tests allowed us to discover and fixed a security flaw in our code.
When testing the `updateUser()` method we realised that, contrary to `createUser()`, we didn't hashed the password before storing it. Therefore when updating a user password, the password was stored in plaintext in the database. This is no more the case thanks to the test on `updateUser()`.

### Servlet Tests

The servlet were tested with Mockito.

## Performance and Load Testing

The Payara container was tested with JMeter.


| Number of Users | Number of Entities | CPU load | Memory Usage | Net I/O |
| --- | --- | --- | --- | --- |
| 1 | 10'000 | 203.91% | 1.391GB | 34MB/39.5MB |
| 100 | 1'000 | 2.76% | 638.5MB | 2.89MB/3.13MB |
