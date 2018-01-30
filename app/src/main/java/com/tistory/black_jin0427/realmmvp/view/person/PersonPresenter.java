package com.tistory.black_jin0427.realmmvp.view.person;

import com.tistory.black_jin0427.realmmvp.model.Person;

/**
 * Created by ifamily on 2018-01-30.
 */

public interface PersonPresenter {

    void onItemClick(Person person);

    void onItemLongCLick(Person person);

    void onAddPerson(String name, String gender, String age);

}
