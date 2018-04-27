using System;
using System.Web;

namespace OTP.Hashing
{
    public interface IHasing
    {
        byte[] Encode(byte[] buffer);
    }
}
