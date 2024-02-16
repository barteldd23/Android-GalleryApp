package edu.ddb.galleryapp;

import java.util.List;
enum Type{
    Grass,
    Fire,
    Water
}
public class Pokemon
{
    public Pokemon(String name, String evolveName, int num, int evolveNum,
                    int infoFile, int img, int evolveImg, Type type){
        this.name = name;
        this.evolveName = evolveName;
        this.number = num;
        this.evolveNumber = evolveNum;
        this.infoFile = infoFile;
        this.img = img;
        this.evolveImg = evolveImg;
        this.type = type;
    }

    public String getName(){
        return name;
    }
    public String getEvolveName(){
        return evolveName;
    }
    public int getNumber(){
        return number;
    }
    public int getEvolveNumber(){
        return evolveNumber;
    }
    public int getInfoFile(){
        return infoFile;
    }
    public String getInfo(){
        return info;
    }
    public int getImg(){
        return img;
    }
    public int getEvolveImg(){
        return evolveImg;
    }
    public Type getType(){
        return type;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEvolveName(String name){
        this.evolveName = name;
    }
    public void setNumber(int num){
        this.number = num;
    }
    public void setEvolveNumber(int num){
        this.evolveNumber = num;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public void setImg(int img){
        this.img = img;
    }
    public void setEvolveImg(int img){
        this.img = img;
    }
    public void setType(Type type){
        this.type = type;
    }

    private String name;
    private String evolveName;
    private int number;
    private int evolveNumber;
    private int infoFile;
    private String info;
    private int img;
    private int evolveImg;
    private Type type;
}
