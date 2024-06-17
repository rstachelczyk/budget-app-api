import { test as setup } from '@playwright/test';

// const userFile = 'playwright/.auth/user.json';
// const adminFile = 'playwright/.auth/admin.json';

setup('authenticate', async ({ request }) => {

  const response = await request.post('/api/v1/login', {
    data: {
      email: 'test@mail.com',
      password: 'password'
    }
  });


  const responseBody = await response.json();
  process.env.USER_JWT = responseBody.token;
//   await request.storageState({ path: userFile });
});
