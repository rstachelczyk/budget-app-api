import { test, expect } from "@playwright/test";

// test.describe('no jwt', async () => {
//   test.use({ extraHTTPHeaders: {} });
//
//   test('transactions', async ({ request }) => {
//     const response = await request.get('/transactions?status=401');
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "Unable to authorize JWT" }]
//         }
//       }
//     });
//   });
//
//   test('transactions by id', async ({ request }) => {
//     const response = await request.get('/transactions/401');
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "Unable to authorize JWT" }]
//         }
//       }
//     });
//   });
// });
//
// test.describe('invalid jwt', async () => {
//   test.use({ extraHTTPHeaders: { 'Authorization': 'Bearer this.tokenis.invalid' } });
//
//   test('transactions', async ({ request }) => {
//     const response = await request.get('/transactions?status=403');
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "JWT unauthorized" }]
//         }
//       }
//     });
//   });
//
//   test('transactions by id', async ({ request }) => {
//     const response = await request.get('/transactions/403');
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//     expect.soft(responseBody).toEqual({
//       result: {
//         status: "Failure",
//         errorResponse: {
//           errors: [{ code: "900", description: "JWT unauthorized" }]
//         }
//       }
//     });
//   });
// });
