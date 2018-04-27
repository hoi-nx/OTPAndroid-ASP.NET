using System;
using System.Web;

namespace OTP.Hashing
{
    public interface IHMAC
    {
        byte[] Encode(byte[] key, byte[] buffer);
    }
}
