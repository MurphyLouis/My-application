package com.example.myapplication.Model;

public class Users
{
   private String name, password, phone, studentNo;

   public Users()
   {

   }

    public Users(String name, String password, String phone, String studentNo) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
}
