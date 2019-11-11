# AMT Project One - Tests
**Authors: Nathanaël Mizutani [NatMiz](https://github.com/NatMiz), Olivier Koffi [Koffi94](https://github.com/Koffi94)**

---

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

### Connection Pool issue

During the load testing we came accross the following issue: when more than 10 users were trying, simultaneously, the application crashed. It was caused by the limited size of the server's connection pool and the short wait time.

To fix this issue we increased the size of the connection pool to 100, and the value of the maximum wait time to 600'000. You can see belown an image of the pool settings with the updated values:

![Payara connection configuration panel](./img/payara-threadPool-waitTime-config.jpg "payara-threadPool-waitTime-config.jpg")
