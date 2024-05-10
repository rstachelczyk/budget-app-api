import { test as setup } from '@playwright/test';

const userFile = 'playwright/.auth/user.json';
const adminFile = 'playwright/.auth/admin.json';

setup('authenticate', async ({ request }) => {

  const response = await request.post('/login', {
    form: {
      'user': 'test@mail.com',
      'password': 'password'
    }
  });

  const body = await response.json();
  process.env.USER_JWT = body.data.accessToken;
  await request.storageState({ path: userFile });
});
