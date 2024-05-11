import { test, expect } from '@playwright/test';

// test('valid request (status only)', async ({ request }) => {
//   const statusList: number[] = [8, 9];
//
//   const queryParams = `status=${statusList.join(',')}`;
//
//   const response = await request.get(`/transactions?${queryParams}`);
//
//   expect.soft(response.status()).toEqual(200);
//
//   const responseBody = await response.json();
//
//   const expectedIds: number[]= [1108, 5, 1109, 1111];
//
//   const responseIds: number[] = responseBody.transactions.map(transaction => transaction.id);
//   const responseStatuses: number[] = responseBody.transactions.map(transaction => transaction.status);
//
//   for (const id of responseIds) {
//     expect.soft(expectedIds).toContainEqual(id);
//   }
//
//   for (const status of responseStatuses) {
//     expect.soft(statusList).toContainEqual(status);
//   }
// });
//
// test('valid request (status only) with pagination page 1', async ({ request }) => {
//     const statusList: number[] = [5, 7];
//     const pageNumber: number = 1;
//     const limit: number = 5;
//     const count: number = 9;
//
//     const queryParams = `status=${statusList.join(',')}&page=${pageNumber}&limit=${limit}`;
//
//     const response = await request.get(`/transactions?${queryParams}`);
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//
//     const responseStatuses: number[] = responseBody.transactions.map(transaction => transaction.status);
//
//     for (const status of responseStatuses) {
//         expect.soft(statusList).toContainEqual(status);
//     }
//
//     expect.soft(responseBody.page).toBe(pageNumber);
//     expect.soft(responseBody.totalPages).toBe(2);
//     expect.soft(responseBody.count).toBe(count);
//     expect.soft(responseBody.transactions.length).toBe(limit);
// });
//
// test('valid request (status only) with pagination page 2', async ({ request }) => {
//     const statusList: number[] = [5, 7];
//     const pageNumber: number = 2;
//     const limit: number = 5;
//     const count: number = 9;
//
//     const queryParams = `status=${statusList.join(',')}&page=${pageNumber}&limit=${limit}`;
//
//     const response = await request.get(`/transactions?${queryParams}`);
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//
//     const responseStatuses: number[] = responseBody.transactions.map(transaction => transaction.status);
//
//     for (const status of responseStatuses) {
//         expect.soft(statusList).toContainEqual(status);
//     }
//
//     expect.soft(responseBody.page).toBe(pageNumber);
//     expect.soft(responseBody.totalPages).toBe(2);
//     expect.soft(responseBody.count).toBe(count);
//     expect.soft(responseBody.transactions.length).toBe(count - limit);
// });
//
// test('when status query param is invalid', async ({ request }) => {
//   const response = await request.get('/transactions?status=NOT_VALID');
//
//   expect.soft(response.status()).toBe(400);
//
//   const responseBody = await response.json();
//
//   expect.soft(responseBody.code).toBe(100);
//   expect.soft(responseBody.message).toBe('Unable to parse path or query parameter.');
// });
//
// test('when status query param is missing', async ({ request }) => {
//   const response = await request.get('/transactions');
//
//   expect.soft(response.status()).toBe(400);
//
//   const responseBody = await response.json();
//
//   expect.soft(responseBody.code).toBe(100);
//   expect.soft(responseBody.message).toBe("Required parameter 'status' is missing");
// });
//
// test('valid request with custom date range', async ({ request }) => {
//     const statusList: number[] = [8, 9];
//
//     const queryParams = `status=${statusList.join(',')}&startDate=2000-01-01&endDate=2024-01-02`;
//
//     const response = await request.get(`/transactions?${queryParams}`);
//
//     expect.soft(response.status()).toEqual(200);
//
//     const responseBody = await response.json();
//
//     const expectedIds: number[]= [1108, 1109, 1111];
//
//     const responseIds: number[] = responseBody.transactions.map(transaction => transaction.id);
//     const responseStatuses: number[] = responseBody.transactions.map(transaction => transaction.status);
//
//     for (const id of responseIds) {
//         expect.soft(expectedIds).toContainEqual(id);
//     }
//
//     expect.soft(responseStatuses).toContain(8);
// });
//
// test('when start date is invalid', async ({ request }) => {
//   const response = await request.get('/transactions?status=8&startDate=9999-12-31');
//
//   expect.soft(response.status()).toBe(400);
//
//   const responseBody = await response.json();
//
//   expect.soft(responseBody.code).toBe(900);
//   expect.soft(responseBody.message).toBe('Invalid Request Parameters');
//   expect.soft(responseBody.meta).toMatchObject(
//       {
//         "constraintViolations": [
//           {
//             "field": "startDate",
//             "message": "must be on or before today's date"
//           }
//         ]
//       }
//   );
// });
//
//
// test('when end date is invalid', async ({ request }) => {
//   const response = await request.get('/transactions?status=8&endDate=1995-01-01');
//
//   expect.soft(response.status()).toBe(400);
//
//   const responseBody = await response.json();
//
//   expect.soft(responseBody.code).toBe(900);
//   expect.soft(responseBody.message).toBe('Invalid Request Parameters');
//   expect.soft(responseBody.meta).toMatchObject(
//       {
//         "constraintViolations": [
//             {
//               "field": "endDate",
//              "message": "must be a date that is after or equal to the start date"
//             }
//         ]
//       }
//   );
// });
//
//
// test('when both start and end date are invalid', async ({ request }) => {
//   const response = await request.get('/transactions?status=8&startDate=9999-12-31&endDate=1995-01-01');
//
//   expect.soft(response.status()).toBe(400);
//
//   const responseBody = await response.json();
//
//   expect.soft(responseBody.code).toBe(900);
//   expect.soft(responseBody.message).toBe('Invalid Request Parameters');
//   expect.soft(responseBody.meta?.constraintViolations).toContainEqual(
//       {
//         "field": "endDate",
//         "message": "must be a date that is after or equal to the start date"
//       }
//   );
//   expect.soft(responseBody.meta?.constraintViolations).toContainEqual(
//       {
//         "field": "startDate",
//         "message": "must be on or before today's date"
//       }
//   );
// });
