import { LoginAndRegisterModule } from './login-and-register.module';

describe('LoginAndRegisterModule', () => {
  let loginAndRegisterModule: LoginAndRegisterModule;

  beforeEach(() => {
    loginAndRegisterModule = new LoginAndRegisterModule();
  });

  it('should create an instance', () => {
    expect(loginAndRegisterModule).toBeTruthy();
  });
});
