# AMT Project One - Known bugs and limitations

**Authors: Nathanaël Mizutani [NatMiz](https://github.com/NatMiz), Olivier Koffi [Koffi94](https://github.com/Koffi94)**

## Known bugs

- Each user can add, edit or delete a film or a cinema. Because of the time, we suppose that users are honest people and don't edit or delete entries for bad reasons.

- Sometimes users get redirected to the login page after a successful login. Clearing the browser cache solves this issue.

- You can format the URL the get a non existing page of the screenings table.

## Limitations

- Amount of simultaneous user sessions are limited by the RAM available for the Payara docker.

- If you try to connect to the MySQL container before all the data are loaded, the connection will fail. Same goes for the tests. You have to wait until the loading is completed.