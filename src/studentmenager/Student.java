/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmenager;

/**
 *
 * @author User
 */
public class Student {
    private int id;
    private String emri;
    private double nota;
    public Student(int id,String emri,double nota){
        this.emri=emri;
        this.id=id;
        this.nota=nota;
    }
    public int getId(){
        return id;
    }
    public String getEmri(){
        return emri;
    }
    public double getNota(){
        return nota;
    }
    @Override
    public String toString(){
       return id+" - "+emri+" - "+nota; 
    }
}
