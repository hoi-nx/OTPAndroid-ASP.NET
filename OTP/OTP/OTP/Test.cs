
using System;

namespace OTP
{
    class Test
    {
        static void Main(string[] args)
        {
            CreateOTP create = new CreateOTP();
           string otp= create.getOTP("WCOKJtNvebNdhHJvLTgnPS9rXE9mQgqLDFcODFPI8=");
            Console.WriteLine("Generated password is hhhh: {0}", otp);
        }

      
    }
}
