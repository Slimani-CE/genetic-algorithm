package com.mustaphaslimani.geneticalgorithmgui.sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SimpleContainer {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController mainAgent=null;
        for (int i=0;i< GAUtils.POPULATION_SIZE;i++){
            mainAgent = agentContainer.createNewAgent(String.valueOf(i), IndividualAgent.class.getName(), new Object[]{});
            mainAgent.start();
        }
        mainAgent = agentContainer.createNewAgent("mainAgent", MainAgentGA.class.getName(), new Object[]{});
        mainAgent.start();
    }
}
