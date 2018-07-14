package pe.tp1.hdpeta.jalame.Singleton;

import pe.tp1.hdpeta.jalame.Bean.PersonBean;

public class PersonSingleton {

    private static PersonSingleton ourInstance;
    private PersonBean person;

    public static PersonSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new PersonSingleton();
        }
        return ourInstance;
    }

    private PersonSingleton() {
    }
    public PersonBean getPersonBean(){
        return person;
    }
    public void setPersonBean(PersonBean personBean){
        this.person = personBean;
    }
}
