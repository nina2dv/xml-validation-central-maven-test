package com.example.model;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Collections;

public class ModelAdapter extends XmlAdapter<Actor, Model> {
    @Override
    public Model unmarshal(Actor actor) throws Exception {
        Model model = new Model();
        model.setActors(Collections.singletonList(actor));
        // If needed, you can set a default Environment here.
        return model;
    }

    @Override
    public Actor marshal(Model model) throws Exception {
        if(model.getActors() != null && !model.getActors().isEmpty()){
            return model.getActors().get(0);
        }
        return null;
    }
}
