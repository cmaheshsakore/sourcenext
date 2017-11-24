package com.mobiroo.n.sourcenextcorporation.agent.item.ui.elements.dependancy;


import com.mobiroo.n.sourcenextcorporation.agent.item.ui.elements.AgentUIElement;

import java.util.ArrayList;
import java.util.List;

public class OrChildCheck implements ChildCheck {

    List<ConditionalElement> conditionals;
    List<AgentUIElement> consequences;

    public OrChildCheck() {
        conditionals = new ArrayList<ConditionalElement>();
        consequences = new ArrayList<AgentUIElement>();
    }

    @Override
    public void addConditional(ConditionalElement element) {
        conditionals.add(element);
        element.addChildCheck(this);
        onConditionalChanged(element);
    }

    @Override
    public void addConsequence(AgentUIElement element) {
        consequences.add(element);
    }

    @Override
    public void onConditionalChanged(ConditionalElement element) {
        if(element.getState()) {
            notifyEnabled();
            return;
        }

        for(ConditionalElement conditional : conditionals) {
            if(conditional.getState()) {
                notifyEnabled();
                return;
            }
        }

        notifyDisabled();
    }

    protected void notifyEnabled() {
        for(AgentUIElement element : consequences) {
            element.enable();
        }
    }

    protected void notifyDisabled() {
        for(AgentUIElement element : consequences) {
            element.disable();
        }
    }

}
