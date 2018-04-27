using System;
using System.Web;

namespace OTP
{
    public interface IUserCredentialService
    {
        string GeneratePassword(String token, DateTime dateTime);
    }
}
