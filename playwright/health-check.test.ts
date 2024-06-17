import { test, expect } from '@playwright/test';

test.describe('no jwt', async () => {
  test.use({ extraHTTPHeaders: {} });

  test('health check', async ({ request }) => {
    const response = await request.get('/actuator/health');

    expect(response.ok()).toBeTruthy();
  });

  test('build info check', async ({ request }) => {
    const response = await request.get('/actuator/info');

    expect(response.ok()).toBeTruthy();
  });
});
