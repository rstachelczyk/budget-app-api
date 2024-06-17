import { test, expect } from "@playwright/test";

//Cases to test
//1. No JWT on a non-whitelisted endpoint
//2. Valid JWT on non-whitelisted endpoint
//3. No user found when logging in (bad username and password)
//4. User is found, but password is incorrect
//5. User is found but account was locked inside the lockout window
//6. User is found, account is locked, but timeout window has expired
//    6.1. With a valid login on the next attempt, it should succeed
//    6.2. With an invalid login on the next attempt, it should re-lock the account for another 30 min

test.describe('no jwt', async () => {
  test.use({ extraHTTPHeaders: {} });

  test('transactions', async ({ request }) => {
    const response = await request.get('/transactions');

    expect.soft(response.status()).toEqual(403);

//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "Unable to authorize JWT" }]
//         }
//       }
//     });
  });
});

test.describe('invalid jwt', async () => {
  test.use({ extraHTTPHeaders: { 'Authorization': 'Bearer this.tokenis.invalid' } });

  test('transactions', async ({ request }) => {
    const response = await request.get('/transactions');

    expect.soft(response.status()).toEqual(403);

//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "JWT unauthorized" }]
//         }
//       }
//     });
  });
});
