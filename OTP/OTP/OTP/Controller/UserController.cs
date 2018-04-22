using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.Http;

namespace OTP.Controller
{
    public class UserController : ApiController
    {
        string hash = "xuanhoinguyen";

        [HttpGet]
        public List<User> GetAllUser()
        {
            UserDataDataContext context = new UserDataDataContext();
            List<User> listUser = context.Users.ToList();
            return listUser;
        }

        [HttpPost]
        public String saveUser(String email, String password, String phone)
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
                user.password = EnCryptor(password);
                user.phone = phone;
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
                User user = context.Users.FirstOrDefault(x => x.email == email && x.password == EnCryptor(pass));
                if (user != null)
                {
                    return user;
                }
            }
            catch (Exception e) { }
            return null;
            } 
           
                    
    [HttpPost]
    public String sendSMS(String phone)
        {
            try
            {
                Random random = new Random();
                int value = random.Next(1001, 9999);
                string message = "Your OTP Number is " + value + " ( Sent By : XuanHoi )";
                String message1 = HttpUtility.UrlEncode(message);

                using (var wb = new WebClient())
                {
                    byte[] response = wb.UploadValues("https://api.txtlocal.com/send/", new NameValueCollection()
                {
                {"apikey" , "xKlLfVuT0tM-d0EGvBHPaHjdDkxf7AD9Zfel8LaSRs"},
                {"numbers" , "84"+phone},
                {"message" , message1},
                {"sender" , "TXTLCL"}
                });
                    string result = System.Text.Encoding.UTF8.GetString(response);
                    // Session["otp"] = value;

                    return result;


                }
            }
            catch { }
            return null;
        }
            
           
            

        

         public String EnCryptor(String pass)
        {
            byte[] data = UTF8Encoding.UTF8.GetBytes(pass);
            using (MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider())
            {
                byte[] keys = md5.ComputeHash(UTF8Encoding.UTF8.GetBytes(hash));
                using (TripleDESCryptoServiceProvider trip = new TripleDESCryptoServiceProvider() { Key = keys, Mode = CipherMode.ECB, Padding = PaddingMode.PKCS7 })
                {
                    ICryptoTransform transfrom = trip.CreateEncryptor();
                    byte[] re = transfrom.TransformFinalBlock(data, 0, data.Length);
                    return Convert.ToBase64String(re, 0, re.Length);
                }
            }
        }



        public String DeCryptor(String encry)
        {

            byte[] data = Convert.FromBase64String(encry.ToString());
            using (MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider())
            {
                byte[] keys = md5.ComputeHash(UTF8Encoding.UTF8.GetBytes(hash));
                using (TripleDESCryptoServiceProvider trip = new TripleDESCryptoServiceProvider() { Key = keys, Mode = CipherMode.ECB, Padding = PaddingMode.PKCS7 })
                {
                    ICryptoTransform transfrom = trip.CreateDecryptor();
                    byte[] re = transfrom.TransformFinalBlock(data, 0, data.Length);
                    return UTF8Encoding.UTF8.GetString(re);

                }
            }
        }


    }

}
