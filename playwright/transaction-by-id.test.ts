import { test, expect } from '@playwright/test';

test('Get Transaction By Id', async ({ request }) => {
  const TRANSACTION_ID: number = 7;
  const response = await request.get(`/api/v1/transactions/${TRANSACTION_ID}`);

  expect.soft(response.status()).toEqual(200);

  const responseBody = await response.json();
  expect.soft(responseBody.id).toBe(TRANSACTION_ID);
  expect.soft(responseBody.budgetId).toBe(9);
  expect.soft(responseBody.budgetName).toBe('Subscriptions');
  expect.soft(responseBody.description).toBe('Netflix');
  expect.soft(responseBody.amount).toBe(1500);
  expect.soft(responseBody.type).toBe('charge');
  expect.soft(responseBody.status).toBe('settled');
  expect.soft(responseBody.isRecurring).toBe(true);
  expect.soft(responseBody.createdAt).toBe('2024-05-07T10:42:10.356206Z');
  expect.soft(responseBody.updatedAt).toBe('2024-05-07T10:42:10.356206Z');
});

test('Get Nonexistent Transaction', async ({ request }) => {
  const INVALID_ID: number = -1;
  const response = await request.get(`/api/v1/transactions/${INVALID_ID}`);

  expect.soft(response.status()).toEqual(404);

  const responseBody = await response.json();

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "20",
          "message": `Transaction not found with Id: ${INVALID_ID}`
      }
  );
});
