using OTP.Hashing;
using OTP.TOTP;
using System;
using System.Text;
using System.Web;

namespace OTP
{
    public class UserCredentialService : IUserCredentialService
    {
        private readonly ITOTP _totp;
        private readonly IHasing _hasing;

        public UserCredentialService(ITOTP totp, IHasing hasing)
        {
            _totp = totp;
            _hasing = hasing;
        }

        /// <summary>
        /// Generate a one time used password base on user ID and input time
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="dateTime"></param>
        /// <returns></returns>
        public string GeneratePassword(String tokenUser, DateTime dateTime)
        {
            if (tokenUser == null)
                throw new ArgumentException("ID cannot be 0", "userId");

            byte[] userIdBytes = Encoding.ASCII.GetBytes(tokenUser);

            return _totp.GenerateOtp(_hasing.Encode(userIdBytes), dateTime);
        }
    }
}
