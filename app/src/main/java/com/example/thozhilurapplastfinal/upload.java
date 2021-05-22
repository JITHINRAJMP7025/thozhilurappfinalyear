package com.example.thozhilurapplastfinal;

public class upload {
  //  private String mname;
    private String mimageuri;

    public upload()
    {

    }
    //String name,
    public upload(String imageuri) {
      /*  if (name.trim().equals("")) {
            name = "No Name";
        }*/
       // mname = name;
        mimageuri = imageuri;
    }
  /*  public String getName()
    {
        return mname;
    }
    public void setName(String name){
        mname=name;
    }*/
    public String getimageuri()
    {
        return mimageuri;
    }
    public void setimageuri(String imageuri){
        mimageuri=imageuri;
    }
}
