using Autofac;
using Nexmo.Api;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.Http;


namespace OTP.Controller
{
    public class UserController : ApiController
    {
        string hash = "xuanhoinguyen";
        string key = "Ef2q2r86XjqiCe92dvTKUjBgeIXS2oCiN4BbiRvj";

        [HttpGet]
        public List<User> GetAllUser()
        {
            UserDataDataContext context = new UserDataDataContext();
            List<User> listUser = context.Users.ToList();
            return listUser;
        }

        [HttpGet]
        public User GetAllUser(String token)
        {
            UserDataDataContext context = new UserDataDataContext();
            User user = context.Users.FirstOrDefault(x => x.TokenUser == token);
            return user;
        }

        [HttpPost]
        public String saveUser(String email, String password, int phone)
        {
            try
            {
                UserDataDataContext context = new UserDataDataContext();
                User mUser = context.Users.FirstOrDefault(x => x.email == email);
                if (mUser != null)
                {
                    return "Tài khoản đã có người sử dụng";
                }
                User user = new User();
                user.email = email;
                user.password = CreateToken(password,key);
                user.phone = phone;
                user.TokenUser = CreateTokenUser(email,password, key);
                context.Users.InsertOnSubmit(user);
                context.SubmitChanges();
                return "Đăng ký thành công";
            }
            catch (Exception e1) {
                return "Đăng ký thất bại";
            }

        }

        [HttpGet]
        public User checkUserNamePassword(String email, String pass)
        {
            try
            {
                UserDataDataContext context = new UserDataDataContext();
                User user = context.Users.FirstOrDefault(x => x.email == email && x.password == CreateToken(pass,key));
                if (user != null)
                {
                    return user;
                }
            }
            catch (Exception e) { }
            return null;
            } 
           
                    
    [HttpPost]
    public String sendSMS(String email,String phone,String sender,String token)
        {
            try
            {
                UserDataDataContext context = new UserDataDataContext();
                User user = context.Users.FirstOrDefault(x => x.TokenUser == token);
               
                if (user == null)
                {
                    return "User không hợp lệ";
                }
                
                CreateOTP create = new CreateOTP();
                String otp=create.getOTP(token);
                string message = "Your OTP Number is " + otp + " ( Sent By : XuanHoi )";
                SpeedSMSAPI speed = new SpeedSMSAPI("N4PkqqiO8UVGFtRKrZUCs5A2z5MIdPPA");
               String u= speed.sendSMS(phone, message, SpeedSMSAPI.TYPE_BRANDNAME_NOTIFY, sender);
                if (u != null)
                {
                    //return otp;
                    return CreateToken(otp.ToString(),key);
                }
            }
            catch (Exception e1)
            {
                return "Send OTP thất bại"+e1.ToString();
            }

            return "THAT BAI";

        }

        [HttpPost]
        public String sendSMSx(String phone,String token,String name)
        {
            try
            {
                Random random = new Random();
                int value = random.Next(100001, 999999);
                UserDataDataContext context = new UserDataDataContext();
                User user = context.Users.FirstOrDefault(x => x.TokenUser == token);
                if (user == null)
                {
                    return "User không hợp lệ";
                }
                string message = "Your OTP Number is " + value + " ( Sent By : XuanHoi )";
                String message1 = HttpUtility.UrlEncode(message);

                var creds = new Nexmo.Api.Request.Credentials
                {
                    ApiKey = "3816c0b5",
                    ApiSecret = "lVsuxxA611lXIm8x"

                };
                var results = SMS.Send(new SMS.SMSRequest
                 {
                    from = "Stealer",
                    to = "84"+phone,
                    text = message
                 }, creds);

                return CreateToken(value.ToString(),key);
            }
            catch (Exception e1) {
                return "Send OTP thất bại";
            }
           
        }

  
        private string CreateToken(string message, string secret)
        {
            
            byte[] secretKey = Encoding.ASCII.GetBytes(secret);
            HMACSHA256 hmac = new HMACSHA256(secretKey);
            hmac.Initialize();
            byte[] bytes = Encoding.ASCII.GetBytes(message);
            byte[] rawHmac = hmac.ComputeHash(bytes);
            return Convert.ToBase64String(rawHmac);
        }

        public string CreateTokenUser(string email,string pass,string seretKey)
        {
            Random random = new Random();
            int value = random.Next(100001, 999999);
            String token = email + pass + value;
            byte[] secretKey = Encoding.ASCII.GetBytes(seretKey);
            HMACSHA256 hmac = new HMACSHA256(secretKey);
            hmac.Initialize();
            byte[] bytes = Encoding.ASCII.GetBytes(token);
            byte[] rawHmac = hmac.ComputeHash(bytes);
            return ""+Convert.ToBase64String(rawHmac)+"";
        }
    }


}
