package com.example.ppe3_bts;

public class PraticienDataUtils {

    public static Praticien[] getPraticien()  {
        Praticien emp1 = new Praticien("Bobichon", "Tristan", " ", 1000);
        Praticien emp2 = new Praticien("Michael", "Garcia", " ", 50000);
        Praticien emp3 = new Praticien("Robert", "Johnson", " ", 2000);

        return new Praticien[] {emp1, emp2, emp3};
    }
}
