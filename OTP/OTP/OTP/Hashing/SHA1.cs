using System;
using System.Security.Cryptography;
using System.Web;

namespace OTP.Hashing
{
    public class SHA1 : IHasing
    {
        public byte[] Encode(byte[] buffer)
        {
            var sha1 = new SHA1CryptoServiceProvider();

            return sha1.ComputeHash(buffer);
        }
    }
}
