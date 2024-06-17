import { test, expect } from '@playwright/test';

test('Create Charge Transaction', async ({ request }) => {
  const AMOUNT: number = 1300;
  const DESCRIPTION: string = 'test delete';
  const TYPE: string = 'charge';
  const STATUS: string = 'settled';
  const BUDGET_ID: number = 2;

  const response = await request.post('/api/v1/transactions', {
    data: {
      amount: AMOUNT,
      description: DESCRIPTION,
      type: TYPE,
      status: STATUS,
      budgetId: BUDGET_ID
    }
  });

  expect.soft(response.status()).toEqual(201);

  const responseBody = await response.json();

  expect.soft(responseBody.budgetId).toBe(BUDGET_ID);
  expect.soft(responseBody.budgetName).toBe('Gas');
  expect.soft(responseBody.description).toBe(DESCRIPTION);
  expect.soft(responseBody.amount).toBe(AMOUNT);
  expect.soft(responseBody.type).toBe(TYPE);
  expect.soft(responseBody.status).toBe(STATUS);
  expect.soft(responseBody.isRecurring).toBe(false);
});

test('Create Transaction - All Validation Errors', async ({ request }) => {
  const response = await request.post('/api/v1/transactions', {
    data: {
      amount: -1300,
      description: ""
    }
  });

  expect.soft(response.status()).toEqual(400);

  const responseBody = await response.json();

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "10",
          "message": "Amount must be greater than or equal to 0."
      }
  );


  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "10",
          "message": "Description is required."
      }
  );

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "10",
          "message": "Budget Id is required."
      }
  );

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "10",
          "message": "Status is required."
      }
  );

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "10",
          "message": "Type is required."
      }
  );
});

test('Budget Does Not Exist', async ({ request }) => {
  const AMOUNT: number = 1300;
  const DESCRIPTION: string = 'Foreign Key Failure';
  const TYPE: string = 'charge';
  const STATUS: string = 'settled';
  const BUDGET_ID: number = 10000;

  const response = await request.post('/api/v1/transactions', {
    data: {
      amount: AMOUNT,
      description: DESCRIPTION,
      type: TYPE,
      status: STATUS,
      budgetId: BUDGET_ID
    }
  });

  expect.soft(response.status()).toEqual(404);

  const responseBody = await response.json();

  expect.soft(responseBody.errors).toContainEqual(
      {
          "code": "20",
          "message": `Could not find budget (id=${BUDGET_ID})`
      }
  );
});
