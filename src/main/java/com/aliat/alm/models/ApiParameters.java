package com.aliat.alm.models;

public class ApiParameters {
       
       private String firstName;
       private String middleName;
       
       public String getfirstName() {
              return firstName;
       }
       public void setfirstName(String firstName) {
              this.firstName = firstName;
       }
       public String getmiddleName() {
              return middleName;
       }
       public void setmiddleName(String middleName) {
              this.middleName = middleName;
       }
       public ApiParameters(String firstName, String middleName) {
              super();
              this.firstName = firstName;
              this.middleName = middleName;
       }
       public ApiParameters() {
              super();
              // TODO Auto-generated constructor stub
       }
       
       


}


 

