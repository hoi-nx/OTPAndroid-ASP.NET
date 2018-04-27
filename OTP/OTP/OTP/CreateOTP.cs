using Autofac;
using OTP.Hashing;
using OTP.HOTP;
using OTP.TOTP;
using System;
using System.Web;

namespace OTP
{
    class CreateOTP
    {


        public CreateOTP()
        {

        }
        private static IContainer SetupContainer()
        {
            var builder = new ContainerBuilder();
            builder.RegisterType<UserCredentialService>().As<IUserCredentialService>();
            builder.RegisterType<HMACSHA1>().As<IHMAC>();
            builder.RegisterType<HOTP.HOTP>().As<IHOTP>();
            builder.RegisterType<TOTP.TOTP>().As<ITOTP>();
            builder.RegisterType<SHA1>().As<IHasing>();

            return builder.Build();
        }

        public String getOTP(String token)
        {
            IContainer container = SetupContainer();

            using (var scope = container.BeginLifetimeScope())
            {
                var service = scope.Resolve<IUserCredentialService>();
                string otp = service.GeneratePassword(token, DateTime.Now);

                return otp;
            }
        }

    }     
}
